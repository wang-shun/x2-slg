package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser11 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_11;
	}
	
	@Override
	public void onAction(IEventObject event) {
		super.onAction(event);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.MATERIALS_COMPOUND);
	}
}
