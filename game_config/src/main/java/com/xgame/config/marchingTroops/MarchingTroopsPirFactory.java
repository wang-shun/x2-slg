package com.xgame.config.marchingTroops;

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
public class MarchingTroopsPirFactory extends BasePriFactory<MarchingTroopsPir>{
	

	public void init(MarchingTroopsPir pir) {
		pir.num = new AttributeConfMap(pir.lv);
	}
	
	@Override
	public void load(MarchingTroopsPir pir) {
		
	}
	
	@ConfParse("num")
	public void numPares(String conf,MarchingTroopsPir pir){
		AttributeParser.parse(conf, pir.getNum());
	}
	
	
	@Override
	public MarchingTroopsPir newPri() {
		return new MarchingTroopsPir();
	}
	
	public static MarchingTroopsPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final MarchingTroopsPirFactory instance = new MarchingTroopsPirFactory(); 
	
	
	public static MarchingTroopsPirFactory getInstance() {
		return instance;
	}
}