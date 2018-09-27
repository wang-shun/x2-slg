package com.xgame.config.copy;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:20 
 */
public class CopyPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**名称*/
	int name;
	/**描述*/
	int features;
	/**解锁章节id*/
	int unlock;
	/**节点数量*/
	int num;
	/**包含节点id*/
	Object points;
	/**奖励所需星星数（1档,2档,3档）*/
	Object rewardNeed;
	/**1档奖励*/
	Object rewards1;
	/**2档奖励*/
	Object rewards2;
	/**3档奖励*/
	Object rewards3;
	/**图标*/
	Object icon;
	/**界面地图*/
	Object image;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**名称*/
	public int getName(){
		return name;
	}
	/**描述*/
	public int getFeatures(){
		return features;
	}
	/**解锁章节id*/
	public int getUnlock(){
		return unlock;
	}
	/**节点数量*/
	public int getNum(){
		return num;
	}
	/**包含节点id*/
	@SuppressWarnings("unchecked")
	public <T> T getPoints(){
		return (T)points;
	}
	/**奖励所需星星数（1档,2档,3档）*/
	@SuppressWarnings("unchecked")
	public <T> T getRewardNeed(){
		return (T)rewardNeed;
	}
	/**1档奖励*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards1(){
		return (T)rewards1;
	}
	/**2档奖励*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards2(){
		return (T)rewards2;
	}
	/**3档奖励*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards3(){
		return (T)rewards3;
	}
	/**图标*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	/**界面地图*/
	@SuppressWarnings("unchecked")
	public <T> T getImage(){
		return (T)image;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}