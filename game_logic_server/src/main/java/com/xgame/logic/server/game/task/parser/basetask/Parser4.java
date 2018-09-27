package com.xgame.logic.server.game.task.parser.basetask;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierData;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.entity.model.SoldierChangeEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser4 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_4;
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
			updateBaseTaskProgress(event,addNum,0);
		}
	}

	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		if(event != null){
			SoldierChangeEventObject event0 = (SoldierChangeEventObject)event;
			return event0.getCurrentNum();
		}
		long num = 0;
		SoldierData soldierData = player.roleInfo().getSoldierData();
		int level = getV1(taskPir);
		for(Soldier soldier : soldierData.getSoldiers().values()){
			DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
			if(level == 0){
				num += soldier.getNum();
			}else if(level == designMap.getSystemIndex()){
				num += soldier.getNum();
			}
		}
		return num;
	}
	

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.SOLDIER_CHANGE);
	}

	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		SoldierChangeEventObject event0 = (SoldierChangeEventObject)event;
		int level = getV1(taskPir);
		return level == 0 || event0.getLevel() == level;
	}
}
