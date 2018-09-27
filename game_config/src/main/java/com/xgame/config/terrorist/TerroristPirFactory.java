package com.xgame.config.terrorist;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:40 
 */
public class TerroristPirFactory extends BasePriFactory<TerroristPir>{
	

	public void init(TerroristPir pir) {
		
	}
	
	@Override
	public void load(TerroristPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  showId
	 */
	@ConfParse("showId")
	public void showIdPares(String conf,TerroristPir pir){
		
	}
	
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,TerroristPir pir){
		
	}
	
	/**
	 *自定义解析  rewardShow
	 */
	@ConfParse("rewardShow")
	public void rewardShowPares(String conf,TerroristPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,TerroristPir pir){
		
	}
	
	/**
	 *自定义解析  XY
	 */
	@ConfParse("XY")
	public void XYPares(String conf,TerroristPir pir){
		
	}
	
	/**
	 *自定义解析  fristRewards
	 */
	@ConfParse("fristRewards")
	public void fristRewardsPares(String conf,TerroristPir pir){
		
	}
	
	
	
	
	@Override
	public TerroristPir newPri() {
		return new TerroristPir();
	}
	
	public static TerroristPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final TerroristPirFactory instance = new TerroristPirFactory(); 
	
	
	public static TerroristPirFactory getInstance() {
		return instance;
	}
}