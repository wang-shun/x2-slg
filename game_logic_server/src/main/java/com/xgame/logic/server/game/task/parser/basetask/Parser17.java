package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelUpBulidFinishEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser17 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_17;
	}
	
	@Override
	public void onAction(IEventObject event) {
		super.onAction(event);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.EVENT_BUILD_LEVELUP_FINISH);
	}
	
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		if(event != null){
			LevelUpBulidFinishEventObject event0 = (LevelUpBulidFinishEventObject)event;
			return event0.getoldLevel() + 1;
		}
		int level = 0;
		int sid = getV1(taskPir);
		for(XBuild bb : player.roleInfo().getBaseCountry().getAllBuild().values()){
			if(bb.getSid() == sid){
				if(bb.getLevel() > level){
					level = bb.getLevel();
				}
			}
		}
		return level;
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		LevelUpBulidFinishEventObject event0 = (LevelUpBulidFinishEventObject)event;
		int sid = getV1(taskPir);
		return event0.getTid()== sid;
	}
}
