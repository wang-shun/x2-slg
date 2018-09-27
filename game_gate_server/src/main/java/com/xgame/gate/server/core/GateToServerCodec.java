package com.xgame.gate.server.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.xgame.data.message.BroadcastMsg;
import com.xgame.data.message.ServerMessage;
import com.xgame.data.message.SessionClose;
import com.xgame.data.message.SessionMessage;
import com.xgame.data.message.SessionNew;
import com.xgame.data.message.SessionShutDown;

/**
 * gate-server 编解码
 * @author jacky.jiang
 *
 */
@Slf4j
public class GateToServerCodec extends ByteToMessageCodec<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.order(ByteOrder.BIG_ENDIAN);
        if (msg instanceof SessionNew) {
            SessionNew sessionNew = (SessionNew) msg;
            out.writeInt(Protocol.SESSION_NEW);
            out.writeLong(sessionNew.getSessionID());
            out.writeLong(sessionNew.getIp());
            out.writeInt(sessionNew.getPort());
        } else if (msg instanceof SessionClose) {
            SessionClose sessionClose = (SessionClose) msg;
            out.writeInt(Protocol.SESSION_CLOSED);
            out.writeLong(sessionClose.getSessionID());
        } else if (msg instanceof SessionMessage) {
            SessionMessage sessionRaw = (SessionMessage) msg;
            out.writeInt(Protocol.SEND_TO_SERVER);
            out.writeInt(sessionRaw.getMsgID());
            out.writeInt(sessionRaw.getCalbackId());
            out.writeLong(sessionRaw.getSessionID());
            byte[] bytes = sessionRaw.getBytes();
            out.writeBytes(bytes);
            
            log.error("收到服务器发送的消息id:{}, 消息大小{}", sessionRaw.getMsgID(), out.capacity());
        }
        
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) {
        try {
            int length = in.readableBytes();
            if (length > 0) {
                in.markReaderIndex();
                int protocol = in.readInt();
                if (protocol == Protocol.SESSION_SHUTDOWN) {
                    SessionShutDown sessionShutDown = new SessionShutDown(in.readLong());
                    list.add(sessionShutDown);

                } else if (protocol == Protocol.SEND_TO_SESSION_IMMEDIATELY || protocol == Protocol.SEND_TO_SESSION) {
                    boolean immediate = protocol == Protocol.SEND_TO_SESSION_IMMEDIATELY ? true : false;
                    int msgID = in.readInt();
                    int callbackId = in.readInt();
                    int errorcode = in.readInt();
                    int size = in.readInt();
                    List<Long> sessionIDList = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            sessionIDList.add(in.readLong());
                        }
                    }
                    byte[] bts = new byte[in.readableBytes()];
                    in.readBytes(bts);
                    ServerMessage serverMessage = new ServerMessage(msgID, sessionIDList, immediate,callbackId, errorcode, bts);
                    list.add(serverMessage);
                } else if(protocol == Protocol.BROADCAST) {
                	 int msgID = in.readInt();
                     int size = in.readInt();
                     List<Long> sessionIDList = new ArrayList<>();
                     if (size > 0) {
                         for (int i = 0; i < size; i++) {
                             sessionIDList.add(in.readLong());
                         }
                     }
                     byte[] bts = new byte[in.readableBytes()];
                     in.readBytes(bts);
                     BroadcastMsg broadcastMsg = new BroadcastMsg(msgID, bts);
                     list.add(broadcastMsg);
                }
            }
        } catch (Exception e) {
            log.error("server to gate, decode occur error, message:{}", e);
        }
    }
}
