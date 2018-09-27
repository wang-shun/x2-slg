package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceBuildUpEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser45 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_45;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.ALLIANCE_BUILD_UP);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		AllianceBuildUpEventObject event0 = (AllianceBuildUpEventObject)event;
		int type = getV1(taskPir);
		return type == 0 || event0.getBuildUpType()== type;
	}
}
