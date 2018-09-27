package com.xgame.logic.server.game.gameevent.listener;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.IListener;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.awardcenter.AwardUtil;
import com.xgame.logic.server.game.awardcenter.entity.AwardDB;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.email.MailKit;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.gameevent.constant.EventTypeEnum;
import com.xgame.logic.server.game.gameevent.entity.EventScoreInfo;
import com.xgame.logic.server.game.player.entity.Player;

public abstract class BaseEventListener implements IListener {
	
	@Autowired
	private GlobalRedisClient globalRedisClient;

	@PostConstruct
	public void init(){
		EventBus.getSelf().addEventListener(this); 
	}
	
	/**
	 * 维护排行榜
	 * @param key
	 * @param score
	 * @param name
	 */
	public void updateRank(String key,int score,String name,int eventType){
		//维护本服排行 flag为true表示排行有更新
		boolean flag = InjectorUtil.getInjector().eventManager.compareAndRefresh(eventType, name, score);
		//维护世界排行
		if(flag){
			globalRedisClient.zadd(DBKey.GLOBAL_RANK_KEY+":"+key, (double)score, name);
			long globalSize = globalRedisClient.zcard(DBKey.GLOBAL_RANK_KEY+":"+key);
			if(globalSize > DBKey.GLOBAL_EVENT_RANK_SIZE){
				globalRedisClient.zremrangebyrank(DBKey.GLOBAL_RANK_KEY+":"+key, 0, 0);
			}
		}
	}
	
	/**
	 * 发奖
	 * @param esi
	 * @param player
	 */
	public void giveRewards(EventScoreInfo esi,Set<Long> playerIds,int eventType){
		int mailId = 0;
		if(eventType == EventTypeEnum.Group.getValue()){
			mailId = EmailTemplet.军团事件奖励1_MAIL_ID;
		}else if(eventType == EventTypeEnum.Player.getValue()){
			mailId = EmailTemplet.个人事件奖励1_MAIL_ID;
		}else if(eventType == EventTypeEnum.Honour.getValue()){
			mailId = EmailTemplet.荣耀事件奖励_MAIL_ID;
		}
		if(esi.getCurrentScore()>=esi.getScore1() && esi.getStatu1()==0){
			esi.setStatu1(1);
			String[] itemInfo = esi.getRewards1().split("_");
			for(long playerId : playerIds){
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
				AwardDB adb = AwardUtil.ITEM.giveCenter(player, Integer.parseInt(itemInfo[0]), Integer.parseInt(itemInfo[1]), SystemEnum.EVENT.getId(), GameLogSource.EVENT_ACHIVE);
				player.getAwardCenterManager().sendAwardsToClient(Lists.newArrayList(adb));
				if(mailId > 0){
					MailKit.sendSystemEmail(playerId, mailId, "",esi.getRewards1());
				}
			}
		} 
		if(esi.getCurrentScore()>=esi.getScore2() && esi.getStatu2()==0){
			esi.setStatu2(1);
			String[] itemInfo = esi.getRewards2().split("_");
			for(long playerId : playerIds){
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
				AwardDB adb = AwardUtil.ITEM.giveCenter(player, Integer.parseInt(itemInfo[0]), Integer.parseInt(itemInfo[1]), SystemEnum.EVENT.getId(), GameLogSource.EVENT_ACHIVE);
				player.getAwardCenterManager().sendAwardsToClient(Lists.newArrayList(adb));
				if(mailId > 0){
					MailKit.sendSystemEmail(playerId, mailId, "",esi.getRewards2());
				}
			}
		}
		if(esi.getCurrentScore()>=esi.getScore3() && esi.getStatu3()==0){
			esi.setStatu3(1);
			String[] itemInfo = esi.getRewards3().split("_");
			for(long playerId : playerIds){
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
				AwardDB adb = AwardUtil.ITEM.giveCenter(player, Integer.parseInt(itemInfo[0]), Integer.parseInt(itemInfo[1]), SystemEnum.EVENT.getId(), GameLogSource.EVENT_ACHIVE);
				player.getAwardCenterManager().sendAwardsToClient(Lists.newArrayList(adb));
				if(mailId > 0){
					MailKit.sendSystemEmail(playerId, mailId, "",esi.getRewards3());
				}
			}
		}
	}
	
}
