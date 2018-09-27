package com.xgame.framework.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettySocketServer {

	private ServerBootstrap bootstrap;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	ChannelFuture channelFuture;

	public void init(ChannelHandler handler) {
		try {
			bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("netty-boss", Thread.MAX_PRIORITY));
			workerGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("netty-worker", Thread.MAX_PRIORITY));
			
			// handler
			bootstrap = new ServerBootstrap() //
					.group(bossGroup, workerGroup) //
					.channel(NioServerSocketChannel.class) //
					.handler(new LoggingHandler(LogLevel.INFO)) //
					.childHandler(handler);
			// options
			bootstrap.option(ChannelOption.TCP_NODELAY, true);
			bootstrap.option(ChannelOption.SO_REUSEADDR, true);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.option(ChannelOption.SO_LINGER, 0);
			bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000); // 5_seconds
			bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);
			bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
			bootstrap.option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 10 * 65536);
			bootstrap.option(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 2 * 65536);
			
			bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT); 
			bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		} catch (Throwable thr) {
			log.error("server init error", thr);
			closeGroup();
		}
	}

	public void shutdown() {
		if (channelFuture != null) {
			channelFuture.channel().close();
		}
	}

	public void start(String ip, int port) {
		try {
			if (ip == null || ip.trim().isEmpty()) {
				channelFuture = bootstrap.bind(port).sync();
			} else {
				channelFuture = bootstrap.bind(ip, port).sync();
			}
		} catch (Throwable ex) {
			log.error("server start error", ex);
			closeGroup();
		}
	}

	private void closeGroup() {
		
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}
}
