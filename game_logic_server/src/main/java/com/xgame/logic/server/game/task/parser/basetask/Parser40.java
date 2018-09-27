package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.commander.entity.eventmodel.CommanderLevelUpEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser40 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_40;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.EVENT_COMMAND_LEVELUP);
	}
	
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		if(event != null){
			CommanderLevelUpEventObject event0 = (CommanderLevelUpEventObject)event;
			return event0.getCurrentLevel();
		}
		long num = player.getCommandLevel();
		return num;
	}
}
