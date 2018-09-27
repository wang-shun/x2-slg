package com.xgame.config.serverEvent;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-15 10:43:54 
 */
public class ServerEventPirFactory extends BasePriFactory<ServerEventPir>{
	

	public void init(ServerEventPir pir) {
		
	}
	
	@Override
	public void load(ServerEventPir pir) {
		
	}
	
	
	
	/**
	 *自定义解析  serverIds
	 */
	@ConfParse("serverIds")
	public void serverIdsPares(String conf,ServerEventPir pir){
		
	}
	
	
	@Override
	public ServerEventPir newPri() {
		return new ServerEventPir();
	}
	
	public static ServerEventPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ServerEventPirFactory instance = new ServerEventPirFactory(); 
	
	
	public static ServerEventPirFactory getInstance() {
		return instance;
	}
}