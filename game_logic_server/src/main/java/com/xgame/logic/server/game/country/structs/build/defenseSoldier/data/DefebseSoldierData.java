package com.xgame.logic.server.game.country.structs.build.defenseSoldier.data;

import java.io.Serializable;

import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;

/**
 *
 * 2016-11-29 19:57:15
 *
 * @author ye.yuan
 *
 */
public class DefebseSoldierData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int buildUid;

	private SoldierBrief soldierBrief;

	private boolean autoPatch;

	private boolean autoWork;

	public int getBuildUid() {
		return buildUid;
	}

	public void setBuildUid(int buildUid) {
		this.buildUid = buildUid;
	}

	public SoldierBrief getSoldierBrief() {
		return soldierBrief;
	}

	public void setSoldierBrief(SoldierBrief soldierBrief) {
		this.soldierBrief = soldierBrief;
	}

	public boolean isAutoPatch() {
		return autoPatch;
	}

	public void setAutoPatch(boolean autoPatch) {
		this.autoPatch = autoPatch;
	}

	public boolean isAutoWork() {
		return autoWork;
	}

	public void setAutoWork(boolean autoWork) {
		this.autoWork = autoWork;
	}

}
