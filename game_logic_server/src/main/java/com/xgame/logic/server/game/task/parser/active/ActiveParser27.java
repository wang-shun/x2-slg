package com.xgame.logic.server.game.task.parser.active;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.KillMonsterEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class ActiveParser27 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_27;
	}

	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		KillMonsterEventObject event0 = (KillMonsterEventObject)event;
		updateActiveTaskProgress(event,event0.getKillNum());
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.KILL_MONSTER);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		KillMonsterEventObject event0 = (KillMonsterEventObject)event;
		int level = getV1(taskPir);
		return level == 0 || event0.getMonsterLevel() >= level;
	}
}
