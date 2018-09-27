package com.xgame.logic.server.game.country.structs.build;

import java.util.HashMap;

import com.xgame.common.AwardConfList;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.awardcenter.AwardUtil;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;


/**
 *
 * 2016-7-15 10:55:02	
 *
 * @author ye.yuan
 *
 */
public class CountryBuildControl extends BuildControl{
	
	
	protected void createInit0(Player player,XBuild build) {
		CountryBuild countryBuild = (CountryBuild)build;
//		BuildAttributeObject buildAttributeObject = new BuildAttributeObject();
//		buildAttributeObject.sid = sid;
//		buildAttributeObject.uid = countryBuild.getUid();
//		player.getAttributeAppenderManager().addAttributeObject(AttributeNodeEnum.BUILD.ordinal(), build.getUid(), buildAttributeObject);
		player.roleInfo().getBaseCountry().getAllBuild().put(countryBuild.getUid(), countryBuild);
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	/**
	 * 创建完成
	 * @param player
	 * @param uid 建筑物唯一id
	 */
	public void createEnd(Player player,int uid) {
		CountryBuild build = getBuild(uid);
		//计算属性
//		player.getAttributeAppenderManager().rebuildObject(AttributeNodeEnum.BUILD.ordinal(), uid, true);
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		//发奖
		BuildingPir buildingPir = BuildingPirFactory.get(build.getSid());
		HashMap<Integer, AwardConfList> map = buildingPir.getExp();
		AwardUtil.awardToCenter(player, map.get(build.getLevel()), SystemEnum.COUNTRY.getId(), GameLogSource.BUILD_LEVEL_UP);
	}
	
	/**
	 * 升级后 的一些基础信息处理  比如建筑是障碍物  那么 该方法不重写  如果是 家园建筑  会被重写 因为要更新属性发奖 等等
	 * @param player
	 * @param build
	 */
	public void baseLevelUpHandle(Player player,XBuild build){
		//重构建筑属性
//		player.getAttributeAppenderManager().rebuildObject(AttributeNodeEnum.BUILD.ordinal(), build.getUid(), true);
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		//更新属性到客户端
//		player.getAttributeAppenderManager().send();
		//更新货币和战力到客户端
		CurrencyUtil.send(player);
		//发奖到奖励中心
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		HashMap<Integer, AwardConfList> map = buildingPir.getExp();
		AwardUtil.awardToCenter(player, map.get(build.getLevel()),SystemEnum.COUNTRY.getId(), GameLogSource.BUILD_LEVEL_UP);
		//录入数据库
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
}
