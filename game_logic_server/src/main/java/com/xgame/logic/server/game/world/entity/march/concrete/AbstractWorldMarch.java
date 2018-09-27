package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.MathUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.entity.model.SoldierChangeEventObject;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.AllianceBattleTeamLeaderData;
import com.xgame.logic.server.game.timertask.entity.job.data.RunCampTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchFightState;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.IMarchExecutor;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.logic.server.game.world.message.ResUpdateSpriteMessage;
import com.xgame.logic.server.game.world.message.ResUpdateVectorInfoMessage;
import com.xgame.utils.TimeUtils;

/**
 * 世界地图行军
 * @author jacky.jiang
 *
 */
@Slf4j
public abstract class AbstractWorldMarch implements IMarchExecutor {
	
	// 出征信息
	private WorldMarch worldMarch;
	// 玩家信息
	private Player player;
	//行军类型
	private MarchType marchType;
	// 精灵信息
	private SpriteInfo targetSpriteInfo;
	// 士兵列表
	private WorldMarchSoldier worldMarchSoldier;
	// 目标点
	private int targetPointId;
	// 出征士兵信息
	private List<WorldSimpleSoldierBean> soldierBriefBeans;
	
	/**
	 * 行军信息
	 * @param player
	 * @param targetPointId
	 * @param marchType
	 * @param worldMarchSoldier
	 * @param soldierBriefBeans
	 */
	public AbstractWorldMarch(Player player, int targetPointId, MarchType marchType, WorldMarchSoldier worldMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans) {
		super();
		this.player = player;
		this.marchType = marchType;
		this.targetSpriteInfo = player.getSpriteManager().getGrid(targetPointId);
		this.targetPointId = targetPointId;
		this.soldierBriefBeans = soldierBriefBeans;
		this.worldMarchSoldier = worldMarchSoldier;
	}
	
	@Override
	public boolean checkMarch() {
		int num = PlayerAttributeManager.get().matchQueue(player.getId());
		List<WorldMarch> worldMarchs = this.player.getWorldMarchManager().getWorldMarchByPlayerId(player.getRoleId());
		if(num <= 0 || (worldMarchs != null && worldMarchs.size() >= num)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE1, num);
			return false;
		}
		
