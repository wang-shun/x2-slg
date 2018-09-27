package com.xgame.logic.server.game.copy.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.copy.CopyManager;
import com.xgame.logic.server.game.copy.message.ReqCopyRaidMessage;

/**
 * 副本扫荡
 * @author zehong.he
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqCopyRaidHandler extends PlayerCommand<ReqCopyRaidMessage>{


	@Autowired
	private CopyManager copyManager;
	
	@Override
    public void action() {
		copyManager.raid(player,message.copyId,message.copyPointId);
    }
}
