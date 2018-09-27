package com.xgame.config.dailyTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.item.ItemBox;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-24 10:17:41 
 */
public class DailyTaskPirFactory extends BasePriFactory<DailyTaskPir>{
	

	public void init(DailyTaskPir pir) {
		
	}
	
	@Override
	public void load(DailyTaskPir pir) {
		
	}
	
	
	public static final List<ItemBox> boxs = new ArrayList<>();
	
	
	@Override
	public void loadOver(String programConfigPath, Map<Integer,DailyTaskPir> data){
		boxs.clear();
		for(DailyTaskPir dt : data.values()){
			ItemBox box = new ItemBox();
			box.settId(dt.id);
			box.setOdds(dt.chance);
			boxs.add(box);
		}
	}
	
	/**
	 *自定义解析  rewards
	 */
	@ConfParse("rewards")
	public void rewardsPares(String conf,DailyTaskPir pir){
		
	}
	
	
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,DailyTaskPir pir){
		
	}
	
	@Override
	public DailyTaskPir newPri() {
		return new DailyTaskPir();
	}
	
	public static DailyTaskPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final DailyTaskPirFactory instance = new DailyTaskPirFactory(); 
	
	
	public static DailyTaskPirFactory getInstance() {
		return instance;
	}
}