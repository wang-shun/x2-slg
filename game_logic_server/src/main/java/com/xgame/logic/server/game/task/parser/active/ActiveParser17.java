package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelUpBulidFinishEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser17 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_17;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.EVENT_BUILD_LEVELUP_FINISH);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		LevelUpBulidFinishEventObject event0 = (LevelUpBulidFinishEventObject)event;
		int sid = getV1(taskPir);
		return event0.getTid()== sid;
	}
}
