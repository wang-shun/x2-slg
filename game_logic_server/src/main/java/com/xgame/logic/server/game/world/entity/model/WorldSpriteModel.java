package com.xgame.logic.server.game.world.entity.model;


/**
 * 
 * @author jacky.jiang
 *
 */
public class WorldSpriteModel {
	
	/**
	 * 玩家id
	 */
	private long uid;
	
	/**
	 * x坐标
	 */
	private int x;
	
	/**
	 * y坐标
	 */
	private int y;
	
	/**
	 * 玩家名称
	 */
	private String playerName;
	
	/**
	 * 头像
	 */
	private String headImg;
	
	/**
	 * 联盟id
	 */
	private int allainceId;
	
	/**
	 * 联盟名称
	 */
	private String allianceName;
	
	/**
	 * 联盟名称
	 */
	private String allianceImg;
	
	/**
	 * 资源等级
	 */
	private int resourceLevel;
	
	/**
	 * 资源类型
	 */
	private int resourceType;
	
	/**
	 * 资源数量
	 */
	private int resourceNum;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getAllainceId() {
		return allainceId;
	}

	public void setAllainceId(int allainceId) {
		this.allainceId = allainceId;
	}

	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public String getAllianceImg() {
		return allianceImg;
	}

	public void setAllianceImg(String allianceImg) {
		this.allianceImg = allianceImg;
	}

	public int getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceLevel(int resourceLevel) {
		this.resourceLevel = resourceLevel;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public int getResourceNum() {
		return resourceNum;
	}

	public void setResourceNum(int resourceNum) {
		this.resourceNum = resourceNum;
	}
}
