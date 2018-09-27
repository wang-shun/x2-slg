package com.xgame.logic.server.core.net.gate;

import java.net.ConnectException;
import java.nio.ByteOrder;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

import org.springframework.beans.factory.ObjectFactory;

import com.xgame.data.message.ClientMsg;
import com.xgame.data.message.GateMessage;
import com.xgame.data.message.SessionShutDown;
import com.xgame.data.statistic.MessageStat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 网关连接器
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateConnection extends Thread {
	
	public static final int STATISTIS_CHECKCOUNT  = 500;
    public static AttributeKey<Integer> GATE_ID_KEY = AttributeKey.valueOf("GATE_ID");

    private String conName;
    private String host;
    private int port;
    private long connectPeriod;
    private int gateId;
    private static EventLoopGroup eventLoopGroup;
    private long checkConnectionTime = 0;
    private Queue<GateMessage> queue = new ConcurrentLinkedQueue<>();
    private boolean working = true;
    private volatile Channel channel;
	private ObjectFactory<GateMessageHandler> serverHandler;

    public GateConnection(String conName, String host, int port, long connectPeriod, EventLoopGroup eventLoop, int gateId, ObjectFactory<GateMessageHandler> serverHandler) {
        this.conName = conName;
        this.host = host;
        this.port = port;
        this.connectPeriod = connectPeriod;
        this.gateId = gateId;
        GateConnection.eventLoopGroup = eventLoop;
        this.serverHandler = serverHandler;
    }


    @Override
    public void run() {
        checkConnection();
        while (working) {
            try {
                checkConnection();
				GateMessage gateMessage = queue.poll();
				int n = 0;
				while (gateMessage != null) {
					if (channel == null) {
						connect();
					}
	
					channel.writeAndFlush(gateMessage);
	
					log.info("发送给网管的消息，messageID:{},消息", gateMessage.getMsgId());
					if (gateMessage instanceof ClientMsg) {
						MessageStat.getInstance().increase(1);
					}
	
					n++;
	
					// 数据包统计
					if (n > STATISTIS_CHECKCOUNT)
						MessageStat.getInstance().doStatistic();
	
					gateMessage = queue.poll();
				}
				LockSupport.parkNanos(1000 * 1000 * 1000);
            } catch (Exception e) {
                log.error(String.format("GateAgent {} loop crash.", conName), e);
            }
        }
    }

    /**
     * 发送队列消息
     * @param msg
     */
    public void send(GateMessage msg) {
        if (working) {
        	log.debug("logic 发送消息 : getMsgId = " + msg.getMsgId());
        	msg.setSendTime(System.currentTimeMillis());
            queue.offer(msg);
            LockSupport.unpark(this);
        }
    }
    
    /**
     * 发送断线消息
     * @param sessionShutDown
     */
    public void sendKickSession(SessionShutDown sessionShutDown) {
    	if(channel !=  null) {
    		channel.write(sessionShutDown);
    	}
    }

    /**
     * 关闭链接
     */
    public synchronized void close() {
        try {
            if (working) {
                if (channel != null) {
                    channel.close().sync();
                    channel = null;
                }
                queue.clear();
                working = false;
            }
        } catch (Exception e) {

        }

    }

    //检查连接
    private void checkConnection() {
        if (channel == null || !channel.isActive() || !channel.isOpen()) {
            long now = System.currentTimeMillis();
            channel = null;
            if (now > checkConnectionTime + connectPeriod) {
                checkConnectionTime = now;
                connect();
            }
        }
    }
    
    //连接
    private void connect() {
        try {
            working = true;
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                        	ch.pipeline().addLast("decoder", new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 4, true));
                        	ch.pipeline().addLast(new LengthFieldPrepender(4));
                        	ch.pipeline().addLast(new GateCodec());
                            ch.pipeline().addLast(serverHandler.getObject());
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    Channel c = channel;
                                    c.close();
                                    channel = null;
                                    log.error(String.format("%s connection crash", conName), cause);
                                }
                            });
                            
                          
                        }
                    });
            ChannelFuture cf = bootstrap.connect(host, port).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.cause() == null) {
                        channel = future.channel();
                       
                        // 设置gate标识
                        Attribute<Integer> attribute = channel.attr(GATE_ID_KEY);
                        attribute.set(gateId);
                        log.info("{} connect to {}:{}, SUCCESS", conName, host, port);
                    } else {
                        if (future.cause() instanceof ConnectException) {
                            log.info("{} connect to {}:{}, FAILED", conName, host, port);
                        } else {
                            log.error(String.format("{}, connect crash to {}:{}", conName, host, port), future.cause());
                        }
                    }
                }
            });
            cf.await();
        } catch (Exception e) {
            log.error("connect fail:{}", e);
        };
    }

	public int getGateId() {
		return gateId;
	}

	public void setGateId(int gateId) {
		this.gateId = gateId;
	}
    
}
