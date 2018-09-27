package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;

/**
 * 驻防部队
 * @author jacky.jiang
 *
 */
public class ReinforceMarch extends AbstractWorldMarch {

	/**
	 * 防守玩家签名
	 */
	private EmailSignature defSignature;
	
	public ReinforceMarch(Player player, int targetPointId, MarchType marchType, WorldMarchSoldier warMarchSoldier, List<WorldSimpleSoldierBean> soldierBriefBeans,EmailSignature defSignature) {
		super(player, targetPointId, marchType, warMarchSoldier, soldierBriefBeans);
		this.defSignature = defSignature;
	}
	
	@Override
	public boolean checkMarch() {
		SpriteInfo targetSpriteInfo = this.getTargetSpriteInfo();
		if (targetSpriteInfo == null || targetSpriteInfo.getSpriteType() != SpriteType.PLAYER.getType()) {
			Language.ERRORCODE.send(this.getPlayer(),ErrorCodeEnum.E121_WORLD.CODE17);
			return false;
		}
		
		// 没有外事联络处，驻防失败
		WorldSprite worldSprite = targetSpriteInfo.getParam();
		long targetPlayerId = Long.valueOf(worldSprite.getOwnerId());
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		BuildControl buildControl = targetPlayer.getCountryManager().getBuildControl(BuildFactory.CONSULATE.getTid());
		if(buildControl == null) {
			Language.ERRORCODE.send(this.getPlayer(),ErrorCodeEnum.E121_WORLD.CODE41);
			return false;
		}
		
		return true;
	}

	@Override
	public void toDestination() {
		WorldMarch worldMarch = this.getWorldMarch();
		SpriteInfo targetInfo = InjectorUtil.getInjector().spriteManager.getGrid(worldMarch.getTargetId()); 
		if(targetInfo == null || targetInfo.getSpriteType() != SpriteType.PLAYER.getType()) {
			this.failReturn();
			this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.驻防失败基地_MAIL_ID);
			return;
		}
		
		WorldSprite worldSprite = targetInfo.getParam();
		if(worldSprite == null || StringUtils.isEmpty(worldSprite.getOwnerId())) {
			this.failReturn();
			return;
		}
		
		long targetPlayerId = Long.valueOf(worldSprite.getOwnerId());
		if(targetPlayerId == this.getPlayer().getId()) {
			this.failReturn();
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		PlayerAlliance targetPlayerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, Long.valueOf(worldSprite.getOwnerId()));
		if(targetPlayerAlliance.getAllianceId() <= 0 || targetPlayerAlliance.getAllianceId() != this.getPlayer().getAllianceId()) {
			this.failReturn();
			return;
		}
		
		List<WorldMarch> dstMarchList = InjectorUtil.getInjector().worldMarchManager.getDestinationWorldMarch(targetInfo.getIndex());
		boolean tag = false;
		for (WorldMarch dstMarch : dstMarchList) {
			if ((dstMarch.getMarchType() == MarchType.MARCH_REINFORCE) && (this.getPlayer().equals(dstMarch.getOwnerUid()))) {
				tag = true;
				break;
			}
		}
		
		if(tag) {
			this.failReturn();
			return;
		}
		
		BuildControl buildControl = targetPlayer.getCountryManager().getBuildControl(BuildFactory.CONSULATE.getTid());
		if(buildControl == null) {
			this.failReturn();
			return;
		}
		
		int currentReinforce = 0;
		for (WorldMarch dstMarch : dstMarchList) {
			if((dstMarch.getMarchType() == MarchType.MARCH_REINFORCE) && dstMarch.getMarchState() == MarchState.OCCUPY) {
				currentReinforce  += dstMarch.getSoldierNum();
			}
		}
		
		// 处理驻防最大上限
		long playerId = Long.valueOf(worldMarch.getOwnerUid());
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		int maxCacpacity = targetPlayer.getCountryManager().getMaxReinforceNum();
		if(targetPlayer == null || currentReinforce + worldMarch.getSoldierNum() > maxCacpacity) {
			// 驻防数量超过上线
			int plusNum = maxCacpacity - currentReinforce;
		
			// 处理士兵容量超过上限
			dealOverCapacity(player, worldMarch, plusNum);
		}
		
		worldMarch.setMarchState(MarchState.OCCUPY);
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
		
		//驻防战报
		targetPlayer.getPlayerMailInfoManager().sendReinforceEmail(player, targetPlayer, worldMarch, targetInfo.getX(),targetInfo.getY(),true);
		super.toDestination();
		this.pushToWorldMarch(player, worldMarch);
	}

	@Override
	public void failReturn() {
		super.failReturn();
		//撤军邮件
		sendFailReturnEmail();
	}
	
	/**
	 * 撤军邮件
	 */
	private void sendFailReturnEmail() {
		WorldMarch worldMarch = this.getWorldMarch();
		SpriteInfo targetInfo = InjectorUtil.getInjector().spriteManager.getGrid(worldMarch.getTargetId()); 
		if(targetInfo == null || targetInfo.getSpriteType() != SpriteType.PLAYER.getType()) {
			return;
		}
		
		WorldSprite worldSprite = targetInfo.getParam();
		if(worldSprite == null || StringUtils.isEmpty(worldSprite.getOwnerId())) {
			return;
		}
		
		long targetPlayerId = Long.valueOf(worldSprite.getOwnerId());
		if(targetPlayerId == this.getPlayer().getId()) {
			return;
		}
		
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, targetPlayerId);
		if(targetPlayer == null){
			return;
		}
		
		long playerId = Long.valueOf(worldMarch.getOwnerUid());
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerId);
		
		//驻防战报
		player.getPlayerMailInfoManager().sendReinforceEmail(targetPlayer, targetPlayer, worldMarch, targetInfo.getX(),targetInfo.getY(),false);
	}
}
