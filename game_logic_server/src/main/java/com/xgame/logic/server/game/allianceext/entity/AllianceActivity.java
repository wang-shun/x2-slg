package com.xgame.logic.server.game.allianceext.entity;

import com.xgame.config.armyActive.ArmyActivePir;
import com.xgame.config.armyActive.ArmyActivePirFactory;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 *  联盟活跃度
 * @author jacky.jiang
 *
 */
public class AllianceActivity implements JBaseTransform{
	
	private int steel;
	
	private int rare;
	
	private int oil;
	
	private int gold;
	
	private int activityLv;
	
	private int activityScore;

	public int getSteel() {
		return steel;
	}

	public void setSteel(int steel) {
		this.steel = steel;
	}

	public int getRare() {
		return rare;
	}

	public void setRare(int rare) {
		this.rare = rare;
	}

	public int getOil() {
		return oil;
	}

	public void setOil(int oil) {
		this.oil = oil;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getActivityLv() {
		return activityLv;
	}

	public void setActivityLv(int activityLv) {
		this.activityLv = activityLv;
	}

	public int getActivityScore() {
		return activityScore;
	}

	public void setActivityScore(int activityScore) {
		this.activityScore = activityScore;
	}
	
	/**
	 * 刷新联盟活跃信息
	 */
	public synchronized void refresh(){
		this.steel = 0;
		this.rare = 0;
		this.oil = 0;
		this.gold = 0;
		ArmyActivePir armyActivePir = ArmyActivePirFactory.get(this.activityLv);
		if(null != armyActivePir){
			this.activityScore = (this.activityScore - armyActivePir.getActive())<0?0:(this.activityScore - armyActivePir.getActive());
			if(this.activityScore < armyActivePir.getNeedExp()){
				this.activityLv = (this.activityLv - 1) < 1?1:(this.activityLv - 1);
			}
		}
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("steel", steel);
		jbaseData.put("rare", rare);
		jbaseData.put("oil", oil);
		jbaseData.put("gold", gold);
		jbaseData.put("activityLv", activityLv);
		jbaseData.put("activityScore", activityScore);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.steel = jBaseData.getInt("steel",0);
		this.rare = jBaseData.getInt("rare",0);
		this.oil = jBaseData.getInt("oil",0);
		this.gold = jBaseData.getInt("gold",0);
		this.activityLv = jBaseData.getInt("activityLv",0);
		this.activityScore = jBaseData.getInt("activityScore",0);
	}

}
