package com.xgame.logic.server.core.net.gate;

import java.util.Collection;

import org.springframework.beans.factory.ObjectFactory;

import com.google.common.collect.Lists;
import com.xgame.data.message.BroadcastMsg;
import com.xgame.data.message.ClientMsg;
import com.xgame.data.message.SessionShutDown;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;


/**
 * 网关连接管理类
 * @author jacky.jiang
 *
 */
public class GateServer {
	
	private ObjectFactory<GateMessageHandler> handlerFactory;
	
	// 重连时间间隔
	public static final int CONNECTING_PERIOD = 30 * 1000;
	
    private EventLoopGroup eventLoop = new NioEventLoopGroup();
    
    // 连接池
    private GateConnection[] connections;
    private int poolCount;
    
    
    public GateServer(ObjectFactory<GateMessageHandler> handlerFactory) {
    	this.handlerFactory = handlerFactory;
    }

    /**
     * 初始化gateServer
     * @param host
     * @param port
     * @param gateId
     * @param poolCount 连接池数量
     */
    public void start(String host, int port, int gateId, int poolCount) {
        this.poolCount = poolCount;
        initGateConnection(host, port, gateId, handlerFactory);
        run();
    }

    public void shutDown() {
        for (GateConnection con : connections) {
            con.close();
        }
        eventLoop.shutdownGracefully();
    }

    //启动线程
    public void run() {
        for (GateConnection conn : connections) {
            conn.start();
        }
    }

    //初始化
    private void initGateConnection(String host, int port, int gateId, ObjectFactory<GateMessageHandler> handlerFactory) {
        connections = new GateConnection[poolCount];
        String name;
        for (int i = 0; i < poolCount; i++) {
            name = "GateConnection-" + (i + 1);
            connections[i] = new GateConnection(name, host, port, CONNECTING_PERIOD, eventLoop, gateId, handlerFactory);
        }
    }

    /**
     * 获取连接
     * @param sessionID
     * @return
     */
    private GateConnection getConn(long sessionID) {
        return connections[(int) (sessionID % connections.length)];
    }

    /**
     * 发送单个玩家消息
     * @param msgID
     * @param targetSessionId
     * @param immediate
     * @param bytes
     */
    public synchronized void sendNetMessage(int msgID, long targetSessionId, boolean immediate, byte[] bytes) {
    	 getConn(targetSessionId).send(new ClientMsg(msgID, Lists.newArrayList(targetSessionId), immediate, bytes));
    }
    
    /**
     * 发送callback消息
     * @param msgID
     * @param targetSessionId
     * @param immediate
     * @param callbackId
     * @param errorCode
     * @param bytes
     */
    public synchronized void sendNetMessage(int msgID, long targetSessionId, boolean immediate, int callbackId, int errorCode, byte[] bytes) {
    	 getConn(targetSessionId).send(new ClientMsg(msgID, Lists.newArrayList(targetSessionId), immediate, callbackId, errorCode, bytes));
    }
    
    /**
     * 发送广播消息
     * @param sessionID
     * @param msgID
     * @param targets
     * @param immediate
     * @param bytes
     */
    public synchronized void sendMultiSessionMessage(long senderSessionId, int msgID, Collection<Long> targets, boolean immediate, byte[] bytes) {
		getConn(senderSessionId).send(new ClientMsg(msgID, Lists.newArrayList(targets), immediate, bytes));
    }
    
    /**
     * 发送当前在线玩家
     * @param msgID
     * @param bytes
     */
    public synchronized void sendOnline(int msgID, byte[] bytes) {
    	getConn(0).send(new BroadcastMsg(msgID,  bytes));
    }
    
    /**
     * 踢玩家下线
     * @param sessionShutDown
     */
    public synchronized void sendKickSession(SessionShutDown sessionShutDown) {
    	getConn(sessionShutDown.getSessionID()).sendKickSession(sessionShutDown);
    }

}
