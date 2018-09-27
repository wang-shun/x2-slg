package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.RepairSoldierEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser6 extends BaseActiveParser {

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
		updateActiveTaskProgress(event,event0.getNum());
	}

	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		return true;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.REPAIR_SOLDIER);
	}
}
