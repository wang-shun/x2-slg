package com.xgame.logic.server.game.task.parser.active;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xgame.config.active.ActivePir;
import com.xgame.config.active.ActivePirFactory;
import com.xgame.config.activeRewards.ActiveRewardsPir;
import com.xgame.config.activeRewards.ActiveRewardsPirFactory;
import com.xgame.logic.server.core.gamelog.event.AbstractEventListener;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.TaskManager;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.converter.TaskConverter;
import com.xgame.logic.server.game.task.enity.ActiveInfo;
import com.xgame.logic.server.game.task.enity.ActiveItem;
import com.xgame.logic.server.game.task.enity.RewardsStatus;
import com.xgame.logic.server.game.task.enity.eventmodel.FinishTaskEventObject;

/**
 * 活跃度任务解析器基类
 * @author zehong.he
 *
 */
public abstract class BaseActiveParser extends AbstractEventListener{
	
	@Autowired
	TaskManager taskManager;
	
	public List<Integer> eventTypes = new ArrayList<>();
	
	public static final Map<Integer,BaseActiveParser> activeParserMap = new HashMap<>();
	
	@PostConstruct
	public void init(){
		subEvent();
		getEventBus().addEventListener(this); 
		activeParserMap.put(getTaskTypeEnum().getCode(), this);
	}
	
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		updateActiveTaskProgress(event,1);
	}
	
	/**
	 * 更新活跃度任务
	 * @param event
	 */
	public void updateActiveTaskProgress(IEventObject event,long addProgress){
		List<ActivePir> configs = getConfigs();
		taskManager.updateActiveItems(event.getPlayer());
		ActiveInfo activeInfo = event.getPlayer().roleInfo().getTaskInfo().getActiveInfo();
		for(ActivePir ap : configs){
			if(!checkedBuild(event,ap) || !checkedUpdate(event,ap)){
				continue;
			}
			ActiveItem activeItem = activeInfo.getActiveItems().get(ap.getId());
			if(activeItem == null){
				continue;
			}
			int totalProgress = ap.getV2();
			if(activeItem != null && !activeItem.isComplete()){
				if(activeItem.getFiniCount() + addProgress > totalProgress){
					activeItem.setFiniCount(totalProgress);
				}else{
					activeItem.setFiniCount(activeItem.getFiniCount() + (int)addProgress);
				}
				if(activeItem.getFiniCount() >= totalProgress){
					activeItem.setFiniCount(totalProgress);
					//激活宝箱    增加活跃度
					activeItem.setComplete(true);
					int addActive = ap.getSingleRewards();
					activeInfo.setValue(activeInfo.getValue() + addActive);
					List<ActiveRewardsPir> rewards = ActiveRewardsPirFactory.getInstance().getByActive(activeInfo.getValue());
					for(ActiveRewardsPir reward : rewards){
						if(!activeInfo.getRewards().containsKey(reward.getId())){
							RewardsStatus rewardsStatus = new RewardsStatus();
							rewardsStatus.setId(reward.getId());
							activeInfo.getRewards().put(reward.getId(), rewardsStatus);
						}
					}
				}
				InjectorUtil.getInjector().dbCacheService.update(event.getPlayer());
				pushTaskUpdate(event.getPlayer(),activeItem);
			}
		}
	}
	
	public boolean checkedBuild(IEventObject event,ActivePir ap){
		int[] arr = ap.getUnlock();
		CountryBuild build = null;
		for(CountryBuild b : event.getPlayer().roleInfo().getBaseCountry().getAllBuild().values()){
			if(b.getSid() == arr[0]){
				build = b;
				break;
			}
		}
		if(build == null || build.getLevel() < arr[1]){
			return false;
		}
		return true;
	}
	
	/**
	 * 检查是否与该任务类型匹配
	 * @param event
	 * @param taskPir
	 * @return
	 */
	public boolean checkedUpdate(IEventObject event,ActivePir activePir){
		return true;
	}
	
	/**
	 * 任务类型
	 * @return
	 */
	public abstract BaseTaskTypeEnum getTaskTypeEnum();
	
	/**
	 * 是否是与我有关的事件
	 * @param event
	 * @return
	 */
	public boolean isMyEvent(IEventObject event) {
		return eventTypes.contains(event.getType());
	}
	
	/**
	 * 推送客户端任务更新
	 * @param player
	 */
	public void pushTaskUpdate(Player player,ActiveItem activeItem){
		ActivePir activePir = ActivePirFactory.get(activeItem.getId());		
		player.send(TaskConverter.resPushActiveTaskMessageBuilder(activeItem,activePir,player.roleInfo().getTaskInfo().getActiveInfo().getValue()));
		if(activeItem.isComplete()){
			EventBus.getSelf().fireEvent(new FinishTaskEventObject(player,FinishTaskEventObject.TASK_TYPE_4,activeItem.getId()));
		}
	}
	
	/**
	 * 添加监听事件
	 */
	public abstract void subEvent();
	
	
	public List<ActivePir> getConfigs(){
		List<ActivePir> configs = ActivePirFactory.getByType(getTaskTypeEnum().getCode());
		if(configs == null){
			return new ArrayList<>();
		}
		return configs;
	}
	
	public int getV1(ActivePir activePir){
		String v1Str = activePir.getV1();
		int v1 = 0;
		if(v1Str != null){
			v1 = Integer.parseInt(v1Str);
		}
		return v1;
	}
	
	@Override
	public int[] focusActions() {
		int[] result = new int[eventTypes.size()];
		Integer[] t = eventTypes.toArray(ArrayUtils.toArray(new Integer[]{}));
		for(int i =0;i<result.length; i++) {
			result[i] = t[i];
		}
		return result;
	}
	
	public void beforRead(Player player,ActiveItem activeItem){
		
	}
}
