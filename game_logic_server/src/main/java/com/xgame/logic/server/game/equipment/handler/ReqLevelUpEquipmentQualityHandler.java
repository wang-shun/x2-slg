package com.xgame.logic.server.game.equipment.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.equipment.message.ReqLevelUpEquipmentQualityMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqLevelUpEquipmentQualityHandler extends PlayerCommand<ReqLevelUpEquipmentQualityMessage>{
    @Override
    public void action() {
        player.getEquipmentManager().composeEquipment(message.idList, message.equipmentID);
    }
}
