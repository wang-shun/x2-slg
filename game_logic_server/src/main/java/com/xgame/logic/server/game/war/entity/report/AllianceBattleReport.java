package com.xgame.logic.server.game.war.entity.report;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 战斗报告
 * @author jacky.jiang
 *
 */
public class AllianceBattleReport implements JBaseTransform {

	/**
	 * 进攻方id
	 */
	private long attackId;
	/**
	 * 进攻方名称
	 */
	private String attackName;
	/**
	 * 防守方id
	 */
	private long defendId;
	/**
	 * 防守方名称
	 */
	private String defendName;
	/**
	 * 战斗结果
	 */
	private int result;
	/**
	 * 战斗附件
	 */
	private String addition; 
	/**
	 * 创建时间
	 */
	private long createTime;
	
	/**
	 * 详情
	 */
	private String info;
	
	public long getAttackId() {
		return attackId;
	}
	public void setAttackId(long attackId) {
		this.attackId = attackId;
	}
	public String getAttackName() {
		return attackName;
	}
	public void setAttackName(String attackName) {
		this.attackName = attackName;
	}
	public long getDefendId() {
		return defendId;
	}
	public void setDefendId(long defendId) {
		this.defendId = defendId;
	}
	public String getDefendName() {
		return defendName;
	}
	public void setDefendName(String defendName) {
		this.defendName = defendName;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getAddition() {
		return addition;
	}
	public void setAddition(String addition) {
		this.addition = addition;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("attackId", attackId);
		jbaseData.put("attackName", attackName);
		jbaseData.put("defendId", defendId);
		jbaseData.put("defendName", defendName);
		jbaseData.put("result", result);
		jbaseData.put("addition", addition);
		jbaseData.put("createTime", createTime);
		jbaseData.put("info", info);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.attackId = jBaseData.getLong("attackId", 0);
		this.attackName = jBaseData.getString("attackName","");
		this.defendId = jBaseData.getLong("defendId", 0);
		this.defendName = jBaseData.getString("defendName","");
		this.result = jBaseData.getInt("result", 0);
		this.addition = jBaseData.getString("addition","");
		this.createTime = jBaseData.getLong("createTime", 0);
		this.info = jBaseData.getString("info","");
	}
}

