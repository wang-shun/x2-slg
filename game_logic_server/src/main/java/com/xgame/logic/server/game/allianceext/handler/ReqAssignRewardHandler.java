package com.xgame.logic.server.game.allianceext.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.allianceext.AllianceExtManager;
import com.xgame.logic.server.game.allianceext.message.ReqAssignRewardMessage;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqAssignRewardHandler extends PlayerCommand<ReqAssignRewardMessage>{
	
	@Autowired
	private AllianceExtManager allianceExtManager;

	@Override
    public void action() {
		allianceExtManager.assignAllianceFightReward(player, message.allianceId, message.itemId, message.num, message.playerId);
	}
}
