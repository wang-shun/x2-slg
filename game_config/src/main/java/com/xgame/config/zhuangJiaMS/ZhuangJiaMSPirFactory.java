package com.xgame.config.zhuangJiaMS;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:40 
 */
public class ZhuangJiaMSPirFactory extends BasePriFactory<ZhuangJiaMSPir>{
	

	public void init(ZhuangJiaMSPir pir) {
		
	}
	
	@Override
	public void load(ZhuangJiaMSPir pir) {
		
	}
	
	
	
	
	
	
	@Override
	public ZhuangJiaMSPir newPri() {
		return new ZhuangJiaMSPir();
	}
	
	public static ZhuangJiaMSPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ZhuangJiaMSPirFactory instance = new ZhuangJiaMSPirFactory(); 
	
	
	public static ZhuangJiaMSPirFactory getInstance() {
		return instance;
	}
}