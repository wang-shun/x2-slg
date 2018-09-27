package com.xgame.gate.server.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;

import lombok.extern.slf4j.Slf4j;
import akka.actor.ActorRef;

import com.xgame.data.message.SessionClose;
import com.xgame.data.message.SessionMessage;
import com.xgame.data.message.SessionNew;
import com.xgame.data.statistic.ChannelStatistic;
import com.xgame.gate.server.cache.GateChannelCache;
import com.xgame.gate.server.config.ConfigManager;
import com.xgame.utils.IPUtil;

/**
 * gate-client消息编码解码器
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateToClientMsgHandler extends ChannelInboundHandlerAdapter {

	public static AttributeKey<Long> SESSION_ID = AttributeKey.valueOf("sessionId");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //build to connect
        long sessionID = AtomicRecord.sessionID.incrementAndGet();

        //sesisonid - channel cache
        GateChannelCache.getInstance().putSessionIDToChannel(sessionID, ctx.channel());
        Attribute<Long> attribute = ctx.attr(SESSION_ID);
        attribute.set(sessionID);
        
        //channel - actor cache
        createAndSendSessionNew(sessionID, ctx);
        ChannelStatistic.getInstance().increase(1);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof SessionMessage) {
                SessionMessage sessionMessage = (SessionMessage) msg;
                Attribute<Long> attribute=  ctx.attr(SESSION_ID);
                Long sessionID = attribute.get();
                if (sessionID != null) {
                    sessionMessage.setSessionID(sessionID);
                    ActorRef actorRef =  GateChannelCache.getInstance().getClientAkka(sessionID);
                    if (actorRef != null) {
                        actorRef.tell(sessionMessage, ActorRef.noSender());
                    } else {
                        log.warn("channel to actorRef is not exist");
                    }
                } else {
                    log.warn("sessionID is not exist");
                }
            }
        } catch (Exception e) {
            log.error("channel read error info:", e);
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //链接断开移除数据， 防止内容泄漏
        dealChannelDown(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        dealChannelDown(ctx);
        super.exceptionCaught(ctx, cause);
    }

    private void createAndSendSessionNew(long sessionID, ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        SessionNew sessionNew = new SessionNew(sessionID, IPUtil.ipToLong(clientIP), ConfigManager.getInstance().getC2GPort());
        sendMsg(sessionNew, sessionID);
    }

    private void sendMsg(Object msg, long sessionID) {
        ActorRef actorRef =  GateChannelCache.getInstance().getClientAkka(sessionID);
        if (actorRef != null) {
            actorRef.tell(msg, ActorRef.noSender());
        } else {
            log.warn("channel to actorRef is not exist");
        }
    }

    private void dealChannelDown(ChannelHandlerContext ctx) {
        Attribute<Long> attribute =  ctx.attr(SESSION_ID);
        Long sessionID = attribute.get();
        if (sessionID != null) {
        	GateChannelCache.getInstance().removeChannelBySessionID(sessionID);
            SessionClose sessionClose = new SessionClose(sessionID);
            sendMsg(sessionClose, sessionID);
            ChannelStatistic.getInstance().increase(-1);
        }
        ctx.close();
    }

}
