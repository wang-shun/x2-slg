package com.xgame.config.attrBonusSource;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:33 
 */
public class AttrBonusSourcePirFactory extends BasePriFactory<AttrBonusSourcePir>{
	

	public void init(AttrBonusSourcePir pir) {
		
	}
	
	@Override
	public void load(AttrBonusSourcePir pir) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public AttrBonusSourcePir newPri() {
		return new AttrBonusSourcePir();
	}
	
	public static AttrBonusSourcePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final AttrBonusSourcePirFactory instance = new AttrBonusSourcePirFactory(); 
	
	
	public static AttrBonusSourcePirFactory getInstance() {
		return instance;
	}
}