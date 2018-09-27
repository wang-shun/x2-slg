package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Lists;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.entity.eventmodel.CollectSatrtEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.PickEventObject;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.CollectTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.ExplorerInfo;
import com.xgame.logic.server.game.world.entity.MarchCollect;
import com.xgame.logic.server.game.world.entity.MarchUtils;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.ExplorerRecord;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.TimeUtils;

/**
 * 采集行军
 * @author jacky.jiang
 *
 */
@Slf4j
public class ExplorerWorldMarch extends AbstractWorldMarch {

	/**
	 * 采集资源类型
	 */
	private int resourceType;
	
	/**
	 * 防守玩家签名
	 */
	private EmailSignature defSignature;
	
	public ExplorerWorldMarch(Player player, int targetPointId, MarchType marchType, int resourceType, WorldMarchSoldier worldMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans,EmailSignature defSignature) {
		super(player, targetPointId, marchType, worldMarchSoldier, soldierBriefBeans);
		this.resourceType = resourceType;
		this.defSignature = defSignature;
	}
	
	@Override
	public boolean checkMarch() {
		SpriteInfo targetSprite = getTargetSpriteInfo();
		if(targetSprite== null || targetSprite.getSpriteType() != SpriteType.RESOURCE.getType()) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE17);
			return false;
		}
		
		if(checkSameAllianceMarch()) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE24);
			return false;
		}
		
		return super.checkMarch();
	}

	@Override
	public void toDestination() {
		Vector2Bean destination = WorldConverter.getVector2Bean(this.getWorldMarch().getTargetId());
		log.info("资源采集到达目的地:x:[{}],y[{}]", destination.x, destination.y);
	
		WorldMarch worldMarch = this.getWorldMarch();
		Player player = getPlayer();
		if(worldMarch == null) {
			log.error("出征部队丢失...");
			return;
		}
		
		worldMarch.setMarchState(MarchState.OCCUPY);
		SpriteInfo target = this.getTargetByTargetId();
		if(target == null || target.getEnumSpriteType() != SpriteType.RESOURCE) {
			log.error("目标丢失返回...");
			this.failReturn();
			player.getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.进攻失败资源_MAIL_ID);
			return;
		}
		
		// 世界行军信息
		WorldSprite worldSprite = target.getParam();
		if(worldSprite != null && worldSprite instanceof ResourceSprite) {
			ResourceSprite resourceSprite = (ResourceSprite)worldSprite;
			if(resourceSprite.getOwnerMarchId() > 0) {
				WorldMarch defendMatch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, resourceSprite.getOwnerMarchId());
				if(defendMatch != null) {
					dealToDestination();
				} else {
					//  计算采集时间，采集速度，之后开始采集
					resourceSprite.setOwnerMarchId(worldMarch.getId());
					ExplorerInfo explorerInfo = getResoruceExploreInfo(0, worldMarch, player, resourceSprite);
					doExplorer(worldMarch, getPlayer(), target, explorerInfo);
					log.info("开始采集,采集所需时间:[{}]", explorerInfo.getExplorerTime());
				}
			} else {
				//  计算采集时间，采集速度，之后开始采集
				resourceSprite.setOwnerMarchId(worldMarch.getId());
				ExplorerInfo explorerInfo = getResoruceExploreInfo(0, worldMarch, player, resourceSprite);
				doExplorer(worldMarch, getPlayer(), target, explorerInfo);
				log.info("开始采集,采集所需时间:[{}]", explorerInfo.getExplorerTime());
			}
		} else {
			this.failReturn();
		}
		
		this.pushToWorldMarch(player, worldMarch);
	}
	
	@Override
	public void handleReturn() {
		// 计算资源采集数量
		try {
			SpriteInfo spriteInfo = this.getTargetSpriteInfo();
			if(spriteInfo != null) {
				WorldSprite worldSprite = spriteInfo.getParam();
				if(worldSprite != null && worldSprite instanceof ResourceSprite) {
					ResourceSprite resourceSprite = (ResourceSprite)worldSprite;
					
					// 清空资源点上的行军信息
					if(resourceSprite.getOwnerMarchId() == this.getWorldMarch().getId()) {
						resourceSprite.setOwnerMarchId(0);
						InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
					}
					
					WorldMarch worldMarch = this.getWorldMarch();
					if(this.getWorldMarch().getSoldierNum() <= 0) {
						backHomeImmediately(worldMarch);
					} else {
						// 计算采集返回
						
						MarchCollect marchCollect = getMarchCollect(getPlayer(), worldMarch, spriteInfo);
						if(marchCollect != null) {
							worldMarch.setAttach(marchCollect);
							InjectorUtil.getInjector().dbCacheService.update(worldMarch);
						}
						
						// 判断资源为0刷新资源点信息
						boolean refresh = false;
						
						// 设置采集信息
						if(resourceSprite.getCurNum() - marchCollect.getCollectNum() <= 0) {
							// 采集先返回，再刷新地图
							this.backReturn();
							refreshResource(spriteInfo);
							refresh = true;
						} else {
							// 更新精灵信息 
							resourceSprite.setCurNum(Math.max(resourceSprite.getCurNum() - marchCollect.getCollectNum(), 0));
					
							// 添加采集报告
							ExplorerRecord explorerRecord = new ExplorerRecord();
							explorerRecord.setPlayerName(this.getPlayer().getName());
							explorerRecord.setPlusNum(resourceSprite.getCurNum());
							explorerRecord.setExplorerTime(TimeUtils.getCurrentTimeMillis());
							resourceSprite.addRecord(explorerRecord);
							this.backReturn();
						}
						
						InjectorUtil.getInjector().dbCacheService.update(this.getWorldMarch());
						InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
						if(!refresh) {
							this.pushToWorldSprites(spriteInfo);	
						}
						
						log.info("部队id:{}资源采集完毕, 携带资源id:[{}], 资源数量:[{}]",worldMarch.getId(),  marchCollect.getResourceType(), marchCollect.getCollectNum());
					}
				} else {
					log.info("资源转换成空地");
					this.backReturn();
				}
			}
		} catch(Exception e) {
			log.error("采集返回报错:", e);
			this.failReturn();
		}
	}
	
	@Override
	public void backReturn() {
		WorldMarch worldMarch = getWorldMarch();
		if(worldMarch.getMarchType() == MarchType.SCOUT || worldMarch.getMarchType() == MarchType.TRADE) {
			super.backReturn();
		} else {
			if (worldMarch.getSoldierNum() <= 0) {
				super.backHomeImmediately(worldMarch);
			} else {
				super.backReturn();
			}
		}
	}

	/**
	 * 计算采集信息
	 * @param resourceSprite
	 * @param speed
	 * @param costTime
	 * @param resWarEndMessage
	 */
	public MarchCollect getMarchCollect(Player player, WorldMarch worldMarch, SpriteInfo spriteInfo) {
		// 采集时间
		WorldSprite worldSprite = spriteInfo.getParam();
		if(worldSprite == null) {
			return new MarchCollect();
		}
		
		if(worldMarch.getCollectTimerTaskData() == null) {
			return new MarchCollect();
		}
		
		ResourceSprite resourceSprite = (ResourceSprite)worldSprite;
		int collectNum = worldMarch.getCollectTimerTaskData().getExploreredNum(player, worldMarch, resourceSprite);
		
		MarchCollect marchCollect = new MarchCollect();
		marchCollect.setCollectNum(Math.min(resourceSprite.getCurNum(), collectNum));
		marchCollect.setResourceType(resourceSprite.getResourceType());
		marchCollect.setLevel(resourceSprite.getLevel());
		return marchCollect;
	}
	
