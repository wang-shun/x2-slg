package com.xgame.gate.server.core;

import java.nio.ByteOrder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * gate-serve连接初始化
 * @author jacky.jiang
 *
 */
public class GateToServerCodecFactory extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 4, true));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new GateToServerCodec());
        pipeline.addLast(new GateToServerMsgHandler());
    }
}
