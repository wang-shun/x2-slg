package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.eventmodel.FinishTaskEventObject;

@Component
public class ActiveParser38 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_38;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.FINISH_TASK);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		FinishTaskEventObject event0 = (FinishTaskEventObject)event;
		int type = getV1(taskPir);
		return type == 0 || event0.getTaskType() == type;
	}
}
