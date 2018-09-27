package com.xgame.logic.server.game.player.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.converter.SimplePlayerConverter;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.player.message.ReqSimplePlayerMessage;
import com.xgame.logic.server.game.player.message.ResSimplePlayerMessage;

/** 
 * @author messageGenerator
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSimplePlayerHandler extends PlayerCommand<ReqSimplePlayerMessage>{

	@Autowired
	private CrossPlayerManager simpleRoleInfoManager;
	
	@Override
    public void action() {
        ReqSimplePlayerMessage msg = this.message;
        List<Long> playerIds = msg.playerIds;
        ResSimplePlayerMessage resSimplePlayerMessage = new ResSimplePlayerMessage();
        if(playerIds == null || playerIds.isEmpty()) {
        	return;
        }
        
        List<SimpleRoleInfo> simpleRoleInfos = simpleRoleInfoManager.getSimpleRoleInfos(playerIds);
        if(simpleRoleInfos != null) {
        	for(SimpleRoleInfo simpleRoleInfo : simpleRoleInfos) {
        		resSimplePlayerMessage.players.add(SimplePlayerConverter.converterSimplePlayer(simpleRoleInfo));
        	}
        }
        
        if (player != null) {
        	player.send(resSimplePlayerMessage);
        }
        
    }
}
