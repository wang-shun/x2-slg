package com.xgame.logic.server.game.country.structs.build.Radar.data;

import java.io.Serializable;

import com.xgame.logic.server.core.utils.geometry.data.Vector2;

import io.protostuff.Tag;

/**
 *雷达建筑父类
 *2016-12-28  12:03:58
 *@author ye.yuan
 *
 */
public abstract class RadarBuild implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	protected int uid;
	@Tag(2)
	protected int level;
	@Tag(3)
	protected Vector2 loaction;
	@Tag(100)
	protected int sid;
	
	public RadarBuild(int sid,int uid, int level, Vector2 loaction) {
		this.sid = sid;
		this.uid = uid;
		this.level = level;
		this.loaction = loaction;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int id) {
		this.uid = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Vector2 getLoaction() {
		return loaction;
	}
	public void setLoaction(Vector2 loaction) {
		this.loaction = loaction;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	

}
