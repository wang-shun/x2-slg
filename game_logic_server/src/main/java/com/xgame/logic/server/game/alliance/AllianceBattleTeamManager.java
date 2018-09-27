package com.xgame.logic.server.game.alliance;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.data.sprite.SpriteType;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.AllianceBattleTeamSequence;
import com.xgame.logic.server.game.alliance.constant.AllianceBattleTeamType;
import com.xgame.logic.server.game.alliance.constant.AllianceTeamState;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.world.entity.WorldMarch;

/**
 * 联盟战队信息管理
 * @author jacky.jiang
 *
 */
@Component
public class AllianceBattleTeamManager extends CacheProxy<AllianceBattleTeam> {

	@Autowired
	private AllianceBattleTeamSequence allianceBattleTeamSequence;
	@Autowired
	private RedisClient redisClient;
	
	private ConcurrentHashMap<Long, ConcurrentHashMap<String, AllianceBattleTeam>> leaderTeamMap = new ConcurrentHashMap<>();
	
	private ConcurrentHashMap<Long, ConcurrentHashMap<String, AllianceBattleTeam>> allianceTeamMap = new ConcurrentHashMap<Long, ConcurrentHashMap<String,AllianceBattleTeam>>();
	
	@Override
	public Class<?> getProxyClass() {
		return AllianceBattleTeam.class;
	}
	
	@Startup(order= StartupOrder.ALLIANCE_BATTLE_TEAM_START, desc = "启动加载队伍信息")
	public void startLoadTeam() {
		List<AllianceBattleTeam> teamList = redisClient.hvals(AllianceBattleTeam.class);
		if(teamList != null) {
			for(AllianceBattleTeam allianceBattleTeam : teamList) {
				this.add(allianceBattleTeam);
				this.addLeaderTeamMap(allianceBattleTeam);
				this.addAllianceTeamMap(allianceBattleTeam);
			}
		}
	}
	
	/**
	 * 添加玩家队伍
	 * @param allianceBattleTeam
	 */
	private void addLeaderTeamMap(AllianceBattleTeam allianceBattleTeam) {
		ConcurrentHashMap<String, AllianceBattleTeam> teamMap = leaderTeamMap.get(allianceBattleTeam.getLeaderId());
		if(teamMap == null) {
			teamMap = new ConcurrentHashMap<String, AllianceBattleTeam>();
			leaderTeamMap.put(allianceBattleTeam.getLeaderId(), teamMap);
		}
		
		teamMap.put(allianceBattleTeam.getId(), allianceBattleTeam);
	}
	
	/**
	 * 移除玩家战队列表
	 * @param allianceBattleTeam
	 */
	private void removeLeaderTeamMap(AllianceBattleTeam allianceBattleTeam) {
		ConcurrentHashMap<String, AllianceBattleTeam> teamMap = leaderTeamMap.get(allianceBattleTeam.getLeaderId());
		if(teamMap != null) {
			teamMap.remove(allianceBattleTeam.getId());
		}
	}
	
	/**
	 * 添加联盟队伍
	 * @param allianceBattleTeam
	 */
	private void addAllianceTeamMap(AllianceBattleTeam allianceBattleTeam) {
		ConcurrentHashMap<String, AllianceBattleTeam> allianceMap = allianceTeamMap.get(allianceBattleTeam.getAllianceId());
		if(allianceMap == null) {
			allianceMap = new ConcurrentHashMap<String, AllianceBattleTeam>();
			allianceTeamMap.put(allianceBattleTeam.getAllianceId(), allianceMap);
		}
		allianceMap.put(allianceBattleTeam.getId(), allianceBattleTeam);
	}
	
	/**
	 * 移除战队队伍
	 * @param allianceBattleTeam
	 */
	private void removeAllianceTeamMap(AllianceBattleTeam allianceBattleTeam) {
		ConcurrentHashMap<String, AllianceBattleTeam> allianceMap = allianceTeamMap.get(allianceBattleTeam.getAllianceId());
		if(allianceMap != null) {
			allianceMap.remove(allianceBattleTeam.getId());
		}
	}
	
