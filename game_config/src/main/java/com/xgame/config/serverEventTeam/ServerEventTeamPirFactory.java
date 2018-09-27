package com.xgame.config.serverEventTeam;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-15 10:43:54 
 */
public class ServerEventTeamPirFactory extends BasePriFactory<ServerEventTeamPir>{
	

	public void init(ServerEventTeamPir pir) {
		
	}
	
	@Override
	public void load(ServerEventTeamPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  openTime1
	 */
	@ConfParse("openTime1")
	public void openTime1Pares(String conf,ServerEventTeamPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  openTime2
	 */
	@ConfParse("openTime2")
	public void openTime2Pares(String conf,ServerEventTeamPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  openTime3
	 */
	@ConfParse("openTime3")
	public void openTime3Pares(String conf,ServerEventTeamPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  openTime4
	 */
	@ConfParse("openTime4")
	public void openTime4Pares(String conf,ServerEventTeamPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  openTime5
	 */
	@ConfParse("openTime5")
	public void openTime5Pares(String conf,ServerEventTeamPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  openTime6
	 */
	@ConfParse("openTime6")
	public void openTime6Pares(String conf,ServerEventTeamPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  openTime7
	 */
	@ConfParse("openTime7")
	public void openTime7Pares(String conf,ServerEventTeamPir pir){
		
	}
	
	
	@Override
	public ServerEventTeamPir newPri() {
		return new ServerEventTeamPir();
	}
	
	public static ServerEventTeamPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ServerEventTeamPirFactory instance = new ServerEventTeamPirFactory(); 
	
	
	public static ServerEventTeamPirFactory getInstance() {
		return instance;
	}
}