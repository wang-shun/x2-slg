package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.message.ResWarEndMessage;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;
import com.xgame.utils.TimeUtils;

/**
 * 地图攻打玩家行军
 * @author jacky.jiang
 *
 */
@Slf4j
public class CityFightWorldMarch extends AbstractWorldMarch {

	/**
	 * 防守玩家签名
	 */
	private EmailSignature defSignature;
	
	public CityFightWorldMarch(Player player, int targetPointId, MarchType marchType, WorldMarchSoldier worldMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans,EmailSignature defSignature) {
		super(player, targetPointId, marchType, worldMarchSoldier, soldierBriefBeans);
		this.defSignature = defSignature;
	}

	@Override
	public WorldMarch march() {
		WorldMarch worldMarch = super.march();
		// 发送被打报告  TODO 
		try {
//			Player passPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, this.getTargetSpriteInfo().getId());
//			int startTime = (int)(worldMarch.getMarchStartTime() / 1000);
//			RadarBuildControl radarBuildControl = passPlayer.getCountryManager().getRadarBuildControl();
//			if (radarBuildControl != null) {
//				radarBuildControl.investigate(this.getPlayer(), passPlayer, startTime, worldMarch.getMarchTime(), worldMarch.getDestination(), worldMarch.getWorldMarchSoldier().querySoldierList(),worldMarch.getId());
//			}
		} catch(Exception e) {
			log.error("出征处理异常:", e);
		}
		return worldMarch;
	}
	
	@Override
	public boolean checkMarch() {
		SpriteInfo targetSpriteInfo = getTargetSpriteInfo();
		PlayerSprite defendPlayerSprite = targetSpriteInfo.getParam();
		if(defendPlayerSprite.getShieldTime() > TimeUtils.getCurrentTimeMillis()) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE37);
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
		log.info("[{}], PVP到达目的地:x:[{}],y[{}]", this.getPlayer().getName(), this.getWorldMarch().getDestination().x, this.getWorldMarch().getDestination().y);
		
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
			this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.进攻失败基地_MAIL_ID);
			return;
		}
		
		// 世界行军信息
		Player attackPlayer = this.getPlayer();
		WorldSprite worldSprite = this.getTargetSpriteInfo().getParam();
		long targetPlayerId = Long.parseLong(worldSprite.getOwnerId());
		Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class,targetPlayerId);
		if(attackPlayer == null || defendPlayer == null) {
			log.error("数据异常，返回");
			failReturn();
			return;
		}
		
		PlayerSprite defendPlayerSprite = target.getParam();
		if(defendPlayerSprite == null) {
			log.error("数据异常，返回 , 玩家id:{}", target.getId());
			failReturn();
			return;
		}
		
		if(defendPlayerSprite.getShieldTime() > TimeUtils.getCurrentTimeMillis()) {
			log.error("数据异常，返回 , 玩家id:{}", target.getId());
			failReturn();
			return;
		}
		
		// 到达目的地的战斗
		dealToDestination();
		this.pushToWorldMarch(attackPlayer, worldMarch);
	}

	@Override
	public void backReturnHome() {
		WorldMarch worldMarch = this.getWorldMarch();
		if (worldMarch != null) {
			if(worldMarch.getAttach() != null) {
				String report = worldMarch.getAttach().toString();
				if(!StringUtils.isBlank(report)) {
					ResWarEndMessage resWarEndMessage = JsonUtil.fromJSON(report, ResWarEndMessage.class);
					if(resWarEndMessage!=null&&resWarEndMessage.winUid == this.getPlayer().getRoleId()) {
						CurrencyUtil.increase(this.getPlayer(), resWarEndMessage.warResourceBean.get(0).rareNum, CurrencyEnum.RARE,  GameLogSource.EXPLORER);
						CurrencyUtil.increase(this.getPlayer(), resWarEndMessage.warResourceBean.get(0).oilNum, CurrencyEnum.OIL, GameLogSource.EXPLORER);
						CurrencyUtil.increase(this.getPlayer(), resWarEndMessage.warResourceBean.get(0).steelNum, CurrencyEnum.STEEL, GameLogSource.EXPLORER);
						CurrencyUtil.increase(this.getPlayer(), resWarEndMessage.warResourceBean.get(0).moneyNum, CurrencyEnum.GLOD, GameLogSource.EXPLORER);
						CurrencyUtil.send(this.getPlayer());
					}
				
				};	
			}
			
			// 士兵回家
			this.goback(this.getPlayer(), worldMarch.getWorldMarchSoldier(), this.getMarchType());
		}
		
		// 移除行军队列
		worldMarch.setMarchState(MarchState.OVER);
		this.getPlayer().getWorldMarchManager().removeWorldMarch(worldMarch.getId());
		
		// 推送给其他玩家
		pushToWorldMarch(this.getPlayer(), worldMarch);
	}

	@Override
	public boolean initBattle() {
		// 到达目的地的战斗
		Player attackPlayer = this.getPlayer();
		SpriteInfo target = this.getTargetByTargetId();
		Long playerId = Long.valueOf(((WorldSprite)target.getParam()).getOwnerId());
		Player defendPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		if(checkSameAlliancePlayer()) {
			this.failReturn();
			return false;
		}
		
		WarFightParam warFightParam = new WarFightParam();
		warFightParam.setAttackPlayer(attackPlayer);
		warFightParam.setAttackWorldMarch(this.getWorldMarch());
		warFightParam.setTargetSpriteInfo(this.getTargetByTargetId());
		warFightParam.setBattleType(WarType.WORLD_CITY);
		
		// 设置进攻方
		List<WorldMarchSoldier> attackMarchList = Lists.newArrayList(this.getWorldMarch().getWorldMarchSoldier());
		warFightParam.setAttackSoldiers(attackMarchList);
		
		// 设置防守方
		List<WorldMarchSoldier> defendMarchSoldiers = Lists.newArrayList();
		List<WorldMarch> defendMarchList = InjectorUtil.getInjector().worldMarchManager.getReinforceWorldMarch(target.getIndex());
		if(defendMarchList != null && !defendMarchList.isEmpty()) {
			for(WorldMarch defendMarch : defendMarchList) {
				defendMarchSoldiers.add(defendMarch.getWorldMarchSoldier());
			}
		}
		
		warFightParam.setDefendPlayer(defendPlayer);
		warFightParam.setDefendMarchList(defendMarchList);
		warFightParam.setDefendSoldiers(defendMarchSoldiers);
		WorldSprite worldSprite = target.getParam();
		if(worldSprite != null) {
			warFightParam.setMoveCity(worldSprite.isMoveCity());
		}
	
		super.initBattle();
		attackPlayer.getWarManager().startBattle(warFightParam);
		return true;
	}
}
