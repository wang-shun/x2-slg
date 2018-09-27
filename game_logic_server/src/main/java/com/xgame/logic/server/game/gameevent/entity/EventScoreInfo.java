package com.xgame.logic.server.game.gameevent.entity;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 事件积分详细信息
 * @author dingpeng.qu
 *
 */
public class EventScoreInfo implements JBaseTransform {
	
	/**
	 * 青铜积分
	 */
	private int score1;
	/**
	 * 白银积分
	 */
	private int score2;
	/**
	 * 黄金积分
	 */
	private int score3;
	/**
	 * 青铜奖励
	 */
	private String rewards1;
	/**
	 * 白银奖励
	 */
	private String rewards2;
	/**
	 * 黄金奖励
	 */
	private String rewards3;
	/**
	 * 青铜宝箱状态
	 */
	private int statu1;
	/**
	 * 白银宝箱状态
	 */
	private int statu2;
	/**
	 * 黄金宝箱状态
	 */
	private int statu3;
	/**
	 * 当前事件积分
	 */
	private int currentScore;
	
	public int getScore1() {
		return score1;
	}
	public void setScore1(int score1) {
		this.score1 = score1;
	}
	public int getScore2() {
		return score2;
	}
	public void setScore2(int score2) {
		this.score2 = score2;
	}
	public int getScore3() {
		return score3;
	}
	public void setScore3(int score3) {
		this.score3 = score3;
	}
	public String getRewards1() {
		return rewards1;
	}
	public void setRewards1(String rewards1) {
		this.rewards1 = rewards1;
	}
	public String getRewards2() {
		return rewards2;
	}
	public void setRewards2(String rewards2) {
		this.rewards2 = rewards2;
	}
	public String getRewards3() {
		return rewards3;
	}
	public void setRewards3(String rewards3) {
		this.rewards3 = rewards3;
	}
	public int getStatu1() {
		return statu1;
	}
	public void setStatu1(int statu1) {
		this.statu1 = statu1;
	}
	public int getStatu2() {
		return statu2;
	}
	public void setStatu2(int statu2) {
		this.statu2 = statu2;
	}
	public int getStatu3() {
		return statu3;
	}
	public void setStatu3(int statu3) {
		this.statu3 = statu3;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("score1", score1);
		jbaseData.put("score2", score2);
		jbaseData.put("score3", score3);
		jbaseData.put("rewards1", rewards1);
		jbaseData.put("rewards2", rewards2);
		jbaseData.put("rewards3", rewards3);
		jbaseData.put("statu1", statu1);
		jbaseData.put("statu2", statu2);
		jbaseData.put("statu3", statu3);
		jbaseData.put("currentScore", currentScore);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.score1 = jBaseData.getInt("score1", 0);
		this.score2 = jBaseData.getInt("score2", 0);
		this.score3 = jBaseData.getInt("score3", 0);
		this.rewards1 = jBaseData.getString("rewards1", "");
		this.rewards2 = jBaseData.getString("rewards2", "");
		this.rewards3 = jBaseData.getString("rewards3", "");
		this.statu1 = jBaseData.getInt("statu1", 0);
		this.statu2 = jBaseData.getInt("statu2", 0);
		this.statu3 = jBaseData.getInt("statu3", 0);
		this.currentScore = jBaseData.getInt("currentScore", 0);
	}
	
}
