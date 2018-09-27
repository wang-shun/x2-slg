package com.xgame.logic.server.game.modify.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.modify.message.ReqReformSoldierMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqReformSoldierHandler extends PlayerCommand<ReqReformSoldierMessage>{

	@Override
    public void action() {
		player.getModifyManager().reformArmor(player, message.id, message.num);
    }
}
