package com.xgame.logic.server.game.world.handler;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.logic.server.game.world.message.ReqUserTroopListMessage;
import com.xgame.logic.server.game.world.message.ResUserTroopListMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqUserTroopListHandler extends PlayerCommand<ReqUserTroopListMessage>{

	@Override
    public void action() {
		List<VectorInfo> list = player.getWorldLogicManager().getPlayerMarch(player);
		ResUserTroopListMessage resUserTroopListMessage = new ResUserTroopListMessage();
		resUserTroopListMessage.vectorInfo = list;
		player.send(resUserTroopListMessage);
    }
}
