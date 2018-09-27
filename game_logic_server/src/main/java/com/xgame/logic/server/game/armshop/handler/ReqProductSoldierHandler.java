package com.xgame.logic.server.game.armshop.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.armshop.ArmyShopManager;
import com.xgame.logic.server.game.armshop.message.ReqProductSoldierMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqProductSoldierHandler extends PlayerCommand<ReqProductSoldierMessage>{

	@Autowired
	private ArmyShopManager armyShopManager; 

	@Override
    public void action() {
		armyShopManager.output(player, message.buildUid, message.soldier.soldierId, message.soldier.num, message.useType);
    }
}
