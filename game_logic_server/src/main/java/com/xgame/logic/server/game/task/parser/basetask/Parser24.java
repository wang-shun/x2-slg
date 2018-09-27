package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.PickEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.RoleCurrency;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser24 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_24;
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.PICK);
	}
	
	@Override
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		if(event != null){
			PickEventObject event0 = (PickEventObject)event;
			return event0.getCurrentNum();
		}
		long num = 0;
		RoleCurrency roleCurrency = player.roleInfo().getCurrency();
		int type = getV1(taskPir);
		switch(type){
		case 1:
			num = roleCurrency.getOil();
			break;
		case 2:
			num = roleCurrency.getRare();
			break;
		case 3:
			num = roleCurrency.getSteel();
			break;
		case 4:
			num = roleCurrency.getMoney();
			break;
		}
		return num;
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		PickEventObject event0 = (PickEventObject)event;
		int type = getV1(taskPir);
		return type == 0 || event0.getSubType() == type;
	}
}
