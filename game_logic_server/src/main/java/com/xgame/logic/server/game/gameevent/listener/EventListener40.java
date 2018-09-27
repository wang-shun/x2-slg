package com.xgame.logic.server.game.gameevent.listener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.xgame.config.eventTask.EventTaskPir;
import com.xgame.config.eventTask.EventTaskPirFactory;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.gameevent.constant.EventTaskTypeEnum;
import com.xgame.logic.server.game.gameevent.entity.AllianceEvent;
import com.xgame.logic.server.game.gameevent.entity.Event;
import com.xgame.logic.server.game.gameevent.entity.EventScoreInfo;
import com.xgame.logic.server.game.gameevent.entity.PlayerEvent;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.WarSimulatorWinEventObject;
import com.xgame.utils.TimeUtils;
/**
 * 模拟器胜利
 * @author dingpeng.qu
 *
 */
@Component
public class EventListener40 extends BaseEventListener {
	
	public void onAction(IEventObject event) {
		WarSimulatorWinEventObject wswEvent = (WarSimulatorWinEventObject)event;
		//获取个人正在进行中的事件
		Player player = event.getPlayer();
		PlayerEvent playerEvent = InjectorUtil.getInjector().dbCacheService.get(PlayerEvent.class, player.getId());
		if(playerEvent != null){
			Map<Long,EventScoreInfo> eventScoreInfo = playerEvent.getEventScoreInfoMap();
			Set<Long> playerIds = new HashSet<Long>();
			playerIds.add(player.getId());
			updateScore(eventScoreInfo, wswEvent, player.getName(),player.getId(),playerIds);
			InjectorUtil.getInjector().dbCacheService.update(playerEvent);
		}
		
		//获取联盟正在进行中的事件
		AllianceEvent allianceEvent = InjectorUtil.getInjector().dbCacheService.get(AllianceEvent.class, player.getAllianceId());
		if(allianceEvent != null){
			Map<Long,EventScoreInfo> eventScoreInfo = allianceEvent.getEventScoreInfoMap();
			Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, player.getAllianceId());
			updateScore(eventScoreInfo, wswEvent, alliance.getAllianceName(),alliance.getId(),alliance.getAllianceMember());
			InjectorUtil.getInjector().dbCacheService.update(allianceEvent);
		}
	}
	/**
	 * 更新积分
	 * @param eventScoreInfo
	 * @param scEvent
	 */
	private void updateScore(Map<Long,EventScoreInfo> eventScoreInfo,WarSimulatorWinEventObject wswEvent,String name,long playerOrAllianceId,Set<Long> playerIds){
		if(eventScoreInfo != null){
			for(Long id : eventScoreInfo.keySet()){
				EventScoreInfo esi = eventScoreInfo.get(id);
				Event pEvent = InjectorUtil.getInjector().dbCacheService.get(Event.class, id);
				if(pEvent.getStartTime() <= TimeUtils.getCurrentTimeMillis()){
					Map<Integer,EventTaskPir> eventTaskMap = EventTaskPirFactory.getInstance().getFactory();
					for(EventTaskPir etp : eventTaskMap.values()){
						//此处需要结合参数判断
						//判断事件类型 类型 模拟器类型 模拟器步数
						if(pEvent.getType()==etp.getEventType() && EventTaskTypeEnum.SIMULATOR_TYPE.getValue() == etp.getType() && etp.getV1() != null && Integer.parseInt(etp.getV1())==wswEvent.getWarType() && etp.getV2() != null && Integer.parseInt(etp.getV2())==wswEvent.getStep()){
							//计算公式 现积分=原积分+模拟器积分
							esi.setCurrentScore(esi.getCurrentScore()+etp.getScore());
							//发奖
							giveRewards(esi, playerIds,pEvent.getEventType());
							//维护排行榜
							updateRank(String.valueOf(id), esi.getCurrentScore(), playerOrAllianceId+"#"+InjectorUtil.getInjector().serverId+"["+(wswEvent.getPlayer().getAllianceAbbr()==null?"":wswEvent.getPlayer().getAllianceAbbr())+"]"+name,pEvent.getEventType());
						}
					}
				}
			}
		}
	}
	
	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.WAR_SIMULATOR_WIN};
	}
}
