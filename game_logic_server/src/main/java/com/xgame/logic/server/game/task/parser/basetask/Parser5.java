package com.xgame.logic.server.game.task.parser.basetask;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.soldier.entity.model.SoldierProductStartEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;
import com.xgame.logic.server.game.task.enity.TaskRecord;

@Component
public class Parser5 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_5;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		SoldierProductStartEventObject event0 = (SoldierProductStartEventObject)event;
		Player player = event.getPlayer();
		TaskRecord taskRecord = player.roleInfo().getTaskInfo().getTaskRecord();
		Map<Integer,Integer> productSoldierMap = taskRecord.getProductSoldierMap();
		for(Integer pejianId : event0.getSids()){
			if(!productSoldierMap.containsKey(pejianId)){
				productSoldierMap.put(pejianId, event0.getProductNum());
			}else{
				int value = productSoldierMap.get(pejianId) + event0.getProductNum();
				productSoldierMap.put(pejianId, value);
			}
		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		updateBaseTaskProgress(event,event0.getProductNum(),0);
	}

	@Override
	public boolean checkedUpdate(IEventObject event, TaskPir taskPir) {
		SoldierProductStartEventObject event0 = (SoldierProductStartEventObject)event;
		int sid = getV1(taskPir);
		return event0.getSids().contains(sid);
	}

	@Override
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		TaskRecord taskRecord = player.roleInfo().getTaskInfo().getTaskRecord();
		Map<Integer,Integer> productSoldierMap = taskRecord.getProductSoldierMap();
		int sid = getV1(taskPir);
		if(!productSoldierMap.containsKey(sid)){
			if(event != null){
				SoldierProductStartEventObject event0 = (SoldierProductStartEventObject)event;
				return event0.getProductNum();
			}else{
				return 0;
			}
		}
		return productSoldierMap.get(sid);
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.SOLDIER_PRODUCT_START);
	}
}
