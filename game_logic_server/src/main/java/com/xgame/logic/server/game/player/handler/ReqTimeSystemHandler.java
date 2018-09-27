package com.xgame.logic.server.game.player.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.player.message.ReqTimeSystemMessage;
import com.xgame.logic.server.game.player.message.ResTimeSystemMessage;

/** 
 * 客户端服务器心跳
 * @author messageGenerator
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqTimeSystemHandler extends PlayerCommand<ReqTimeSystemMessage> {

	@Override
	protected void action() {
		ResTimeSystemMessage tm = new ResTimeSystemMessage();
		tm.serverTime1970s = System.currentTimeMillis();
		// 发送心跳包
		player.send(tm);
	}

}
