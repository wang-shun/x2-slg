package com.xgame.logic.server.game.gameevent.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.event.EventPir;
import com.xgame.config.event.EventPirFactory;
import com.xgame.config.eventRank.EventRankPir;
import com.xgame.config.eventRank.EventRankPirFactory;
import com.xgame.config.serverEvent.ServerEventPir;
import com.xgame.config.serverEvent.ServerEventPirFactory;
import com.xgame.config.serverEventTeam.ServerEventTeamPir;
import com.xgame.config.serverEventTeam.ServerEventTeamPirFactory;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.game.gameevent.entity.Event;

/**
 * 根据配置获取当前活动
 * @author dingpeng.qu
 *
 */
@Slf4j
@Component
public class ConfigResolve {
	
	@Autowired 
	private IDFactrorySequencer idSequencer;
	
	public List<Event> getEventList(){
		int server = InjectorUtil.getInjector().serverId;
		String serverKey = String.valueOf(server);
		List<Event> eventList = new ArrayList<Event>();
		Map<Integer, ServerEventPir> map = ServerEventPirFactory.getInstance().getFactory();
		if(map != null){
			for(int id : map.keySet()){
				ServerEventPir sep = map.get(id);
				if(sep.getServerIds().toString().contains(serverKey)){
					String eventTeamIds = sep.getEventTeamId().toString();
					for(String eventTeamId :eventTeamIds.split(";")){
						ServerEventTeamPir serverEventTeamPir = ServerEventTeamPirFactory.getInstance().getFactory().get(Integer.parseInt(eventTeamId));
						int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
						Integer type = null;
						String openTime = null;
						switch(week){
						case Calendar.MONDAY:
							type = serverEventTeamPir.getType1();
							openTime = serverEventTeamPir.getOpenTime1();
							break;
						case Calendar.TUESDAY:
							type = serverEventTeamPir.getType2();
							openTime = serverEventTeamPir.getOpenTime2();
							break;
						case Calendar.WEDNESDAY:
							type = serverEventTeamPir.getType3();
							openTime = serverEventTeamPir.getOpenTime3();
							break;
						case Calendar.THURSDAY:
							type = serverEventTeamPir.getType4();
							openTime = serverEventTeamPir.getOpenTime4();
							break;
						case Calendar.FRIDAY:
							type = serverEventTeamPir.getType5();
							openTime = serverEventTeamPir.getOpenTime5();
							break;
						case Calendar.SATURDAY:
							type = serverEventTeamPir.getType6();
							openTime = serverEventTeamPir.getOpenTime6();
							break;
						case Calendar.SUNDAY:
							type = serverEventTeamPir.getType7();
							openTime = serverEventTeamPir.getOpenTime7();
							break;
						}
						if(type != 0){
							EventPir eventPir = EventPirFactory.getInstance().getFactory().get(type);
							//时间区间判断
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
							//获取当前日期2017-04-06
							String dateStr = sdf.format(new Date());
							//拼接事件开始事件2017-04-06 08:15
							openTime = dateStr + " " + openTime;
							try {
								Date date = sdf2.parse(openTime);
								//获取事件开始时间
								long dateTime = date.getTime();
								//提醒时间=开始时间-15分钟
								long min = dateTime - 900000;
								//事件结束时间=开始时间+事件持续时间
								long max = dateTime + eventPir.getTime()*1000;
								if(Calendar.getInstance().getTimeInMillis()>=min && Calendar.getInstance().getTimeInMillis()<=max){
									Event event = new Event();
									//定义EVENT生成id规则 (serverEvent表ID)+(yyyyMMdd)+(serverEventTeam表ID) 确保服务器组事件id唯一
									String idStr = sep.getId() + sdf3.format(new Date()) + eventTeamId;
									event.setId(Long.parseLong(idStr));
									event.setEventId(eventPir.getId());
									event.setName(eventPir.getName());
									event.setType(eventPir.getType());
									event.setEventType(eventPir.getEvenType());
									event.setStartTime(dateTime);
									event.setEndTime(max);
									eventList.add(event);
								}
							} catch (ParseException e) {
								log.error(e.getMessage());
								return null;
							}
						}
					}
				}
			}
		}
		return eventList;
	}
	
	/**
	 * 获取服务器组id
	 * @return
	 */
	public int getServerEventId(){
		int server = InjectorUtil.getInjector().serverId;
		String serverKey = String.valueOf(server);
		Map<Integer, ServerEventPir> map = ServerEventPirFactory.getInstance().getFactory();
		if(map != null){
			for(int id : map.keySet()){
				ServerEventPir sep = map.get(id);
				if(sep.getServerIds().toString().contains(serverKey)){
					return sep.getId();
				}
			}
		}
		return -1;
	}
	
	/**
	 * 根据事件类型获取事件排行奖励
	 * @param eventId
	 * @return
	 */
	public List<EventRankPir> getEvenRank(int eventId){
		Map<Integer, EventRankPir> map = EventRankPirFactory.getInstance().getFactory();
		List<EventRankPir> list = new ArrayList<EventRankPir>();
		for(EventRankPir erp : map.values()){
			if(erp.getEventType() == eventId){
				list.add(erp);
			}
		}
		return list;
	}
}
