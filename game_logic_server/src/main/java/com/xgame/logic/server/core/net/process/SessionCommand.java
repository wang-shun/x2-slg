package com.xgame.logic.server.core.net.process;

import com.xgame.framework.network.server.Session1;

import lombok.NoArgsConstructor;

@SuppressWarnings("rawtypes")
@NoArgsConstructor
public abstract class SessionCommand<D> extends StatefulCommand{

	protected abstract void process(Session1 session, D data);

}
