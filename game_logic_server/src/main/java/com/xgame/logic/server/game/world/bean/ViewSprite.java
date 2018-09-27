package com.xgame.logic.server.game.world.bean;

import com.xgame.logic.server.game.world.entity.Vector2;


/**
 *
 *2016-9-14  10:09:40
 *@author ye.yuan
 *
 */
public class ViewSprite {
	
	private long uid;
	private String gangName;
	private String name;
	private int spriteType;
	private int level;
	private Vector2 loaction;
	private boolean isShow;
	private int showType;
	private int serverId;
	

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getGangName() {
		return gangName;
	}

	public void setGangName(String gangName) {
		this.gangName = gangName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
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

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
}

