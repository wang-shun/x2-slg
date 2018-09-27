package com.xgame.logic.server.game.country.structs.build.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.awardcenter.entity.AwardDB;
import com.xgame.logic.server.game.awardcenter.message.ResGetAwardMessage;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.constant.ItemIdConstant;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.structs.build.CountryBuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;

/**
 *
 *2016-7-29  14:54:24
 *@author ye.yuan
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainBuildControl extends CountryBuildControl{

	public void levelUpAfter(Player player,int uid){
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		CountryBuild build = getBuild(uid);
		if (buildingPir != null && build != null) {
//			AttributeConfMap attr = buildingPir.getAttr();
//			player.getAttributeAppenderManager().rebuild(attr, build.getLevel(), AttributeAppenderEnum.BUILD.ordinal(), true);
			EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		}
		//根据等级判断是否需要删除新手传送道具
		if(build.getLevel() > 6){
			//背包中
			int num = player.roleInfo().getPlayerBag().getItemNum(ItemIdConstant.NEW_PLAYER_TRANSFER);
			if(num > 0){
				ItemKit.removeItemByTid(player, ItemIdConstant.NEW_PLAYER_TRANSFER, num, GameLogSource.BUILD_LEVEL_UP);
			}
			//领奖中心中
			List<Integer> indexs = new ArrayList<>();
			Map<Integer,AwardDB> map = player.roleInfo().getAwards().getAwardDBs();
			for(int index : map.keySet()){
				if(map.get(index).getAwardId() == ItemIdConstant.NEW_PLAYER_TRANSFER){
					indexs.add(index);
				}
			}
			boolean flag = false;
			for(int i : indexs){
				player.roleInfo().getAwards().getAwardDBs().remove(i);
				ResGetAwardMessage resGetAwardMessage = new ResGetAwardMessage();
				resGetAwardMessage.index = i;
				player.send(resGetAwardMessage);
				flag = true;
			}
			if(flag){
				InjectorUtil.getInjector().dbCacheService.update(player);
			}
		}
	}
	
	public void createEnd(Player player,int uid) {
		super.createEnd(player, uid);
		levelUpAfter(player, uid);
	}
}
