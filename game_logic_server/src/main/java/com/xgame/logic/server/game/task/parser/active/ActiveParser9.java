package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.config.equipment.EquipmentPir;
import com.xgame.config.equipment.EquipmentPirFactory;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.GetImplantedEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser9 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_9;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		GetImplantedEventObject event0 = (GetImplantedEventObject)event;
		updateActiveTaskProgress(event,event0.getNum());
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.GET_IMPLANTED);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		GetImplantedEventObject event0 = (GetImplantedEventObject)event;
		EquipmentPir pri = EquipmentPirFactory.get(event0.getEquipmentId());
		int quality = getV1(taskPir);
		return pri.getQuality() == quality;
	}
}
