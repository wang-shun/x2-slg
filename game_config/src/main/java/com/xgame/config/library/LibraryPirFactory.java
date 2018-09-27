package com.xgame.config.library;

import java.util.HashMap;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2016-12-06 10:25:10 
 */
public class LibraryPirFactory extends BasePriFactory<LibraryPir>{
	
	public void init(LibraryPir pir) {
		
	}
	
	@Override
	public void load(LibraryPir pir) {
		
	}
	
	public static double math(double n,double x,int attrId) {
		LibraryPir libraryPir = instance.factory.get(attrId);
		if(libraryPir!=null){
			return math(n, x, libraryPir);
		}
		return 0;
	}
	
	public static double math(double n,double x,LibraryPir pir) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("n", n);
		hashMap.put("x", x);
		Expression ex = pir.getFormula();
		return (double)ex.execute(hashMap);
	}
	
	/**
	 *自定义解析  name
	 */
	@ConfParse("name")
	public void namePares(String conf,LibraryPir pir){
	
	}
	
	/**
	 *自定义解析  formula
	 */
	@ConfParse("formula")
	public void formulaPares(String conf,LibraryPir pir){
		pir.formula = AviatorEvaluator.compile(conf);
	}
	
	
	@Override
	public LibraryPir newPri() {
		return new LibraryPir();
	}
	
	public static LibraryPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final LibraryPirFactory instance = new LibraryPirFactory(); 
	
	
	public static LibraryPirFactory getInstance() {
		return instance;
	}
}