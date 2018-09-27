package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.structs.build.tach.eventmodel.ResearchLevelUpEndEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser15 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_15;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		ResearchLevelUpEndEventObject event0 = (ResearchLevelUpEndEventObject)event;
		updateBaseTaskProgress(event,event0.getAddCombat(),0);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.RESEARCH_LEVEL_UP_END);
	}
}
