package com.xgame.config.proflie;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:39 
 */
public class ProfliePirFactory extends BasePriFactory<ProfliePir>{
	

	public void init(ProfliePir pir) {
		
	}
	
	@Override
	public void load(ProfliePir pir) {
		
	}
	
	
	
	
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,ProfliePir pir){
		
	}
	
	
	@Override
	public ProfliePir newPri() {
		return new ProfliePir();
	}
	
	public static ProfliePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ProfliePirFactory instance = new ProfliePirFactory(); 
	
	
	public static ProfliePirFactory getInstance() {
		return instance;
	}
}