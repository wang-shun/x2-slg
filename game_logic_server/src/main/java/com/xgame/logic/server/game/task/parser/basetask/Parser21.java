package com.xgame.logic.server.game.task.parser.basetask;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.config.task.TaskPirFactory;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.PlayerOnlineTimeEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser21 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_21;
	}

	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		PlayerOnlineTimeEventObject event0 = (PlayerOnlineTimeEventObject)event;
		updateBaseTaskProgress(event,0,event0.getOnlineTime());
	}
	
	public void updateBaseTaskProgress(IEventObject event,long addProgress,long newProgress){
		List<TaskPir> configs = getConfigs();
		Map<Integer,BaseTask> baseTaskMap = event.getPlayer().roleInfo().getTaskInfo().getBaseTask();
		for(TaskPir tp : configs){
			if(!checkedUpdate(event,tp)){
				continue;
			}
			BaseTask baseTask = baseTaskMap.get(tp.getId());
			if(baseTask == null){
				continue;
			}
			beforRead(event.getPlayer(),baseTask);
		}
	}
	
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		player.roleInfo().getBasics().refreshOnlineTime();
		InjectorUtil.getInjector().dbCacheService.update(player);
		long newTime;
		if(taskPir.getInitState() == 0){
			newTime = 0;
		}else{
			newTime = player.roleInfo().getBasics().getOnlineTime();
		}
		return newTime;
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.PLAYER_ONLIE_TIME);
	}
	
	@Override
	public void beforRead(Player player,BaseTask baseTask){
		if(!baseTask.isComplete()){
			TaskPir pir = TaskPirFactory.get(baseTask.getTaskId());
			player.roleInfo().getBasics().refreshOnlineTime();
			long onlineTime = player.roleInfo().getBasics().getOnlineTime();
			long addTime;
			if(baseTask.getFlag() == 0){
				addTime = 0;
			}else{
				addTime = onlineTime / 1000 - baseTask.getFlag();
			}
			baseTask.setFlag(onlineTime / 1000);
			long newTime;
			if(pir.getInitState() == 0){
				newTime = baseTask.getProgress() * 1000 + addTime;
			}else{
				newTime = onlineTime;
			}
			baseTask.setProgress(newTime / 1000);
			String str = pir.getV2();
			long totalProgress = Long.valueOf(str);
			if(baseTask.getProgress() > totalProgress){
				baseTask.setProgress(totalProgress);
				baseTask.setComplete(true);
			}
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
}
