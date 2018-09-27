package com.xgame.logic.server.game.task.parser.basetask;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.eventmodel.DamageSideEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser31 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_31;
	}

	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		List<TaskPir> configs = getConfigs();
		Map<Integer,BaseTask> baseTaskMap = event.getPlayer().roleInfo().getTaskInfo().getBaseTask();
		DamageSideEventObject event0 = (DamageSideEventObject)event;
		for(TaskPir tp : configs){
			if(!checkedUpdate(event,tp)){
				continue;
			}
			BaseTask baseTask = baseTaskMap.get(tp.getId());
			String str = tp.getV2();
			long totalProgress = Long.valueOf(str);
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
		eventTypes.add(EventTypeConst.DAMAGE_SIDE);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		DamageSideEventObject event0 = (DamageSideEventObject)event;
		int level = getV1(taskPir);
		return level == 0 || event0.getDamageSideSoldier().containsKey(level);
	}
}
