package com.xgame.logic.server.game.world.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.game.world.ServerGroupManager;
import com.xgame.logic.server.game.world.bean.ServerInfoBean;
import com.xgame.logic.server.game.world.converter.ServerGroupConverter;
import com.xgame.logic.server.game.world.entity.ServerGroup;
import com.xgame.logic.server.game.world.message.ReqServerInfoMessage;
import com.xgame.logic.server.game.world.message.ResServerInfoMessage;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqServerInfoHandler extends PlayerCommand<ReqServerInfoMessage>{

	@Autowired
	private ServerGroupManager serverGroupManager;
	
	@Override
    public void action() {
		List<ServerGroup> serverGroups = serverGroupManager.getServerGroups();
		
		List<ServerInfoBean> result = new ArrayList<ServerInfoBean>();
		for(ServerGroup serverGroup : serverGroups) {
			result.add(ServerGroupConverter.converterServerGroup(serverGroup));
		}
		
		ResServerInfoMessage resServerInfoMessage = new ResServerInfoMessage();
		resServerInfoMessage.serverInfoList = result;
		
		player.send(resServerInfoMessage);
	}
}
