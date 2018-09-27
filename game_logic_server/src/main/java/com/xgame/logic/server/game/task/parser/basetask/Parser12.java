package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.structs.build.tach.data.Tech;
import com.xgame.logic.server.game.country.structs.build.tach.data.TechData;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser12 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_12;
	}
	
	@Override
	public void onAction(IEventObject event) {
		super.onAction(event);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.RESEARCH_LEVEL_UP_END);
	}
	
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		long num = 0;
		TechData techData = player.roleInfo().getTechs();
		for(Tech tech : techData.getTechs().values()){
			if(tech.getLevel() >= 1){
				num++;
			}
		}
		return num;
	}
}
