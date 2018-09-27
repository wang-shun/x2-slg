package com.xgame.logic.server.game.gameevent.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.game.gameevent.bean.EventRankInfoBean;
import com.xgame.logic.server.game.gameevent.entity.EventRank;

/**
 * 事件排行信息converter
 * @author dingpeng.qu
 *
 */
public class EventRankInfoConverter {
	/**
	 * 排行信息转换
	 * @param eventRank
	 * @return
	 */
	public static List<EventRankInfoBean> converterEventRankInfoBean(EventRank eventRank){
		List<EventRankInfoBean> eventRankInfoBeanList = new ArrayList<EventRankInfoBean>();
		Map<String,Integer> map = eventRank.getMap();
		int count = 1;
		for(String name : map.keySet()){
			EventRankInfoBean eventRankInfoBean = new EventRankInfoBean();
			eventRankInfoBean.rank = count;
			eventRankInfoBean.name = name;
			eventRankInfoBean.score = map.get(name);
			eventRankInfoBeanList.add(eventRankInfoBean);
		}
		return eventRankInfoBeanList;
	}
}
