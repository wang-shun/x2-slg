package com.xgame.logic.server.game.task.parser.basetask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;

import com.xgame.config.BaseFilePri;
import com.xgame.config.task.TaskPir;
import com.xgame.config.task.TaskPirFactory;
import com.xgame.logic.server.core.gamelog.event.AbstractEventListener;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.converter.TaskConverter;
import com.xgame.logic.server.game.task.enity.BaseTask;
import com.xgame.logic.server.game.task.enity.eventmodel.FinishTaskEventObject;

/**
 * 任务解析器基类
 * @author zehong.he
 *
 */
public abstract class BaseParser extends AbstractEventListener{
	
	public List<Integer> eventTypes = new ArrayList<>();
	
	public static final Map<Integer,BaseParser> baseParserMap = new HashMap<>();
	
	@PostConstruct
	public void init(){
		subEvent();
		getEventBus().addEventListener(this); 
		baseParserMap.put(getTaskTypeEnum().getCode(), this);
	}
	
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		//基地任务
		updateBaseTaskProgress(event,1,0);
	}
	
	/**
	 * 更新基地任务
	 * @param event
	 */
	public void updateBaseTaskProgress(IEventObject event,long addProgress,long newProgress){
		List<TaskPir> configs = getConfigs();
		Map<Integer,BaseTask> baseTaskMap = event.getPlayer().roleInfo().getTaskInfo().getBaseTask();
		for(TaskPir tp : configs){
			if(!checkedUpdate(event,tp)) {
				continue;
			}
			BaseTask baseTask = baseTaskMap.get(tp.getId());
			if(baseTask == null){
				continue;
			}
			String str = tp.getV2();
			long totalProgress = Long.valueOf(str);
			if(tp.getInitState() == 0){
				newProgress = 0;
			}else{
				newProgress = getCurrProgress(event,event.getPlayer(),tp,baseTask);
				if(newProgress < baseTask.getProgress() + addProgress){
					newProgress = baseTask.getProgress();
				}
			}
			if(baseTask != null && !baseTask.isComplete()){
				if(newProgress == 0){
					baseTask.addProgress(addProgress);
				}else{
					baseTask.setProgress(newProgress);
				}
				if(baseTask.getProgress() >= totalProgress){
					baseTask.setProgress(totalProgress);
					baseTask.setComplete(true);
				}
				InjectorUtil.getInjector().dbCacheService.update(event.getPlayer());
				pushTaskUpdate(event.getPlayer(),baseTask);
			}
		}
	}
	
	/**
	 * 检查是否与该任务类型匹配
	 * @param event
	 * @param taskPir
	 * @return
	 */
	public boolean checkedUpdate(IEventObject event,TaskPir taskPir){
		return true;
	}
	
	/**
	 * 获取最新进度
	 * @param event
	 * @param player
	 * @param taskPir
	 * @param baseTask
	 * @return
	 */
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		return 0;
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
	public boolean isMyEvent(IEventObject event){
		return eventTypes.contains(event.getType());
	}
	
	/**
	 * 推送客户端任务更新
	 * @param player
	 */
	public void pushTaskUpdate(Player player,BaseTask baseTask){
		player.send(TaskConverter.resPushBaseTaskMessageBuilder(baseTask));
		if(baseTask.isComplete()){
			EventBus.getSelf().fireEvent(new FinishTaskEventObject(player,FinishTaskEventObject.TASK_TYPE_3,baseTask.getTaskId()));
		}
	}
	
	/**
	 * 添加监听事件
	 */
	public abstract void subEvent();
	
	/**
	 * 初始化进度
	 * @param config
	 * @param player
	 * @return
	 */
	public long initProgress(BaseFilePri config,Player player){
		TaskPir taskPir = (TaskPir)config;
		if(taskPir.getInitState() == 0){
			return 0;
		}
		return getCurrProgress(null,player,taskPir,null);
	}
	
	
	public List<TaskPir> getConfigs(){
		List<TaskPir> configs = TaskPirFactory.getByType(getTaskTypeEnum().getCode());
		if(configs == null){
			return new ArrayList<>();
		}
		return configs;
	}
	public int getV1(TaskPir taskPir){
		String v1Str = taskPir.getV1();
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
	public void beforRead(Player player,BaseTask baseTask){
	}
}
