package com.xgame.config.copy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:37 
 */
public class CopyPirFactory extends BasePriFactory<CopyPir>{
	

	public void init(CopyPir pir) {
		
	}
	
	@Override
	public void load(CopyPir pir) {
		
	}

	public static final List<CopyPir> initCopyPir = new ArrayList<>();
	
	/**
	 * Alex
	 * 当前配置加载完成后的处理
	 * */
	@Override
	public void loadOver(String programConfigPath, Map<Integer,CopyPir> data){
		for(CopyPir copyPir : data.values()){
			if(copyPir.getUnlock() == 0){
				initCopyPir.add(copyPir);
			}
		}
	}
	
	
	
	
	
	/**
	 *自定义解析  points
	 */
	@ConfParse("points")
	public void pointsPares(String conf,CopyPir pir){
		
	}
	
	/**
	 *自定义解析  rewardNeed
	 */
	@ConfParse("rewardNeed")
	public void rewardNeedPares(String conf,CopyPir pir){
		
	}
	
	/**
	 *自定义解析  rewards1
	 */
	@ConfParse("rewards1")
	public void rewards1Pares(String conf,CopyPir pir){
		
	}
	
	/**
	 *自定义解析  rewards2
	 */
	@ConfParse("rewards2")
	public void rewards2Pares(String conf,CopyPir pir){
		
	}
	
	/**
	 *自定义解析  rewards3
	 */
	@ConfParse("rewards3")
	public void rewards3Pares(String conf,CopyPir pir){
		
	}
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,CopyPir pir){
		
	}
	
	/**
	 *自定义解析  image
	 */
	@ConfParse("image")
	public void imagePares(String conf,CopyPir pir){
		
	}
	
	@Override
	public CopyPir newPri() {
		return new CopyPir();
	}
	
	public static CopyPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CopyPirFactory instance = new CopyPirFactory(); 
	
	
	public static CopyPirFactory getInstance() {
		return instance;
	}
}