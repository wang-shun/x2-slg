package com.xgame.logic.server.game.alliance.enity;

import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.war.entity.report.AllianceBattleReport;

/**
 * 联盟军情
 * @author jacky.jiang
 *
 */
public class AllianceReport extends AbstractEntity<Long> implements JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4209240332164443448L;

	/**
	 * 联盟id
	 */
	private long allianceId;
	
	/**
	 * 联盟战报
	 */
	private List<AllianceBattleReport> battleReportList = new ArrayList<AllianceBattleReport>();
	
	@Override
	public Long getId() {
		return allianceId;
	}

	@Override
	public void setId(Long k) {
		this.allianceId = k;
	}

	public long getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(long allianceId) {
		this.allianceId = allianceId;
	}

	public List<AllianceBattleReport> getBattleReportList() {
		return battleReportList;
	}

	public void setBattleReportList(List<AllianceBattleReport> battleReportList) {
		this.battleReportList = battleReportList;
	}
	
	public void addBattleReport(AllianceBattleReport battleReport) {
		this.battleReportList.add(battleReport);
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("allianceId", allianceId);
		List<JBaseData> battleReportListList = new ArrayList<JBaseData>();
		for (int i = 0; i < battleReportList.size(); i++) {
			Object obj = battleReportList.get(i);
			battleReportListList.add(((JBaseTransform)obj).toJBaseData());
		}
		jbaseData.put("battleReportList", battleReportListList);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.allianceId = jBaseData.getLong("allianceId", 0);
		List<JBaseData> jBaseDatas = jBaseData.getSeqBaseData("battleReportList");
		for(JBaseData jBaseData2 : jBaseDatas) {
			AllianceBattleReport allianceBattleReport = new AllianceBattleReport();
			allianceBattleReport.fromJBaseData(jBaseData2);
			battleReportList.add(allianceBattleReport);
		}
	}
}
