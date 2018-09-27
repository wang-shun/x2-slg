package com.xgame.config.C100101;

import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 13:02:34 
 */
public class C100101PirFactory extends BasePriFactory<C100101Pir>{
	

	public void init(C100101Pir pir) {
		
	}
	
	@Override
	public void load(C100101Pir pir) {
		
	}
	
	
//	
//	
//	
//	Class T;
//	try {
//		T = Class.forName("com.xgame.config.C100101.C100101PirFactory");
//		Class sub = Class.forName("com.xgame.config.C100101.C100101Pir");
//		Object t = T.getMethod("getInstance", T.getClasses()).invoke(null,null);
//		Map<Integer, Object> map1 =  (Map)t.getClass().getMethod("getFactory", null).invoke(t, null);
//		for(Object o : map1.values()){
//			int buildingId = (int)o.getClass().getMethod("getBuildingId",null).invoke(o,null);
//			System.err.println(buildingId);
////			/**﻿建筑id（building表）*/
////			public int getBuildingId(){
////				return buildingId;
////			}
////			/**坐标X*/
////			public int getX(){
////				return x;
////			}
////			/**坐标Y*/
////			public int getY(){
////				return y;
////			}
////			/**建筑物等级*/
////			public int getBuildingLv(){
////				return buildingLv;
////			}
////			/**资源仓库中含有资源的数量*/
////			public int getResource(){
////				return resource;
////			}
////			/**驻军怪物组id（对应copyMonster表,怪物id）*/
////			public int getMonsterId(){
////				return monsterId;
////			}
////			/**驻军数量*/
////			public int getMonsterNum(){
////				return monsterNum;
////			}
//		}
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
	
	
	@Override
	public C100101Pir newPri() {
		return new C100101Pir();
	}
	
	public static C100101Pir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final C100101PirFactory instance = new C100101PirFactory(); 
	
	
	public static C100101PirFactory getInstance() {
		return instance;
	}
}