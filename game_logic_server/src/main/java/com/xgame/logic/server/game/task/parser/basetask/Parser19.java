package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.UseItemEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser19 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_19;
	}
	
	@Override
	public void onAction(IEventObject event) {
		super.onAction(event);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.USE_ITEM);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		UseItemEventObject event0 = (UseItemEventObject)event;
		int tab = getV1(taskPir);
		return event0.getTab() == tab;
	}
}
