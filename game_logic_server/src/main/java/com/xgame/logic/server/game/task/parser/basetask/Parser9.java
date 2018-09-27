package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.equipment.EquipmentPir;
import com.xgame.config.equipment.EquipmentPirFactory;
import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.GetImplantedEventObject;
import com.xgame.logic.server.game.equipment.entity.Equipment;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser9 extends BaseParser {

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
		updateBaseTaskProgress(event,event0.getNum(),0);
	}
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		long num = 0;
		int quality = getV1(taskPir);
		for(Equipment equipment : player.roleInfo().getPlayerBag().getEquipmentMap().values()){
			EquipmentPir pri = EquipmentPirFactory.get(equipment.getEquipmentId());
			if(pri.getQuality() == quality){
				num++;
			}
		}
		return num;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.GET_IMPLANTED);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		GetImplantedEventObject event0 = (GetImplantedEventObject)event;
		EquipmentPir pri = EquipmentPirFactory.get(event0.getEquipmentId());
		int quality = getV1(taskPir);
		return pri.getQuality() == quality;
	}
}
