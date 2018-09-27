package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser48 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_48;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.LEAGUE_SHOPPING);
	}
}
