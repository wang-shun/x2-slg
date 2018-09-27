package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.LeaderInvasionEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser50 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_50;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.LEADER_INVASION);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		LeaderInvasionEventObject event0 = (LeaderInvasionEventObject)event;
		int type = getV1(taskPir);
		return type == 0 || event0.getInvasionType() == type;
	}
}
