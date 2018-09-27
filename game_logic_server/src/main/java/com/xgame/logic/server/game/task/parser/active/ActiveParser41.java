package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.WarSimulatorWinEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser41 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_41;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.WAR_SIMULATOR_WIN);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		WarSimulatorWinEventObject event0 = (WarSimulatorWinEventObject)event;
		int type = getV1(taskPir);
		return type == 0 || event0.getWarType() == type;
	}
}
