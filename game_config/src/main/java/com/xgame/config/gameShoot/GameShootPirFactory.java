package com.xgame.config.gameShoot;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class GameShootPirFactory extends BasePriFactory<GameShootPir>{
	

	public void init(GameShootPir pir) {
		
	}
	
	@Override
	public void load(GameShootPir pir) {
		
	}
	
	
	
	
	
	
	
	
	@Override
	public GameShootPir newPri() {
		return new GameShootPir();
	}
	
	public static GameShootPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final GameShootPirFactory instance = new GameShootPirFactory(); 
	
	
	public static GameShootPirFactory getInstance() {
		return instance;
	}
}