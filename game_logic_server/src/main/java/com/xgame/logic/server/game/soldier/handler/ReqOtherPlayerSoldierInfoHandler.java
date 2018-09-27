package com.xgame.logic.server.game.soldier.handler;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteProBean;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.message.ReqOtherPlayerSoldierInfoMessage;
import com.xgame.logic.server.game.soldier.message.ResOtherPlayerSoldierInfoMessage;

/**
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqOtherPlayerSoldierInfoHandler extends PlayerCallbackCommand<ReqOtherPlayerSoldierInfoMessage> {

	@Override
	public ResOtherPlayerSoldierInfoMessage callback() {
		
		
		// 这里只有图纸信息，没有兵信息
		ResOtherPlayerSoldierInfoMessage otherPlayerSoldierInfoMessage = new ResOtherPlayerSoldierInfoMessage();
		Player otherPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, message.playerId);
		FullSoldierBean fullSoldierBean = new FullSoldierBean();
		DesignMap designMap = otherPlayer.getCustomWeaponManager().queryDesignMapById(otherPlayer, message.soldierId);
		if(designMap != null) {
			Map<AttributesEnum,Double> attrMap = AttributeCounter.getSoldierAttribute(otherPlayer.getId(), message.soldierId);
			for(Entry<AttributesEnum,Double> entry : attrMap.entrySet()){
				AttrbuteProBean libraryBean = new AttrbuteProBean();
				libraryBean.attributeId = entry.getKey().getId();;
				libraryBean.value = entry.getValue().intValue();
				fullSoldierBean.attrList.add(libraryBean);
			}
			otherPlayerSoldierInfoMessage.soldier = fullSoldierBean;
			fullSoldierBean.designMap = CustomWeaponConverter.converterDesignMapBean(designMap);
		}
		
//		if (otherPlayer != null) {
//			Soldier soldier = otherPlayer.getSoldierManager().getSoldier(otherPlayer, message.soldierId);
//			if (soldier != null) {
//				FullSoldierBean fullSoldierBean = new FullSoldierBean();
//				SoldierBean soldierBean = new SoldierBean();
//				soldierBean.soldierId = soldier.getSoldierId();
//				soldierBean.num = soldier.getNum();
//				fullSoldierBean.soldier = soldierBean;
//				
//				DesignMap designMap = otherPlayer.getCustomWeaponManager().queryDesignMapById(otherPlayer, soldier.getSoldierId());
//				if(designMap != null) {
//					Map<AttributesEnum,Double> attrMap = AttributeCounter.getSoldierAttribute(otherPlayer.getId(), soldier.getSoldierId());
//					for(Entry<AttributesEnum,Double> entry : attrMap.entrySet()){
//						AttrbuteProBean libraryBean = new AttrbuteProBean();
//						libraryBean.attributeId = entry.getKey().getId();;
//						libraryBean.value = entry.getValue().intValue();
//						fullSoldierBean.attrList.add(libraryBean);
//					}
//					otherPlayerSoldierInfoMessage.soldier = fullSoldierBean;
//					fullSoldierBean.designMap = CustomWeaponConverter.converterDesignMapBean(designMap);
//				}
//			}
//		}
		return otherPlayerSoldierInfoMessage;
	}
}
