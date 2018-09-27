package com.xgame.logic.server.game.radar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.constant.BuildingConstant;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarBuilding;


/**
 *
 *2017-1-06  11:22:54
 *@author ye.yuan
 *
 */
public class InvestigateData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1722905991764321792L;

	/**
	 * 防御类型建筑
	 */
	private List<WarBuilding> warBuildList = new ArrayList<>();
	
	/**
	 * 雷达侦查的目标类型
	 */
	private int spriteType;
	
	/**
	 * 
	 */
	private Vector2Bean position;
	
	/**
	 * 防守驻军
	 */
	private List<DefendSoldierBean> radrSoldiers = new ArrayList<>();

	public int getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}

	public List<WarBuilding> getWarBuildList() {
		return warBuildList;
	}

	public void setWarBuildList(List<WarBuilding> warBuildList) {
		this.warBuildList = warBuildList;
	}

	public List<DefendSoldierBean> getRadrSoldiers() {
		return radrSoldiers;
	}

	public void setRadrSoldiers(List<DefendSoldierBean> radrSoldiers) {
		this.radrSoldiers = radrSoldiers;
	}

	public Vector2Bean getPosition() {
		return position;
	}

	public void setPosition(Vector2Bean position) {
		this.position = position;
	}
	
	/**
	 * 获取防御塔类型的建筑
	 * @return
	 */
	public List<WarBuilding> getTowerBuilds() {
		List<WarBuilding> towerBuilds = new ArrayList<>();
		for(WarBuilding warBuilding : warBuildList) {
			if(ArrayUtils.contains(BuildingConstant.TOWER_BUILDING, warBuilding.building.sid)){
				towerBuilds.add(warBuilding);
			}
		}
		return towerBuilds;
	}
	
	/**
	 * 根据uid获取唯一建筑
	 * @param uid
	 * @return
	 */
	public WarBuilding getWarBuild(int uid) {
		for(WarBuilding warBuilding  : warBuildList) {
			if(warBuilding.building.uid == uid) {
				return warBuilding;
			}
		}
		return null;
	}
	
}
