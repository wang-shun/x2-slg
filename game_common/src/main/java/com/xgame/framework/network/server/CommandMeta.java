package com.xgame.framework.network.server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandMeta {
	private int messageId;
	private boolean sync;
	private Class<? extends Command> type;
	private Class<?> dataType;

	public String toString() {
		return String.format("[%d]%s(%s)", messageId, type.toString(), dataType.toString());
	}
}
