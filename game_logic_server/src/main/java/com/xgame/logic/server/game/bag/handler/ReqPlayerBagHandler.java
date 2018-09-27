package com.xgame.logic.server.game.bag.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.bag.message.ReqPlayerBagMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqPlayerBagHandler extends PlayerCommand<ReqPlayerBagMessage>{
    @Override
    public void action() {
    	player.send(ItemConverter.getMsgPlayerBag(player));
    }
}
