package com.xgame.logic.server.game.world.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.cross.CrossManager;
import com.xgame.logic.server.game.cross.rmi.RemoteService;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.message.ReqSpriteInfoMessage;
import com.xgame.logic.server.game.world.message.ResSpriteInfoMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSpriteInfoHandler extends PlayerCommand<ReqSpriteInfoMessage>{

	@Autowired
	private CrossManager crossManager;
	
	@Override
    public void action() {
        //判断坐标范围的合法性
        if(message.viewCenter.x <0 || message.viewCenter.x > 512 || message.viewCenter.y < 0 || message.viewCenter.y > 512) {
        	return;
        }
        
        if(message.serverId > 0) {
        	SpriteBean spriteBean = player.getWorldLogicManager().getSpriteInfo(message.viewCenter.x, message.viewCenter.y);
      
    		// 返回消息
			ResSpriteInfoMessage resViewSpriteMessage = new ResSpriteInfoMessage();
			resViewSpriteMessage.spriteBean = spriteBean;
			player.send(resViewSpriteMessage);
        } else {
        	RemoteService remoteService = crossManager.getRemoteService(message.serverId);
        	SpriteBean spriteBean = remoteService.getSpriteInfo(message.viewCenter.x, message.viewCenter.y);
        	
        	// 返回消息
			ResSpriteInfoMessage resViewSpriteMessage = new ResSpriteInfoMessage();
			resViewSpriteMessage.spriteBean = spriteBean;
			resViewSpriteMessage.serverId = message.serverId;
			player.send(resViewSpriteMessage);
        }
        
	}
}
