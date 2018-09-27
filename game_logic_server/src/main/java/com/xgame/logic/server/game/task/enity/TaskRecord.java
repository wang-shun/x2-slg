package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.SplitUtil;

/**
 * 任务记录（特殊处理某些任务数据记录）
 * @author zehong.he
 *
 */
public class TaskRecord implements Serializable, JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8262282750745667290L;
	
	/**
	 * 士兵生产记录
	 * key:地盘ID
	 * value:士兵数量
	 */
	@Tag(1)
	private Map<Integer,Integer> productSoldierMap = new HashMap<>();
	
	/**
	 * 野外采集次数
	 */
	@Tag(2)
	private int collectTimes;
	
	/**
	 * 侦查玩家次数
	 */
	@Tag(3)
	private int scoutTimes;
	
	/**
	 * 矿车产量
	 * key:资源类型
	 */
	@Tag(4)
	private Map<Integer,Integer> mineProduct = new HashMap<>();

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("productSoldierMap", SplitUtil.map2String0(productSoldierMap));
		jbaseData.put("collectTimes", collectTimes);
		jbaseData.put("scoutTimes", scoutTimes);
		jbaseData.put("mineProduct", SplitUtil.map2String0(mineProduct));
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		String productSoldierMapStr = jBaseData.getString("productSoldierMap", "");
		this.productSoldierMap = SplitUtil.string2Map0(productSoldierMapStr, productSoldierMap);
		this.collectTimes = jBaseData.getInt("collectTimes", 0);
		this.scoutTimes = jBaseData.getInt("scoutTimes", 0);
		String mineProductStr = jBaseData.getString("mineProduct", "");
		this.mineProduct = SplitUtil.string2Map0(mineProductStr, mineProduct);
	}

	public Map<Integer, Integer> getProductSoldierMap() {
		return productSoldierMap;
	}

	public void setProductSoldierMap(Map<Integer, Integer> productSoldierMap) {
		this.productSoldierMap = productSoldierMap;
	}

	public int getCollectTimes() {
		return collectTimes;
	}

	public void setCollectTimes(int collectTimes) {
		this.collectTimes = collectTimes;
	}

	public int getScoutTimes() {
		return scoutTimes;
	}

	public void setScoutTimes(int scoutTimes) {
		this.scoutTimes = scoutTimes;
	}

	public Map<Integer, Integer> getMineProduct() {
		return mineProduct;
	}

	/**
	 * 更新矿车产量值
	 * @param resourceType
	 * @param newValue
	 * @return
	 */
	public boolean updateMineProduct(int resourceType,int newValue){
		if(!mineProduct.containsKey(resourceType)){
			mineProduct.put(resourceType, newValue);
			return true;
		}else{
			int oldValue = mineProduct.get(resourceType);
			if(oldValue != newValue){
				mineProduct.put(resourceType, newValue);
				return true;
			}else{
				return false;
			}
		}
	}
}
