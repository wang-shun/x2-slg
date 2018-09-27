package com.xgame.config.commanderInfo;

import com.xgame.config.BasePriFactory;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:09 
 */
public class CommanderInfoPirFactory extends BasePriFactory<CommanderInfoPir>{
	

	public void init(CommanderInfoPir pir) {
		
	}
	
	@Override
	public void load(CommanderInfoPir pir) {
		
	}
	
	
	@Override
	public CommanderInfoPir newPri() {
		return new CommanderInfoPir();
	}
	
	public static CommanderInfoPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CommanderInfoPirFactory instance = new CommanderInfoPirFactory(); 
	
	
	public static CommanderInfoPirFactory getInstance() {
		return instance;
	}
}