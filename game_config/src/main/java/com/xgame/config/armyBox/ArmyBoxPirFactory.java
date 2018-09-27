package com.xgame.config.armyBox;

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
public class ArmyBoxPirFactory extends BasePriFactory<ArmyBoxPir>{
	

	public void init(ArmyBoxPir pir) {
		pir.addBoxExp = new ArrayList<Integer>();
	}
	
	@Override
	public void load(ArmyBoxPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,ArmyBoxPir pir){
		
	}
	
	
	/**
	 *自定义解析  addBoxExp
	 */
	@ConfParse("addBoxExp")
	public void addBoxExpPares(String conf,ArmyBoxPir pir){
		List<Integer> list = pir.getAddBoxExp();
		String[] split = conf.split(";");
		for(int i=0;i<split.length;i++){
			list.add(Integer.parseInt(split[i]));
		}
	}
	
	@Override
	public ArmyBoxPir newPri() {
		return new ArmyBoxPir();
	}
	
	public static ArmyBoxPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyBoxPirFactory instance = new ArmyBoxPirFactory(); 
	
	
	public static ArmyBoxPirFactory getInstance() {
		return instance;
	}
}