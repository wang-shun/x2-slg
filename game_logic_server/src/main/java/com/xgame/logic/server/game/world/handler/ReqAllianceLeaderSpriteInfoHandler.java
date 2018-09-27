package com.xgame.logic.server.game.world.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.message.ReqAddCollectMessage;

/**
 * 请求军团长周围
 * @author jacky.jiang
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqAllianceLeaderSpriteInfoHandler extends PlayerCommand<ReqAddCollectMessage> {
	
	@Override
    public void action() {
		
	}
}
