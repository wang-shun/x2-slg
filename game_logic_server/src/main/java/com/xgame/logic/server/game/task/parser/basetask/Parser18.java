package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelUpBulidFinishEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser18 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_18;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		LevelUpBulidFinishEventObject event0 = (LevelUpBulidFinishEventObject)event;
		updateBaseTaskProgress(event,event0.getAddCombat(),0);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.EVENT_BUILD_LEVELUP_FINISH);
	}
}
