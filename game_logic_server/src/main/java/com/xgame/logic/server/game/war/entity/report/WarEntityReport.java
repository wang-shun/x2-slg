package com.xgame.logic.server.game.war.entity.report;

import java.util.ArrayList;
import java.util.List;


/**
 * 士兵战报
 * @author jacky.jiang
 *
 */
public class WarEntityReport {
	
	/**
	 * 进攻方士兵信息
	 */
	private List<WarSoldierInfo> attackSoldierBean = new ArrayList<WarSoldierInfo>();
	
	/**
	 * 防守方士兵信息
	 */
	private List<WarSoldierInfo> defendSoldierBean = new ArrayList<WarSoldierInfo>();
	
	/**
	 * 建筑战报信息
	 */
	private List<WarBuildingInfo> buildingReport = new ArrayList<WarBuildingInfo>();

	public List<WarSoldierInfo> getAttackSoldierBean() {
		return attackSoldierBean;
	}

	public void setAttackSoldierBean(List<WarSoldierInfo> attackSoldierBean) {
		this.attackSoldierBean = attackSoldierBean;
	}

	public List<WarSoldierInfo> getDefendSoldierBean() {
		return defendSoldierBean;
	}

	public void setDefendSoldierBean(List<WarSoldierInfo> defendSoldierBean) {
		this.defendSoldierBean = defendSoldierBean;
	}

	public List<WarBuildingInfo> getBuildingReport() {
		return buildingReport;
	}

	public void setBuildingReport(List<WarBuildingInfo> buildingReport) {
		this.buildingReport = buildingReport;
	}
	
}
