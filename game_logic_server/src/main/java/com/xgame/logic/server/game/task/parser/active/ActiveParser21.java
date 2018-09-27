package com.xgame.logic.server.game.task.parser.active;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xgame.config.active.ActivePir;
import com.xgame.config.active.ActivePirFactory;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.ActiveInfo;
import com.xgame.logic.server.game.task.enity.ActiveItem;

@Component
public class ActiveParser21 extends BaseActiveParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_21;
	}

	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
//		PlayerOnlineTimeEventObject event0 = (PlayerOnlineTimeEventObject)event;
		updateActiveTaskProgress(event,0); 
	}
	
	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.PLAYER_ONLIE_TIME);
	}
	
	public void updateActiveTaskProgress(IEventObject event,long addProgress){
		List<ActivePir> configs = getConfigs();
		ActiveInfo activeInfo = event.getPlayer().roleInfo().getTaskInfo().getActiveInfo();
		for(ActivePir ap : configs){
			if(!checkedUpdate(event,ap)){
				continue;
			}
			ActiveItem activeItem = activeInfo.getActiveItems().get(ap.getId());
			if(activeItem == null){
				continue;
			}
			beforRead(event.getPlayer(),activeItem);
		}
	}
	
	@Override
	public void beforRead(Player player,ActiveItem activeItem){
		if(!activeItem.isComplete()){
			ActivePir pir = ActivePirFactory.get(activeItem.getId());
			player.roleInfo().getBasics().refreshOnlineTime();
			long onlineTime = player.roleInfo().getBasics().getOnlineTime();
			int addTime;
			if(activeItem.getFlag() == 0) {
				addTime = 0;
			}else{
				addTime = (int)(onlineTime / 1000) - activeItem.getFlag();
			}
			activeItem.setFlag((int)(onlineTime / 1000));
			activeItem.setFiniCount(activeItem.getFiniCount() + addTime);
			int totalProgress = pir.getV2();
			if(activeItem.getFiniCount() > totalProgress){
				activeItem.setFiniCount(totalProgress);
				activeItem.setComplete(true);
			}
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
}
