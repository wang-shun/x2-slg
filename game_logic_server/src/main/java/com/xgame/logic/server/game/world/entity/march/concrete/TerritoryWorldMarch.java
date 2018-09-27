package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Lists;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.data.sprite.SpriteType;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.OccLandEventObject;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.OccupyTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchFightState;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.TimeUtils;


/**
 * 占领世界地图行军
 * @author jacky.jiang
 *
 */
@Slf4j
public class TerritoryWorldMarch extends AbstractWorldMarch {

	/**
	 * 防守玩家签名
	 */
	private EmailSignature defSignature;
	
	public TerritoryWorldMarch(Player player, int targetPointId, MarchType marchType, WorldMarchSoldier worldMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans,EmailSignature defSignature) {
		super(player, targetPointId, marchType, worldMarchSoldier, soldierBriefBeans);
		this.defSignature = defSignature;
	}
	
	@Override
	public boolean checkMarch() {
		Player player = getPlayer();
		SpriteInfo targetSprite = getTargetSpriteInfo();
		if(targetSprite== null || (targetSprite.getSpriteType() != SpriteType.NONE.getType() && targetSprite.getSpriteType() != SpriteType.CAMP.getType() && targetSprite.getSpriteType() != SpriteType.TERRITORY.getType())) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE17);
			return false;
		}
		
		// TODO 是否是军事管理区
		if(checkSameAllianceMarch()) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE24);
			return false;
		}
		
		// 周围相邻格子必须要有我联盟领地才能出征
		WorldSprite worldSprite = targetSprite.getParam();
		if(worldSprite != null && worldSprite.getOwnerMarchId() <= 0) {
			boolean flag = player.getWorldLogicManager().checkAllianceTerritory(player, getTargetPointId());
			if(!flag) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE15);
				return false;
			}
		}
		
		return super.checkMarch();
	}

	@Override
	public void toDestination() {
		this.getWorldMarch().setMarchState(MarchState.OCCUPY);
		log.info("到达领地目的地:x:[{}],y[{}]", this.getWorldMarch().getDestination().x, this.getWorldMarch().getDestination().y);

		WorldMarch worldMarch = this.getWorldMarch();
		if(worldMarch == null ) {
			log.error("出征部队丢失...");
			this.failReturn();
			return;
		}
		
		SpriteInfo target = this.getTargetByTargetId();
		WorldSprite worldSprite = null;
		if(target == null) {
			target = getPlayer().getSpriteManager().createOccupySprite(worldMarch.getDestination().x, worldMarch.getDestination().y, worldMarch.getId());
			worldSprite = target.getParam();
		} else {
			// 到达目标
			worldSprite = target.getParam();
			if(target.getEnumSpriteType() != SpriteType.TERRITORY && target.getEnumSpriteType() != SpriteType.NONE && target.getEnumSpriteType() != SpriteType.CAMP) {
				this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.进攻失败占领_MAIL_ID);
				this.failReturn();
				return;
			}
		}
		
		// TODO 是否需要发送报告
		if(checkSameAllianceMarch()) {
			this.failReturn();
			return;	
		}
		
		// 不是当前玩家
		worldMarch.setMarchState(MarchState.OCCUPY);
		if (worldSprite.getOwnerMarchId() <= 0) {
			if(worldMarch.getMarchFight() == MarchFightState.FIGHT) {
				worldMarch.getExecutor().handleReturn();
				return;
			}
			
			// 进攻方开始占领
			int minute = getOccupyTimeByMarch(worldMarch.getWorldMarchSoldier());
			if(minute > 0) {
				doOccupy(worldMarch, this.getPlayer(), target, minute);
			} else {
				handleReturn();
			}
		} else {
			WorldMarch defendMatch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
			if(defendMatch != null) {
				//  攻打自己的部队
				if(Long.valueOf(defendMatch.getOwnerUid()) == getPlayer().getId()) {
					this.failReturn();
					return;
				}
				
				dealToDestination();
			} else {
				int minute = getOccupyTimeByMarch(worldMarch.getWorldMarchSoldier());
				if(minute > 0) {
					doOccupy(worldMarch, this.getPlayer(), target, minute);
				} else {
					handleReturn();
				}
			}
		}
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
		// 推送行军队列
		this.pushToWorldMarch(getPlayer(), worldMarch);
		this.pushToWorldSprites(target);
	}
	
	/**
	 * 开始占领
	 * @param worldMarch
	 * @param player
	 * @param target
	 * @param occupyTime 分钟数
	 */
	public void doOccupy(WorldMarch worldMarch, Player player, SpriteInfo target, int occupyTime) {
		if (player.getAllianceId() <= 0) {
			worldMarch.getExecutor().handleReturn();
			return;
		}
		
		// 进攻方开始占领
		WorldSprite worldSprite = target.getParam();
		TimerTaskData timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.OCCUPY).register(player, occupyTime * 60, new OccupyTimerTaskData(worldMarch.getId()));
		worldMarch.setOccupyTaskId(timerTaskData.getId());
		worldMarch.setExploreTime(occupyTime * 60);
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
		// 更新行军信息
		target.setSpriteType(SpriteType.TERRITORY.getType());
		worldSprite.setStartTime(TimeUtils.getCurrentTimeMillis());
		worldSprite.setEndTime(worldSprite.getStartTime() + occupyTime * 60 * 1000L);
		worldSprite.setOwnerMarchId(worldMarch.getId());
		InjectorUtil.getInjector().dbCacheService.update(target);
		// 发送战报
		player.getPlayerMailInfoManager().sendTerritoryEmail(player, worldMarch, target.getX(), target.getY());
		if(worldSprite.getOwnerMarchId() <= 0){
			EventBus.getSelf().fireEvent(new OccLandEventObject(player,OccLandEventObject.OLDOWNER_KONGDI));
		}else{
			EventBus.getSelf().fireEvent(new OccLandEventObject(player,OccLandEventObject.OLDOWNER_PLAYER));
		}
	}
	
	@Override
	public void handleReturn() {
		SpriteInfo spriteInfo = this.getTargetByTargetId();
		if(spriteInfo != null) {
			WorldSprite worldSprite = spriteInfo.getParam();
			if(worldSprite.getOwnerMarchId() == getWorldMarch().getId()) {
				worldSprite.setOwnerMarchId(0);
				spriteInfo.setSpriteType(SpriteType.NONE.getType());
				InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
			}
		}
		super.handleReturn();
	}

	/**
	 * 获取占领时间
	 * @param soldierMarchList
	 * @return
	 */
	public int getOccupyTimeByMarch(WorldMarchSoldier worldMarchSoldier) {
		GlobalPirFactory.get(GlobalConstant.OCCUPY_RATIO);
		// TODO占领时间
		int totalMinute = 2;
//		if(worldMarchSoldier != null) {
//			for(Soldier soldier : worldMarchSoldier.querySoldierList()) {
//				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
//				DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
//				int ratio = GlobalPirFactory.getInstance().jia_ren_ratio[designMap.getSystemIndex()];
//				int num = ratio * soldier.getNum();
//				double[] occupyRatio = GlobalPirFactory.getInstance().occupy_ratio;
//				totalMinute += Math.max(Math.min(occupyRatio[0] / Math.ceil((double)num / occupyRatio[1]), occupyRatio[2]), occupyRatio[3]);
//			}
//		}
		return totalMinute;
	}
	
	
