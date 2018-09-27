package com.xgame.logic.server.game.world.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.message.ReqUseTroopSpeedUpPropMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqUseTroopSpeedUpPropHandler extends PlayerCommand<ReqUseTroopSpeedUpPropMessage>{
	
	@Override
    public void action() {
		player.getWorldLogicManager().speedUp(player, message.propId, message.spriteUid);
	}
}
