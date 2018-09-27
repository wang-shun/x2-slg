package com.xgame.logic.server.game.email.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.email.PlayerMailInfoManager;
import com.xgame.logic.server.game.email.message.ReqSaveEmailMessage;

/** 
 * 保存邮件
 * @author zehong.he
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSaveEmailHandler extends PlayerCommand<ReqSaveEmailMessage>{


	@Autowired
	PlayerMailInfoManager playerMailInfoManager;
	
	@Override
    public void action() {
		playerMailInfoManager.saveEmail(player, message.emailId, message.isSave);
    }
}
