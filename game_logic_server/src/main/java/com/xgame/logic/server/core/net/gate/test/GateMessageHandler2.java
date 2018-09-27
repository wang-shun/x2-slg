package com.xgame.logic.server.core.net.gate.test;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.message.MessageNode;
import com.message.MessageSystem;
import com.xgame.data.message.G2SProtocol;
import com.xgame.framework.network.server.CommandProcessor;
import com.xgame.framework.network.server.ServerStateEnum;
import com.xgame.logic.server.core.net.gate.GateConnection;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.system.LogicServer;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.msglib.able.Communicationable;
import com.xgame.utils.IPUtil;

import io.netty.buffer.ByteBuf;
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
public class GateMessageHandler2 extends ChannelInboundHandlerAdapter {

	@Autowired
	private CommandProcessor processor;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private PlayerManager playerManager;
	
    /**
     * 接收网关消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       // 网管接受消息处理
    	ByteBuf rawData = (ByteBuf) msg;
		if (LogicServer.getState() != ServerStateEnum.RUNNING) {
			return;
		}

		int messageId = rawData.readInt();
		try {
			int callbackId = rawData.readInt(); // TODO: try optimize this to
			long sessionId = rawData.readLong();
			switch (messageId) {
				case G2SProtocol.SESSION_NEW:
					long ip = rawData.readLong();
					int port = rawData.readInt();
					Attribute<Integer> attr = ctx.attr(GateConnection.GATE_ID_KEY);
					onSessionNew(sessionId, attr.get(), ip, port);
					break;
				case G2SProtocol.SESSION_CLOSED:
					onSessionClose(sessionId);
					break;
				case G2SProtocol.SEND_TO_SERVER:
					PlayerSession session = sessionManager.getSession(messageId);
					if(session != null)
						onRawMessage(session, messageId, callbackId, rawData);
					break;
				default:
					break;
			}
			

		} catch (Exception e) {
			log.error(String.format("消息处理出错 :%s", messageId), e);
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
	private void onSessionNew(long sessionId, int gateId, long ip, int port) {
		PlayerSession session = new PlayerSession(sessionId, gateId, IPUtil.longToIP(ip), port);
		PlayerSession oldSession = sessionManager.getSession(sessionId);
		if (oldSession != null) {
			log.warn("Deprecated session id {}, old: {} new: {}", sessionId, oldSession.getAddress(), session.getAddress());
		}

		log.info("session connected {}, from address: {}", sessionId,session.getAddress());
		sessionManager.putSession(sessionId, session);
	}
    
	/**
	 * 关闭session
	 * @param sessionId
	 */
	private void onSessionClose(long sessionId) {
		sessionManager.removeSessiion(sessionId);
//		if (playerSession != null) {
//			long playerId = playerSession.getPlayerId();
//			if (playerId > 0) {
//				playerManager.removePlayer(playerSession.getPlayerId());
//			}
//		}
	}
    
    private void onRawMessage(PlayerSession session, int messageId, int callbackId, ByteBuf rawData) {
    	// prepare command
    	MessageNode<? extends Communicationable> node = MessageSystem.getMessageNode(messageId);
    	if(node!=null){
    		PlayerCommand<?> command = node.gen(rawData);
    		if(command!=null) {
    			//设置回调id i think 是用来做客户端消息阻塞回执
                command.getMessage().setCallbackId(callbackId);
    			/*
    			 * 命令枚举   保留原结构的产物
    			 */
    			 switch (command.getMessage().getCommandEnum()) {
    			 case PLAYERMSG:
    			 {
    				 if (session.getPlayerId() > 0) {
    					 Player player = playerManager.getPlayer(session.getPlayerId());
    					 command.setPlayer(player);
    				 }
    				 break;
    			 }
    			 default:
    				 break;
    			 }
    			 command.setPlayerSession(session);
    			 /*
    			  *处理类型  根据处理类型 来决定执行线程
    			  *准确的说  可能不是所有消息都以固定的模式分发  所以 这里提供根据消息常量来决定该消息的分发模式  
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
    					 long proccessId = session.getSessionID();
    					 Player player = playerManager.getPlayer(session.getPlayerId());
    					 if (Objects.nonNull(player) && Objects.nonNull(player.roleInfo())) {
    						 proccessId = player.getId();
    					 }
    					 
    					 processor.runAsync(proccessId, command);
    				 }
    				 break;
    			 }
    		}
    	}
    }
}
