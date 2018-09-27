package com.xgame.logic.server.game.alliance.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.alliance.message.ReqExchangeAllianceShopItemMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqExchangeAllianceShopItemHandler extends PlayerCommand<ReqExchangeAllianceShopItemMessage>{


	@Override
    public void action() {
    }
}
