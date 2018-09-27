package com.xgame.logic.server.game.task.parser.achieve;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xgame.config.achieve.AchievePirFactory;
import com.xgame.logic.server.core.gamelog.event.AbstractEventListener;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.task.enity.MedalTask;
import com.xgame.logic.server.game.task.enity.MedalTaskInfo;
import com.xgame.logic.server.game.task.enity.eventmodel.FinishTaskEventObject;

/**
 * 勋章任务处理器
 * @author zehong.he
 *
 */
@Component
public class AchieveParser extends AbstractEventListener{
	
	public static final int FINISH_STAR_NUM = 3;//每个勋章任务星星数量
	
	@Override
	public void onAction(IEventObject event) {
		if(event.getType() == EventTypeConst.FINISH_TASK){
			FinishTaskEventObject event0 = (FinishTaskEventObject)event;
			if(event0.getTaskType() == FinishTaskEventObject.TASK_TYPE_3 || event0.getTaskType() == FinishTaskEventObject.TASK_TYPE_4){
				MedalTaskInfo medalTaskInfo = event.getPlayer().roleInfo().getTaskInfo().getMedalTaskInfo();
				int star = event0.getTaskId();
				for(MedalTask task : medalTaskInfo.getMedalTaskList()){
					if(medalTaskInfo.getFinishMedal().contains(task.getTaskId())){
						continue;
					}
					List<Integer> stars = AchievePirFactory.achievePirMap.get(task.getTaskId());
					if(stars.contains(star) && !task.getCurrentValue().contains(star)){
						task.getCurrentValue().add(star);
						if(task.getCurrentValue().size() >= FINISH_STAR_NUM){
							medalTaskInfo.getFinishMedal().add(task.getTaskId());
						}
						InjectorUtil.getInjector().dbCacheService.update(event.getPlayer());
					}
				}
			}
		}
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.FINISH_TASK};
	}
}
