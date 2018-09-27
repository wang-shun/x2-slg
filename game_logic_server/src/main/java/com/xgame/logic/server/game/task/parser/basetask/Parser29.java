package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;
import com.xgame.logic.server.game.task.enity.TaskRecord;

@Component
public class Parser29 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_29;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.SCOUT);
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		Player player = event.getPlayer();
		TaskRecord taskRecord = player.roleInfo().getTaskInfo().getTaskRecord();
		taskRecord.setScoutTimes(taskRecord.getScoutTimes() + 1);
		InjectorUtil.getInjector().dbCacheService.update(player);
		updateBaseTaskProgress(event,1,0);
	}
	
	@Override
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		TaskRecord taskRecord = player.roleInfo().getTaskInfo().getTaskRecord();
		return taskRecord.getScoutTimes();
	}
}