//	@Override
//	public int getMarchTime() {
//		return (int)AttributeEnum.ARMY_FIGHT_MARCH_TIME.playerMath(getPlayer(), super.getMarchTime());
//	}

	@Override
	public boolean initBattle() {
		SpriteInfo target = this.getTargetByTargetId();
		WorldSprite worldSprite = target.getParam();
		WorldMarch defendMatch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
		Player defenPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(defendMatch.getOwnerUid()));

		// TODO 同盟玩家是否需要邮件
		if(this.checkSameAllianceMarch()) {
			log.debug("同盟玩家直接返回。");
			this.failReturn();
			return false;
		}
	
		if(defendMatch.getOccupyTaskId() > 0) {
			// 取消占领
			TimerTaskData timerTaskData  = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, defendMatch.getOccupyTaskId());
			int nowTime = TimeUtils.getCurrentTime();
			if(timerTaskData != null && timerTaskData.getTriggerTime() > TimeUtils.getCurrentTime()) {
				defenPlayer.getTimerTaskManager().removeTimerTask(defenPlayer, defendMatch.getOccupyTaskId());
				defendMatch.setOccupyTaskId(0);

				// 已经占领的比率
				double ratio = ((double)(timerTaskData.getTriggerTime() - nowTime)) / (timerTaskData.getTriggerTime() - timerTaskData.getStartTime());
				defendMatch.setFinishOccupyRatio(ratio);
			}
		}
		
		WarFightParam warFightParam = new WarFightParam();
		warFightParam.setAttackWorldMarch(getWorldMarch());
		warFightParam.setBattleType(WarType.TERRITORY);
		warFightParam.setAttackPlayer(getPlayer());
		List<WorldMarchSoldier> attackMarchList = Lists.newArrayList(this.getWorldMarch().getWorldMarchSoldier());
		warFightParam.setAttackSoldiers(attackMarchList);
		
		warFightParam.setDefendWorldMarch(defendMatch);
		warFightParam.setDefendPlayer(defenPlayer);
		warFightParam.setDefendSoldiers(Lists.newArrayList(defendMatch.getWorldMarchSoldier()));

		warFightParam.setTargetSpriteInfo(target);
		
		super.initBattle();
		getPlayer().getWarManager().startBattle(warFightParam);
		return true;
	}
	
	
	@Override
	public void failReturn() {
		super.failReturn();
	}

	/**
	 * 处理推出联盟
	 */
	public void dealExitAlliance() {
		//  处理出征的玩家
		WorldMarch worldMarch = getWorldMarch();
		Player player = getPlayer();
		if(worldMarch.getMarchType() == MarchType.TERRITORY && worldMarch.getMarchState() == MarchState.OCCUPY) {
			if(worldMarch.getOccupyTaskId() > 0) {
				SpriteInfo spriteInfo = ((TerritoryWorldMarch)worldMarch.getExecutor()).getTargetByTargetId();
				if(spriteInfo != null) {
					WorldSprite worldSprite = spriteInfo.getParam();
					if(worldSprite != null && worldSprite.getOwnerMarchId() == worldMarch.getId()) {
						if(worldSprite.checkFight()) {
							return;
						}
						
						spriteInfo.setSpriteType(SpriteType.NONE.getType());
						worldSprite.setOwnerMarchId(0);
						InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
						
						// 推送精灵变更
						player.getWorldLogicManager().pushWorldTerritory(player, spriteInfo);
					}
				}
				
				TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getOccupyTaskId());
				if(timerTaskData != null) {
					// 移除占领时间
					player.getTimerTaskManager().removeTimerTask(player, timerTaskData);
					worldMarch.getExecutor().failReturn();
				}
			}
		}
	}
}
