package com.xgame.logic.server.game.email.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.email.message.ReqNewEmailFlagMessage;

/** 
 * 请求新邮件标识
 * @author zehong.he
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqNewEmailFlagHandler extends PlayerCommand<ReqNewEmailFlagMessage>{


	@Override
    public void action() {
        player.getPlayerMailInfoManager().pushNewEmailFlag(player);
    }
}
