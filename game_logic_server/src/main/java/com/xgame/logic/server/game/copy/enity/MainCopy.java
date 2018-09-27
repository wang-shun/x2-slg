package com.xgame.logic.server.game.copy.enity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 主线副本
 * @author zehong.he
 *
 */
public class MainCopy implements Serializable,JBaseTransform{
	
	public static final int STATE_1 = 1;//进行中
	public static final int STATE_2 = 2;//完成
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4046211058825685069L;

	@Tag(1)
	private int copyId;								//副本ID
	
	@Tag(2)
	private int currCopyPoint;						//当前完成副本节点数
	
	@Tag(3)
	private int star;								//当前获得星星数
	
	@Tag(4)
	private int state;								//0-未解锁；1-进行中；2-已完成
	
	@Tag(5)
	private int rewardBoxFlag;						//宝箱领取记录
	
	@Tag(6)
	private Map<Integer,MainCopyPoint> pointMap = new HashMap<>();	//节点信息
	
	public MainCopy(int copyId,int state,Map<Integer,MainCopyPoint> pointMap){
		this.copyId = copyId;
		this.state = state;
		this.pointMap = pointMap;
	}
	
	public MainCopy(){
		
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("copyId", copyId);
		jbaseData.put("currCopyPoint", currCopyPoint);
		jbaseData.put("star", star);
		jbaseData.put("state", state);
		jbaseData.put("rewardBoxFlag", rewardBoxFlag);
		
		List<JBaseData> pointList = new ArrayList<JBaseData>();
		for(Map.Entry<Integer, MainCopyPoint> entry:pointMap.entrySet()) {
			pointList.add(((JBaseTransform)entry.getValue()).toJBaseData());
		}
		jbaseData.put("points", pointList);
		
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.copyId = jBaseData.getInt("value",0);
		this.currCopyPoint = jBaseData.getInt("currCopyPoint",0);
		this.star = jBaseData.getInt("star",0);
		this.state = jBaseData.getInt("state",0);
		this.rewardBoxFlag = jBaseData.getInt("rewardBoxFlag",0);
		
		List<JBaseData> pointList = jBaseData.getSeqBaseData("points");
		for(JBaseData jBaseData2 : pointList) {
			MainCopyPoint mainCopyPoint = new MainCopyPoint();
			mainCopyPoint.fromJBaseData(jBaseData2);
			this.pointMap.put(mainCopyPoint.getCopyPointId(), mainCopyPoint);
		}
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getCurrCopyPoint() {
		return currCopyPoint;
	}

	public void setCurrCopyPoint(int currCopyPoint) {
		this.currCopyPoint = currCopyPoint;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRewardBoxFlag() {
		return rewardBoxFlag;
	}

	public void setRewardBoxFlag(int rewardBoxFlag) {
		this.rewardBoxFlag = rewardBoxFlag;
	}

	public Map<Integer, MainCopyPoint> getPointMap() {
		return pointMap;
	}

	public void setPointMap(Map<Integer, MainCopyPoint> pointMap) {
		this.pointMap = pointMap;
	}
	
	
}
