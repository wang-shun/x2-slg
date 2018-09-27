package com.xgame.config.armyScience;

import java.util.ArrayList;
import java.util.List;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-02-18 17:01:16 
 */
public class ArmySciencePirFactory extends BasePriFactory<ArmySciencePir>{
	

	public void init(ArmySciencePir pir) {
		pir.exp = new ArrayList<Integer>();
	}
	
	@Override
	public void load(ArmySciencePir pir) {
		
	}
	
	
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,ArmySciencePir pir){
		
	}
	
	
	
	/**
	 *自定义解析  exp
	 */
	@ConfParse("exp")
	public void expPares(String conf,ArmySciencePir pir){
		List<Integer> list = pir.getExp();
		String[] value = conf.split(";");
		for(int i = 0;i<value.length;i++){
			list.add(Integer.valueOf(value[i]));
		}
	}
	
	/**
	 *自定义解析  add
	 */
	@ConfParse("add")
	public void addPares(String conf,ArmySciencePir pir){
		
	}
	
	@Override
	public ArmySciencePir newPri() {
		return new ArmySciencePir();
	}
	
	public static ArmySciencePir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmySciencePirFactory instance = new ArmySciencePirFactory(); 
	
	
	public static ArmySciencePirFactory getInstance() {
		return instance;
	}
}