package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser1 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_1;
	}
	
	@Override
	public void onAction(IEventObject event) {
		super.onAction(event);
	}

	
	public int initProgress(){
		return 0;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.BUILDING_REWARD);
	}

	@Override
	public long getCurrProgress(IEventObject event, Player player,
			TaskPir taskPir, BaseTask baseTask) {
		return baseTask.getProgress() + 1;
	}

	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		return true;
	}

}
