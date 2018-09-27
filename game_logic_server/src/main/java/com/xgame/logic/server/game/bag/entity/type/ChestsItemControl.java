package com.xgame.logic.server.game.bag.entity.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.xgame.common.CampV2;
import com.xgame.common.ItemConf;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.item.ArmyBoxControl;
import com.xgame.config.item.ItemBox;
import com.xgame.config.item.ItemBoxControl;
import com.xgame.config.item.PeiJianBoxControl;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.drop.DropService;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.bag.entity.Item;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.entity.Soldier;

/**
 * 重新实现宝箱使用逻辑 type=7
 * 返回宝箱开启道具列表给客户端
 * 服务端实现道具的添加 刷新
 * @author dingpeng.qu
 *
 */
public class ChestsItemControl extends ItemControl {
	

	/** 固定宝箱  */
	public static final int TYPE_GD = 1;
	/** 随机宝箱  */
	public static final int TYPE_SJ = 2;
	/** 兵种宝箱 */
	public static final int TYPE_BZBX =3;
	/** 充值活动宝箱 */
	public static final int TYPE_CZHD =4;
	/** 配件宝箱 */
	public static final int TYPE_PJ =5;
	
	public ChestsItemControl(int type) {
		super(type);
	}
	
	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param) {
		if(num < 1){
			return false;
		}
		
		ItemsPir configModel = ItemsPirFactory.get(itemId);
		int v1 = Integer.parseInt(configModel.getV1());
		if(v1 == TYPE_GD) {
			List<ItemConf> itemConfs = useFixedBox(player,num,configModel);
			player.send(ItemConverter.getBoxItems(itemConfs));
			return true;
		} else if(v1 == TYPE_SJ) {
			//批量使用随机宝箱必须每次都参与随机 不能像固定箱子一样直接*num
			List<ItemConf> itemConfs = useRandomBox(player,num,configModel);
			player.send(ItemConverter.getBoxItems(itemConfs));
			return true;
		} else if(v1 == TYPE_BZBX) {
			List<ItemConf> itemConfs = useSoldierBox(player,num,configModel);
			player.send(ItemConverter.getBoxItems(itemConfs));
			return true;
		} else if(v1 == TYPE_PJ){
			//批量使用配件宝箱
			List<ItemConf> itemConfs = usePeiJianBox(player,num,configModel);
			player.send(ItemConverter.getBoxItems(itemConfs));
			return true;
		}
		return false;
	}
	
	/**
	 * 使用固定（判定背包是否足够，游戏当中背包数量无限制）
	 * @param player
	 * @param num
	 * @param configModel
	 * @return
	 */
	private List<ItemConf> useFixedBox(Player player, int num,ItemsPir configModel) {
		List<ItemConf> itemConfs = new ArrayList<ItemConf>();
		Map<Integer,ItemConf> itemConfMap = new HashMap<Integer,ItemConf>();
		List<Item> itemList = new ArrayList<Item>();
		ItemBoxControl control = configModel.getV2();
		Iterator<ItemBox> iterator = control.getItemBoxs().iterator();
		while (iterator.hasNext()) {
			ItemBox itemBox = iterator.next();
			ItemContext rewardContext = ItemKit.addItem(player, itemBox.getTid(), itemBox.getNum() * num, SystemEnum.BOX, GameLogSource.USE_FIX_BOX);
			itemList.addAll(rewardContext.getFinalItemList());
			//进行重复性叠加
			if(null != itemConfMap.get(itemBox.getTid())){
				ItemConf itemConf = itemConfMap.get(itemBox.getTid());
				itemConf.setNum(itemConf.getNum()+itemBox.getNum() * num);
				itemConfMap.put(itemBox.getTid(), itemConf);
			}else{
				itemConfMap.put(itemBox.getTid(), new ItemConf(itemBox.getTid(), itemBox.getNum() * num));
			}
		}
		//返回道具列表
		player.send(ItemConverter.getMsgItems(itemList));
		
		itemConfs.addAll(itemConfMap.values());
		return itemConfs;
	}
	
	/**
	 * 使用随机道具
	 * 随机产生道具
	 * @param playerBagManager
	 */
	private List<ItemConf> useRandomBox(Player player, int num,ItemsPir configModel) {
		List<ItemConf> itemConfs = new ArrayList<ItemConf>();
		Map<Integer,ItemConf> itemConfMap = new HashMap<Integer,ItemConf>();
		List<Item> itemList = new ArrayList<>();
		ItemBoxControl control = configModel.getV2();
		for(int j=0;j<num;j++) {
			for(int i = 0;i<control.getCount();i++) {
				// 处理掉落
				ItemBox itemBox = DropService.getDrop(control.getItemBoxs());
				ItemContext rewardContext = ItemKit.addItem(player, itemBox.getTid(), itemBox.getNum(), SystemEnum.BOX,  GameLogSource.USE_FIX_BOX);
				itemList.addAll(rewardContext.getFinalItemList());

				//进行重复性叠加
				if(null != itemConfMap.get(itemBox.getTid())){
					ItemConf itemConf = itemConfMap.get(itemBox.getTid());
					itemConf.setNum(itemConf.getNum()+itemBox.getNum());
					itemConfMap.put(itemBox.getTid(), itemConf);
				}else{
					itemConfMap.put(itemBox.getTid(), new ItemConf(itemBox.getTid(), itemBox.getNum()));
				}
			}
		}
		//返回道具列表
		player.send(ItemConverter.getMsgItems(itemList));
		
		itemConfs.addAll(itemConfMap.values());
		return itemConfs;
	}
	
	/**
	 * 使用兵种宝箱
	 * 产出兵种
	 * 最高级的4个兵种中，随机抽取1种赠送100单位
	 * @param playerBagManager
	 */
	private List<ItemConf> useSoldierBox(Player player, int num,ItemsPir configModel) {
		List<ItemConf> itemConfs = new ArrayList<ItemConf>();
		Map<Integer,ItemConf> itemConfMap = new HashMap<Integer,ItemConf>();
		ArmyBoxControl control = configModel.getV2();
		//获取建筑兵种并倒序
		Map<Integer,CampV2> campV2Map = BuildingPirFactory.get(control.getBuildId()).getV2();
		Collection<CampV2> camps = campV2Map.values();
		List<CampV2> campsList = new ArrayList<CampV2>(camps);
		Collections.reverse(campsList);
		int soldierType = player.getSoldierManager().getSoldierTypeByBuildTid(control.getBuildId());
		for(int j=0;j<num;j++) {
			for(int i = 0;i<control.getCount();i++) {
				// 处理掉落
				ItemBox itemBox = DropService.getDrop(control.getDropList());
				//根据建筑 品质 id获取对应兵种id
				CampV2 campV2 = campsList.get(itemBox.getTid()-1);
				DesignMap designMap = player.getCustomWeaponManager().queryLastestDesignMap(player, soldierType, campV2.getT(), 0);
				if(designMap == null) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E120_SHOP.CODE6);
					return null;
				}
				
				Soldier soldier = player.getSoldierManager().getOrCreateSoldier(player, designMap.getId());
				soldier.updateSoldierByType(itemBox.getNum(), SoldierChangeType.COMMON);
				
				//进行重复性叠加
				if(null != itemConfMap.get(campsList.get(itemBox.getTid()-1).getGlobalId())){
					ItemConf itemConf = itemConfMap.get(campsList.get(itemBox.getTid()-1).getGlobalId());
					itemConf.setNum(itemConf.getNum()+itemBox.getNum());
					itemConfMap.put(campsList.get(itemBox.getTid()-1).getGlobalId(), itemConf);
				}else{
					itemConfMap.put(campsList.get(itemBox.getTid()-1).getGlobalId(), new ItemConf(campsList.get(itemBox.getTid()-1).getGlobalId(), itemBox.getNum()));
				}
			}
		}
		//发送兵种更新信息
		player.getSoldierManager().send(player);
		InjectorUtil.getInjector().dbCacheService.update(player);
		itemConfs.addAll(itemConfMap.values());
		return itemConfs;
	}
	
	/**
	 * 使用配件宝箱
	 * @param playerBagManager
	 */
	private List<ItemConf> usePeiJianBox(Player player, int num,ItemsPir configModel) {
		List<ItemConf> itemConfs = new ArrayList<ItemConf>();
		List<Item> itemList = new ArrayList<Item>();
		Map<Integer,ItemConf> itemConfMap = new HashMap<Integer,ItemConf>();
		
		PeiJianBoxControl control = (PeiJianBoxControl)configModel.getV2();
		//已解锁配件ID
		Set<Integer> unlockIds = player.roleInfo().getSoldierData().getUnlockPeijians().keySet();
		//宝箱配置配件组ID
		List<Integer> peiJianIds = control.getPeiJianList();
		//根据品质将配件组ID转化为配件ID
		List<Integer> ids = new ArrayList<Integer>();
		for(int peiJianId : peiJianIds){
			PeiJianPir pjp = PeiJianPirFactory.getInstance().getPeiJianPirByType6AndQuality(peiJianId, control.getLevel());
			if(null != pjp){
				ids.add(pjp.getId());
			}
		}
		ids.removeAll(unlockIds);
		//存在未解锁配件则产出配件  否则产出道具
		Random random = new Random();
		for(int i=0;i<num;i++){
			if(null != ids && ids.size()>0){
				int id = ids.get(random.nextInt(ids.size()));
				player.getCustomWeaponManager().unlockPeijian(player, id, GameLogSource.USE_PEIJIAN_BOX);
				
				//进行重复性叠加
				if(null != itemConfMap.get(id)){
					ItemConf itemConf = itemConfMap.get(id);
					itemConf.setNum(itemConf.getNum()+1);
					itemConfMap.put(id, itemConf);
				}else{
					itemConfMap.put(id, new ItemConf(id, 1));
				}
				ids.remove(Integer.valueOf(id));
			}else{
				int dropNum = random.nextInt(control.getItemMax())%(control.getItemMax()-control.getItemMin()+1)+control.getItemMin();
				ItemContext rewardContext = ItemKit.addItem(player, control.getItemId(), dropNum, SystemEnum.BOX,  GameLogSource.USE_PEIJIAN_BOX);
				itemList.addAll(rewardContext.getFinalItemList());
				//进行重复性叠加
				if(null != itemConfMap.get(control.getItemId())){
					ItemConf itemConf = itemConfMap.get(control.getItemId());
					itemConf.setNum(itemConf.getNum()+dropNum);
					itemConfMap.put(control.getItemId(), itemConf);
				}else{
					itemConfMap.put(control.getItemId(), new ItemConf(control.getItemId(), dropNum));
				}
			}
		}
		//返回道具列表
		if(itemList != null && itemList.size()>0){
			player.send(ItemConverter.getMsgItems(itemList));
		}
		itemConfs.addAll(itemConfMap.values());
		return itemConfs;
	}
}
