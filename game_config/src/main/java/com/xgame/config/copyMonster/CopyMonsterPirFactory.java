package com.xgame.config.copyMonster;

import java.util.ArrayList;
import java.util.List;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:37 
 */
public class CopyMonsterPirFactory extends BasePriFactory<CopyMonsterPir>{
	

	public void init(CopyMonsterPir pir) {
		
	}
	
	@Override
	public void load(CopyMonsterPir pir) {
		
	}
	
	
	
	
	/**
	 *自定义解析  peijianIds
	 */
	@ConfParse("peijianIds")
	public void peijianIdsPares(String conf,CopyMonsterPir pir){
		String[] arr = conf.split(";");
		List<Integer> list = new ArrayList<>();
		for(String p : arr){
			list.add(Integer.parseInt(p));
		}
		pir.peijianIds = list;
	}
	
	
	@Override
	public CopyMonsterPir newPri() {
		return new CopyMonsterPir();
	}
	
	public static CopyMonsterPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CopyMonsterPirFactory instance = new CopyMonsterPirFactory(); 
	
	
	public static CopyMonsterPirFactory getInstance() {
		return instance;
	}
}