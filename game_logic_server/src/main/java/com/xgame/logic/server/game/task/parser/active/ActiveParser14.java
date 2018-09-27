package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.structs.build.tach.eventmodel.ResearchLevelUpEndEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser14 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_14;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.RESEARCH_LEVEL_UP);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir activePir) {
		ResearchLevelUpEndEventObject event0 = (ResearchLevelUpEndEventObject)event;
		int scienceId = getV1(activePir);
		return event0.getScienceId() == scienceId;
	}
}
