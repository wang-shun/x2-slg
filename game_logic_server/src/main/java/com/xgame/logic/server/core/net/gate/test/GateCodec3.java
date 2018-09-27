package com.xgame.logic.server.core.net.gate.test;

import java.nio.ByteOrder;
import java.util.List;

import com.xgame.data.message.ClientMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;


/**
 * 网管编解码
 * @author jacky.jiang
 *
 */
public class GateCodec3 extends ByteToMessageCodec<Object>{

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf arg1,
			List<Object> out) throws Exception {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
		
		int size = arg1.readInt();
		int msgId = arg1.readInt();
		int callbackId = arg1.readInt(); // TODO: try optimize this to
		long sessionId = arg1.readLong();
		ClientMessage msg = new ClientMessage();
		msg.setSessionID(sessionId);
		out.add(msg);
	}

	@Override
	protected void encode(ChannelHandlerContext arg0, Object arg1, ByteBuf out) throws Exception {
		System.out.println("============================================");
		out.order(ByteOrder.BIG_ENDIAN);
		byte[] bytes = (byte[])arg1;
		if(arg1 instanceof byte[]) {
			out.writeBytes(bytes);
		}
	}
}
