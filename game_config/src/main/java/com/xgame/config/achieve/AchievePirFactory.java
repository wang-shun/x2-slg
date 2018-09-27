package com.xgame.config.achieve;

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
 * @date 2017-03-04 09:52:32 
 */
public class AchievePirFactory extends BasePriFactory<AchievePir>{
	

	public void init(AchievePir pir) {
		
	}
	
	@Override
	public void load(AchievePir pir) {
		
	}
	
	
	public static final Map<Integer,List<Integer>>  achievePirMap = new HashMap<>();
	
	
	/**
	 * Alex
	 * 当前配置加载完成后的处理
	 * */
	@Override
	public void loadOver(String programConfigPath, Map<Integer,AchievePir> data){
		achievePirMap.clear();
		for(AchievePir achievePir : data.values()){
			List<Integer> list = new ArrayList<Integer>();
			list.add(achievePir.getUnlock1());
			list.add(achievePir.getUnlock2());
			list.add(achievePir.getUnlock3());
			achievePirMap.put(achievePir.getId(), list);
		}
	}
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,AchievePir pir){
		
	}
	
	/**
	 *自定义解析  goto
	 */
	@ConfParse("goto")
	public void gotoPares(String conf,AchievePir pir){
		
	}
	
	@Override
	public AchievePir newPri() {
		return new AchievePir();
	}
	
	public static AchievePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final AchievePirFactory instance = new AchievePirFactory(); 
	
	
	public static AchievePirFactory getInstance() {
		return instance;
	}
}