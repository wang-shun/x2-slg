package com.xgame.logic.server.game.task.parser.basetask;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.eventmodel.DestroySideEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser32 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_32;
	}

	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		List<TaskPir> configs = getConfigs();
		Map<Integer,BaseTask> baseTaskMap = event.getPlayer().roleInfo().getTaskInfo().getBaseTask();
		DestroySideEventObject event0 = (DestroySideEventObject)event;
		for(TaskPir tp : configs){
			if(!checkedUpdate(event,tp)){
				continue;
			}
			BaseTask baseTask = baseTaskMap.get(tp.getId());
			String strv2 = tp.getV2();
			long totalProgress = Long.valueOf(strv2);
			int level = getV1(tp);
			int addProgress = event0.getDamageSideSoldier().get(level);

			if(baseTask != null && !baseTask.isComplete()){
				baseTask.addProgress(addProgress);
				if(baseTask.getProgress() >= totalProgress){
					baseTask.setComplete(true);
				}
				InjectorUtil.getInjector().dbCacheService.update(event.getPlayer());
				pushTaskUpdate(event.getPlayer(),baseTask);
			}
		}
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.DESTROY_SIDE);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		DestroySideEventObject event0 = (DestroySideEventObject)event;
		if(!event0.isCurrentServer()){
			return false;
		}
		int level = getV1(taskPir);
		return level == 0 || event0.getDamageSideSoldier().containsKey(level);
	}
}
