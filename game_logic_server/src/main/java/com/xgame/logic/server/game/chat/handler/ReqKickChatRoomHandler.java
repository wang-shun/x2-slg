package com.xgame.logic.server.game.chat.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.CrossUtils;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.message.ReqKickChatRoomMessage;
import com.xgame.logic.server.game.cross.CrossManager;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.rmi.RemoteService;

import lombok.extern.slf4j.Slf4j;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqKickChatRoomHandler extends PlayerCommand<ReqKickChatRoomMessage>{

	@Autowired
	private ChatRoomManager chatRoomManager;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private CrossManager crossManager;

	@Override
    public void action() {
		
		if(StringUtils.isBlank(message.roomKey)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE15.get());
			return;
		}
		
		int serverId = CrossUtils.getCrossServerByIncrId(message.roomKey);
		CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getId());
		if(crossPlayer == null) {
			log.error("跨服数据玩家不存在。");
			return;
		}
		
		if(serverId == InjectorUtil.getInjector().serverId) {
			chatRoomManager.kickPlayer(crossPlayer, Long.valueOf(message.roomKey), message.targetPlayerId);
		} else {
			RemoteService remoteService = crossManager.getRemoteService(serverId);
			if(remoteService != null) {
				remoteService.kickPlayer(crossPlayer, Long.valueOf(message.roomKey), message.targetPlayerId);
			}
		}
    }
}
