package com.xgame.framework.network.server;

import java.util.HashMap;
import java.util.Map;

public final class MessageManager {
	private static Map<Integer, ClientMessage> clientMsgs;
	private static Map<Integer, ServerMessage> serverMsgs;

	static {
		clientMsgs = new HashMap<>();
		for (ClientMessage msg : ClientMessage.values()) {
			clientMsgs.put(msg.id(), msg);
		}
		serverMsgs = new HashMap<>();
		for (ServerMessage msg : ServerMessage.values()) {
			serverMsgs.put(msg.id(), msg);
		}
	}

	public static ClientMessage clientMessage(int id) {
		return clientMsgs.get(id);
	}

	public static ServerMessage serverMessage(int id) {
		return serverMsgs.get(id);
	}
}
