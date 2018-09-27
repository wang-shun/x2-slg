package com.xgame.config.limitRankRewards;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class LimitRankRewardsPirFactory extends BasePriFactory<LimitRankRewardsPir>{
	

	public void init(LimitRankRewardsPir pir) {
		
	}
	
	@Override
	public void load(LimitRankRewardsPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  rank
	 */
	@ConfParse("rank")
	public void rankPares(String conf,LimitRankRewardsPir pir){
		
	}
	
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,LimitRankRewardsPir pir){
		
	}
	
	@Override
	public LimitRankRewardsPir newPri() {
		return new LimitRankRewardsPir();
	}
	
	public static LimitRankRewardsPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LimitRankRewardsPirFactory instance = new LimitRankRewardsPirFactory(); 
	
	
	public static LimitRankRewardsPirFactory getInstance() {
		return instance;
	}
}