//	/**
//	 * 
//	 * @param player
//	 * @param worldMarch
//	 * @param spriteInfo
//	 * @return
//	 */
//	public MarchCollect getFailMarchCollect(Player player, WorldMarch worldMarch, SpriteInfo spriteInfo) {
//		// 采集时间
//		ResourceSprite resourceSprite = spriteInfo.getParam();
//		int collectNum = worldMarch.getCollectTimerTaskData().getExploreredNum(player, worldMarch, resourceSprite);
//		
//		MarchCollect marchCollect = new MarchCollect();
//		marchCollect.setCollectNum(Math.min(resourceSprite.getCurNum(), collectNum));
//		marchCollect.setResourceType(resourceSprite.getResourceType());
//		marchCollect.setLevel(resourceSprite.getLevel());
//		return marchCollect;
//	}
	
	@Override
	public void updateWorldMarch() {
		// 修改行军信息
		WorldMarch worldMarch = this.getWorldMarch();
		worldMarch.setTargetSubType(resourceType);
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
	}

	@Override
	public void failReturn() {
		WorldMarch worldMarch = this.getWorldMarch();
		if(worldMarch != null && worldMarch.getMarchState() == MarchState.OCCUPY) {
			if(worldMarch.getExlporerTaskId() > 0) {
				getPlayer().getTimerTaskManager().removeTimerTask(getPlayer(), worldMarch.getExlporerTaskId());
			}
			
			SpriteInfo spriteInfo = this.getTargetByTargetId();
			if(spriteInfo != null) {
				WorldSprite worldSprite = spriteInfo.getParam();
				if(worldSprite != null && worldSprite instanceof ResourceSprite) {
					ResourceSprite resourceSprite = (ResourceSprite)worldSprite;
					
					// 采集信息
					if(worldMarch.getId() == worldSprite.getOwnerMarchId()) {
						worldSprite.setOwnerMarchId(0);
					}

					MarchCollect marchCollect = getMarchCollect(this.getPlayer(), this.getWorldMarch(), spriteInfo);
					this.getWorldMarch().setAttach(marchCollect);
					
					// 添加采集报告
					int plusNum = Math.max(resourceSprite.getCurNum() - marchCollect.getCollectNum(), 0);
					ExplorerRecord explorerRecord = new ExplorerRecord();
					explorerRecord.setPlayerName(this.getPlayer().getName());
					explorerRecord.setPlusNum(plusNum);
					explorerRecord.setExplorerTime(TimeUtils.getCurrentTimeMillis());
					resourceSprite.addRecord(explorerRecord);
					
					// 更新精灵信息 
					resourceSprite.setCurNum(plusNum);
					
					// 更新资源信息
					InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
				} else {
					if(worldMarch.getId() == worldSprite.getOwnerMarchId()) {
						worldSprite.setOwnerMarchId(0);
					}
				}
			}
		}
		
		super.failReturn();
	}
	
	@Override
	public void backReturnHome() {
		MarchCollect marchCollect = (MarchCollect)this.getWorldMarch().getAttach();
		try {
			if(marchCollect != null) {
				// 采集事件触发
				PickEventObject peo = new PickEventObject(this.getPlayer());
				peo.setNum(marchCollect.getCollectNum());
				
				// 采集报告
				this.getPlayer().getPlayerMailInfoManager().sendCollectionEmailInfo(this.getPlayer(), WorldConverter.getVector2Bean(getWorldMarch().getTargetId()), marchCollect);
				if(marchCollect.getResourceType() == CurrencyEnum.OIL) {
					CurrencyUtil.increaseCurrency(this.getPlayer(), 0, 0, marchCollect.getCollectNum(), 0, GameLogSource.EXPLORER);
				} else if(marchCollect.getResourceType() == CurrencyEnum.GLOD) {
					CurrencyUtil.increaseCurrency(this.getPlayer(), marchCollect.getCollectNum(), 0, 0, 0,  GameLogSource.EXPLORER);
					peo.setCurrentNum(this.getPlayer().roleInfo().getCurrency().getMoney());
					peo.setSubType(CurrencyEnum.GLOD.ordinal());
				} else if(marchCollect.getResourceType() == CurrencyEnum.RARE) {
					CurrencyUtil.increaseCurrency(this.getPlayer(), 0, 0, 0, marchCollect.getCollectNum(),  GameLogSource.EXPLORER);
					peo.setCurrentNum(this.getPlayer().roleInfo().getCurrency().getRare());
					peo.setSubType(CurrencyEnum.RARE.ordinal());
				} else if(marchCollect.getResourceType() == CurrencyEnum.STEEL) {
					CurrencyUtil.increaseCurrency(this.getPlayer(), 0, marchCollect.getCollectNum(), 0, 0, GameLogSource.EXPLORER);
					peo.setCurrentNum(this.getPlayer().roleInfo().getCurrency().getSteel());
					peo.setSubType(CurrencyEnum.STEEL.ordinal());
				}
				
				EventBus.getSelf().fireEvent(peo);
			}
		} catch(Exception e) {
			log.error("采集任务结束异常:", e);
		}
		
		super.backReturnHome();
		log.info("采集任务完成,返回主基地。");
	}

	/**
	 * 刷新资源
	 * @param spriteInfo
	 */
	private void refreshResource(SpriteInfo spriteInfo) {
		SpriteInfo spriteInfo2 = InjectorUtil.getInjector().spriteManager.explorerRefreshResource(spriteInfo);
		
		// 推送精灵
		if(spriteInfo2 != null) {
			this.pushToWorldSprites(spriteInfo2);
		} else {
			this.pushToWorldSprites(spriteInfo);
		}
	}
	
	/**
	 * 开始采集
	 * @param worldMarch
	 * @param player
	 * @param spriteInfo
	 * @param explorerTime
	 */
	public void doExplorer(WorldMarch worldMarch, Player player, SpriteInfo spriteInfo, ExplorerInfo explorerInfo) {
		if(explorerInfo.getExplorerTime() <= 0) {
			worldMarch.getExecutor().handleReturn();
			return;
		}
		
		worldMarch.setExploreTime(explorerInfo.getExplorerTime()) ;
		int taskTime = worldMarch.getExploreTime();
		
		// 采集信息
		CollectTimerTaskData collectTimerTaskData = new CollectTimerTaskData(worldMarch.getId(), explorerInfo.getMaxNum(), explorerInfo.getExplorerTime(), explorerInfo.getExplorerSpeed(), TimeUtils.getCurrentTimeMillis(), worldMarch.getExlplorerNum());
		TimerTaskData timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.COLLECT).register(player, taskTime, collectTimerTaskData);
		worldMarch.setExlporerTaskId(timerTaskData.getId());
		worldMarch.setCollectTimerTaskData(collectTimerTaskData);
		worldMarch.setExploreTime(taskTime);
		worldMarch.setMaxNum(explorerInfo.getMaxNum());
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
		// 生成采集信息
		log.info("战斗结束, 采集开始,采集所需时间[{}]", taskTime);
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		pushToWorldMarch(player, worldMarch);
		pushToWorldSprites(spriteInfo);
		
		EventBus.getSelf().fireEvent(new CollectSatrtEventObject(player));
	}

