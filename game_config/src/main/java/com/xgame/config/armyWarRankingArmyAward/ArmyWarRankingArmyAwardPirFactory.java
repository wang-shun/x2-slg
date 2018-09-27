package com.xgame.config.armyWarRankingArmyAward;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class ArmyWarRankingArmyAwardPirFactory extends BasePriFactory<ArmyWarRankingArmyAwardPir>{
	

	public void init(ArmyWarRankingArmyAwardPir pir) {
		
	}
	
	@Override
	public void load(ArmyWarRankingArmyAwardPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  award
	 */
	@ConfParse("award")
	public void awardPares(String conf,ArmyWarRankingArmyAwardPir pir){
		
	}
	
	@Override
	public ArmyWarRankingArmyAwardPir newPri() {
		return new ArmyWarRankingArmyAwardPir();
	}
	
	public static ArmyWarRankingArmyAwardPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyWarRankingArmyAwardPirFactory instance = new ArmyWarRankingArmyAwardPirFactory(); 
	
	
	public static ArmyWarRankingArmyAwardPirFactory getInstance() {
		return instance;
	}
}