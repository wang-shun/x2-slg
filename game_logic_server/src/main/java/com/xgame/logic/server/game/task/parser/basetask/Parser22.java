package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.OccLandEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser22 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_22;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.OCC_LAND);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		OccLandEventObject event0 = (OccLandEventObject)event;
		int oldOwner = getV1(taskPir);
		if(oldOwner == 0){
			return true;
		}
		return event0.getOldOwner() == oldOwner;
	}
}
