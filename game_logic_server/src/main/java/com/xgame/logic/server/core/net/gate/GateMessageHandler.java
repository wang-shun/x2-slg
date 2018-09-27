package com.xgame.logic.server.core.net.gate;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.message.MessageNode;
import com.message.MessageSystem;
import com.xgame.data.message.SessionClose;
import com.xgame.data.message.SessionNew;
import com.xgame.data.message.SessionRawMessage;
import com.xgame.framework.network.server.CommandProcessor;
import com.xgame.framework.network.server.ServerStateEnum;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.system.LogicServer;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.msglib.able.Communicationable;
import com.xgame.utils.IPUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;

/**
 * 网关接收消息
 * @author jacky.jiang
 *
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class GateMessageHandler extends ChannelInboundHandlerAdapter {

	@Autowired
	private CommandProcessor processor;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private CrossPlayerManager crossManager;
	
    /**
     * 接收网关消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       // 网管接受消息处理
		if (LogicServer.getState() != ServerStateEnum.RUNNING) {
			return;
		}

		// 消息处理
		try {
			if(msg instanceof SessionNew) {
				SessionNew sesionNew = (SessionNew)msg;
				Channel channel = ctx.channel();
				Attribute<Integer> attr = channel.attr(GateConnection.GATE_ID_KEY);
				onSessionNew(sesionNew, attr.get());
			} else if(msg instanceof SessionClose) {
				SessionClose sessionClose = (SessionClose)msg;
				onSessionClose(sessionClose.getSessionID());
			} else if(msg instanceof SessionRawMessage) {
				SessionRawMessage rawMessage = (SessionRawMessage)msg;
				PlayerSession playerSession = sessionManager.getSession(rawMessage.getSessionID());
				log.debug("接收消息底层1 getMsgID=" + rawMessage.getMsgID());
				if(playerSession != null) {
					onRawMessage(playerSession, rawMessage.getMsgID(), rawMessage.getCallbackId(), rawMessage.getBuffer());
				} else {
					log.error("session为空: sessionId:{}, msgeId:{}",rawMessage.getSessionID(), rawMessage.getMsgID());
					return;
				}
			}
		} catch (Exception e) {
			log.error("消息处理出错:", e);
			return;
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
        super.channelInactive(ctx);
    }
    
	/**
	 * 新建session消息
	 * @param sessionId
	 * @param ip
	 * @param port
	 */
	private void onSessionNew(SessionNew sessionNew, int gateId) {
		PlayerSession session = new PlayerSession(sessionNew.getSessionID(), gateId, IPUtil.longToIP(sessionNew.getIp()), sessionNew.getPort());
		PlayerSession oldSession = sessionManager.getSession(sessionNew.getSessionID());
		if (oldSession != null) {
			log.warn("Deprecated session id {}, old: {} new: {}", sessionNew.getSessionID(), oldSession.getAddress(), session.getAddress());
		}

		log.info("session connected {}, from address: {}", sessionNew.getSessionID(), session.getAddress());
		sessionManager.putSession(sessionNew.getSessionID(), session);
	}
    
    /**
     * 关闭session
     * @param sessionId
     */
    private void onSessionClose(long sessionId) {
    	PlayerSession playerSession = sessionManager.removeSessiion(sessionId);
    	if(playerSession != null) {
    		// 移除玩家身上session缓存
    		long playerId = playerSession.getPlayerId();
    		Player player = playerManager.get(playerId);
    		if(player != null) {
    			player.leave();
    			sessionManager.removePlayerSession(playerId);;
    		}
    	}
    }
    
    /**
     * 消息处理
     * @param session
     * @param messageId
     * @param callbackId
     * @param rawData
     */
    private void onRawMessage(PlayerSession session, int messageId, int callbackId, byte[] rawData) {
    	// prepare command
    	MessageNode<? extends Communicationable> node = MessageSystem.getMessageNode(messageId);
    	log.info("messageId:{}, node:{}", messageId, node);
    	if(node!=null){
    		PlayerCommand<?> command = node.gen(rawData);
			if (command != null) {
    			//设置回调客户端回调id
				command.getMessage().setCallbackId(callbackId);
                
				if (session.getPlayerId() > 0) {
					 if(session.isCross()) {
						 CrossPlayer crossPlayer = crossManager.getCrossPlayer(session.getPlayerId());
						 command.setCrossPlayer(crossPlayer);
					 } else {
						 Player player = playerManager.getPlayer(session.getPlayerId());
						 command.setPlayer(player); 
					 }
				 }
				
    			 command.setPlayerSession(session);
    			 
    			 /**
    			  * 处理类型  根据处理类型 来决定执行线程
    			  * 准确的说  可能不是所有消息都以固定的模式分发  所以 这里提供根据消息常量来决定该消息的分发模式  
    			  */
    			 switch (command.getMessage().getHandlerEnum()) {
    			 case MSG10:
    			 {
    				 command.execute();
    				 break;
    			 }
    			 default:
    				 if (command.getMessage().isSync()) {
    					 processor.runSync(command);
    				 } else {
    					 if(!session.isCross()) {
    						 long processId = session.getSessionID();
        					 Player player = playerManager.getPlayer(session.getPlayerId());
        					 if (Objects.nonNull(player) && Objects.nonNull(player.roleInfo())) {
        						 processId = player.getId();
        					 }
        					 
        					 // 执行handler
        					 processor.runAsync(processId, command);
    					 } else {
    						 // 非本服玩家,校验权限
    						 long processId = session.getSessionID();
    						 if(crossManager.checkCrossPermission(messageId)) {
    							 processor.runAsync(processId, command);
    						 } else {
    							 log.error(String.format("非法请求cmd:[%s], sessionId:[%s]", messageId, processId));
    						 }
    					 }
    				 }
    				 break;
    			 }
    		}
    	}
    }
}
