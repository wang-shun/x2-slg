package com.xgame.logic.server.game.task.parser.active;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.config.activeRewards.ActiveRewardsPir;
import com.xgame.config.activeRewards.ActiveRewardsPirFactory;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.eventmodel.DestroySideEventObject;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.ActiveInfo;
import com.xgame.logic.server.game.task.enity.ActiveItem;
import com.xgame.logic.server.game.task.enity.RewardsStatus;

@Component
public class ActiveParser33 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_32;
	}

	@Override
	public void updateActiveTaskProgress(IEventObject event,long addProgress){
		List<ActivePir> configs = getConfigs();
		taskManager.updateActiveItems(event.getPlayer());
		ActiveInfo activeInfo = event.getPlayer().roleInfo().getTaskInfo().getActiveInfo();
		DestroySideEventObject event0 = (DestroySideEventObject)event;
		for(ActivePir tp : configs){
			if(checkedBuild(event,tp) || !checkedUpdate(event,tp)){
				continue;
			}
			ActiveItem activeItem = activeInfo.getActiveItems().get(tp.getId());
			String v2 = tp.getV2();
			int totalProgress = Integer.parseInt(v2);
			int level = getV1(tp);
			addProgress = event0.getDamageSideSoldier().get(level);
			if(activeItem != null && !activeItem.isComplete()){
				if(activeItem.getFiniCount() + addProgress > totalProgress){
					activeItem.setFiniCount(totalProgress);
				}else{
					activeItem.setFiniCount(activeItem.getFiniCount() + (int)addProgress);
				}
				if(activeItem.getFiniCount() >= totalProgress){
					activeItem.setComplete(true);
					//激活宝箱    增加活跃度
					int addActive = tp.getSingleRewards();
					activeInfo.setValue(activeInfo.getValue() + addActive);
					List<ActiveRewardsPir> rewards = ActiveRewardsPirFactory.getInstance().getByActive(activeInfo.getValue());
					for(ActiveRewardsPir reward : rewards){
						if(!activeInfo.getRewards().containsKey(reward.getId())){
							RewardsStatus rewardsStatus = new RewardsStatus();
							rewardsStatus.setId(reward.getId());
							activeInfo.getRewards().put(reward.getId(), rewardsStatus);
						}
					}
				}
				InjectorUtil.getInjector().dbCacheService.update(event.getPlayer());
				pushTaskUpdate(event.getPlayer(),activeItem);
			}
		}
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.DESTROY_SIDE);
	}
	
	@Override
	public boolean checkedUpdate(IEventObject event, ActivePir taskPir) {
		DestroySideEventObject event0 = (DestroySideEventObject)event;
		if(event0.isCurrentServer()){
			return false;
		}
		int level = getV1(taskPir);
		return level == 0 || event0.getDamageSideSoldier().containsKey(level);
	}
}
