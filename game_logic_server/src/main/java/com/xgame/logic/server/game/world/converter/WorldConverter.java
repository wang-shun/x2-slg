package com.xgame.logic.server.game.world.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.constant.AllianceTeamState;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.war.converter.BattleConverter;
import com.xgame.logic.server.game.world.bean.LandFormBean;
import com.xgame.logic.server.game.world.bean.PlayerSpriteBean;
import com.xgame.logic.server.game.world.bean.ResourceExplorerBean;
import com.xgame.logic.server.game.world.bean.ResourceSpriteBean;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.bean.TeamAttackBean;
import com.xgame.logic.server.game.world.bean.TeamPlayerBean;
import com.xgame.logic.server.game.world.bean.TerrianBean;
import com.xgame.logic.server.game.world.bean.TerrianSpriteBean;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import com.xgame.logic.server.game.world.bean.WorldAllianceSignure;
import com.xgame.logic.server.game.world.bean.WorldPlayerSignure;
import com.xgame.logic.server.game.world.bean.WorldTerritoryBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.entity.LandFormData;
import com.xgame.logic.server.game.world.entity.MarchCollect;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ExplorerWorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.TimeUtils;


/**
 * 世界转换器
 * @author jacky.jiang
 *
 */
public class WorldConverter {
	
