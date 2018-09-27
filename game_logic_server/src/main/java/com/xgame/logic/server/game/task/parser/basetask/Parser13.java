package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser13 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_13;
	}
	
	@Override
	public void onAction(IEventObject event) {
		super.onAction(event);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.RESEARCH_LEVEL_UP);
	}
}
