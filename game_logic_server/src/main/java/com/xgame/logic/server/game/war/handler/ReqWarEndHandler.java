package com.xgame.logic.server.game.war.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.message.ReqWarEndMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqWarEndHandler extends PlayerCommand<ReqWarEndMessage>{

	@Autowired
	private WarManager warManager;

	@Override
    public void action() {
		// TODO 判断战斗结束
		Battle battle = InjectorUtil.getInjector().battleManager.getBattle(message.battleId);
		if(battle != null) {
			battle.getWarHandler().fight(battle);
		}
    }
}
