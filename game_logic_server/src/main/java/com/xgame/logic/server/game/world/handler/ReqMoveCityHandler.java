package com.xgame.logic.server.game.world.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.message.ReqMoveCityMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqMoveCityHandler extends PlayerCommand<ReqMoveCityMessage>{

	@Override
    public void action() {
		// 迁城
		player.getWorldLogicManager().moveFixCity(player, this.message.viewCenter.x, this.message.viewCenter.y,this.message.type);
    }
}
