package com.xgame.logic.server.game.playerattribute.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.message.ReqAttrbutesAddMessage;

/**
 * 请求属性加成列表
 * @author zehong.he
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqAttrbutesAddHandler extends PlayerCommand<ReqAttrbutesAddMessage>{



	@Autowired
	private PlayerAttributeManager playerAttributeManager;
	
	@Override
    public void action() {
		playerAttributeManager.queryAttrbutesAdd(player);
    }
}