	public static SpriteBean converterSprite(SpriteInfo spriteInfo, boolean viewDetail) {
		if(spriteInfo.getEnumSpriteType() == SpriteType.PLAYER) {
			PlayerSpriteBean playerSpriteBean = new PlayerSpriteBean();
			PlayerSprite playerSprite = spriteInfo.getParam();
			if(playerSprite != null) {
				playerSpriteBean.level = playerSprite.getPlayerLevel();
				playerSpriteBean.playerSignure = converterWorldPlayerSignure(Long.valueOf(playerSprite.getOwnerId()));
				playerSpriteBean.shieldTime = playerSprite.getShieldTime();
				playerSpriteBean.attackTime = playerSprite.getAttackTime();
				playerSpriteBean.attackTime = playerSprite.getAttackTime();
			}
			
			SpriteBean spriteBean = WorldConverter.converterSprite(spriteInfo, playerSpriteBean, playerSprite);
			return spriteBean;
		} else if(spriteInfo.getEnumSpriteType() == SpriteType.RESOURCE) {
			ResourceSprite resourceSprite = spriteInfo.getParam();
			ResourceSpriteBean resourceSpriteBean = new ResourceSpriteBean();
			resourceSpriteBean.level = resourceSprite.getLevel();
			resourceSpriteBean.type = resourceSprite.getResourceType().ordinal();
			resourceSpriteBean.num = resourceSprite.getCurNum();
			
			// 行军队列id
			if(resourceSprite.getOwnerMarchId() > 0) {
				WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, resourceSprite.getOwnerMarchId());
				if(worldMarch != null) {
					resourceSpriteBean.occupyPlayer = converterWorldPlayerSignure(Long.valueOf(worldMarch.getOwnerUid()));
					resourceSpriteBean.resourceExplorerBean = converterResourceExplorerBean(spriteInfo, worldMarch);
					resourceSpriteBean.totalWeight = worldMarch.getMaxNum();
					resourceSpriteBean.ownerMarchId = worldMarch.getId();
				}
			}
			
			if(viewDetail) {
				resourceSpriteBean.record = JsonUtil.toJSON(resourceSprite.getExplorer());
			}
			
			if(resourceSprite.getAllianceId()  > 0) {
				resourceSpriteBean.allianceSignure = converterWorldAllianceSignure(resourceSprite.getAllianceId());
			}
			
			SpriteBean spriteBean = WorldConverter.converterSprite(spriteInfo, resourceSpriteBean, resourceSprite);
			return spriteBean;
		} else if(spriteInfo.getEnumSpriteType() == SpriteType.CAMP || spriteInfo.getEnumSpriteType() == SpriteType.TERRITORY || spriteInfo.getEnumSpriteType() == SpriteType.NONE) {
			WorldSprite worldSprite = spriteInfo.getParam();
			TerrianSpriteBean terrianSpriteBean = new TerrianSpriteBean();
			SpriteBean spriteBean = null;
			if(worldSprite != null) {
				if(worldSprite.getOwnerMarchId() > 0) {
					WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
					if(worldMarch != null) {
						long playerId = Long.valueOf(worldMarch.getOwnerUid());
						terrianSpriteBean.occupyPlayer = converterWorldPlayerSignure(playerId);
						terrianSpriteBean.startTime = worldSprite.getStartTime();
						terrianSpriteBean.endTime = worldSprite.getEndTime();
						terrianSpriteBean.ownerMarchId = worldSprite.getOwnerMarchId();
					}
					spriteBean = WorldConverter.converterSprite(spriteInfo, spriteInfo.getSpriteType(), terrianSpriteBean);
				} 
				
				if(worldSprite.getAllianceId() > 0) {
					terrianSpriteBean.allianceSignure = converterWorldAllianceSignure(worldSprite.getAllianceId());
				}
				
				spriteBean = WorldConverter.converterSprite(spriteInfo, terrianSpriteBean, worldSprite);
			}
			return spriteBean;
		} else if(spriteInfo.getEnumSpriteType() == SpriteType.BLOCK) {
			SpriteBean spriteBean = WorldConverter.converterSprite(spriteInfo, null, null);
			spriteBean.type = SpriteType.BLOCK.getType();
			return spriteBean;
		}
		return null;
	}
	
	/**
	 * 玩家签名
	 * @param playerId
	 * @return
	 */
	public static WorldPlayerSignure converterWorldPlayerSignure(long playerId) {
		WorldPlayerSignure worldPlayerSignure = new WorldPlayerSignure();
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		if(player != null) {
			worldPlayerSignure.allianceId = player.getAllianceId();
			worldPlayerSignure.allianceImg = player.getAllianceImg();
			worldPlayerSignure.allianceName = player.getAllianceName() ;
			worldPlayerSignure.allianceSimpleName = player.getAllianceAbbr();
			worldPlayerSignure.playerId = player.getId();
			worldPlayerSignure.playerImg = player.getHeadImg();
			worldPlayerSignure.playerName = player.getName();
		}
		return worldPlayerSignure;
	}
	
	public static WorldAllianceSignure converterWorldAllianceSignure(long allianceId) {
		WorldAllianceSignure worldAllianceSignure = new WorldAllianceSignure();
		Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, allianceId);
		if(alliance != null) {
			worldAllianceSignure.allianceId = allianceId;
			worldAllianceSignure.allianceImg = alliance.getIcon();
			worldAllianceSignure.allianceName = alliance.getAllianceName();
			worldAllianceSignure.allianceSimpleName = alliance.getAbbr();
		}
		return worldAllianceSignure;
	}
	
	
	public static SpriteBean converterSprite(SpriteInfo spriteInfo, int spriteType, Object obj) {
		SpriteBean spriteBean = new SpriteBean();
		spriteBean.x = spriteInfo.getVector2().getX();
		spriteBean.y = spriteInfo.getVector2().getY();
		spriteBean.id = spriteInfo.getIndex();
		spriteBean.serverId = InjectorUtil.getInjector().serverId;
		if(obj != null) {
			spriteBean.data = JsonUtil.toJSON(obj);
		}
		
		spriteBean.type = spriteType;
		spriteBean.id = spriteBean.y * WorldConstant.X_GRIDNUM + spriteBean.x;
		LandFormData landFormData = spriteInfo.getLandform();
		if(landFormData != null) {
			LandFormBean landFormBean = new LandFormBean();
			landFormBean.groundId = landFormData.getGroundId();
			landFormBean.surfaceId = landFormData.getSurfaceId();
		}
		
		if(spriteInfo.getTerrainConfigModel() != null) {
			TerrianBean terrianBean = new TerrianBean();
			terrianBean.fightZone = spriteInfo.getTerrainConfigModel().getIsFightZone();
			terrianBean.terrain = spriteInfo.getTerrainConfigModel().getTerrain();
			terrianBean.battleFailRandomTransfer = spriteInfo.getTerrainConfigModel().getBattleFailRandomTransfer();
		}
		
		return spriteBean;
	}
	
	/**
	 * 精灵bean
	 * @param spriteInfo
	 * @param obj
	 * @return
	 */
	public static SpriteBean converterSprite(SpriteInfo spriteInfo, Object obj, WorldSprite worldSprite) {
		SpriteBean spriteBean = converterSprite(spriteInfo, spriteInfo.getEnumSpriteType().getType(), obj);
		if(worldSprite != null) {
			// 地图上需要显示战场结束时间播放动画
			spriteBean.battleEndTime = worldSprite.getCurrentBattleEndTime();
			if(worldSprite.getBattleSoldierIds() != null && !worldSprite.getBattleSoldierIds().isEmpty()) {
				spriteBean.battleSoldier.addAll(worldSprite.getBattleSoldierIds());
			}
		}
		return spriteBean;
	}
	
	/**
	 * 转换向量坐标
	 * @param worldMarch
	 * @return
	 */
	public static VectorInfo converterVectorInfo(WorldMarch worldMarch, boolean viewSoldier) {
		VectorInfo vectorInfo = new VectorInfo();
		if(worldMarch.getTargetId() > 0) {
			vectorInfo.destination = WorldConverter.getVector2Bean(worldMarch.getTargetId());
			SpriteInfo spriteInfo = InjectorUtil.getInjector().spriteManager.getGrid(vectorInfo.destination.x, vectorInfo.destination.y);
			if(spriteInfo != null) {
				if(spriteInfo.getEnumSpriteType() == SpriteType.RESOURCE) {
					ResourceSprite resourceSprite = (ResourceSprite)spriteInfo.getParam();
					if(worldMarch.getMarchState() != MarchState.BACK) {
						vectorInfo.spriteName = String.format("%s:%s", resourceSprite.getResourceType().ordinal(), resourceSprite.getLevel());
						vectorInfo.spriteType = SpriteType.RESOURCE.getType();
					} else {
						if(!StringUtils.isBlank(worldMarch.getOwnerUid())) {
							vectorInfo.spriteName = InjectorUtil.getInjector().playerManager.getPlayerNameByPlayerId(Long.valueOf(worldMarch.getOwnerUid()));
							vectorInfo.spriteType = SpriteType.PLAYER.getType();
						}
					}
					
					if(resourceSprite.getOwnerMarchId() > 0) {
						WorldMarch worldMarch2 = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, resourceSprite.getOwnerMarchId());
						if(worldMarch2 != null) {
							vectorInfo.targetMarchPlayer = converterWorldPlayerSignure(Long.valueOf(worldMarch2.getOwnerUid())); 
						}
					}
					
				} else if(spriteInfo.getEnumSpriteType() == SpriteType.PLAYER) {
					vectorInfo.spriteType = SpriteType.PLAYER.getType();
					WorldSprite playerSprite = (WorldSprite)spriteInfo.getParam();
					if(!StringUtils.isEmpty(playerSprite.getOwnerId())) {
						long playerId = Long.valueOf(playerSprite.getOwnerId());
						vectorInfo.spriteName = InjectorUtil.getInjector().playerManager.getPlayerNameByPlayerId(playerId);
						if(!StringUtils.isEmpty(playerSprite.getOwnerId())) {
							vectorInfo.targetMarchPlayer = converterWorldPlayerSignure(playerId); 
						}
					}
				} else {
					vectorInfo.spriteType = spriteInfo.getSpriteType();
					if(spriteInfo.getParam() != null) {
						WorldSprite worldSprite = (WorldSprite)spriteInfo.getParam();
						if(worldSprite != null && worldSprite.getOwnerMarchId() > 0) {
							WorldMarch worldMarch2 = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
							if(worldMarch2 != null) {
								long playerId = Long.valueOf(worldMarch2.getOwnerUid());
								vectorInfo.targetMarchPlayer = converterWorldPlayerSignure(playerId);
								vectorInfo.spriteName = InjectorUtil.getInjector().playerManager.getPlayerNameByPlayerId(playerId);
							}
						}
					}
				}
			}
		}
		
		Vector2Bean location = worldMarch.getOwnerPosition();
		if(location != null) {
			vectorInfo.loaction = location;
		}
		
		long ownerPlayerId = Long.valueOf(worldMarch.getOwnerUid());
		vectorInfo.marchId = worldMarch.getId();
		vectorInfo.type = worldMarch.getMarchType().ordinal();
		vectorInfo.state = worldMarch.getMarchState().ordinal();
		vectorInfo.serverId = InjectorUtil.getInjector().serverId;
		vectorInfo.warResourceBean = worldMarch.getWarResourceBean();
		vectorInfo.spriteSubType = worldMarch.getTargetSubType();
		vectorInfo.marchFightType = worldMarch.getMarchFight().ordinal();
		vectorInfo.ownerMarchPlayer = WorldConverter.converterWorldPlayerSignure(ownerPlayerId);
		
		if(viewSoldier) {
			DesignMap maxPowerSoldier = null;
			long maxPower = 0;
			WorldMarchSoldier  worldMarchSoldier = worldMarch.getWorldMarchSoldier();
			if(worldMarchSoldier != null) {
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
				if(player != null) {
					for(Soldier soldier : worldMarchSoldier.querySoldierList()) {
						DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
						WarSoldierBean warSoldierBean = BattleConverter.valueOf(player, soldier);
						vectorInfo.warSoldierBeanList.add(warSoldierBean);
						long nowPower = soldier.getFightPower(player, soldier.getNum());
						if(maxPower < nowPower) {
							maxPowerSoldier = designMap;
						}
					}
				}
			}
			
			// 最高兵种信息
			if(maxPowerSoldier != null) {
				vectorInfo.designMapBean = CustomWeaponConverter.converterDesignMapBean(maxPowerSoldier);
			}
		} else {
			DesignMap maxPowerSoldier = null;
			long maxPower = 0;
			WorldMarchSoldier  worldMarchSoldier = worldMarch.getWorldMarchSoldier();
			if(worldMarchSoldier != null) {
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
				if(player != null) {
					for(Soldier soldier : worldMarchSoldier.querySoldierList()) {
						DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
						WarSoldierBean warSoldierBean = BattleConverter.valueOf(player, soldier);
						vectorInfo.warSoldierBeanList.add(warSoldierBean);
						long nowPower = soldier.getFightPower(player, soldier.getNum());
						if(maxPower < nowPower) {
							maxPowerSoldier = designMap;
						}
					}
				}
			}
			
			// 配件信息
			if(maxPowerSoldier != null) {
				vectorInfo.designMapBean = CustomWeaponConverter.converterDesignMapBean(maxPowerSoldier);
			}
		}
		
		if(worldMarch.getMarchType() == MarchType.TEAM_ATTACK) {
			if(worldMarch.getMarchState() == MarchState.MARCH) {
				AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
				if(allianceBattleTeam != null) {
					if(allianceBattleTeam.getMemberMarch().size() > 0) {
						vectorInfo.multi = 1;
					}
				}
			}
		}
		
		// 判断战斗中状态
		if(worldMarch.getBattleEndTime() > TimeUtils.getCurrentTimeMillis()) {
			vectorInfo.endTime = (int)(worldMarch.getBattleEndTime() / 1000);
			vectorInfo.startTime = (int)(worldMarch.getMarchStartTime() / 1000);
			vectorInfo.loaction = location;
			vectorInfo.destination = worldMarch.getDestination();
			vectorInfo.state = MarchState.FIGHT.ordinal();
		// 处理其他状态	
		} else {
			if(worldMarch.getMarchState() == MarchState.MASS) {
				AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
				if(allianceBattleTeam != null) {
					vectorInfo.startTime = (int)(allianceBattleTeam.getCreateTime() / 1000);
					vectorInfo.endTime =  (int)(allianceBattleTeam.getWaitTime() / 1000);
				}
				vectorInfo.loaction = location;
				vectorInfo.destination = worldMarch.getDestination();
			} else if(worldMarch.getMarchState() == MarchState.MARCH) {
				vectorInfo.endTime = (int)(worldMarch.getMarchArrivalTime() / 1000);
				vectorInfo.startTime = (int)(worldMarch.getMarchStartTime() / 1000);
				vectorInfo.loaction = location;
				vectorInfo.destination = worldMarch.getDestination();
			} else if(worldMarch.getMarchState() == MarchState.OCCUPY) {
				if(worldMarch.getMarchType() == MarchType.EXPLORER) {
					vectorInfo.destination = WorldConverter.getVector2Bean(worldMarch.getTargetId());
					SpriteInfo spriteInfo = InjectorUtil.getInjector().spriteManager.getGrid(vectorInfo.destination.x, vectorInfo.destination.y);
					WorldSprite worldSprite = spriteInfo.getParam();
					if(worldSprite != null) {
						TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getExlporerTaskId());
						if(timerTaskData != null) {
							int currentTime = TimeUtils.getCurrentTime();
							if(timerTaskData.getStartTime() <= currentTime && timerTaskData.getTriggerTime() >= currentTime) {
								vectorInfo.state = MarchState.COLLECTING.ordinal();
							}
							vectorInfo.startTime = timerTaskData.getStartTime();
							vectorInfo.endTime = timerTaskData.getTriggerTime();
						}
						
						if(ownerPlayerId > 0) {
							vectorInfo.resourceExplorerBean = converterResourceExplorerBean(spriteInfo, worldMarch);
						}
					}
				} else if(worldMarch.getMarchType() == MarchType.TERRITORY) {
					TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class, worldMarch.getOccupyTaskId());
					if(timerTaskData != null) {
						vectorInfo.startTime = timerTaskData.getStartTime();
						vectorInfo.endTime = timerTaskData.getTriggerTime();
						int currentTime = TimeUtils.getCurrentTime();
						if(vectorInfo.startTime <= currentTime && vectorInfo.endTime >= currentTime) {
							vectorInfo.state = MarchState.OCCUPYING.ordinal();
						}
					}
				} else if(worldMarch.getMarchType() == MarchType.ALLIANCE_MEMBER_ASSEMBLY)  {
					AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarch.getTeamId());
					if(allianceBattleTeam != null) {
						if (allianceBattleTeam.getAllianceTeamState() == AllianceTeamState.WAIT_MARCH) {
							vectorInfo.startTime = (int)(allianceBattleTeam.getCreateTime() / 1000);
							vectorInfo.endTime =  (int)(allianceBattleTeam.getWaitTime() / 1000);
							
							vectorInfo.loaction = location;
							vectorInfo.destination = worldMarch.getDestination();
						} else if (allianceBattleTeam.getAllianceTeamState() == AllianceTeamState.MARCH) {
							WorldMarch leaderMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, allianceBattleTeam.getMarchId());
							if(leaderMarch != null) {
								vectorInfo.startTime = leaderMarch.getMarchStartTime() / 1000;
								vectorInfo.endTime = leaderMarch.getMarchArrivalTime() / 1000;
								vectorInfo.state = MarchState.GATHER_MARCH.ordinal();
							}
							
							vectorInfo.loaction = location;
							vectorInfo.destination = worldMarch.getDestination();
						}
					}
				}
			} else if(worldMarch.getMarchState() == MarchState.BACK) {
				vectorInfo.startTime = (int)(worldMarch.getReturnStartTime() / 1000);
				vectorInfo.endTime = (int)(worldMarch.getMarchArrivalTime() / 1000);
				vectorInfo.destination = location;
				vectorInfo.loaction = WorldConverter.getVector2Bean(worldMarch.getTargetId());
			} else if(worldMarch.getMarchState() == MarchState.BACK && vectorInfo.endTime <= TimeUtils.getCurrentTimeMillis()) {
				vectorInfo.state = MarchState.OVER.ordinal();
			}
		}
		
		return vectorInfo;
	}
	
	/**
	 * 转换士兵信息
	 * @param worldMarch
	 * @return
	 */
	public static List<WarSoldierBean> converterWarSoldier(WorldMarch worldMarch) {
		List<WarSoldierBean> soldierBeans = new ArrayList<WarSoldierBean>();
		WorldMarchSoldier  worldMarchSoldier = worldMarch.getWorldMarchSoldier();
		if(worldMarchSoldier != null) {
			Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, worldMarchSoldier.getPlayerId());
			if(player != null) {
				for(Soldier soldier : worldMarchSoldier.querySoldierList()) {
					WarSoldierBean warSoldierBean = BattleConverter.valueOf(player, soldier);
					soldierBeans.add(warSoldierBean);
				}
			}
		}
		return soldierBeans;
	}
	
	public static TeamAttackBean converterTeamAttackBean(AllianceBattleTeam allianceBattleTeam, Player leaderPlayer, Player defendPlayer, WorldMarch worldMarch) {
		TeamAttackBean teamAttackBean = new TeamAttackBean();
		teamAttackBean.attackAllianceAbbr = leaderPlayer.getAllianceAbbr();
		teamAttackBean.attackId = leaderPlayer.getId();
		teamAttackBean.attackImg = leaderPlayer.getHeadImg();
		teamAttackBean.attackName = leaderPlayer.getName();
		teamAttackBean.defendId = defendPlayer.getId();
		teamAttackBean.defendAllianceAbbr = defendPlayer.getAllianceAbbr();
		teamAttackBean.defendImg = defendPlayer.getHeadImg();
		teamAttackBean.defendName = defendPlayer.getName();
		if(allianceBattleTeam != null) {
			teamAttackBean.teamId = allianceBattleTeam.getTeamId();
			teamAttackBean.remainTime = allianceBattleTeam.getWaitTime();
			teamAttackBean.createTime = allianceBattleTeam.getCreateTime();
			teamAttackBean.maxCapacity = InjectorUtil.getInjector().allianceBattleTeamManager.getMaxPlayerMaxcapacity(allianceBattleTeam.getLeaderId());
			teamAttackBean.currentValue = allianceBattleTeam.getTeamSoldierNum();
			teamAttackBean.memberNum = allianceBattleTeam.getMemberMarch().size();
		} else {
			teamAttackBean.remainTime = worldMarch.getArrivalTime();
			teamAttackBean.createTime = worldMarch.getMarchStartTime();
			teamAttackBean.maxCapacity = InjectorUtil.getInjector().allianceBattleTeamManager.getMaxPlayerMaxcapacity(defendPlayer.getId());
			teamAttackBean.currentValue = worldMarch.getSoldierNum();
		}
		
		return teamAttackBean;
	}
	
	/**
	 * 行军队列玩家
	 * @param teamId
	 * @param playerMarch
	 * @param player
	 * @return
	 */
	public static TeamPlayerBean converterTeamPlayerBean(String teamId, WorldMarch playerMarch, Player player) {
		TeamPlayerBean teamPlayerBean = new TeamPlayerBean();
		teamPlayerBean.allianceAbbr = player.getAllianceAbbr();
		teamPlayerBean.headImg = player.getHeadImg();
		teamPlayerBean.playerName = player.getName();
		teamPlayerBean.teamId = teamId;
		teamPlayerBean.remainTime = playerMarch.getArrivalTime();
		teamPlayerBean.soldierNum = playerMarch.getSoldierNum();
		teamPlayerBean.playerId = Long.valueOf(playerMarch.getOwnerUid());
		return teamPlayerBean;
	}
	
	/**
	 * 
	 * @param targetId
	 * @return
	 */
	public static Vector2Bean getVector2Bean(int targetId) {
		Vector2Bean vector2Bean = new Vector2Bean();
		vector2Bean.y = targetId / WorldConstant.X_GRIDNUM;
		vector2Bean.x = targetId % WorldConstant.X_GRIDNUM;
		return vector2Bean;
	}
	
	public static int getIndex(int x, int y) {
		int index = y * WorldConstant.X_GRIDNUM + x;
		return index;
	}
	
	/**
	 * 转换bean
	 * @param spriteInfo
	 * @return
	 */
	public static WorldTerritoryBean converterWoldTerritoryBean(SpriteInfo spriteInfo) {
		WorldTerritoryBean worldBean = new WorldTerritoryBean();
		WorldSprite worldSprite = spriteInfo.getParam();
		if(worldSprite != null) {
			if(worldSprite.getAllianceId() > 0) {
				worldBean.abbr = InjectorUtil.getInjector().allianceManager.getAllianceAbbrByAllianceId(worldSprite.getAllianceId());
				worldBean.allianceId = worldSprite.getAllianceId();
			}
			
			Vector2Bean vector2Bean = new Vector2Bean();
			vector2Bean.x = spriteInfo.getX();
			vector2Bean.y = spriteInfo.getY();
			worldBean.position = vector2Bean;
			worldBean.serverId = InjectorUtil.getInjector().serverId;
		}
		return worldBean;
	}
	
	public static ResourceExplorerBean converterResourceExplorerBean(SpriteInfo spriteInfo, WorldMarch worldMarch) {
		ResourceExplorerBean resourceExplorerBean = new ResourceExplorerBean();
		TimerTaskData timerTaskData = InjectorUtil.getInjector().dbCacheService.get(TimerTaskData.class,  worldMarch.getExlporerTaskId());
		if(timerTaskData != null) {
			resourceExplorerBean.explorerEndTime = timerTaskData.getTriggerTime();
			resourceExplorerBean.explorerStartTime = timerTaskData.getStartTime();
			ExplorerWorldMarch explorerWorldMarch = (ExplorerWorldMarch)worldMarch.getExecutor();
			
			// 采集玩家信息
			if(!StringUtils.isBlank(worldMarch.getOwnerUid())) {
				long playerId = Long.valueOf(worldMarch.getOwnerUid());
				Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
				if(player != null && explorerWorldMarch != null) {
					MarchCollect marchCollect = explorerWorldMarch.getMarchCollect(player, worldMarch, spriteInfo);
					ResourceSprite resourceSprite = spriteInfo.getParam();
					resourceExplorerBean.explorerNum = marchCollect.getCollectNum();
					resourceExplorerBean.totalWeight = worldMarch.getMaxNum();
					resourceExplorerBean.explorerSpeed = PlayerAttributeManager.get().getExplorerSpeed(player, resourceSprite.getResourceType(), resourceSprite.getAllianceId(), resourceSprite.getLevel());
				}
				System.out.println("json="+JsonUtil.toJSON(resourceExplorerBean));
			}
		}
		return resourceExplorerBean;
	}
	
}
