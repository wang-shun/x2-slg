package com.xgame.config.armyWarPlayerRankingAward;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class ArmyWarPlayerRankingAwardPirFactory extends BasePriFactory<ArmyWarPlayerRankingAwardPir>{
	

	public void init(ArmyWarPlayerRankingAwardPir pir) {
		
	}
	
	@Override
	public void load(ArmyWarPlayerRankingAwardPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  award
	 */
	@ConfParse("award")
	public void awardPares(String conf,ArmyWarPlayerRankingAwardPir pir){
		
	}
	
	@Override
	public ArmyWarPlayerRankingAwardPir newPri() {
		return new ArmyWarPlayerRankingAwardPir();
	}
	
	public static ArmyWarPlayerRankingAwardPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyWarPlayerRankingAwardPirFactory instance = new ArmyWarPlayerRankingAwardPirFactory(); 
	
	
	public static ArmyWarPlayerRankingAwardPirFactory getInstance() {
		return instance;
	}
}