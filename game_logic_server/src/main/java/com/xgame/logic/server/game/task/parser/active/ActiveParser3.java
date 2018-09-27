package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.soldier.entity.model.SoldierChangeEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser3 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_3;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		SoldierChangeEventObject event0 = (SoldierChangeEventObject)event;
		if(event0.getMarchType() != null){
			return;
		}
		long addNum = event0.getCurrentNum() - event0.getBeforeNum();
		if(addNum > 0){
			updateActiveTaskProgress(event,addNum);
		}
	}
	
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.SOLDIER_CHANGE);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		SoldierChangeEventObject event0 = (SoldierChangeEventObject)event;
		int type = getV1(taskPir);
		return event0.getSoldierType() == type;
	}
}
