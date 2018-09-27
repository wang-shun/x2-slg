package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.player.entity.eventmodel.CopyPassEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;

@Component
public class Parser35 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_35;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.COPY_PASS);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		CopyPassEventObject event0 = (CopyPassEventObject)event;
		int copyId = getV1(taskPir);
		return copyId == 0 || event0.getCopyId() == copyId;
	}
}
