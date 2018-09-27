package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Lists;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchFightState;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;

/**
 * 扎营
 * @author jacky.jiang
 *
 */
@Slf4j
public class CampWorldMarch extends AbstractWorldMarch {

	/**
	 * 防守玩家签名
	 */
	private EmailSignature defSignature;
	
	public CampWorldMarch(Player player, int targetPointId, MarchType marchType, WorldMarchSoldier worldMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans,EmailSignature defSignature) {
		super(player, targetPointId, marchType, worldMarchSoldier, soldierBriefBeans);
		this.defSignature = defSignature;
	}
	
	@Override
	public boolean checkMarch() {
		SpriteInfo targetSprite = getTargetSpriteInfo();
		if(targetSprite== null || (targetSprite.getSpriteType() != SpriteType.NONE.getType() && targetSprite.getSpriteType() != SpriteType.CAMP.getType())) {
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
		this.getWorldMarch().setMarchState(MarchState.OCCUPY);
		Vector2Bean destination = WorldConverter.getVector2Bean(this.getWorldMarch().getTargetId());
		log.info("扎营到达目的地:x:[{}],y[{}]", destination.x, destination.y);
		WorldMarch worldMarch = this.getWorldMarch();
		if(worldMarch == null ) {
			log.error("出征部队丢失...");
			this.failReturn();
			return;
		}
		
		if(checkSameAllianceMarch()) {
			this.failReturn();
			return;
		}
		
		worldMarch.setMarchState(MarchState.OCCUPY);
		SpriteInfo target = this.getTargetByTargetId();
		WorldSprite worldSprite = null;
		if(target == null) {
			target =  getPlayer().getSpriteManager().createCampSprite(destination.x, destination.y, worldMarch.getId());
			if(target.getParam() != null) {
				worldSprite = target.getParam();
			}
		} else {
			// 到达目标
			worldSprite = target.getParam();
			if(target.getEnumSpriteType() != SpriteType.CAMP && target.getEnumSpriteType() != SpriteType.NONE) {
				this.failReturn();
				this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.进攻失败扎营_MAIL_ID);
				return;
			}
		}
		
		if(worldSprite.getOwnerMarchId() == worldMarch.getId()) {
			this.failReturn();
			return;
		}
		
		// 到达处理扎营
		if (worldSprite.getOwnerMarchId() <= 0) {
			if(worldMarch.getMarchFight() == MarchFightState.FIGHT) {
				this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.进攻失败扎营_MAIL_ID);
				worldMarch.getExecutor().handleReturn();
				return;
			} 
			
			worldSprite.setOwnerMarchId(worldMarch.getId());
			refreshLocation(target);
			InjectorUtil.getInjector().dbCacheService.update(target);
		} else {
			WorldMarch defendMatch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
			if(defendMatch != null) {
				dealToDestination();
			} else {
				worldSprite.setOwnerMarchId(worldMarch.getId());
				target.setSpriteType(SpriteType.CAMP.getType());
				refreshLocation(target);
				InjectorUtil.getInjector().dbCacheService.update(target);
			}
		}
		
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
		// 推送行军队列
		this.pushToWorldMarch(getPlayer(), worldMarch);
		this.pushToWorldSprites(target);
	}
	
	@Override
	public void handleReturn() {
		SpriteInfo spriteInfo = this.getTargetByTargetId();
		if(spriteInfo != null) {
			WorldSprite worldSprite = spriteInfo.getParam();
			if(worldSprite.getOwnerMarchId() == this.getWorldMarch().getId()) {
				worldSprite.setOwnerMarchId(0);
				spriteInfo.setSpriteType(SpriteType.NONE.getType());
				InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
			}
			
			// 刷新地图信息
			refreshLocation(spriteInfo);
		}
		super.handleReturn();
	}

	@Override
	public double getMarchSpeed() {
		return super.getMarchSpeed();
	}

	@Override
	public boolean initBattle() {
		SpriteInfo target = this.getTargetByTargetId();
		WorldSprite worldSprite = target.getParam();
		WorldMarch defendMatch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, worldSprite.getOwnerMarchId());
		Player defenPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(defendMatch.getOwnerUid()));
		if(defenPlayer == null ||  (defenPlayer.getAllianceId() > 0 && defenPlayer.getAllianceId() == getPlayer().getAllianceId())) {
			log.info("同盟玩家直接返回。");
			this.failReturn();
			return false;
		}
		
		WarFightParam warFightParam = new WarFightParam();
		warFightParam.setBattleType(WarType.CAMP);
		warFightParam.setAttackPlayer(getPlayer());
		warFightParam.setAttackWorldMarch(this.getWorldMarch());
		List<WorldMarchSoldier> attackMarchList = Lists.newArrayList(this.getWorldMarch().getWorldMarchSoldier());
		warFightParam.setAttackSoldiers(attackMarchList);
		warFightParam.setTargetSpriteInfo(target);
		
		List<WorldMarchSoldier> defendMarchSoldiers = Lists.newArrayList(defendMatch.getWorldMarchSoldier());
		warFightParam.setDefendWorldMarch(defendMatch);
		warFightParam.setDefendPlayer(defenPlayer);
		warFightParam.setDefendSoldiers(defendMarchSoldiers);
		super.initBattle();
		getPlayer().getWarManager().startBattle(warFightParam);
		return true;
	}
}
