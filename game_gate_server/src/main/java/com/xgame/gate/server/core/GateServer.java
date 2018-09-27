package com.xgame.gate.server.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import com.xgame.data.service.IService;
import com.xgame.gate.server.constant.ServerType;

/**
 * 网关服务器
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateServer extends Thread implements IService {

    private final int port; //端口
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelInitializer<NioSocketChannel> channelInitializer;
    private ServerType serverType;
    
    private ChannelFuture cf;

    public GateServer(int port, ServerType serverType, ChannelInitializer<NioSocketChannel> channelInitializer) {
        this.port = port;
        this.serverType = serverType;
        this.channelInitializer = channelInitializer;
    }

    @Override
    public void init() {
    	 start();
    }
    
    @Override
	public void run() {
    	 initServer();
    }

	public void initServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        try {
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保存连接
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 内存池
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT) // 内存池
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT); // 动态内存分配

            // 绑定并监听断开
            cf = serverBootstrap.bind(port).sync();
            if (cf.isSuccess()) {
                log.info("start server success,port:{}, serverType:{}",  port, serverType);
            }
            // 等待关闭事件
            cf.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error("server occur exception, message:{}", e);
        } finally {
            // 释放资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    /**
     * 
     */
	@Override
	public void shutdown() {
		if (cf != null) {
			cf.channel().close();
		}
	}
}
