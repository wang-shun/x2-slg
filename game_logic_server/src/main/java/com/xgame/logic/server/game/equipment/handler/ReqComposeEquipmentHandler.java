package com.xgame.logic.server.game.equipment.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.equipment.message.ReqComposeEquipmentMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqComposeEquipmentHandler extends PlayerCommand<ReqComposeEquipmentMessage>{
    @Override
    public void action() {
        player.getEquipmentManager().composeEquipmentByFragmentAndEquipment(message.subComposeEquipments, message.materials, message.isImmediately, message.fragmentID, message.buildId);
    }
}
