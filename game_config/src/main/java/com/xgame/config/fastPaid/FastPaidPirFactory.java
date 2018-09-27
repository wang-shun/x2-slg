package com.xgame.config.fastPaid;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-24 10:18:04 
 */
public class FastPaidPirFactory extends BasePriFactory<FastPaidPir>{
	

	public void init(FastPaidPir pir) {
		
	}
	
	@Override
	public void load(FastPaidPir pir) {
		
	}
	
	
	
	
	@Override
	public FastPaidPir newPri() {
		return new FastPaidPir();
	}
	
	public static FastPaidPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final FastPaidPirFactory instance = new FastPaidPirFactory(); 
	
	
	public static FastPaidPirFactory getInstance() {
		return instance;
	}
}