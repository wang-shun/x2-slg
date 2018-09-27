package com.xgame.logic.server.game.repair.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.repair.message.ReqRepairSoldierMessage;


/**
 * 请求修理士兵
 * @author jacky.jiang
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqRepairHandler extends PlayerCommand<ReqRepairSoldierMessage>{
	
	@Override
    public void action() {
		player.getRepairManager().mod(player, message.soldierList, message.useType);
	}
}
