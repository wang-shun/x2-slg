package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.structs.build.tach.data.Tech;
import com.xgame.logic.server.game.country.structs.build.tach.data.TechData;
import com.xgame.logic.server.game.country.structs.build.tach.eventmodel.ResearchLevelUpEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser14 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_14;
	}
	
	@Override
	public void onAction(IEventObject event) {
		super.onAction(event);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.RESEARCH_LEVEL_UP);
	}
	
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		if(event != null){
			ResearchLevelUpEventObject event0 = (ResearchLevelUpEventObject)event;
			return event0.getNewLevel();
		}
		int scienceId = getV1(taskPir);
		TechData techData = player.roleInfo().getTechs();
		Tech tech = techData.getTechs().get(scienceId);
		if(tech == null){
			return 0;
		}
		long num = tech.getLevel();
		return num;
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		ResearchLevelUpEventObject event0 = (ResearchLevelUpEventObject)event;
		int scienceId = getV1(taskPir);
		return event0.getScienceId() == scienceId;
	}
}
