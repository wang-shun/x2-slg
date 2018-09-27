package com.xgame.logic.server.game.player.handler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCallbackCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.message.ReqCheckPlayerNameMessage;
import com.xgame.logic.server.game.player.message.ResCheckPlayerNameMessage;
import com.xgame.msglib.XMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqCheckPlayerNameHandler extends PlayerCallbackCommand<ReqCheckPlayerNameMessage>{


	@Override
	protected XMessage callback() {
		String utf = null;
        ResCheckPlayerNameMessage checkPlayerNameMessage = new ResCheckPlayerNameMessage();
        //如果有playerId 优先使用  回复邮件和发送栏再次发送  直接发playerId来
        if(message.playerId!=0){
        	Player player2 = InjectorUtil.getInjector().playerManager.getPlayer(message.playerId);
        	if(player2!=null){
        		utf = player2.getName();
        	}
        //如果发玩家名字来 一律视为给非 回复邮件
        }else if(InjectorUtil.getInjector().playerManager.checkPlayerNameExist(message.playerName)){
        	utf = message.playerName;
        }
        checkPlayerNameMessage.playerName = utf;
		return checkPlayerNameMessage;
	}
}
