package com.xgame.config.server;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:39 
 */
public class ServerPirFactory extends BasePriFactory<ServerPir>{
	

	public void init(ServerPir pir) {
		
	}
	
	@Override
	public void load(ServerPir pir) {
		
	}
	
	
	
	
	
	
	
	@Override
	public ServerPir newPri() {
		return new ServerPir();
	}
	
	public static ServerPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ServerPirFactory instance = new ServerPirFactory(); 
	
	
	public static ServerPirFactory getInstance() {
		return instance;
	}
}