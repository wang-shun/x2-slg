package com.xgame.logic.server.game.chat.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.constant.ItemIdConstant;
import com.xgame.logic.server.game.chat.AllianceChatManager;
import com.xgame.logic.server.game.chat.ChatRoomManager;
import com.xgame.logic.server.game.chat.constant.ChatChannelType;
import com.xgame.logic.server.game.chat.constant.ChatConstant;
import com.xgame.logic.server.game.chat.constant.MessageType;
import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.chat.message.ReqSendChatMessage;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.notice.NoticeManager;
import com.xgame.logic.server.game.notice.constant.NoticeConstant;

/** 
 * @author messageGenerator
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqSendChatHandler extends PlayerCommand<ReqSendChatMessage>{

	@Autowired
	private ChatRoomManager chatRoomManager;
	@Autowired
	private NoticeManager noticeManager;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private AllianceChatManager allianceChatManager;
	
	@Override
    public void action() {
		ReqSendChatMessage msg = this.message;
		if(StringUtils.isBlank(this.message.content) || StringUtils.isBlank(this.message.content.trim())) {
			return;
		}
		
        if(this.message.content.length() > ChatConstant.CHAT_CONTENT_SIZE) {
        	Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE2.get(), String.valueOf(ChatConstant.CHAT_CONTENT_SIZE));
        	return;
        }
        
        if(msg.channel == ChatChannelType.PRIVATENESS.getType() && message.messageType == MessageType.COMMON.ordinal()) {
        	if(StringUtils.isEmpty(msg.target)) {
        		Language.ERRORCODE.send(player,ErrorCodeEnum.E1001_CHAT.CODE16.get());
        		return;
        	}
        	long id = Long.valueOf(msg.target);
        	if(id == player.getId()) {
        		Language.ERRORCODE.send(player,ErrorCodeEnum.E1001_CHAT.CODE1.get());
        		return;
        	}
        	// 目标对象不存在
        	CrossPlayer targetPlayer = this.crossPlayerManager.getCrossPlayer(id);
        	if(targetPlayer == null) {
        		Language.ERRORCODE.send(player,ErrorCodeEnum.E1001_CHAT.CODE3.get());
        		return;
        	}
        	// 发送私聊
        	player.getPlayerChatManager().sendPrivateChat(player, targetPlayer, msg.content, msg.channel, msg.source, msg.messageType);
        } else if(msg.channel == ChatChannelType.WORLD.getType() && msg.messageType != MessageType.PYTHON.ordinal()) {
        	InjectorUtil.getInjector().chatManager.sendWorldChat(player, msg.content, msg.messageType, msg.source);
        } else if(msg.channel == ChatChannelType.ALLIANCE.getType() && msg.messageType != MessageType.PYTHON.ordinal()) {
        	allianceChatManager.sendAllianceChat(player, msg.content, msg.messageType, msg.source);
        } else if(msg.channel == ChatChannelType.CHAT_ROOM.getType() && msg.messageType != MessageType.PYTHON.ordinal()) {
        	CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getId());
        	if(crossPlayer != null) {
        		chatRoomManager.sendChatRoom(crossPlayer, Long.valueOf(msg.target), msg.content, msg.channel, msg.source, msg.messageType);
        	}
        } else if(msg.messageType == MessageType.PYTHON.ordinal() && msg.channel == ChatChannelType.WORLD.getType()) {
    		// 扣除道具
    		//判断道具数量够不够
        	int playerNum = this.getPlayer().roleInfo().getPlayerBag().getItemNum(ItemIdConstant.TYPHON);
        	if(playerNum < 1){
        		Language.ERRORCODE.send(player,ErrorCodeEnum.E001_LOGIN.CODE12.get());
        		return;
        	}
    		
        	ItemKit.removeItemByTid(player, ItemIdConstant.TYPHON, 1, GameLogSource.CHAT);
        	
    		// 广播消息
    		ChatMessage chatMessage = new ChatMessage();
    		chatMessage.setContent(this.message.content);
    		chatMessage.setSendPlayerId(player.getRoleId());
    		chatMessage.setSendTime(System.currentTimeMillis());
    		chatMessage.setChannel(ChatChannelType.WORLD.getType());
    		chatMessage.setSource(msg.source);
    		chatMessage.setMessageType(this.message.messageType);
    		
    		// 广播消息
    		InjectorUtil.getInjector().chatManager.sendWorldChat(player, msg.content, msg.messageType, msg.source);
    		
    		// 发送世界公告
        	noticeManager.sendWorldNotice(NoticeConstant.PLAYER_TYPON, this.player.getName(), this.message.content);
        }
    }
}
