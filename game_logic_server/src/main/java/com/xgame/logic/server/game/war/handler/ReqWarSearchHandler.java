package com.xgame.logic.server.game.war.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.message.ReqWarSearchMessage;
import com.xgame.logic.server.game.war.message.ResWarSearchMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqWarSearchHandler extends PlayerCommand<ReqWarSearchMessage>{

	@Autowired
	private WarManager battleManager;
	
	@Override
    public void action() {
		ResWarSearchMessage resWarSearchMessage = new ResWarSearchMessage();
		resWarSearchMessage.defenData = battleManager.search(player);
		player.send(resWarSearchMessage);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E500_WAR.CODE1);
	}
}
