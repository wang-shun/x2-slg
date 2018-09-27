package com.xgame.config.armyExercise;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-02-18 17:01:16 
 */
public class ArmyExercisePirFactory extends BasePriFactory<ArmyExercisePir>{
	

	public void init(ArmyExercisePir pir) {
		
	}
	
	@Override
	public void load(ArmyExercisePir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  reward
	 */
	@ConfParse("reward")
	public void rewardPares(String conf,ArmyExercisePir pir){
		
	}
	
	@Override
	public ArmyExercisePir newPri() {
		return new ArmyExercisePir();
	}
	
	public static ArmyExercisePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyExercisePirFactory instance = new ArmyExercisePirFactory(); 
	
	
	public static ArmyExercisePirFactory getInstance() {
		return instance;
	}
}