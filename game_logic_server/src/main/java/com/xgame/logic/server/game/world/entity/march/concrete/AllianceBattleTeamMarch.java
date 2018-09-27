package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.constant.AllianceBattleTeamType;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.notice.constant.NoticeConstant;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.logic.server.game.world.message.ResCancelTeamAttackMessage;
import com.xgame.utils.TimeUtils;


/**
 * 联盟战队行军信息
 * @author jacky.jiang
 *
 */
@Slf4j
public class AllianceBattleTeamMarch extends AbstractWorldMarch {
	
	/**
	 * 队伍编号
	 */
	private String teamId;
	
	/**
	 * 行军剩余时间
	 */
	private int remainTime;
	
	/**
	 * 防守玩家签名
	 */
	private EmailSignature defSignature;
	
	@Override
	public boolean checkMarch() {
		// 出征信息
		SpriteInfo targetSprite = getTargetSpriteInfo();
		if(targetSprite == null || targetSprite.getParam() == null) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE1);
			return false;
		}
		
		WorldSprite worldSprite = targetSprite.getParam();
		if(worldSprite == null || StringUtils.isBlank(worldSprite.getOwnerId())) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE1);
			return false;
		}
		
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, getPlayer().getId());
		if(playerAlliance.getAllianceId() <=0) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return false;	
		}
		
		Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, playerAlliance.getAllianceId());
		if(alliance == null) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E1007_ALLIANCE.CODE16);
			return false;
		}
		
		if(!super.checkMarch()) {
			return false;
		}
		
		String name ="";
		Player player = this.getPlayer();
		SpriteInfo targetInfo = this.getTargetSpriteInfo();
		if(targetInfo.getEnumSpriteType() == SpriteType.PLAYER) {
			PlayerSprite playerSprite = targetInfo.getParam();
			name = InjectorUtil.getInjector().playerManager.getPlayerNameByPlayerId(Long.valueOf(playerSprite.getOwnerId()));
		}
		
		InjectorUtil.getInjector().noticeManager.sendAllianceNotice(player, alliance, NoticeConstant.TEAM_ATTACK, player.getName(), name);
		return true;
	}

	public AllianceBattleTeamMarch(Player player, int targetPointId, MarchType marchType, WorldMarchSoldier worldMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans, String team, int remainTime, EmailSignature defSignature) {
		super(player, targetPointId, marchType, worldMarchSoldier, soldierBriefBeans);
		this.teamId = team;
		this.remainTime = remainTime;
		this.defSignature = defSignature;
	}

	@Override
	public void updateWorldMarch() {
		WorldMarch worldMarch = this.getWorldMarch();
		Player player = getPlayer();
		
		// 创建战队
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().allianceBattleTeamManager.createAllianceBattleTeam(player, player.getAllianceId(), AllianceBattleTeamType.ATK, getTargetPointId(), String.valueOf(defSignature.playerId), SpriteType.PLAYER, remainTime);
		worldMarch.setTeamId(allianceBattleTeam.getId());
		allianceBattleTeam.setMarchId(worldMarch.getId());
		this.teamId = allianceBattleTeam.getId();
		
		// 更新行军信息
		worldMarch.setMarchState(MarchState.MASS);
		worldMarch.setMarchStartTime(worldMarch.getMarchStartTime() + this.remainTime * 1000);
		worldMarch.setMarchArrivalTime(worldMarch.getMarchStartTime() + worldMarch.getMarchTime() * 1000);
		worldMarch.setArrivalTime(worldMarch.getMarchArrivalTime());
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
		this.pushToWorldMarch(player, worldMarch);
	}
	
	@Override
	public void toDestination() {
		WorldMarch worldMarch = this.getWorldMarch();
		if(worldMarch == null) {
			log.error("出征部队丢失...");
			failReturn();
			return;
		}
		
		worldMarch.setMarchState(MarchState.OCCUPY);
		SpriteInfo target = this.getTargetByTargetId();
		if(target == null || target.getEnumSpriteType() != SpriteType.PLAYER) {
			log.error("目标丢失返回...");
			failReturn();
			this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.集结进攻失败基地_MAIL_ID);
			return;
		}
		
		// 世界行军信息
		Player attackPlayer = this.getPlayer();
		WorldSprite worldSprite = target.getParam();
		Player defenPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldSprite.getOwnerId()));
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
		if(allianceBattleTeam == null) {
			log.error("数据异常，返回 , 玩家id:{}", target.getId());
			dealDismissAllianceBattleTeam(attackPlayer, worldMarch, allianceBattleTeam, true);
			return;
		}
		
		if(attackPlayer == null || defenPlayer == null) {
			log.error("数据异常，返回");
			dealDismissAllianceBattleTeam(attackPlayer, worldMarch, allianceBattleTeam, true);
			return;
		}
		
		PlayerSprite playerSprite = target.getParam();
		if(playerSprite == null) {
			log.error("数据异常，返回 , 玩家id:{}", target.getId());
			dealDismissAllianceBattleTeam(attackPlayer, worldMarch, allianceBattleTeam, true);
			return;
		}

		if(playerSprite.getShieldTime() > TimeUtils.getCurrentTimeMillis()) {
			log.error("数据异常，返回 , 玩家id:{}", target.getId());
			dealDismissAllianceBattleTeam(attackPlayer, worldMarch, allianceBattleTeam, true);
			return;
		}
		
		// 到达目的地
		dealToDestination();
		super.toDestination();
		
		// 推送行军队列变化
		this.pushToWorldMarch(attackPlayer, worldMarch);
	}
	
	@Override
	public boolean initBattle() {
		log.error("开始初始化战斗。。。。。");
		Player attackPlayer = this.getPlayer();
		SpriteInfo target = this.getTargetByTargetId();
		WorldSprite worldSprite = target.getParam();
		WorldMarch worldMarch = this.getWorldMarch();
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
		if(allianceBattleTeam == null) {
			log.error("数据异常，返回 , 玩家id:{}", target.getId());
			dealDismissAllianceBattleTeam(attackPlayer, worldMarch, allianceBattleTeam, true);
			return false;
		}
		
		if(worldSprite == null) {
			log.error("战斗异常返回。");
			dealDismissAllianceBattleTeam(attackPlayer, worldMarch, allianceBattleTeam, true);
			return false;
		}
		
		Player defenPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldSprite.getOwnerId()));
		if(defenPlayer == null) {
			log.error("初始化战斗，玩家不存在, 返回。");
			dealDismissAllianceBattleTeam(attackPlayer, worldMarch, allianceBattleTeam, true);
			return false;
		}
		
		WarFightParam warFightParam = new WarFightParam();
		warFightParam.setAttackPlayer(attackPlayer);
		warFightParam.setAttackWorldMarch(worldMarch);
		
		warFightParam.setDefendPlayer(defenPlayer);
		warFightParam.setTeamId(teamId);
		
		SpriteInfo attackSpriteInfo = attackPlayer.getSprite();
		warFightParam.setAttackSpriteInfo(attackSpriteInfo);
		warFightParam.setTargetSpriteInfo(this.getTargetByTargetId());
		warFightParam.setBattleType(WarType.TEAM_ATTACK);
		
		List<WorldMarchSoldier> attackWorldMarchSoldiers = Lists.newArrayList();
		List<WorldMarch> worldMarchs = new ArrayList<>();
		Collection<Long> collection = allianceBattleTeam.getMemberMarch().values();
		if(collection != null && !collection.isEmpty()) {
			for(Long id : collection) {
				WorldMarch worldMarch2 = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, id);
				if(worldMarch2 != null) {
					worldMarch2.setMarchState(MarchState.FIGHT);
					attackWorldMarchSoldiers.add(worldMarch2.getWorldMarchSoldier());
					worldMarchs.add(worldMarch2);
				}
			}
		}
		
		worldMarchs.add(worldMarch);
		attackWorldMarchSoldiers.add(worldMarch.getWorldMarchSoldier());
		
		warFightParam.setAttackMarchList(worldMarchs);
		warFightParam.setAttackSoldiers(attackWorldMarchSoldiers);
		
		List<WorldMarchSoldier> defendMarchSoldiers = Lists.newArrayList();
		List<WorldMarch> defendMarchList = InjectorUtil.getInjector().worldMarchManager.getReinforceWorldMarch(target.getIndex());
		if(defendMarchList != null && !defendMarchList.isEmpty()) {
			for(WorldMarch defendMarch : defendMarchList) {
				defendMarchSoldiers.add(defendMarch.getWorldMarchSoldier());
			}
		}

		warFightParam.setDefendMarchList(defendMarchList);
		warFightParam.setDefendSoldiers(defendMarchSoldiers);
		
		super.initBattle();
		attackPlayer.getWarManager().startBattle(warFightParam);
		return true;
	}
	
	/**
	 * 处理解散队伍
	 * @param player
	 * @param mainMarch
	 * @param allianceBattleTeam
	 */
	public void dealDismissAllianceBattleTeam(Player player, WorldMarch mainMarch, AllianceBattleTeam allianceBattleTeam, boolean returnMainMarch) {
		if(allianceBattleTeam != null) {
			long taskId = mainMarch.getTaskId();
			if(taskId > 0) {
				player.getTimerTaskManager().removeTimerTask(player, taskId);
			}
			
			for(Long worldMarchId  : allianceBattleTeam.getMemberMarch().values()) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldMarchId);
				if(worldMarch != null) {
					worldMarch.getExecutor().handleReturn();
				}
			}
			
			// 处理行军队列返回
			if(returnMainMarch) {
				mainMarch.getExecutor().handleReturn();
			} else {
				mainMarch.getExecutor().backHomeImmediately(mainMarch);
			}
			
			InjectorUtil.getInjector().dbCacheService.delete(allianceBattleTeam);
			
			ResCancelTeamAttackMessage resCancelTeamAttackMessage = new ResCancelTeamAttackMessage();
			resCancelTeamAttackMessage.teamId = teamId;
			player.send(resCancelTeamAttackMessage);
		}
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
}
