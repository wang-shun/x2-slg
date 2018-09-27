package com.xgame.config.active;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:08 
 */
public class ActivePirFactory extends BasePriFactory<ActivePir>{
	

	/**
	 * key:type
	 */
	private static final Map<Integer,List<ActivePir>> activePirMap = new HashMap<>(); 
	
	
	/**
	 * Alex
	 * 当前配置加载完成后的处理
	 * */
	@Override
	public void loadOver(String programConfigPath, Map<Integer,ActivePir> data){
		for(ActivePir activePir : data.values()){
			if(!activePirMap.containsKey(activePir.type)){
				activePirMap.put(activePir.type, new ArrayList<ActivePir>());
			}
			activePirMap.get(activePir.type).add(activePir);
		}
	}
	
	public static List<ActivePir> getByType(int type){
		return activePirMap.get(type);
	}
	
	public void init(ActivePir pir) {
		pir.unlock = new int[2];
	}
	
	@Override
	public void load(ActivePir pir) {
		
	}
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,ActivePir pir){
	
	}
	
	/**
	 *自定义解析  v2
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,ActivePir pir){
		pir.v2 = Integer.parseInt(conf);
	}
	
	
	/**
	 *自定义解析  unlock
	 */
	@ConfParse("unlock")
	public void unlockPares(String conf,ActivePir pir){
		String[] unlockStr = conf.split(",");
		int [] arr = pir.getUnlock();
		arr[0] = Integer.valueOf(unlockStr[0]);
		arr[1] = Integer.valueOf(unlockStr[1]);
	}
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,ActivePir pir){
	
	}
	
	@Override
	public ActivePir newPri() {
		return new ActivePir();
	}
	
	public static ActivePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ActivePirFactory instance = new ActivePirFactory(); 
	
	
	public static ActivePirFactory getInstance() {
		return instance;
	}
}