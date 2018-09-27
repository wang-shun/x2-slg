package com.xgame.logic.server.core.net.gate;

import java.nio.ByteOrder;
import java.util.List;

import com.xgame.data.message.BroadcastMsg;
import com.xgame.data.message.ClientMsg;
import com.xgame.data.message.G2SProtocol;
import com.xgame.data.message.SessionClose;
import com.xgame.data.message.SessionNew;
import com.xgame.data.message.SessionRawMessage;
import com.xgame.data.message.SessionShutDown;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

/**
 * 网关数据编码解码
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateCodec extends ByteToMessageCodec<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.order(ByteOrder.BIG_ENDIAN);
        if (msg instanceof ClientMsg) {
            ClientMsg clientMsg = (ClientMsg) msg;
            out.writeInt(G2SProtocol.SEND_TO_SESSION);
            out.writeInt(clientMsg.getMsgId());
            out.writeInt(clientMsg.getCallbackId());
            out.writeInt(clientMsg.getErrorCode());
            List<Long> sessionIDList = clientMsg.getSessionIDList();
            out.writeInt(sessionIDList.size());
            for (Long sessionID : sessionIDList) {
                out.writeLong(sessionID);
            }
            out.writeBytes(clientMsg.getContents());
            log.info(">>>>>>>>>>>>>>>发送给网关的消息id:{}, 消息大小{}",clientMsg.getMsgId(), out.capacity());
        } else if (msg instanceof SessionShutDown) {
        	SessionShutDown sessionShutdownMsg = (SessionShutDown) msg;
            out.writeInt(G2SProtocol.SESSION_SHUTDOWN);
            out.writeLong(sessionShutdownMsg.getSessionID());
        } else if(msg instanceof BroadcastMsg) {
        	BroadcastMsg noticeServerMsg = (BroadcastMsg) msg;
        	out.writeInt(G2SProtocol.BROADCAST);
        	out.writeInt(noticeServerMsg.getMsgId());
			List<Long> sessionIDList = noticeServerMsg.getIgnoreSesison();
			out.writeInt(sessionIDList != null ? sessionIDList.size() : 0);
			if(sessionIDList != null) {
				for (Long sessionID : sessionIDList) {
					out.writeLong(sessionID);
				}
			}
			out.writeBytes(noticeServerMsg.getContents());
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
       
        int length = in.readableBytes();
        if (length > 0) {
            in.markReaderIndex();
            int protocol = in.readInt();
            //发送消息
            if (protocol == G2SProtocol.SEND_TO_SERVER) {
                int msgID = in.readInt();
                int callbackId = in.readInt();
                long sessionID = in.readLong();
                byte[] bts = new byte[in.readableBytes()];
                in.readBytes(bts);
                out.add(new SessionRawMessage(msgID, sessionID, callbackId,  bts));
                log.info("<<<<<<<<<<<<<<<<收到网关发送的消息id:{}, 消息大小{}", msgID, bts.length);
                // 新建连接
            } else if (protocol == G2SProtocol.SESSION_NEW) {
                out.add(new SessionNew(in.readLong(), in.readLong(), in.readInt()));
            } else if (protocol == G2SProtocol.SESSION_CLOSED) {
                out.add(new SessionClose(in.readLong()));
            }
        }

    }
}
