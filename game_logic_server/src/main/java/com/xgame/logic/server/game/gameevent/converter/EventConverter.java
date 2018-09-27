package com.xgame.logic.server.game.gameevent.converter;

import com.xgame.logic.server.game.gameevent.bean.EventBean;
import com.xgame.logic.server.game.gameevent.entity.Event;
import com.xgame.logic.server.game.gameevent.entity.EventScoreInfo;
/**
 * 事件信息converter
 * @author dingpeng.qu
 *
 */
public class EventConverter {
	
	/**
	 * 事件信息
	 * @param alliance
	 * @return
	 */
	public static EventBean converterEventBean(Event event,EventScoreInfo eventScoreInfo) {
		EventBean eventBean = new EventBean();
		eventBean.sid = event.getId();
		eventBean.eventId = event.getEventId();
		eventBean.startTime = event.getStartTime()/1000;
		eventBean.endTime = event.getEndTime()/1000;
		eventBean.score1 = eventScoreInfo.getScore1();
		eventBean.score2 = eventScoreInfo.getScore2();
		eventBean.score3 = eventScoreInfo.getScore3();
		eventBean.rewards1 = eventScoreInfo.getRewards1();
		eventBean.rewards2 = eventScoreInfo.getRewards2();
		eventBean.rewards3 = eventScoreInfo.getRewards3();
		eventBean.statu1 = eventScoreInfo.getStatu1();
		eventBean.statu2 = eventScoreInfo.getStatu2();
		eventBean.statu3 = eventScoreInfo.getStatu3();
		eventBean.currentScore = eventScoreInfo.getCurrentScore();
		return eventBean;
	}
}
