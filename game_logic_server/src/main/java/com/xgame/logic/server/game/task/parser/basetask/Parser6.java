package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.RepairSoldierEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser6 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_6;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		RepairSoldierEventObject event0 = (RepairSoldierEventObject)event;
		updateBaseTaskProgress(event,event0.getNum(),0);
	}

	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		return true;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.REPAIR_SOLDIER);
	}
}
