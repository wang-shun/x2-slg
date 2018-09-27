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
import com.xgame.logic.server.game.player.entity.eventmodel.DestroySideEventObject;
import com.xgame.utils.TimeUtils;
/**
 * 摧毁敌方士兵
 * @author dingpeng.qu
 *
 */
@Component
public class EventListener32 extends BaseEventListener {
	
	public void onAction(IEventObject event) {
		DestroySideEventObject dsEvent = (DestroySideEventObject)event;
		//获取个人正在进行中的事件
		Player player = event.getPlayer();
		PlayerEvent playerEvent = InjectorUtil.getInjector().dbCacheService.get(PlayerEvent.class, player.getId());
		if(playerEvent != null){
			Map<Long,EventScoreInfo> eventScoreInfo = playerEvent.getEventScoreInfoMap();
			Set<Long> playerIds = new HashSet<Long>();
			playerIds.add(player.getId());
			updateScore(eventScoreInfo, dsEvent, player.getName(),player.getId(),playerIds);
			InjectorUtil.getInjector().dbCacheService.update(playerEvent);
		}
		
		//获取联盟正在进行中的事件
		AllianceEvent allianceEvent = InjectorUtil.getInjector().dbCacheService.get(AllianceEvent.class, player.getAllianceId());
		if(allianceEvent != null){
			Map<Long,EventScoreInfo> eventScoreInfo = allianceEvent.getEventScoreInfoMap();
			Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, player.getAllianceId());
			updateScore(eventScoreInfo, dsEvent, alliance.getAllianceName(),alliance.getId(),alliance.getAllianceMember());
			InjectorUtil.getInjector().dbCacheService.update(allianceEvent);
		}
	}
	/**
	 * 更新积分
	 * @param eventScoreInfo
	 * @param scEvent
	 */
	private void updateScore(Map<Long,EventScoreInfo> eventScoreInfo,DestroySideEventObject dsEvent,String name,long playerOrAllianceId,Set<Long> playerIds){
		if(eventScoreInfo != null){
			for(Long id : eventScoreInfo.keySet()){
				EventScoreInfo esi = eventScoreInfo.get(id);
				Event pEvent = InjectorUtil.getInjector().dbCacheService.get(Event.class, id);
				if(pEvent.getStartTime() <= TimeUtils.getCurrentTimeMillis()){
					Map<Integer,EventTaskPir> eventTaskMap = EventTaskPirFactory.getInstance().getFactory();
					for(EventTaskPir etp : eventTaskMap.values()){
						//此处需要结合参数判断
						//判断事件类型 类型 士兵类型
						if(pEvent.getType()==etp.getEventType() && EventTaskTypeEnum.DESTORY_SOLDIER_TYPE.getValue() == etp.getType() && etp.getV1() != null && dsEvent.getDamageSideSoldier().get(Integer.parseInt(etp.getV1())) != null){
							//计算公式 现积分=原积分+士兵积分*士兵数量
							esi.setCurrentScore(esi.getCurrentScore()+etp.getScore()*dsEvent.getDamageSideSoldier().get(Integer.parseInt(etp.getV1())));
							//发奖
							giveRewards(esi, playerIds,pEvent.getEventType());
							//维护排行榜
							updateRank(String.valueOf(id), esi.getCurrentScore(), playerOrAllianceId+"#"+InjectorUtil.getInjector().serverId+"["+(dsEvent.getPlayer().getAllianceAbbr()==null?"":dsEvent.getPlayer().getAllianceAbbr())+"]"+name,pEvent.getEventType());
						}
					}
				}
			}
		}
	}
	
	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.DESTROY_SIDE};
	}
}