	/**
	 * 创建集结进攻组队
	 * @param player
	 * @param allianceId
	 * @param teamType
	 * @param targetPoint
	 * @param targetUid
	 * @param targetType
	 * @return
	 */
	public AllianceBattleTeam createAllianceBattleTeam(Player player, long allianceId, AllianceBattleTeamType teamType, int targetPoint, String targetUid, SpriteType targetType, int remainTime) {
		AllianceBattleTeam allianceBattleTeam = new AllianceBattleTeam();
		allianceBattleTeam.setId(DBKey.getAllianceBattleTeamId(allianceId, allianceBattleTeamSequence.genAllianceBattleTeamId()));
		allianceBattleTeam.setAllianceId(allianceId);
		allianceBattleTeam.setLeaderId(player.getId());
		allianceBattleTeam.setTargetUid(targetUid);
		allianceBattleTeam.setTargetType(targetType);
		allianceBattleTeam.setTargetId(targetPoint);
		allianceBattleTeam.setTeamType(teamType);
		allianceBattleTeam.setAllianceTeamState(AllianceTeamState.WAIT_MARCH);
		allianceBattleTeam.setWaitTime(System.currentTimeMillis() + remainTime * 1000);
		allianceBattleTeam.setCreateTime(System.currentTimeMillis());
		allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.create(allianceBattleTeam);
		this.addLeaderTeamMap(allianceBattleTeam);
		this.addAllianceTeamMap(allianceBattleTeam);
		return allianceBattleTeam;
	}
	
	/**
	 * 加入队伍
	 * @param teamId
	 * @param worldMarch
	 */
	public void addAllianceBattleTeam(String teamId, WorldMarch worldMarch) {
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamId);
		synchronized (allianceBattleTeam) {
			allianceBattleTeam.getMemberMarch().put(Long.valueOf(worldMarch.getOwnerUid()), worldMarch.getId());
			InjectorUtil.getInjector().dbCacheService.update(allianceBattleTeam);
		}
	}
	
	/**
	 * 推出队伍
	 * @param teamId
	 * @param playerId
	 */
	public void dismissAllianceBattleTeam(String teamId, long playerId) {
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamId);
		synchronized (allianceBattleTeam) {
			allianceBattleTeam.getMemberMarch().remove(playerId);
			InjectorUtil.getInjector().dbCacheService.update(allianceBattleTeam);
		}
	}
	
	/**
	 * 解散退伍
	 * @param teamId
	 */
	public void cancelAllianceBattleTeam(String teamId) {
		AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, teamId);
		if(allianceBattleTeam != null) {
			synchronized (allianceBattleTeam) {
				InjectorUtil.getInjector().dbCacheService.delete(allianceBattleTeam);
				removeLeaderTeamMap(allianceBattleTeam);
				removeAllianceTeamMap(allianceBattleTeam);
			}
		}
	}
	
	/**
	 * 获取联盟战队列表
	 * @param allianceId
	 * @return
	 */
	public List<AllianceBattleTeam> getAllianceBattleTeamList(long allianceId) {
		ConcurrentHashMap<String, AllianceBattleTeam> concurrentHashMap = this.allianceTeamMap.get(allianceId);
		if(concurrentHashMap != null){
			return Lists.newArrayList(concurrentHashMap.values());
		}
		return null;
	}
	
	/**
	 * 获取玩家战队信息
	 * @param playerId
	 * @return
	 */
	public List<AllianceBattleTeam> getPlayerBattleTeamList(long playerId) {
		ConcurrentHashMap<String, AllianceBattleTeam> concurrentHashMap = this.allianceTeamMap.get(playerId);
		if(concurrentHashMap != null) {
			return Lists.newArrayList(concurrentHashMap.values());
		}
		return null;
	}
	
	public void addWorldMarch(AllianceBattleTeam allianceBattleTeam, long playerId, WorldMarch worldMarch) {
		allianceBattleTeam.getMemberMarch().put(playerId, worldMarch.getId());
		InjectorUtil.getInjector().dbCacheService.update(allianceBattleTeam);
	}
	
	/**
	 * 判断判断是否超过外事联络处容量
	 * @param player
	 * @param allianceBattleTeam
	 * @return true 标识超过 false未超过
	 */
	public boolean checkPlayerMaxCapacity(Player player, AllianceBattleTeam allianceBattleTeam) {
		int currentMember = allianceBattleTeam.getTeamSoldierNum();
		int maxCapacity = player.getCountryManager().getMaxReinforceNum();
		if (currentMember >= maxCapacity) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取联盟容量上线
	 * @param allianceBattleTeam
	 * @return
	 */
	public int getMaxPlayerMaxcapacity(long playerId) {
		return PlayerAttributeManager.get().concentrateArmy(playerId);
	}
}
