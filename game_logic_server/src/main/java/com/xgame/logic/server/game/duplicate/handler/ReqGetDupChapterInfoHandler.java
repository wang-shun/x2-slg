package com.xgame.logic.server.game.duplicate.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.duplicate.message.ReqGetDupChapterInfoMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqGetDupChapterInfoHandler extends PlayerCommand<ReqGetDupChapterInfoMessage>{


	@Override
    public void action() {
    }
}