//	/**
//	 * 获取资源已采集数量
//	 * @param worldMarch
//	 * @param resourceSprite
//	 * @return
//	 */
//	public ExplorerInfo getResourceExplorerInfo(WorldMarch worldMarch, ResourceSprite resourceSprite) {
//		int weight = worldMarch.getWeight(getPlayer());
//		int maxNum = MarchUtils.mathGiveNum(resourceSprite.getCurNum(), weight, resourceSprite.getResourceType());
//		int explorerTime = Long.valueOf((TimeUtils.getCurrentTimeMillis() - worldMarch.getCollectTimerTaskData().getExplorerStartTime()) / 1000).intValue();
//		double explorerSpeed = PlayerAttributeManager.get().getExplorerSpeed(this.getPlayer(), resourceSprite.getResourceType(), resourceSprite.getAllianceId(), resourceSprite.getLevel());
//		return new ExplorerInfo(explorerSpeed, weight, maxNum, explorerTime);
//	}
	
	/**
	 * 获取采集时间
	 * @param plusNum
	 * @param worldMarch
	 * @param attackPlayer
	 * @param spriteInfo
	 * @return
	 */
	public ExplorerInfo getResoruceExploreInfo(int plusNum, WorldMarch worldMarch, Player attackPlayer, ResourceSprite resourceSprite) {
		int weight = worldMarch.getWeight(attackPlayer);
		int explorerNum = MarchUtils.mathGiveNum(weight, resourceSprite.getResourceType());
		explorerNum = Math.min(resourceSprite.getCurNum(), explorerNum);
		
		int explorerPlusNum = (explorerNum - plusNum);
		double speed =  PlayerAttributeManager.get().getExplorerSpeed(attackPlayer, resourceSprite.getResourceType(), resourceSprite.getAllianceId(), resourceSprite.getLevel());
		int explorerTime = MarchUtils.explorer(speed, explorerPlusNum);
		
		ExplorerInfo explorerInfo = new ExplorerInfo();
		explorerInfo.setExplorerTime(explorerTime);
		explorerInfo.setExplorerSpeed(speed);
		explorerInfo.setMaxNum(explorerNum);
		explorerInfo.setPlusNum(plusNum);
		return explorerInfo;
	}
	

	/**
	 * 加速采集
	 * @param player
	 * @param worldMarch
	 * @param ratio
	 */
	public void speedUpExplorer(double ratio, long endTime) {
		WorldMarch worldMarch = getWorldMarch();
		Player player = getPlayer();
		TimerTaskData timerTaskData = InjectorUtil.getInjector().timerTaskManager.get(worldMarch.getExlporerTaskId());
		if(timerTaskData != null && timerTaskData.getTriggerTime() > TimeUtils.getCurrentTimeMillis()) {
			// 采集加速
			SpriteInfo spriteInfo = this.getTargetByTargetId();
			
			int calcEndTime = (int)(endTime / 1000);
			int deductTime = 0;
			if(timerTaskData.getTriggerTime() > calcEndTime) {
				deductTime = calcEndTime - TimeUtils.getCurrentTime();
			} else {
				deductTime = (timerTaskData.getTriggerTime() - TimeUtils.getCurrentTime());
			}
			
			int time = Double.valueOf(deductTime * ratio).intValue();
			worldMarch.setExploreTime(worldMarch.getExploreTime()  - time);
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
			TimerTaskHolder.getTimerTask(TimerTaskCommand.COLLECT).speedUp(player, timerTaskData, time);
			this.pushToWorldMarch(player, worldMarch);
			this.pushToWorldSprites(spriteInfo);
		}
	}

	@Override
	public boolean initBattle() {
		SpriteInfo target = this.getTargetByTargetId();
		ResourceSprite resourceSprite = target.getParam();
		WorldMarch defendMatch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, resourceSprite.getOwnerMarchId());
		Player defenPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(defendMatch.getOwnerUid()));
		if(checkSameAllianceMarch()) {
			this.failReturn();
			return false;
		}
	
		int exlploreNum = ((ExplorerWorldMarch)defendMatch.getExecutor()).getMarchCollect(defenPlayer, defendMatch, target).getCollectNum();
		defendMatch.setExlplorerNum(defendMatch.getExlplorerNum() + exlploreNum);
		if(defendMatch.getExlporerTaskId() > 0) {
			defenPlayer.getTimerTaskManager().removeTimerTask(defenPlayer, defendMatch.getExlporerTaskId());
			
			defendMatch.setExlporerTaskId(0);
			InjectorUtil.getInjector().dbCacheService.update(defendMatch);
			resourceSprite.setExplorerSpeed(0);
			InjectorUtil.getInjector().dbCacheService.update(target);
		}
		
		WarFightParam warFightParam = new WarFightParam(); 
		warFightParam.setAttackPlayer(getPlayer());
		warFightParam.setAttackWorldMarch(this.getWorldMarch());
		
		List<WorldMarchSoldier> attackMarchList = Lists.newArrayList(this.getWorldMarch().getWorldMarchSoldier());
		warFightParam.setAttackSoldiers(attackMarchList);
		warFightParam.setTargetSpriteInfo(target);
		warFightParam.setBattleType(WarType.EXPLORER);
		if(exlploreNum > 0) {
			warFightParam.setDefendExplorerNum(defendMatch.getExlplorerNum());
		}
		
		List<WorldMarchSoldier> defendMarchSoldiers = Lists.newArrayList(defendMatch.getWorldMarchSoldier());
		warFightParam.setDefendWorldMarch(defendMatch);
		warFightParam.setDefendPlayer(defenPlayer);
		warFightParam.setDefendSoldiers(defendMarchSoldiers);
		
		warFightParam.setResourceType(resourceSprite.getResourceType().ordinal());
		warFightParam.setResourceLevel(resourceSprite.getLevel());
		
		super.initBattle();
		this.getPlayer().getWarManager().startBattle(warFightParam);
		return true;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}
	
}
