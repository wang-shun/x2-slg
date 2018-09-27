package com.xgame.logic.server.game.gameevent;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.gamelog.event.AbstractEventListener;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceAbbrChangeEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceJoinEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceLeftEventObject;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceNameChangeEventObject;
import com.xgame.logic.server.game.commander.entity.eventmodel.CommanderChangeNameEventObject;
import com.xgame.logic.server.game.gameevent.entity.AllianceEvent;
import com.xgame.logic.server.game.gameevent.entity.Event;
import com.xgame.logic.server.game.gameevent.entity.EventScoreInfo;
import com.xgame.logic.server.game.gameevent.entity.PlayerEvent;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.utils.TimeUtils;

/**
 * 时间排行监听
 * @author dingpeng.qu
 *
 */
@Component
public class EventListener extends AbstractEventListener {

	@Autowired
	private EventManager eventManager;
	@Autowired
	private GlobalRedisClient globalRedisClient;
	

	public void onAction(IEventObject event) {
		int type = event.getType();
		switch (type) {
		case EventTypeConst.EVENT_COMMANDER_CHANGE_NAME:
		{
			//玩家改名事件
			CommanderChangeNameEventObject commanderChangeNameEventObject = (CommanderChangeNameEventObject)event;
			Player player = commanderChangeNameEventObject.getPlayer();
			String oldKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(player.getAllianceAbbr()==null?"":player.getAllianceAbbr())+"]"+commanderChangeNameEventObject.getOldName();
			String curKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(player.getAllianceAbbr()==null?"":player.getAllianceAbbr())+"]"+player.getName();
			this.updatePlayerRankInfo(oldKey, curKey, player.getId());
		}
		break;
		case EventTypeConst.EVENT_ALLIANCE_JOIN:
		{
			AllianceJoinEventObject allianceJoinEventObject = (AllianceJoinEventObject)event;
			Player player = allianceJoinEventObject.getPlayer();
			String oldKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"[]"+player.getName();
			String curKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(allianceJoinEventObject.getAbbr()==null?"":allianceJoinEventObject.getAbbr())+"]"+player.getName();
			this.updatePlayerRankInfo(oldKey, curKey, player.getId());
		}
		break;
		case EventTypeConst.EVENT_ALLIANCE_LEFT:
		{
			AllianceLeftEventObject allianceLeftEventObject = (AllianceLeftEventObject)event;
			Player player = allianceLeftEventObject.getPlayer();
			String oldKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(allianceLeftEventObject.getAbbr()==null?"":allianceLeftEventObject.getAbbr())+"]"+player.getName();
			String curKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"[]"+player.getName();
			this.updatePlayerRankInfo(oldKey, curKey, player.getId());
		}
		break;
		case EventTypeConst.ALLIANCE_NAME_CHANGE:
		{
			AllianceNameChangeEventObject allianceNameChangeEventObject = (AllianceNameChangeEventObject)event;
			//获取联盟正在进行中的事件
			Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceNameChangeEventObject.getAllianceId());
			if(alliance != null){
				String allianceOldKey = alliance.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(alliance.getAbbr()==null?"":alliance.getAbbr())+"]"+allianceNameChangeEventObject.getOldName();
				String allianceCurKey = alliance.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(alliance.getAbbr()==null?"":alliance.getAbbr())+"]"+allianceNameChangeEventObject.getAllianceName();
				this.updateAllianceRankInfo(allianceOldKey, allianceCurKey, alliance.getAllianceId());
			}
		}
		break;
		case EventTypeConst.ALLIANCE_ABBR_CHANGE:
		{
			AllianceAbbrChangeEventObject allianceAbbrChangeEventObject = (AllianceAbbrChangeEventObject)event;
			//获取联盟正在进行中的事件
			Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceAbbrChangeEventObject.getAllianceId());
			if(alliance != null){
				String allianceOldKey = alliance.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(allianceAbbrChangeEventObject.getOldAbbr()==null?"":allianceAbbrChangeEventObject.getOldAbbr())+"]"+alliance.getAllianceName();
				String allianceCurKey = alliance.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(allianceAbbrChangeEventObject.getAbbr()==null?"":allianceAbbrChangeEventObject.getAbbr())+"]"+alliance.getAllianceName();
				this.updateAllianceRankInfo(allianceOldKey, allianceCurKey, alliance.getAllianceId());
				
				for(Long playerId : alliance.getAllianceMember()){
					Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
					if(player != null){
						String oldKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(allianceAbbrChangeEventObject.getOldAbbr()==null?"":allianceAbbrChangeEventObject.getOldAbbr())+"]"+player.getName();
						String curKey = player.getId()+"#"+InjectorUtil.getInjector().serverId+"["+(allianceAbbrChangeEventObject.getAbbr()==null?"":allianceAbbrChangeEventObject.getAbbr())+"]"+player.getName();
						this.updatePlayerRankInfo(oldKey, curKey, player.getId());
					}
				}
			}
		}
		break;
		}
	}
	
	private void updatePlayerRankInfo(String oldKey,String curKey,long playerId){
		PlayerEvent playerEvent = InjectorUtil.getInjector().dbCacheService.get(PlayerEvent.class, playerId);
		if(playerEvent != null){
			Map<Long,EventScoreInfo> eventScoreInfo = playerEvent.getEventScoreInfoMap();
			if(eventScoreInfo != null){
				for(Long id : eventScoreInfo.keySet()){
					EventScoreInfo esi = eventScoreInfo.get(id);
					Event pEvent = InjectorUtil.getInjector().dbCacheService.get(Event.class, id);
					if(pEvent != null) {
						if(pEvent.getStartTime() <= TimeUtils.getCurrentTimeMillis() && esi.getCurrentScore() > 0) {
							eventManager.compareAndRefresh(pEvent.getEventType(), curKey, esi.getCurrentScore());
							globalRedisClient.zrem(DBKey.GLOBAL_RANK_KEY+":"+id, oldKey);
							globalRedisClient.zadd(DBKey.GLOBAL_RANK_KEY+":"+id, (double)esi.getCurrentScore(), curKey);
							long globalSize = globalRedisClient.zcard(DBKey.GLOBAL_RANK_KEY+":"+id);
							if(globalSize > DBKey.GLOBAL_EVENT_RANK_SIZE){
								globalRedisClient.zremrangebyrank(DBKey.GLOBAL_RANK_KEY+":"+id, 0, 0);
							}
						}
					}
				}
			}
		}
	}
	
	private void updateAllianceRankInfo(String oldKey,String curKey,long allianceId){
		AllianceEvent allianceEvent = InjectorUtil.getInjector().dbCacheService.get(AllianceEvent.class, allianceId);
		if(allianceEvent != null){
			Map<Long,EventScoreInfo> eventScoreInfo = allianceEvent.getEventScoreInfoMap();
			if(eventScoreInfo != null){
				for(Long id : eventScoreInfo.keySet()){
					EventScoreInfo esi = eventScoreInfo.get(id);
					Event pEvent = InjectorUtil.getInjector().dbCacheService.get(Event.class, id);
					if(pEvent != null) {
						if(pEvent.getStartTime() <= TimeUtils.getCurrentTimeMillis() && esi.getCurrentScore() > 0) {
							eventManager.compareAndRefresh(pEvent.getEventType(), curKey, esi.getCurrentScore());
							globalRedisClient.zrem(DBKey.GLOBAL_RANK_KEY+":"+id, oldKey);
							globalRedisClient.zadd(DBKey.GLOBAL_RANK_KEY+":"+id, (double)esi.getCurrentScore(), curKey);
							long globalSize = globalRedisClient.zcard(DBKey.GLOBAL_RANK_KEY+":"+id);
							if(globalSize > DBKey.GLOBAL_EVENT_RANK_SIZE){
								globalRedisClient.zremrangebyrank(DBKey.GLOBAL_RANK_KEY+":"+id, 0, 0);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EVENT_COMMANDER_CHANGE_NAME,EventTypeConst.EVENT_ALLIANCE_JOIN,EventTypeConst.EVENT_ALLIANCE_LEFT,EventTypeConst.ALLIANCE_ABBR_CHANGE,EventTypeConst.ALLIANCE_NAME_CHANGE};
	}
}