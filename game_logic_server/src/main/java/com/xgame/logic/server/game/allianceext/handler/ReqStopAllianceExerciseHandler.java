package com.xgame.logic.server.game.allianceext.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.allianceext.AllianceExtManager;
import com.xgame.logic.server.game.allianceext.message.ReqStopAllianceExerciseMessage;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqStopAllianceExerciseHandler extends PlayerCommand<ReqStopAllianceExerciseMessage>{
	
	@Autowired
	private AllianceExtManager allianceExtManager;

	@Override
    public void action() {
		allianceExtManager.stopAllianceExercise(player, message.allianceId);
	}
}
