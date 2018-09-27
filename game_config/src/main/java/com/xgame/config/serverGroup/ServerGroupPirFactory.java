package com.xgame.config.serverGroup;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:39 
 */
public class ServerGroupPirFactory extends BasePriFactory<ServerGroupPir>{
	

	public void init(ServerGroupPir pir) {
		
	}
	
	@Override
	public void load(ServerGroupPir pir) {
		
	}
	
	
	
	
	
	
	
	
	@Override
	public ServerGroupPir newPri() {
		return new ServerGroupPir();
	}
	
	public static ServerGroupPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ServerGroupPirFactory instance = new ServerGroupPirFactory(); 
	
	
	public static ServerGroupPirFactory getInstance() {
		return instance;
	}
}