package com.xgame.gate.server;

import com.xgame.framework.network.server.Session;
import com.xgame.framework.objectpool.IPooledObject;

public class ClientSession implements IPooledObject {

	public Session session;

	public long loginTime;
	
	public int serverArea;
	
	public long roleId;

	@Override
	public void poolClear() {
		session = null;
		loginTime = 0;
		serverArea = 0;
		roleId = 0;
	}

	@Override
	public boolean poolValidate() {
		return true;
	}
}
