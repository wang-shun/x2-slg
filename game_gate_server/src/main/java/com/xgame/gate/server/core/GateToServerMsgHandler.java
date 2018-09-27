package com.xgame.gate.server.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import akka.actor.ActorRef;

import com.xgame.gate.server.akka.AkkaFactory;
import com.xgame.gate.server.akka.GateToServerAkka;
import com.xgame.gate.server.cache.GateChannelCache;

/**
 * gate-server消息处理
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateToServerMsgHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	GateChannelCache.getInstance().addServerChannel(ctx.channel(), AkkaFactory.createActorRef("gateToServerActorRef_" + AtomicRecord.actorSeq.incrementAndGet(), GateToServerAkka.class));
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ActorRef actorRef = GateChannelCache.getInstance().getServerChannel(ctx.channel());
            if (actorRef != null) {
                actorRef.tell(msg, ActorRef.noSender());
            }
        } catch (Exception e) {
            log.error("异常信息：{}", e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	GateChannelCache.getInstance().removeServerChannel(ctx.channel());
        ctx.channel().close();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	GateChannelCache.getInstance().removeServerChannel(ctx.channel());
        ctx.channel().close();
    }
}
