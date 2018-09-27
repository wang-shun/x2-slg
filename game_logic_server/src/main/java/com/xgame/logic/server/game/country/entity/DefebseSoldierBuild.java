package com.xgame.logic.server.game.country.entity;

import io.protostuff.Tag;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;

/**
 *
 *2016-12-22  14:47:12
 *@author ye.yuan
 *
 */
public class DefebseSoldierBuild extends CountryBuild{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6552966053409760641L;
	
	@Tag(101)
	private SoldierBean soldierBean = new SoldierBean();
	
	@Tag(102)
	private boolean autoPatch;
	
	@Tag(103)
	private boolean autoWork;

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

	public SoldierBean getSoldierBean() {
		return soldierBean;
	}

	public void setSoldierBean(SoldierBean soldierBean) {
		this.soldierBean = soldierBean;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = super.toJBaseData();
		if(soldierBean != null) {
			jBaseData.put("soldierId", soldierBean.soldierId);
			jBaseData.put("num", soldierBean.num);	
		}
		jBaseData.put("autoPatch", autoPatch);
		jBaseData.put("autoWork", autoWork);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		super.fromJBaseData(jBaseData);
		this.soldierBean.soldierId = jBaseData.getLong("soldierId", 0);
		this.soldierBean.num = jBaseData.getInt("num", 0);
		this.autoPatch = jBaseData.getBoolean("autoPatch", false);
		this.autoWork = jBaseData.getBoolean("autoWork", false);
	}

}
