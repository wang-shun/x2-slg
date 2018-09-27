package com.xgame.gate.server.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.Attribute;

import java.nio.ByteOrder;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.xgame.data.message.ResponseMessage;
import com.xgame.data.message.SessionMessage;

/**
 * gate-client 编解码
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateToClientCodec extends ByteToMessageCodec<Object> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.order(ByteOrder.BIG_ENDIAN);
        if (msg instanceof ResponseMessage) {
            ResponseMessage respMsg = (ResponseMessage) msg;
            out.writeInt(0); // length field placeholder
            int msgId = respMsg.getMsgID();
            out.writeInt(respMsg.getMsgID()); // msgId
            if(respMsg.getCallbackId() == 0) {
            	out.writeInt(10000); // callbackId
            } else {
            	out.writeInt(respMsg.getCallbackId()); // callbackId
            }
            
            out.writeInt(0);//errorcode
            out.writeBytes(respMsg.getBytes());
            out.setInt(0, out.readableBytes() - 4);
            
            Attribute<Long> attribute = ctx.attr(GateToClientMsgHandler.SESSION_ID);
            if(attribute != null) {
            	log.error("网关返回给客户端的消息id:{}, 消息大小：{}", msgId, out.capacity());
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            int length = in.readableBytes();
            if (length > 0) {
                in.markReaderIndex();
                int token = in.readUnsignedShort();
                int msgID = in.readInt();
                int callbackId = in.readInt();
                byte[] bytes = new byte[in.readableBytes()];
                in.readBytes(bytes);
                SessionMessage sessionMessage = new SessionMessage(0, msgID, callbackId, bytes);
                out.add(sessionMessage);
            }

        } catch (Exception e) {
            in.resetReaderIndex();
            log.error("client to gate, decode occur error, message:{}", e);
        }

    }
}
