package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.RebelsAttackEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser49 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_49;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.REBELS_ATTACK);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		RebelsAttackEventObject event0 = (RebelsAttackEventObject)event;
		int type = getV1(taskPir);
		return type == 0 || event0.getAttackType() == type;
	}
}
