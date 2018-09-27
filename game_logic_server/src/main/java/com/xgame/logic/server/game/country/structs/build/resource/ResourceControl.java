package com.xgame.logic.server.game.country.structs.build.resource;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.warehouse.bean.ResourcePro;
import com.xgame.logic.server.game.warehouse.message.ResResourceMessage;

/**
 * 资源仓库建筑 2016-12-28 18:12:41
 *
 * @author ye.yuan
 *
 */
public abstract class ResourceControl extends CountryBuildControl {


	public void chack(long num) {

	}

	/**
	 * 更新客户端
	 * 
	 * @param player
	 */
	protected void updataClient(Player player) {
		ResResourceMessage resResourceMessage = new ResResourceMessage();
		player.getWarehouseManager().fillPro(this, resResourceMessage);
		player.send(resResourceMessage);
	}

	/**
	 * 计算容量
	 * @return
	 */
	public long getCapacity() {
		long n = 0;
		// 每次改变重算 当前种类存量 以及保护量
		Iterator<XBuild> iterator = getBuildMap().values().iterator();
		while (iterator.hasNext()) {
			XBuild xBuild = iterator.next();
			BuildingPir buildingPir = BuildingPirFactory.get(sid);
			if (buildingPir != null) {
				Map<Integer, Integer> map = buildingPir.getV1();
				Integer c = map.get(xBuild.getLevel());
				if (c != null) {
					n += c;
				}
			}
		}

		return n;
	}
	
	/**
	 * 计算保护数量
	 * @param player
	 * @return
	 */
	public int getProtect(Player player) {
		int protect = 0;
		// 计算保护量 需要取属性来计算
		GlobalPir globalPir = GlobalPirFactory.get(GlobalConstant.RESOURCE_BAG_SAVE_RATE);
		Map<Integer, Double> map2 = globalPir.getValue();
		Double v = map2.get(getResourceId());
		if (v != null) {
			AttributesEnum attributesEnum = AttributeUtil.getResourceSaveRateAttr(getResourceId());
			double protectOdds = AttributeCounter.getPlayerAttribute(player.getId(), attributesEnum, v);
			protect = (int) (getCapacity() * protectOdds);
		}
		return protect;
	}
	
	
	/**
	 * 获取仓库可悲掠夺的资源数量（每个资源仓库对应保护比率）
	 * 例子：防守方玩家石油仓库容量上限为1000k，保护资源比例为：20%(200k)（资源的保护比例数值有资源仓库建筑物决定）
		（1）	玩家A拥有700K石油，存储在油罐中，则不受保护的石油为700k-200k=500k。
		（2）	玩家每次可以被掠夺的资源上限为产库容量的15%（见下文配置表），即每次最多可被掠夺150k的资源。
	 * @param num
	 * @param ratio 保护比率 global表配置
	 * @return 返回当前没有放入资源仓库的资源数量
	 */
	public long invasionResource(Player player, long num,  double ratio, Map<Integer, Long> resoruceMap) {
		// 计算该仓库总资源是否超过玩家资源上线
		long capacity = getCapacity();
		if(num > capacity) {
			ConcurrentSkipListMap<Integer, XBuild> map = getBuildMap();
			if(map != null) {
				BuildingPir buildingPir = BuildingPirFactory.get(sid);
				Map<Integer, Integer> configMap = buildingPir.getV1();
				Iterator<XBuild> iterator = getBuildMap().values().iterator();
				while (iterator.hasNext()) {
					XBuild xBuild = iterator.next();
					Integer c = configMap.get(xBuild.getLevel());
					
					// 当前仓库分配的额数量
					long protect  = getWareHourseProtect(player, c);
					long invasion = 0;
					if(c >  protect) {
						invasion = c - protect;
					}
					
					num = num - c;
					
					// 玩家可掠夺最大资源数量
					long invasionMax = Double.valueOf(c * ratio).longValue();
					
					//  当前仓库可掠夺资源上线
					resoruceMap.put(xBuild.getUid(), Math.max(0, Math.min(invasion, invasionMax)));
				}
			}
		// 当前数量小于最大数量	
		} else {
			BuildingPir buildingPir = BuildingPirFactory.get(sid);
			Map<Integer, Integer> configMap = buildingPir.getV1();
			Iterator<XBuild> iterator = getBuildMap().values().iterator();
			while (iterator.hasNext()) {
				XBuild xBuild = iterator.next();
				Integer c = configMap.get(xBuild.getLevel());
				
				// 当前仓库分配的额数量
				long warehourseNum = c * num / capacity;
				long protect  = getWareHourseProtect(player, c);
				long invasion = 0;
				if(warehourseNum >  protect) {
					invasion = warehourseNum - protect;
				}
				
				// 玩家可掠夺最大资源数量
				long invasionMax = Double.valueOf(c * ratio).longValue();
				
				//  当前仓库可掠夺资源上线
				resoruceMap.put(xBuild.getUid(), Math.max(0, Math.min(invasion, invasionMax)));
			}
		}

		return num;
	}
	
	/**
	 * 获取保护量
	 * @param capacity
	 * @return
	 */
	private long getWareHourseProtect(Player player, long capacity) {
		double protectOdds = PlayerAttributeManager.get().getWareHourseProtectRate(player.getId(), getResourceId());
		return (int) (capacity * protectOdds);
	}
	
	/**
	 * 计算资源数量
	 * 
	 * @param player
	 * @param uid
	 * @return
	 */
	public ResourcePro mathResourceNum(Player player, int uid) {
		long capacity = getCapacity();
		int protect = getProtect(player);
		long currency = CurrencyUtil.getCurrency(player , CurrencyEnum.values()[getResourceId()]);
		ResourcePro resourcePro = new ResourcePro();
		resourcePro.type = getResourceId();
		XBuild build = getBuild(uid);
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		if (buildingPir != null && currency>0 && capacity>0) {
			Map<Integer, Integer> map = buildingPir.getV1();
			Integer c = map.get(build.getLevel());
			if (c != null && c>0) {
				resourcePro.resource = Math.round(currency * c / capacity);
			}
		}
		resourcePro.protect = protect;
		return resourcePro;
	}

	/**
	 * 获得资源类型
	 * 
	 * @return
	 */
	public abstract int getResourceId();
}
