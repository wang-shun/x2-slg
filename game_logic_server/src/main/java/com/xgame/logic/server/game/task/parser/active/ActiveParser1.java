package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser1 extends BaseActiveParser {

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
}
