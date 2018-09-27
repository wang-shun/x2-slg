package com.xgame.logic.server.game.email.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.email.message.ReqDeleteEmailMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqDeleteEmailHandler extends PlayerCommand<ReqDeleteEmailMessage>{


	@Override
    public void action() {
		Set<Long> set  = new HashSet<>();
		set.addAll(message.idList);
		
		if(set == null || set.size() >=66535) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15);
			return;
		}
		
        player.getPlayerMailInfoManager().deleteUserEmail(set, player);
    }
}
