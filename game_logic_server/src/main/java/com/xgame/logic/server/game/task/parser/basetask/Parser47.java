package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser47 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_47;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.REFLRESH_LEAGUE_SHOP);
	}
}
