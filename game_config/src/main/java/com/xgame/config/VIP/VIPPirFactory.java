package com.xgame.config.VIP;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.AttributeParser;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class VIPPirFactory extends BasePriFactory<VIPPir>{
	

	public void init(VIPPir pir) {
		pir.attr = new AttributeConfMap(pir.id);
	}
	
	@Override
	public void load(VIPPir pir) {
		
	}
	
	/**
	 *自定义解析  attr
	 */
	@ConfParse("attr")
	public void attrPares(String conf,VIPPir pir){
		AttributeParser.parse(conf, pir.getAttr());
	}
	
	
	
	
	@Override
	public VIPPir newPri() {
		return new VIPPir();
	}
	
	public static VIPPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final VIPPirFactory instance = new VIPPirFactory(); 
	
	
	public static VIPPirFactory getInstance() {
		return instance;
	}
}