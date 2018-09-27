package com.xgame.logic.server.game.country.entity;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 *
 *2017-1-11  18:52:02
 *@author ye.yuan
 *
 */
public class BlockDeleteInfo implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = -976338635993086253L;

	private int sid;
	
	private int uid;
	
	private int removeTime;
	
	
	public BlockDeleteInfo() {
		
	}
	
	public BlockDeleteInfo(int sid,int uid,int removeTime) {
		this.sid = sid;
		this.uid = uid;
		this.removeTime = removeTime;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getRemoveTime() {
		return removeTime;
	}

	public void setRemoveTime(int removeTime) {
		this.removeTime = removeTime;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("sid", sid);
		jBaseData.put("uid", uid);
		jBaseData.put("removeTime", removeTime);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.sid = jBaseData.getInt("sid", 0);
		this.uid = jBaseData.getInt("uid", 0);
		this.removeTime = jBaseData.getInt("removeTime", 0);
	}
	
}
