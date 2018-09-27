package com.xgame.config.monsterBorn;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:38 
 */
public class MonsterBornPirFactory extends BasePriFactory<MonsterBornPir>{
	

	public void init(MonsterBornPir pir) {
		
	}
	
	@Override
	public void load(MonsterBornPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  groupId
	 */
	@ConfParse("groupId")
	public void groupIdPares(String conf,MonsterBornPir pir){
		
	}
	
	
	
	
	
	
	
	
	
	@Override
	public MonsterBornPir newPri() {
		return new MonsterBornPir();
	}
	
	public static MonsterBornPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final MonsterBornPirFactory instance = new MonsterBornPirFactory(); 
	
	
	public static MonsterBornPirFactory getInstance() {
		return instance;
	}
}