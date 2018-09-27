package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.soldier.entity.model.SoldierProductStartEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser5 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_5;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
	}

	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		SoldierProductStartEventObject event0 = (SoldierProductStartEventObject)event;
		int sid = getV1(taskPir);
		return event0.getSids().contains(sid);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.SOLDIER_PRODUCT_START);
	}
}
