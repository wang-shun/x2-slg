package com.xgame.logic.server.game.alliance.enity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 联盟头衔
 * @author jacky.jiang
 *
 */
public class AllianceTitle implements JBaseTransform {
	
	private String office1Name;
	private String office2Name;
	private String office3Name;
	private String office4Name;
	
	private String rankOneName;
	private String rankTwoName;
	private String rankThreeName;
	private String rankFourName;
	private String rankFiveName;
	public String getOffice1Name() {
		return office1Name;
	}
	public void setOffice1Name(String office1Name) {
		this.office1Name = office1Name;
	}
	public String getOffice2Name() {
		return office2Name;
	}
	public void setOffice2Name(String office2Name) {
		this.office2Name = office2Name;
	}
	public String getOffice3Name() {
		return office3Name;
	}
	public void setOffice3Name(String office3Name) {
		this.office3Name = office3Name;
	}
	public String getOffice4Name() {
		return office4Name;
	}
	public void setOffice4Name(String office4Name) {
		this.office4Name = office4Name;
	}
	public String getRankOneName() {
		return rankOneName;
	}
	public void setRankOneName(String rankOneName) {
		this.rankOneName = rankOneName;
	}
	public String getRankTwoName() {
		return rankTwoName;
	}
	public void setRankTwoName(String rankTwoName) {
		this.rankTwoName = rankTwoName;
	}
	public String getRankThreeName() {
		return rankThreeName;
	}
	public void setRankThreeName(String rankThreeName) {
		this.rankThreeName = rankThreeName;
	}
	public String getRankFourName() {
		return rankFourName;
	}
	public void setRankFourName(String rankFourName) {
		this.rankFourName = rankFourName;
	}
	public String getRankFiveName() {
		return rankFiveName;
	}
	public void setRankFiveName(String rankFiveName) {
		this.rankFiveName = rankFiveName;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("office1Name", office1Name);
		jbaseData.put("office2Name", office2Name);
		jbaseData.put("office3Name", office3Name);
		jbaseData.put("office4Name", office4Name);
		jbaseData.put("rankOneName", rankOneName);
		jbaseData.put("rankTwoName", rankTwoName);
		jbaseData.put("rankThreeName", rankThreeName);
		jbaseData.put("rankFourName", rankFourName);
		jbaseData.put("rankFiveName", rankFiveName);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.office1Name = jBaseData.getString("office1Name", "");
		this.office2Name = jBaseData.getString("office2Name", "");
		this.office3Name = jBaseData.getString("office3Name", "");
		this.office4Name = jBaseData.getString("office4Name", "");
		this.rankOneName = jBaseData.getString("rankOneName", "");
		this.rankTwoName = jBaseData.getString("rankTwoName", "");
		this.rankThreeName = jBaseData.getString("rankThreeName", "");
		this.rankFourName = jBaseData.getString("rankFourName", "");
		this.rankFiveName = jBaseData.getString("rankFiveName", "");
	}
	
}
