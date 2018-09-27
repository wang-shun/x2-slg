package com.xgame.logic.server.game.gameevent.entity;

import com.xgame.logic.server.core.utils.sort.MapRankable;

public class EventRankable extends MapRankable {
	/**
	 * 事件积分
	 */
	public static final String SCORE = "score";
	
	public static EventRankable valueOf(String name, int score) {
		EventRankable eventRankable = new EventRankable();
		eventRankable.setOwnerId(name);
		eventRankable.setScore(score);
		return eventRankable;
	}
	
	public int getScore() {
		return this.getInt(SCORE);
	}

	public void setScore(int score) {
		this.setValue(SCORE, score);
	}
}
