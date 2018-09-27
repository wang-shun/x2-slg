package com.xgame.logic.server.game.task.enity;

import io.protostuff.Tag;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

public class ActiveItem implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	private int id;
	@Tag(2)
	private int finiCount;	//完成次数
	@Tag(3)
	private boolean isComplete;	//是否已完成任务
	@Tag(4)
	private int flag;	//辅助标识
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFiniCount() {
		return finiCount;
	}
	public void setFiniCount(int finiCount) {
		this.finiCount = finiCount;
	}
	public boolean isComplete() {
		return isComplete;
	}
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("finiCount", finiCount);
		jbaseData.put("flag", flag);
		return jbaseData;
	}
	
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getInt("id", 0);
		this.finiCount = jBaseData.getInt("finiCount", 0);
		this.flag = jBaseData.getInt("flag", 0);
	}
}
