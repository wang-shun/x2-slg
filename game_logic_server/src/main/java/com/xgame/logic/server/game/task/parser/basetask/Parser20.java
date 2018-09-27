package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.UseItemEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser20 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_20;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		UseItemEventObject event0 = (UseItemEventObject)event;
		updateBaseTaskProgress(event,event0.getNum(),0);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.USE_ITEM);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		UseItemEventObject event0 = (UseItemEventObject)event;
		int id = getV1(taskPir);
		return event0.getItemTid() == id;
	}
}
