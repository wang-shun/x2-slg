package com.xgame.config.copyPoint;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:20 
 */
public class CopyPointPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**所属章节*/
	int chapterId;
	/**名称*/
	int name;
	/**副本类型（1主线副本，2精英副本，3爬塔）*/
	int copyType;
	/**节点类型（1普通2精英3BOSS）*/
	int type;
	/**完成章节解锁*/
	int unlock;
	/**前置节点*/
	int requirePoint;
	/**对应副本数据（txt文件名）*/
	Object copyFile;
	/**建筑物属性修正*/
	double buildingTrim;
	/**怪物属性修正*/
	double monsterTrim;
	/**消耗体力*/
	int physical;
	/**可领奖次数（不再使用）*/
	int number;
	/**展示奖励（选中和扫荡展示）*/
	Object showRewards;
	/**首次胜利额外奖励（玩家一辈子只能获得一次）*/
	Object fristRewards;
	/**随机奖励1(100006_500,100001_200,100004_200;2表示掉落100006到的概率为500/（500+200+200），2表示随机2次）*/
	Object rewards1;
	/**随机奖励2（100006_500_1000表是获得500个100006的概率是万分之1000）*/
	Object rewards2;
	/**头像*/
	Object head;
	/**图标（CustomWeapon）*/
	Object icon;
	/**节点坐标*/
	Object coordinate;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**所属章节*/
	public int getChapterId(){
		return chapterId;
	}
	/**名称*/
	public int getName(){
		return name;
	}
	/**副本类型（1主线副本，2精英副本，3爬塔）*/
	public int getCopyType(){
		return copyType;
	}
	/**节点类型（1普通2精英3BOSS）*/
	public int getType(){
		return type;
	}
	/**完成章节解锁*/
	public int getUnlock(){
		return unlock;
	}
	/**前置节点*/
	public int getRequirePoint(){
		return requirePoint;
	}
	/**对应副本数据（txt文件名）*/
	@SuppressWarnings("unchecked")
	public <T> T getCopyFile(){
		return (T)copyFile;
	}
	/**建筑物属性修正*/
	public double getBuildingTrim(){
		return buildingTrim;
	}
	/**怪物属性修正*/
	public double getMonsterTrim(){
		return monsterTrim;
	}
	/**消耗体力*/
	public int getPhysical(){
		return physical;
	}
	/**可领奖次数（不再使用）*/
	public int getNumber(){
		return number;
	}
	/**展示奖励（选中和扫荡展示）*/
	@SuppressWarnings("unchecked")
	public <T> T getShowRewards(){
		return (T)showRewards;
	}
	/**首次胜利额外奖励（玩家一辈子只能获得一次）*/
	@SuppressWarnings("unchecked")
	public <T> T getFristRewards(){
		return (T)fristRewards;
	}
	/**随机奖励1(100006_500,100001_200,100004_200;2表示掉落100006到的概率为500/（500+200+200），2表示随机2次）*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards1(){
		return (T)rewards1;
	}
	/**随机奖励2（100006_500_1000表是获得500个100006的概率是万分之1000）*/
	@SuppressWarnings("unchecked")
	public <T> T getRewards2(){
		return (T)rewards2;
	}
	/**头像*/
	@SuppressWarnings("unchecked")
	public <T> T getHead(){
		return (T)head;
	}
	/**图标（CustomWeapon）*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	/**节点坐标*/
	@SuppressWarnings("unchecked")
	public <T> T getCoordinate(){
		return (T)coordinate;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}