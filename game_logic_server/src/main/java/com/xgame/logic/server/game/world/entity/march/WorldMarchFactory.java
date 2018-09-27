package com.xgame.logic.server.game.world.entity.march;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.AllianceBattleTeam;
import com.xgame.logic.server.game.email.converter.EmailConverter;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.constant.WorldConstant;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.Vector2;
import com.xgame.logic.server.game.world.entity.march.concrete.AllianceAssembleMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.AllianceBattleTeamMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.CampWorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.CityFightWorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ExplorerWorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ReinforceMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ScoutWorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.TerritoryWorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.TradeWorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;

/**
 * 世界行军
 * @author jacky.jiang
 *
 */
public class WorldMarchFactory {
	
	public static IMarchExecutor createWorldMarch(WorldMarchParam worldMarchParam) {
		if(worldMarchParam.getMarchType() == MarchType.SCOUT) {
			return new ScoutWorldMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getResourceType(),worldMarchParam.getDefSignature(),worldMarchParam.getSpriteType());
		} else if(worldMarchParam.getMarchType() == MarchType.EXPLORER) {
			Player player = worldMarchParam.getPlayer();
			SpriteInfo targetSprite = player.getSpriteManager().getVisibleGrid(worldMarchParam.getTargetPoint());
			if(targetSprite == null) {
				return null;
			}
			
			// 资源信息
			if(targetSprite.getSpriteType() == SpriteType.RESOURCE.getType()) {
				ResourceSprite resourceSprite = targetSprite.getParam();
				if(resourceSprite != null) {
					worldMarchParam.setResourceType(resourceSprite.getResourceType().ordinal());
				}
			}
			return new ExplorerWorldMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getResourceType(), worldMarchParam.getWorldMarchSoldier(), worldMarchParam.getSoldierBriefBeans(),worldMarchParam.getDefSignature());
		} else if(worldMarchParam.getMarchType() == MarchType.CAMP) {
			return new CampWorldMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getWorldMarchSoldier(), worldMarchParam.getSoldierBriefBeans(),worldMarchParam.getDefSignature());
		} else if(worldMarchParam.getMarchType() == MarchType.CITY_FIGHT) {
			return new CityFightWorldMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getWorldMarchSoldier(), worldMarchParam.getSoldierBriefBeans(),worldMarchParam.getDefSignature());
		} else if(worldMarchParam.getMarchType() == MarchType.TERRITORY) {
			return new TerritoryWorldMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getWorldMarchSoldier(), worldMarchParam.getSoldierBriefBeans(),worldMarchParam.getDefSignature());
		} else if(worldMarchParam.getMarchType() == MarchType.TEAM_ATTACK) {
			Player player = worldMarchParam.getPlayer();
			SpriteInfo targetSprite = player.getSpriteManager().getVisibleGrid(worldMarchParam.getTargetPoint());
			if(targetSprite == null) {
				return null;
			}
			WorldSprite worldSprite = targetSprite.getParam();
			if(worldSprite != null) {
				Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldSprite.getOwnerId()));
				worldMarchParam.setDefSignature(EmailConverter.emailSignatureBuilder(defendPlayer, targetSprite.getX(), targetSprite.getY()));
			}
			return new AllianceBattleTeamMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getWorldMarchSoldier(), worldMarchParam.getSoldierBriefBeans(), worldMarchParam.getTeam(), worldMarchParam.getRemainTime(),worldMarchParam.getDefSignature());
		} else if(worldMarchParam.getMarchType() == MarchType.MARCH_REINFORCE) {
			return new ReinforceMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getWorldMarchSoldier(), worldMarchParam.getSoldierBriefBeans(),worldMarchParam.getDefSignature());
		} else if(worldMarchParam.getMarchType() == MarchType.ALLIANCE_MEMBER_ASSEMBLY) {
			AllianceBattleTeam allianceBattleTeam = InjectorUtil.getInjector().dbCacheService.get(AllianceBattleTeam.class, worldMarchParam.getTeam());
			if(allianceBattleTeam != null) {
				Player leadPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, allianceBattleTeam.getLeaderId());
				if(leadPlayer != null) {
					Vector2 vector =  leadPlayer.getLocation();
					worldMarchParam.setTargetPoint(vector.getY() * WorldConstant.X_GRIDNUM + vector.getX());
				}
			}
			return new AllianceAssembleMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getWorldMarchSoldier(), worldMarchParam.getSoldierBriefBeans(), worldMarchParam.getTeam(), worldMarchParam.getRemainTime());
		} else if(worldMarchParam.getMarchType() == MarchType.TRADE) {
			return new TradeWorldMarch(worldMarchParam.getPlayer(), worldMarchParam.getTargetPoint(), worldMarchParam.getMarchType(), worldMarchParam.getTradeResource(),worldMarchParam.getDefSignature());
		} else {
			throw new RuntimeException("野外行军类型错误.");
		}
	}
}
