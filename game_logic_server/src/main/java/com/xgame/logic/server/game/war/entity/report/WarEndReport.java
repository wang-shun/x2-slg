package com.xgame.logic.server.game.war.entity.report;

import java.util.ArrayList;
import java.util.List;





/**
 * 战报
 * @author jacky.jiang
 *
 */
public class WarEndReport {

	/**获胜一方id*/
	private long winUid;
	/**摧毁程度：0-100*/
	private int destroyLevel;
	
	/**
	 * 士兵的战报
	 */
	private WarEntityReport warEntityReport = new WarEntityReport();

	/**
	 * 玩家战报
	 */
	private List<WarPlayerReport> warPlayerReport = new ArrayList<>();
	
	public long getWinUid() {
		return winUid;
	}
	public void setWinUid(long winUid) {
		this.winUid = winUid;
	}
	public int getDestroyLevel() {
		return destroyLevel;
	}
	
	public void setDestroyLevel(int destroyLevel) {
		this.destroyLevel = destroyLevel;
	}
	
	public WarEntityReport getWarEntityReport() {
		return warEntityReport;
	}
	public void setWarEntityReport(WarEntityReport warEntityReport) {
		this.warEntityReport = warEntityReport;
	}
	public List<WarPlayerReport> getWarPlayerReport() {
		return warPlayerReport;
	}
	public void setWarPlayerReport(List<WarPlayerReport> warPlayerReport) {
		this.warPlayerReport = warPlayerReport;
	}
	
}
