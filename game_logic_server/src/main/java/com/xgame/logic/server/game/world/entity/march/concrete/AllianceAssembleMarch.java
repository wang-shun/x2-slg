package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.List;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.constant.AllianceTeamState;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.TimeUtils;


/**
 * 部队召集
 * @author jacky.jiang
 *
 */
public class AllianceAssembleMarch extends AbstractWorldMarch {
	
	/**
	 * 队伍id
	 */
	private String teamId;
	
	/**
	 * 行军剩余时间
	 */
	private int remainTime;
	
	@Override
	public boolean checkMarch() {
		Player player = getPlayer();
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamId);
		if (allianceBattleTeam == null || allianceBattleTeam.getAllianceTeamState() != AllianceTeamState.WAIT_MARCH) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE5);
			return false;
		}
		
		long leaderId = allianceBattleTeam.getLeaderId();
		if(player.getId() == leaderId) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE33);
			return false;
		}
		
		if(!checkSameAlliancePlayer()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE33);
			return false;
		}
		
		return super.checkMarch();
	}
	
	@Override
	public WorldMarch march() {
		return super.march();
	}

	public AllianceAssembleMarch(Player player, int targetPointId, MarchType marchType, WorldMarchSoldier worldMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans, String teamId, int remainTime) {
		super(player, targetPointId, marchType, worldMarchSoldier, soldierBriefBeans);
		this.teamId = teamId;
		this.remainTime = remainTime;
	}
	
	@Override
	public void toDestination() {
		WorldMarch worldMarch = this.getWorldMarch();
		SpriteInfo targetInfo = InjectorUtil.getInjector().spriteManager.getGrid(worldMarch.getTargetId()); 
		if(targetInfo == null || targetInfo.getSpriteType() != SpriteType.PLAYER.getType()) {
			this.failReturn();
			return;
		}
		
		WorldSprite worldSprite = targetInfo.getParam();
		if(Long.valueOf(worldSprite.getOwnerId()) == this.getPlayer().getId()) {
			this.failReturn();
			return;
		}
		
		PlayerAlliance targetPlayerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, Long.valueOf(worldSprite.getOwnerId()));
		if(targetPlayerAlliance.getAllianceId() <= 0 || targetPlayerAlliance.getAllianceId() != this.getPlayer().getAllianceId()) {
			this.failReturn();
			return;
		}
		
		// 军队已经解散
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamId);
		if(allianceBattleTeam == null || allianceBattleTeam.getAllianceTeamState() != AllianceTeamState.WAIT_MARCH) {
			this.failReturn();
			return;
		}
		
		// 判断队长是否有外事联络处
		Player leaderPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, allianceBattleTeam.getLeaderId());
		BuildControl consulateBuildControl = leaderPlayer.getCountryManager().getBuildControl(BuildFactory.CONSULATE.getTid());
		if(consulateBuildControl == null) {
			this.failReturn();
			return;
		}
		
		int currentMember = allianceBattleTeam.getTeamSoldierNum();
		int maxCapacity = leaderPlayer.getCountryManager().getMaxReinforceNum();
		if (currentMember >= maxCapacity) {
			this.failReturn();
			return;
		} 
		
		if(currentMember + worldMarch.getSoldierNum() > maxCapacity) {
			dealOverCapacity(getPlayer(), worldMarch, maxCapacity - currentMember);
		}
		
		InjectorUtil.getInjector().allianceBattleTeamManager.addAllianceBattleTeam(teamId, worldMarch);
		super.toDestination();
		this.pushToWorldMarch(getPlayer(), worldMarch);
	}
	
	@Override
	public void handleReturn() {
		WorldMarch worldMarch = this.getWorldMarch();
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
		if(allianceBattleTeam != null) {
			if(allianceBattleTeam.getAllianceTeamState() != AllianceTeamState.WAIT_MARCH) {
				worldMarch.setTargetId(allianceBattleTeam.getTargetId());
			}
			
			worldMarch.setMarchState(MarchState.BACK);
			worldMarch.setMarchStartTime(TimeUtils.getCurrentTimeMillis());
			
			int marchTime = getFinalMarchTime();
			worldMarch.setMarchArrivalTime(worldMarch.getMarchStartTime() + marchTime * 1000);
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
			super.handleReturn();
			
			
		// 会出现没有队伍的返回。
		} else {
			super.handleReturn();
		}
	}
	
	@Override
	public void updateWorldMarch() {
		getWorldMarch().setTeamId(teamId);
		getWorldMarch().setRemainTime(remainTime);
		super.updateWorldMarch();
	}

}
