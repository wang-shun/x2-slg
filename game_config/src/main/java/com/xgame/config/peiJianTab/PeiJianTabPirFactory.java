package com.xgame.config.peiJianTab;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:39 
 */
public class PeiJianTabPirFactory extends BasePriFactory<PeiJianTabPir>{
	

	public void init(PeiJianTabPir pir) {
		
	}
	
	@Override
	public void load(PeiJianTabPir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  type6
	 */
	@ConfParse("type6")
	public void type6Pares(String conf,PeiJianTabPir pir){
		
	}
	
	/**
	 *自定义解析  type6Name
	 */
	@ConfParse("type6Name")
	public void type6NamePares(String conf,PeiJianTabPir pir){
		
	}
	
	@Override
	public PeiJianTabPir newPri() {
		return new PeiJianTabPir();
	}
	
	public static PeiJianTabPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final PeiJianTabPirFactory instance = new PeiJianTabPirFactory(); 
	
	
	public static PeiJianTabPirFactory getInstance() {
		return instance;
	}
}