		if(soldierBriefBeans != null && !soldierBriefBeans.isEmpty()) {
			int soldierNum = 0;
			int runNum = 0;
			Iterator<Soldier> soldier = player.roleInfo().getSoldierData().getSoldiers().values().iterator();
			while(soldier.hasNext()) {
				Soldier tempSoldier = soldier.next();
				runNum += tempSoldier.getMarchNum();
			}
			
			// TODO 验证最大战力
			Iterator<WorldSimpleSoldierBean> iterator = soldierBriefBeans.iterator();
			while (iterator.hasNext()) {
				WorldSimpleSoldierBean runCamp =  iterator.next();
				if(runCamp.soldiers == null) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE1);
					return false;
				}
			}
			
			// 出征上线由军营v1 +　统帅
			int levelNum = player.getSoldierManager().getMaxSetOffNum(player);
			if(soldierNum > levelNum || runNum + soldierNum > levelNum) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE8, levelNum);
				return false;
			}
		}
		
		return true;
	}

	@Override
	public WorldMarch march() {
		
		if(!checkMarch()) {
			return null;
		}
		
		// 只有出征士兵才能引起保护罩状态变化
		dealPlayerShield(player, this.marchType.ordinal(), targetSpriteInfo);
		
		// 行军
		worldMarch = crateMarch();
		
		// 出征扣除士兵
		if(soldierBriefBeans != null && !soldierBriefBeans.isEmpty()) {
			WorldMarchSoldier worldMarchSoldier = marchRemoveSoldier(player, soldierBriefBeans, marchType);
			this.worldMarch.setWorldMarchSoldier(worldMarchSoldier);
			this.worldMarchSoldier = worldMarchSoldier;
			InjectorUtil.getInjector().dbCacheService.update(this.worldMarch);
		}
		
		// 行军速度
		double speed = getMarchSpeed();
		
		// 设置出征时间  时间fix
		int marchTime = getFinalMarchTime();
		worldMarch.march(marchTime);
		
		// 更新行军信息
		updateWorldMarch();
		
		// 世界行军信息
		if(worldMarch.getMarchType() == MarchType.TEAM_ATTACK) {
			int remainTime = (int)((this.worldMarch.getMarchStartTime() - TimeUtils.getCurrentTimeMillis()) / 1000);
			TimerTaskData timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.ALLIANCE_BATTLE_TEAM_LEADER).register(player, remainTime, new AllianceBattleTeamLeaderData(worldMarch.getTeamId(), worldMarch.getId()));
			worldMarch.setTaskId(timerTaskData.getId());
		} else {
			TimerTaskData timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.RUN_SOLDIER).register(player, worldMarch.getMarchTime(), new RunCampTaskData(worldMarch.getId()));
			worldMarch.setTaskId(timerTaskData.getId());
		}
	
		InjectorUtil.getInjector().dbCacheService.create(worldMarch);
		if(worldMarch.getMarchType() != MarchType.TEAM_ATTACK) {
			this.pushToWorldMarch(player, worldMarch);
			Vector2Bean vector2Bean = WorldConverter.getVector2Bean(worldMarch.getTargetId());
			log.debug(String.format("前往目标x:%s, y:%s, 速度:%s, 前往时间: %s, 负载数量：%s", vector2Bean.x, vector2Bean.y, speed , worldMarch.getMarchTime(), worldMarch.getWeight(this.getPlayer())));
		} else {
			log.debug("行军集结进攻等待中");
		}
		
		return worldMarch;
	}

	@Override
	public void toDestination() {
		Vector2Bean vector2Bean = WorldConverter.getVector2Bean(worldMarch.getTargetId());
		log.info("到达目的地:x:[{}],y[{}]", vector2Bean.x, vector2Bean.y);
		SpriteInfo target = this.getPlayer().getSpriteManager().getVisibleGrid(vector2Bean.x, vector2Bean.y);
		if(target == null) {
			log.info("到达目的地，目标不存在，返回.");
			this.failReturn();
			return;
		}
		
		WorldMarch worldMarch = this.getWorldMarch();
		if(worldMarch == null) {
			log.error("出征部队丢失.");
			this.failReturn();
			return;
		}
		
		worldMarch.setMarchState(MarchState.OCCUPY);
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		log.info(String.format("从目的返回，行军类型:[%s], 返回所需时间[%s]", worldMarch.getMarchType(), worldMarch.getMarchTime()));
	}
	
	@Override
	public void handleReturn() {
		if(worldMarch.getMarchType() == MarchType.SCOUT || worldMarch.getMarchType() == MarchType.TRADE) {
			this.backReturn();
		} else {
			if (worldMarch.getSoldierNum() <= 0) {
				this.backHomeImmediately(worldMarch);
			} else {
				this.backReturn();
			}
		}
	}
	
	@Override
	public void backReturnHome() {
		// 移除行军队列
		worldMarch.setMarchState(MarchState.OVER);
		
		// 到家返回士兵信息
		if(worldMarch.getWorldMarchSoldier() != null) {
			this.goback(player, worldMarch.getWorldMarchSoldier(), marchType);
		}
		
		player.getWorldMarchManager().removeWorldMarch(worldMarch.getId());
		
		CurrencyUtil.send(player);
		this.getPlayer().getSoldierManager().send(player);
		
		// 推送给其他玩家
		pushToWorldMarch(player, worldMarch);
	}
	
	/**
	 * 处理超过上线
	 * @param worldMarch
	 * @param plusNum
	 */
	public void dealOverCapacity(Player player, WorldMarch worldMarch, int plusNum) {
		Map<String, Soldier> plusSoldier = filterMaxCapacitySoldier(worldMarch.getWorldMarchSoldier(), plusNum);
		Map<String, Soldier> returnWorldMarchSoldier = worldMarch.getWorldMarchSoldier().getSoldiers();
		
		// 返回的士兵信息
		if(returnWorldMarchSoldier != null && !returnWorldMarchSoldier.isEmpty()) {
			// 创建行军队列
			JBaseData jBaseData = worldMarch.toJBaseData();
			WorldMarch returnWorldMarch = new WorldMarch();
			returnWorldMarch.fromJBaseData(jBaseData);
			
			long marchId = InjectorUtil.getInjector().marchSequance.genMarchId();
			returnWorldMarch.setId(marchId);
			
			// 处理士兵
			WorldMarchSoldier worldMarchSoldier = new WorldMarchSoldier();
			worldMarchSoldier.setSoldiers(returnWorldMarchSoldier);
			worldMarchSoldier.setPlayerId(player.getId());
			worldMarchSoldier.setMarchId(marchId);
			returnWorldMarch.setWorldMarchSoldier(worldMarchSoldier);
			returnWorldMarch.setSystemCreate(true);
			
			log.error("+++++++++++++++++++++++++++++超过容量上线，创建返回的行军队列++++++++++++++++++++++++++++++");
			// 执行器
			if(worldMarch.getMarchType() == MarchType.ALLIANCE_MEMBER_ASSEMBLY) {
				AllianceAssembleMarch executor = new AllianceAssembleMarch(player, plusNum, marchType, worldMarchSoldier, null, worldMarch.getTeamId(), 0);
				returnWorldMarch.setMarchState(MarchState.OCCUPY);
				returnWorldMarch.setExecutor(executor);
				returnWorldMarch.setTeamId(worldMarch.getTeamId());
				executor.setWorldMarch(returnWorldMarch);
				InjectorUtil.getInjector().dbCacheService.create(returnWorldMarch);
				
				// 处理返回
				executor.backReturn();
				this.pushToWorldMarch(player, returnWorldMarch);
			} else if(worldMarch.getMarchType() == MarchType.MARCH_REINFORCE) {
				ReinforceMarch executor = new ReinforceMarch(player, worldMarch.getTargetId(), worldMarch.getMarchType(), worldMarchSoldier, null, null);
				returnWorldMarch.setMarchState(MarchState.OCCUPY);
				returnWorldMarch.setExecutor(executor);
				executor.setWorldMarch(returnWorldMarch);
				InjectorUtil.getInjector().dbCacheService.create(returnWorldMarch);
				
				// 处理返回
				executor.backReturn();
				this.pushToWorldMarch(player, returnWorldMarch);
			}
		}
		
		// 驻军的士兵信息
		if(plusSoldier != null && !plusSoldier.isEmpty()) {
			worldMarch.getWorldMarchSoldier().setSoldiers(plusSoldier);
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		}
	}
	
	/**
	 * 创建行军信息
	 */
	private WorldMarch crateMarch() {
		WorldMarch worldMarch = new WorldMarch();
		worldMarch.setId(InjectorUtil.getInjector().marchSequance.genMarchId());
		worldMarch.setMarchStartTime(TimeUtils.getCurrentTimeMillis());
		worldMarch.setTargetId(targetPointId);
		worldMarch.setTargetUid(String.valueOf(targetSpriteInfo.getId()));
		worldMarch.setTargetType(targetSpriteInfo.getSpriteType());
		worldMarch.setMarchType(this.marchType);
		worldMarch.setOwnerUid(String.valueOf(player.getId()));
		worldMarch.setOwnerName(player.getName());
		worldMarch.setWorldMarchSoldier(worldMarchSoldier);
		worldMarch.setMarchState(MarchState.MARCH);
		worldMarch.executor = this;
		return worldMarch;
	}
	
	/**
	 * 发送
	 * @param worldMarch
	 */
	public void pushToWorldMarch(Player player, WorldMarch worldMarch) {
		if(worldMarch == null) {
			return;
		}
		
		// 推送地图中其他玩家向量信息
		ResUpdateVectorInfoMessage resUpdateVectorInfoMessage = new ResUpdateVectorInfoMessage();
		VectorInfo vectorInfo = WorldConverter.converterVectorInfo(worldMarch, false);
		resUpdateVectorInfoMessage.vectorInfo.add(vectorInfo);
		Set<Long> playerIds = player.getWorldLogicManager().getWorldPlayerIds();
		playerIds.remove(player.getId());
		InjectorUtil.getInjector().sessionManager.writePlayers(playerIds, resUpdateVectorInfoMessage);
		
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++"+vectorInfo.state);
		
		// 返回玩家向量信息
		ResUpdateVectorInfoMessage myInfoMessage = new ResUpdateVectorInfoMessage();
		VectorInfo myVectorInfo = WorldConverter.converterVectorInfo(worldMarch, true);
		myInfoMessage.vectorInfo.add(myVectorInfo);
		playerIds.add(player.getId());
		player.send(myInfoMessage);
	}
	
	/**
	 * 推送自己的行军队列
	 * @param player
	 * @param worldMarch
	 */
	public void pushSelfWorldMarch() {
		// 返回玩家向量信息
		ResUpdateVectorInfoMessage myInfoMessage = new ResUpdateVectorInfoMessage();
		
		VectorInfo vectorInfo = WorldConverter.converterVectorInfo(worldMarch, true);
		myInfoMessage.vectorInfo.add(vectorInfo);
		
		System.out.println("???????????????????????????????????????????????????"+vectorInfo.state);
		player.send(myInfoMessage);
	}
	
	/**
	 * 推送世界精灵
	 * @param player
	 * @param spriteInfo
	 */
	public void pushToWorldSprites(SpriteInfo spriteInfo) {
		ResUpdateSpriteMessage resUpdateVectorInfoMessage = new ResUpdateSpriteMessage();
		SpriteBean spriteBean = WorldConverter.converterSprite(spriteInfo, false);
		resUpdateVectorInfoMessage.spriteBean.add(spriteBean);
		
		System.out.println("-----------------------------------------------------------"+JsonUtil.toJSON(spriteBean));
		
		// 推送到世界
		Set<Long> playerIds = player.getWorldLogicManager().getWorldPlayerIds();
		playerIds.add(player.getId());
		InjectorUtil.getInjector().sessionManager.writePlayers(playerIds, resUpdateVectorInfoMessage);
	}

	@Override
	public void failReturn() {
		log.info("失败撤退................");
		if(worldMarch == null) {
			return;
		}
		
		if(worldMarch.getTaskId() > 0) {
			player.getTimerTaskManager().removeTimerTask(player, worldMarch.getTaskId());
			worldMarch.setTaskId(0);
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		}
		
		SpriteInfo spriteInfo = getTargetByTargetId();
		if(spriteInfo != null) {
			WorldSprite worldSprite = spriteInfo.getParam();
			if(worldSprite != null && worldSprite.isMoveCity()) {
				CountDownLatch countDownLatch = worldSprite.getCountDownLatch();
				if(countDownLatch != null) {
					countDownLatch.countDown();
				}
			}
			
			if(worldSprite.getOwnerMarchId() == worldMarch.getId()) {
				worldSprite.setOwnerMarchId(0);
				InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
			}
		}
		
		// 中途返回
		if(worldMarch.getMarchState() == MarchState.MARCH) {
			// 返回
			worldMarch.failBackReturn();
			
			// 出征
			int taskTime = (int)((worldMarch.getMarchArrivalTime() - worldMarch.getReturnStartTime()) / 1000);
			TimerTaskData timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.RUN_SOLDIER).register(player, taskTime, new RunCampTaskData(worldMarch.getId()));
			worldMarch.setTaskId(timerTaskData.getId());
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
	
		// 从目的地放回
		} else {
			backReturn();
		}
		
		this.pushToWorldMarch(player, worldMarch);
	}
	
	/**
	 * 出征返回
	 */
	public void backReturn() {
		worldMarch.setMarchState(MarchState.BACK);
		
		// 移除战斗队列
		SpriteInfo spriteInfo = getTargetByTargetId();
		if(spriteInfo != null) {
			WorldSprite worldSprite = spriteInfo.getParam();
			if(worldSprite != null) {
				worldSprite.removeBattleAction(worldMarch.getId());
				InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
			}
		}
		
		int marchTime = getFinalMarchTime();
		worldMarch.backReturn(marchTime);
		TimerTaskData timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.RUN_SOLDIER).register(player, marchTime, new RunCampTaskData(worldMarch.getId()));
		worldMarch.setTaskId(timerTaskData.getId());
		
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
		this.pushToWorldMarch(player, worldMarch);
		this.pushToWorldSprites(spriteInfo);
	}
	
	/**
	 * 直接返回不需要行军
	 */
	public void backHomeImmediately(WorldMarch worldMarch) {
		worldMarch.setMarchState(MarchState.OVER);
		if(worldMarch.getTaskId() > 0) {
			player.getTimerTaskManager().removeTimerTask(player, worldMarch.getTaskId());
		}
		
		// 到家
		backReturnHome();
		
		// 移除行军信息
		pushToWorldMarch(player, worldMarch);
	}
	
	/**
	 * 获取行军速度
	 * @return
	 */
	public double getMarchSpeed() {
		double speed = 0.0;
		for(Soldier soldier : this.worldMarch.querySoldierList()) {
			double soldierSpeed = Double.valueOf(soldier.getAttribute(player, AttributesEnum.SPEED_FIGHT));
			if (speed <= 0) {
				speed = soldierSpeed;
			} else {
				speed = Math.min(speed, soldierSpeed);
			}
		}
		return Math.max(speed, 0);
	}

	/**
	 * 行军加速
	 */
	@Override
	public void speedUp(double percent) {
		TimerTaskData timerTaskData = InjectorUtil.getInjector().timerTaskManager.get(worldMarch.getTaskId());
		if(timerTaskData != null) {
			WorldMarch worldMarch = this.getWorldMarch();
			//修改返回时间为加速时间
			int time = worldMarch.speedUp(percent);
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
			
			//任务加速使用的是 加速时间 不是行军时间
			TimerTaskHolder.getTimerTask(TimerTaskCommand.RUN_SOLDIER).speedUp(player, timerTaskData, time);
			this.pushToWorldMarch(player, worldMarch);
		}
	}
	
	/**
	 * 获取目标精灵
	 * @return
	 */
	public SpriteInfo getTargetByTargetId() {
		WorldMarch worldMarch = this.getWorldMarch();
		if(worldMarch != null) {
			if(worldMarch.getTargetId() > 0) {
				SpriteInfo spriteInfo = player.getSpriteManager().getVisibleGrid(worldMarch.getTargetId());
				return spriteInfo;
			}
		}
		return null;
	}
	
	/**
	 * 回来
	 * @param worldMarchSoldier
	 * @param marchType
	 */
	public void goback(Player player, WorldMarchSoldier worldMarchSoldier, MarchType marchType) {
		// 返还自己的兵
		for(Soldier soldier : worldMarchSoldier.querySoldierList()) {
			if(soldier.getNum() > 0) {
				SoldierBrief soldierBrief = new SoldierBrief();
				soldierBrief.setSoldierId(soldier.getSoldierId());
				soldierBrief.setNum(soldier.getNum());
				
				DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
				
				player.roleInfo().getSoldierData().decrementSoldier(soldier.getSoldierId(), soldier.getNum(), SoldierChangeType.MARCH);
				player.roleInfo().getSoldierData().addSoldier(soldier.getSoldierId(), soldier.getNum(), SoldierChangeType.COMMON);
				InjectorUtil.getInjector().dbCacheService.update(player);
				
				EventBus.getSelf().fireEvent(new SoldierChangeEventObject(player, EventTypeConst.SOLDIER_CHANGE, soldier.getSoldierId(), designMap.getType(), 
						soldier.getNum() - soldier.getNum(), soldier.getNum(), marchType, MarchState.BACK, GameLogSource.MARCH, 
						designMap.getSystemIndex(), designMap.partBeanIdList()));	
			}
		}
		
		// 计算兵营战力
		FightPowerKit.CAMP_POWER.math(player,GameLogSource.MARCH);
		CurrencyUtil.send(player);
		player.getSoldierManager().send(player);
	}
	
	
	/**
	 * 处理玩家护盾
	 * @param player
	 * @param type
	 * @param targetSprite
	 */
	private void dealPlayerShield(Player player, int type, SpriteInfo targetSprite) {
		boolean flag = false;
		if(type == MarchType.CITY_FIGHT.ordinal() || type == MarchType.TEAM_ATTACK.ordinal() || type == MarchType.SCOUT.ordinal()) {
			flag = true;
		} else if(type == MarchType.EXPLORER.ordinal() || type == MarchType.CAMP.ordinal() || type == MarchType.TERRITORY.ordinal()) {
			WorldSprite worldSprite = targetSprite.getParam();
			if(worldSprite != null && worldSprite.getOwnerMarchId() > 0) {
				flag = true;	
			}
		}
		
		// 解除精灵信息
		if(flag) {
			SpriteInfo spriteInfo = player.getSprite();
			if(spriteInfo != null) {
				PlayerSprite playerSprite = spriteInfo.getParam();
				if(playerSprite != null && playerSprite.getShieldTime() > TimeUtils.getCurrentTimeMillis()) {
					playerSprite.setShieldTime(0);
					InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
					
					this.pushToWorldSprites(spriteInfo);
				}
			}
		}
	}
	
	/**
	 * 获取行军时间(返回时间是秒)
	 * @return
	 */
	private int getMarchTime() {
		Vector2Bean start = worldMarch.getOwnerPosition();
		Vector2Bean destination = WorldConverter.getVector2Bean(this.worldMarch.getTargetId());
		int matrixRunTime = MathUtil.matrixRunTime(start.x, start.y, destination.x, destination.y, this.getMarchSpeed());
		if (matrixRunTime <= 0) {
			throw new RuntimeException("行军事件异常。时间为0");
		}
		return matrixRunTime;
	}
	
	/**
	 * 获取行军时间
	 * @return
	 */
	public int getFinalMarchTime() {
		int initTime = getMarchTime();
		int finalTime = PlayerAttributeManager.get().getFinalMarchTime(marchType, player.getId(), initTime);
		return finalTime;
	}
	
	/**
	 * 更新行军信息
	 */
	public void updateWorldMarch() {
		SpriteInfo target = this.getTargetByTargetId();
		if(worldMarch.getMarchType() == MarchType.EXPLORER || worldMarch.getMarchType() == MarchType.TERRITORY || worldMarch.getMarchType() == MarchType.CAMP) { 
			WorldSprite worldSprite = target.getParam();
			if(worldSprite.getOwnerMarchId() > 0) {
				worldMarch.setMarchFight(MarchFightState.FIGHT);
				InjectorUtil.getInjector().dbCacheService.update(worldMarch);
			}
		} else if(worldMarch.getMarchType() == MarchType.CITY_FIGHT) {
			worldMarch.setMarchFight(MarchFightState.FIGHT);
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		}
	}
	
	
	/**
	 * 出征扣除士兵
	 * @param player
	 * @param soldierBriefBeans
	 * @param marchType
	 * @return
	 */
	public WorldMarchSoldier marchRemoveSoldier(Player player, List<WorldSimpleSoldierBean> soldierBriefBeans, MarchType marchType) {
		WorldMarchSoldier worldMarchSoldier = new WorldMarchSoldier();
		worldMarchSoldier.setPlayerId(player.getRoleId());
		Iterator<WorldSimpleSoldierBean> deductIterator = soldierBriefBeans.iterator();
		while (deductIterator.hasNext()) {
			WorldSimpleSoldierBean soldierBriefPro = deductIterator.next();
			Soldier tmpSoldier = player.getSoldierManager().getSoldier(player, soldierBriefPro.soldiers.soldierId);
			int beforeNum = tmpSoldier.getNum();
			
			// 士兵信息
			Soldier soldier = tmpSoldier.cloneFullSoldier(player, soldierBriefPro.soldiers.num);
			soldier.setIndex(soldierBriefPro.index);
			DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
			worldMarchSoldier.getSoldiers().put(Soldier.factoryBattleSoldierId(soldier.getSoldierId(), soldierBriefPro.index), soldier);
			
			player.roleInfo().getSoldierData().decrementSoldier(soldierBriefPro.soldiers.soldierId, soldierBriefPro.soldiers.num, SoldierChangeType.COMMON);
			
			EventBus.getSelf().fireEvent(new SoldierChangeEventObject(player, EventTypeConst.SOLDIER_CHANGE, tmpSoldier.getSoldierId(), designMap.getType(), 
					beforeNum, tmpSoldier.getNum(), marchType, MarchState.MARCH, 
					GameLogSource.MARCH, designMap.getSystemIndex(), designMap.partBeanIdList()));
		}
		
		InjectorUtil.getInjector().dbCacheService.update(player);
		FightPowerKit.CAMP_POWER.math(player,GameLogSource.MARCH);
		CurrencyUtil.send(player);
		
		// 出征成功士兵数量减少
		player.getSoldierManager().send(player);
		return worldMarchSoldier;
	}
	
	@Override
	public boolean initBattle() {
		log.info("[{}],进入战斗初始化", player.getName());
		// 推送精灵状态变更
		SpriteInfo spriteInfo = this.getTargetByTargetId();
		WorldSprite worldSprite = spriteInfo.getParam();
		if(worldSprite != null) {
			// 更新地图精灵战斗状态
			worldSprite.setFight(true);
			worldSprite.setAttackMarchId(this.getWorldMarch().getId());
			worldSprite.setCurrentBattleStartTime(TimeUtils.getCurrentTimeMillis());
			worldSprite.setCurrentBattleEndTime(worldSprite.getCurrentBattleStartTime() + WorldConstant.BATTLE_TIME);
			worldSprite.setBattleSoldierIds(worldMarchSoldier.getSoldierTypeList());
			InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
			
			// 推送精灵变更
			this.pushToWorldSprites(spriteInfo);
			
			// 推送防守方新军信息发生变化
			if(worldSprite.getOwnerMarchId() > 0) {
				WorldMarch defendMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
				defendMarch.setBattleEndTime(worldSprite.getCurrentBattleEndTime());
				defendMarch.setBattleStartTime(worldSprite.getCurrentBattleStartTime());
				InjectorUtil.getInjector().dbCacheService.update(defendMarch);
				
				// 对宋防守方行军队列
				defendMarch.getExecutor().pushSelfWorldMarch();	
			}
			
			// 推送进攻方行军状态变更
			WorldMarch attackMarch = getWorldMarch();
			attackMarch.setBattleEndTime(worldSprite.getCurrentBattleEndTime());
			attackMarch.setBattleStartTime(worldSprite.getCurrentBattleStartTime());
			InjectorUtil.getInjector().dbCacheService.update(attackMarch);
			
			// 推送行军
			attackMarch.getExecutor().pushSelfWorldMarch();
		}
		
		return true;
	}
	
	/**
	 * 检查野外是否是有同盟玩家驻军
	 * @return true 代表同盟玩家
	 */
	public boolean checkSameAllianceMarch() {
		WorldSprite worldSprite = targetSpriteInfo.getParam();
		if(worldSprite != null && worldSprite.getOwnerMarchId() > 0) {
			if(worldMarch != null) {
				if(worldMarch.getId() == worldSprite.getOwnerMarchId()) {
					return true;
				}
				
				Player attackPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldMarch.getOwnerUid()));
				if(attackPlayer == null) {
					return false;
				}
				
				// 有防守方驻扎
				WorldMarch defendMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
				if(worldMarch != null) {
					Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(defendMarch.getOwnerUid()));
					if(defendPlayer != null && attackPlayer != null) {
						if(defendPlayer.getAllianceId() > 0 && defendPlayer.getAllianceId() == attackPlayer.getAllianceId()) {
							return true;
						}
					}
					
					if(defendPlayer.getId().equals(attackPlayer.getId())) {
						return true;
					}
				}
				
				// 是防守方所在基地
				if(!StringUtils.isBlank(worldSprite.getOwnerId())) {
					Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldSprite.getOwnerId()));
					if(defendPlayer != null) {
						if(defendPlayer.getAllianceId() > 0 && defendPlayer.getAllianceId() == attackPlayer.getAllianceId()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 驻军和集结进攻过滤掉查过上限的士兵
	 * @param worldMarchSoldier
	 * @param num
	 * @return
	 */
	public Map<String, Soldier> filterMaxCapacitySoldier(WorldMarchSoldier worldMarchSoldier, int num) {
		Map<String, Soldier> resultMap = new HashMap<String, Soldier>();
		Map<String, Soldier> map = worldMarchSoldier.getSoldiers();
		if(map != null && !map.isEmpty()) {
			TreeMap<String, Long> soldierFightPowerMap = new TreeMap<>();
			for(Soldier soldier : map.values()) {
				long fightPower = soldier.getSinglePower(worldMarchSoldier.getPlayerId());
				long soldierFightPower = fightPower * soldier.getNum();
				soldierFightPowerMap.put(Soldier.factoryBattleSoldierId(soldier.getSoldierId(), soldier.getIndex()), soldierFightPower);
			}
			
			// 计算出最大士兵
			if(soldierFightPowerMap != null && !soldierFightPowerMap.isEmpty()) {
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
				for(String key : soldierFightPowerMap.keySet()) {
					Soldier soldier = map.get(key);
					Soldier resultSoldier = soldier.cloneFullSoldier(player);
					if(resultSoldier.getNum() > num) {
						resultSoldier.setNum(num);
						soldier.setNum(soldier.getNum() - num);
						resultMap.put(key, resultSoldier);
						break;
					} else {
						int currentNum = resultSoldier.getNum();
						map.remove(key);
						num = num - currentNum;
						resultMap.put(key, resultSoldier);
					}
				}
			}
		}
		
		return resultMap;
	}
	
	/**
	 * 判断玩家基地和当前行军队列是否是同一联盟
	 * @return true 代表同盟玩家
	 */
	public boolean checkSameAlliancePlayer() {
		WorldSprite worldSprite = targetSpriteInfo.getParam();
		Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldSprite.getOwnerId()));

		// 判断野外玩家是否是同盟玩家
		if(player.getAllianceId() > 0 && defendPlayer.getAllianceId() == player.getAllianceId()) {
			return true;
		}
		
		if(defendPlayer.getId().equals(player.getId())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 处理军队抵达目的地
	 */
	public void dealToDestination() {
		log.info("[{}],到达目的地,开始初始化战斗。", player.getName());
		SpriteInfo spriteInfo = getTargetByTargetId();
		if(spriteInfo != null) {
			// 倒带处理异常精灵信息再开打
			player.getSpriteManager().dealSingleExceptionSprite(worldMarch.getTargetId());
			
			// 处理精灵战斗
			WorldSprite worldSprite = spriteInfo.getParam();
			if(worldSprite.getAttackMarchId() > 0) {
				boolean fight = worldSprite.addBattleAction(this.getWorldMarch().getId());
				if(fight) {
					// 初始化战斗
					initBattle();
				}
				InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
			} else {
				// 初始化战斗
				InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
				initBattle();
			}
		}
	}
	
	/**
	 * 刷新精灵
	 * @param spriteInfo
	 */
	public void refreshLocation(SpriteInfo spriteInfo) {
		WorldSprite worldSprite = spriteInfo.getParam();
		if (worldSprite != null	&& worldSprite.getOwnerMarchId() > 0) {
			spriteInfo.setSpriteType(SpriteType.CAMP.getType());
			InjectorUtil.getInjector().spriteManager.removeCanUse(spriteInfo.getIndex());
			InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
		} else {
			spriteInfo.setSpriteType(SpriteType.NONE.getType());
			InjectorUtil.getInjector().spriteManager.addCanUse(spriteInfo.getX(), spriteInfo.getY());
			InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
		}
	}
	
	@Override
	public void dealReload() {
		
	}

	public WorldMarch getWorldMarch() {
		return worldMarch;
	}

	public void setWorldMarch(WorldMarch worldMarch) {
		this.worldMarch = worldMarch;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public MarchType getMarchType() {
		return marchType;
	}

	public void setMarchType(MarchType marchType) {
		this.marchType = marchType;
	}

	public SpriteInfo getTargetSpriteInfo() {
		return targetSpriteInfo;
	}

	public void setTargetSpriteInfo(SpriteInfo targetSpriteInfo) {
		this.targetSpriteInfo = targetSpriteInfo;
	}
	
	public WorldMarchSoldier getWorldMarchSoldier() {
		return worldMarchSoldier;
	}

	public void setWorldMarchSoldier(WorldMarchSoldier worldMarchSoldier) {
		this.worldMarchSoldier = worldMarchSoldier;
	}

	public int getTargetPointId() {
		return targetPointId;
	}

	public void setTargetPointId(int targetPointId) {
		this.targetPointId = targetPointId;
	}

	public List<WorldSimpleSoldierBean> getSoldierBriefBeans() {
		return soldierBriefBeans;
	}

	public void setSoldierBriefBeans(List<WorldSimpleSoldierBean> soldierBriefBeans) {
		this.soldierBriefBeans = soldierBriefBeans;
	}
	
}