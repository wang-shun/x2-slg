package com.xgame.config.copyPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.config.BasePriFactory;
import com.xgame.config.ConfParse;
import com.xgame.config.item.ItemBox;
/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-03-04 09:52:37 
 */
public class CopyPointPirFactory extends BasePriFactory<CopyPointPir>{
	

	public void init(CopyPointPir pir) {
		
	}
	
	@Override
	public void load(CopyPointPir pir) {
		
	}
	
	
	public static final Map<Integer,List<CopyPointPir>> copyMap = new HashMap<>();
	
	
//	public static final Map<Integer,List<CopyPointPir>> initMap = new HashMap<>();
	
	public static final Map<Integer,List<ItemBox>> reward1Boxs = new HashMap<>();
	
	public static final Map<Integer,List<ItemBox>> reward2Boxs = new HashMap<>();
	
	public static final Map<Integer,Integer> reward1Num = new HashMap<>();
	
	/**
	 * Alex
	 * 当前配置加载完成后的处理
	 * */
	@Override
	public void loadOver(String programConfigPath, Map<Integer,CopyPointPir> data){
		reward1Boxs.clear();
		reward2Boxs.clear();
		for(CopyPointPir copyPointPir : data.values()){
//			if(copyPointPir.getRequirePoint() == 0){
//				if(!initMap.containsKey(copyPointPir.getChapterId())){
//					initMap.put(copyPointPir.getChapterId(), new ArrayList<>());
//				}
//				initMap.get(copyPointPir.getChapterId()).add(copyPointPir);
//			}
			if(!copyMap.containsKey(copyPointPir.getChapterId())){
				copyMap.put(copyPointPir.getChapterId(), new ArrayList<>());
			}
			copyMap.get(copyPointPir.getChapterId()).add(copyPointPir);
			
			
			String reward1 = copyPointPir.getRewards1();
			if(reward1 != null){
				String[] arr = reward1.split(";");
				String rewardStr = arr[0];
				int num = Integer.parseInt(arr[1]);
				String[] rewardArr = rewardStr.split(",");
				reward1Num.put(copyPointPir.getId(), num);
				List<ItemBox> boxs1 = new ArrayList<>();
				for(String rewardItem : rewardArr){
					String[] arr1 = rewardItem.split("_");
					ItemBox box = new ItemBox();
					box.settId(Integer.parseInt(arr1[0]));
					box.setNum(Integer.parseInt(arr1[1]));
					box.setOdds(Integer.parseInt(arr1[2]));
					boxs1.add(box);
				}
				reward1Boxs.put(copyPointPir.getId(), boxs1);
			}
			
			String reward2 = copyPointPir.getRewards2();
			if(reward2 != null){
				List<ItemBox> boxs2 = new ArrayList<>();
				String[] arr2 = reward2.split(",");
				for(String item : arr2){
					String[] arr1 = item.split("_");
					ItemBox box = new ItemBox();
					box.settId(Integer.parseInt(arr1[0]));
					box.setNum(Integer.parseInt(arr1[1]));
					box.setOdds(Integer.parseInt(arr1[2]));
					boxs2.add(box);
				}
				reward2Boxs.put(copyPointPir.getId(), boxs2);
			}
		
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 *自定义解析  fristRewards
	 */
	@ConfParse("fristRewards")
	public void fristRewardsPares(String conf,CopyPointPir pir){
		
	}
	
	/**
	 *自定义解析  rewards1
	 */
	@ConfParse("rewards1")
	public void rewards1Pares(String conf,CopyPointPir pir){
		
	}
	
	/**
	 *自定义解析  rewards2
	 */
	@ConfParse("rewards2")
	public void rewards2Pares(String conf,CopyPointPir pir){
		
	}
	
	/**
	 *自定义解析  head
	 */
	@ConfParse("head")
	public void headPares(String conf,CopyPointPir pir){
		
	}
	
	/**
	 *自定义解析  icon
	 */
	@ConfParse("icon")
	public void iconPares(String conf,CopyPointPir pir){
		
	}
	
	/**
	 *自定义解析  coordinate
	 */
	@ConfParse("coordinate")
	public void coordinatePares(String conf,CopyPointPir pir){
		
	}
	
	@Override
	public CopyPointPir newPri() {
		return new CopyPointPir();
	}
	
	public static CopyPointPir get(Object key) {
		return instance.factory.get(key);
	}
	
	/*  single instance*/
	/**
	 * 饿汉单列
	 */
	private static final CopyPointPirFactory instance = new CopyPointPirFactory(); 
	
	
	public static CopyPointPirFactory getInstance() {
		return instance;
	}
}