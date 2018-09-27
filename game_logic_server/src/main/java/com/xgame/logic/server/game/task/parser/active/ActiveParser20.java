package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.UseItemEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser20 extends BaseActiveParser {

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
		updateActiveTaskProgress(event,event0.getNum());
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.USE_ITEM);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		UseItemEventObject event0 = (UseItemEventObject)event;
		int id = getV1(taskPir);
		return event0.getItemTid() == id;
	}
}
