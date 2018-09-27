package com.xgame.logic.server.game.world.entity.sprite;

/**
 * 联盟建筑信息
 * @author jacky.jiang
 *
 */
public class AllianceBuildSprite extends WorldSprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5399047188845808763L;

	/**
	 * 建筑uid
	 */
	private String buildUid;
	
	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 建筑等级
	 */
	private int level;

	public String getBuildUid() {
		return buildUid;
	}

	public void setBuildUid(String buildUid) {
		this.buildUid = buildUid;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
