package com.xgame.logic.server.core.gamelog.event;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.core.utils.scheduler.ScheduledTask;
import com.xgame.logic.server.core.utils.scheduler.Scheduler;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 事件总线
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class EventBus {

	private static EventBus instance;
	
	/**
	 *  事件监听器缓存
	 */
	private static Map<Integer, Set<IListener>> listeners = new ConcurrentHashMap<Integer, Set<IListener>>(); 

	@PostConstruct
	private void init() {
		instance = this;
	}

	public static EventBus getSelf() {
		return instance;
	}

	@Autowired
	private Scheduler scheduler;

	//  增加事件监听
	public void addEventListener(IListener listerner) {
		for(Integer eventType : listerner.focusActions()) {
			Set<IListener> eventList = listeners.get(eventType);
			if(eventList == null) {
				eventList = new ConcurrentHashSet<IListener>();
				listeners.put(eventType, eventList);
			}
			eventList.add(listerner);
		}
	}

	public void fireEvent(final IEventObject event) {
		scheduler.scheduleWithDelay(new ScheduledTask() {
			public String getName() {
				return EventBus.class.getName();
			}

			public void run() {
				Set<IListener> set = listeners.get(event.getType());
				if(set != null) {
					for(IListener listener : set) {
						try {
							if(ArrayUtils.contains(listener.focusActions(), event.getType()) || ArrayUtils.contains(listener.focusActions(), EventTypeConst.ALL_EVENT)) {
								Player player = event.getPlayer();
								if(player != null) {
									player.async(new Runnable() {
										@Override
										public void run() {
											listener.onAction(event);
										}
									});
								} else {
									listener.onAction(event);
								}
							}
						} catch (Exception e) {
							log.error("listener调用异常；[%s]", listener.getClass().getName(), e);
						}
					}
				}
			}
		}, 1);
	}

}
