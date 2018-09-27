package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 
 * @author jacky.jiang
 *
 */
public class ActiveInfo implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Tag(1)
	private int value;	//获得的活跃值， 可算出来
	
	@Tag(2)
	private Map<Integer, RewardsStatus> rewards; //已激活宝箱
	
	@Tag(3)
	private Map<Integer, ActiveItem> activeItems; //活跃完成状态
	
	@Tag(4)
	private long time; //刷新时间
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Map<Integer, RewardsStatus> getRewards() {
		return rewards;
	}

	public void setRewards(Map<Integer, RewardsStatus> rewards) {
		this.rewards = rewards;
	}

	public Map<Integer, ActiveItem> getActiveItems() {
		return activeItems;
	}

	public void setActiveItems(Map<Integer, ActiveItem> activeItems) {
		this.activeItems = activeItems;
	}
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public ActiveInfo() {
		rewards = new ConcurrentHashMap<>();
		activeItems = new ConcurrentHashMap<>();
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("value",value);
		
		List<JBaseData> rewardsJBaseList = new ArrayList<JBaseData>();
		for(Map.Entry<Integer, RewardsStatus> entry:rewards.entrySet()) {
			rewardsJBaseList.add(((JBaseTransform)entry.getValue()).toJBaseData());
		}
		jbaseData.put("rewards", rewardsJBaseList);
		
		List<JBaseData> activeItemsJBaseList = new ArrayList<JBaseData>();
		for(Map.Entry<Integer, ActiveItem> entry:activeItems.entrySet()) {
			activeItemsJBaseList.add(((JBaseTransform)entry.getValue()).toJBaseData());
		}
		jbaseData.put("activeItems", activeItemsJBaseList);
		
		jbaseData.put("time",time);
		return jbaseData;
	}
	
	
	public void fromJBaseData(JBaseData jBaseData) {
		this.value = jBaseData.getInt("value",0);
		List<JBaseData> rewardsList = jBaseData.getSeqBaseData("rewards");
		for(JBaseData jBaseData2 : rewardsList) {
			RewardsStatus rewardsStatus = new RewardsStatus();
			rewardsStatus.fromJBaseData(jBaseData2);
			this.rewards.put(rewardsStatus.getId(), rewardsStatus);
		}
		
		List<JBaseData> activeItemsList = jBaseData.getSeqBaseData("activeItems");
		for(JBaseData jBaseData2 : activeItemsList) {
			ActiveItem activeItem = new ActiveItem();
			activeItem.fromJBaseData(jBaseData2);
			this.activeItems.put(activeItem.getId(), activeItem);
		}
		
		this.time = jBaseData.getLong("time", 0);
	}
}
