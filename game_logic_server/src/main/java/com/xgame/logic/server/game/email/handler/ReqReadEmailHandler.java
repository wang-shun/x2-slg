package com.xgame.logic.server.game.email.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.email.message.ReqReadEmailMessage;

/**
 * 读取邮件（把是否已读设置为true）
 * @author zehong.he
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqReadEmailHandler extends PlayerCommand<ReqReadEmailMessage>{


	@Override
    public void action() {
		player.getPlayerMailInfoManager().readEamil(player, message.idList);
    }
}
