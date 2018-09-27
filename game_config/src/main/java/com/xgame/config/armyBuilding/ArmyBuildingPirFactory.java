package com.xgame.config.armyBuilding;

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
public class ArmyBuildingPirFactory extends BasePriFactory<ArmyBuildingPir>{
	

	public void init(ArmyBuildingPir pir) {
		pir.LvUpArmyMoney = new ArrayList<Integer>();
	}
	
	@Override
	public void load(ArmyBuildingPir pir) {
		
	}
	
	
	
	
	
	
	/**
	 *自定义解析  v1
	 */
	@ConfParse("v1")
	public void v1Pares(String conf,ArmyBuildingPir pir){
		
	}
	
	/**
	 *自定义解析  v2
	 */
	@ConfParse("v2")
	public void v2Pares(String conf,ArmyBuildingPir pir){
		
	}
	
	
	
	
	/**
	 *自定义解析  showID1
	 */
	@ConfParse("showID1")
	public void showID1Pares(String conf,ArmyBuildingPir pir){
		
	}
	
	/**
	 *自定义解析  showID2
	 */
	@ConfParse("showID2")
	public void showID2Pares(String conf,ArmyBuildingPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  LvUpArmyMoney
	 */
	@ConfParse("LvUpArmyMoney")
	public void LvUpArmyMoneyPares(String conf,ArmyBuildingPir pir){
		List<Integer> list = pir.getLvUpArmyMoney();
		String[] value = conf.split(";");
		for(int i = 0;i<value.length;i++){
			list.add(Integer.valueOf(value[i]));
		}
	}
	
	@Override
	public ArmyBuildingPir newPri() {
		return new ArmyBuildingPir();
	}
	
	public static ArmyBuildingPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final ArmyBuildingPirFactory instance = new ArmyBuildingPirFactory(); 
	
	
	public static ArmyBuildingPirFactory getInstance() {
		return instance;
	}
}