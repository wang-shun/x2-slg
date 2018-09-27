package com.xgame.config.armyActiveQuest;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-02-18 17:01:16 
 */
public class ArmyActiveQuestPirFactory extends BasePriFactory<ArmyActiveQuestPir>{
	

	public void init(ArmyActiveQuestPir pir) {
		
	}
	
	@Override
	public void load(ArmyActiveQuestPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,ArmyActiveQuestPir pir){
		
	}
	
	/**
	 *自定义解析  v2
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,ArmyActiveQuestPir pir){
		
	}
	
	
	
	@Override
	public ArmyActiveQuestPir newPri() {
		return new ArmyActiveQuestPir();
	}
	
	public static ArmyActiveQuestPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyActiveQuestPirFactory instance = new ArmyActiveQuestPirFactory(); 
	
	
	public static ArmyActiveQuestPirFactory getInstance() {
		return instance;
	}
}