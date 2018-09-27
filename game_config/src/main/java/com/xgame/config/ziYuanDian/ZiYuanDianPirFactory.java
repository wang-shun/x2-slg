package com.xgame.config.ziYuanDian;

import java.util.HashMap;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class ZiYuanDianPirFactory extends BasePriFactory<ZiYuanDianPir>{
	
	public Map<Integer, Map<Integer, ZiYuanDianPir>> factory0 = new HashMap<>();
	
	public void init(ZiYuanDianPir pir) {
		
	}
	
	@Override
	public void load(ZiYuanDianPir pir) {
		Map<Integer, ZiYuanDianPir> typeMap = factory0.get(pir.getType());
		if(typeMap == null) {
			typeMap = new HashMap<Integer, ZiYuanDianPir>();
			factory0.put(pir.getType(), typeMap);
		} 
		
		typeMap.put(pir.getLv(), pir);
		
		
	}
	
	
	/**
	 *自定义解析  showId
	 */
	@ConfParse("showId")
	public void showIdPares(String conf,ZiYuanDianPir pir){
	
	}
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,ZiYuanDianPir pir){
	
	}
	
	@Override
	public ZiYuanDianPir newPri() {
		return new ZiYuanDianPir();
	}
	
	public Map<Integer, Map<Integer, ZiYuanDianPir>> getFactory0() {
		return factory0;
	}
	
	public ZiYuanDianPir getByTypeAndLevel(int type, int level) {
		Map<Integer, ZiYuanDianPir> map = factory0.get(type);
		if(map != null) {
			return map.get(level);
		}
		return null;
	}
	
	public static Map<Integer, ZiYuanDianPir> get(Object key) {
		return instance.getFactory0().get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ZiYuanDianPirFactory instance = new ZiYuanDianPirFactory(); 
	
	
	public static ZiYuanDianPirFactory getInstance() {
		return instance;
	}
}