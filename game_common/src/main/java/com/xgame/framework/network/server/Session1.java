package com.xgame.framework.network.server;

import io.netty.channel.ChannelFuture;

import java.net.SocketAddress;

public interface Session1 {

	short active();

	void active(short value);

	long getId();

	SocketAddress getRemoteAddress();

	//ChannelFuture write(ByteBuf data);

	ChannelFuture write(int msgId, Object msg);

	ChannelFuture write(MessageName msgName, Object msg);

	ChannelFuture write(MessageName msgName);

	ChannelFuture close();
	
}
