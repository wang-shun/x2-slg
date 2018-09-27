package com.xgame.logic.server.game.world.entity.march.concrete;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.data.sprite.SpriteType;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.trade.TradeBuildControl;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.email.constant.EmailTemplet;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.RoleCurrency;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;


/**
 * 贸易行军队列
 * @author jacky.jiang
 *
 */
@Slf4j
public class TradeWorldMarch extends AbstractWorldMarch {
	
	private WarResourceBean warResourceBean;

	/**
	 * 防守玩家签名
	 */
	private EmailSignature defSignature;
	
	public TradeWorldMarch(Player player, int targetPointId, MarchType marchType, WarResourceBean warResourceBean,EmailSignature defSignature) {
		super(player, targetPointId, marchType, null, null);
		this.warResourceBean = warResourceBean;
		this.defSignature = defSignature;
	}

	@Override
	public void updateWorldMarch() {
		WorldMarch worldMarch = getWorldMarch();
		worldMarch.setWarResourceBean(warResourceBean);
		InjectorUtil.getInjector().dbCacheService.update(worldMarch);
	}

	@Override
	public boolean checkMarch() {
		if(this.getSoldierBriefBeans() != null && !this.getSoldierBriefBeans().isEmpty()) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE19);
			return false;
		}
		
		Player player = getPlayer();
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.TRADE.getTid());
		TradeBuildControl tradeBuildControl = player.getCountryManager().getTradeBuildControl();
		if(tradeBuildControl.getMaxLevelBuild() == null) {
			Language.ERRORCODE.send(getPlayer(), ErrorCodeEnum.E121_WORLD.CODE40);
			return false;
		}
		
		// 非同盟玩家不能贸易
		if(!checkSameAlliancePlayer()) {
			
		}
		
		HashMap<Integer, Integer> map2 = buildingPir.getV2();
		
		//负载检测
		int moneyWeight = Integer.parseInt((String)GlobalPirFactory.get(GlobalConstant.GOLD_WEIGHT).getValue());
		int oilWeight = Integer.parseInt((String)GlobalPirFactory.get(GlobalConstant.OIL_WEIGHT).getValue());
		int steelWeight = Integer.parseInt((String)GlobalPirFactory.get(GlobalConstant.STEEL_WEIGHT).getValue());
		int rareWeight = Integer.parseInt((String)GlobalPirFactory.get(GlobalConstant.RARE_WEIGHT).getValue());
		
		int max = map2.get(getPlayer().getLevel());
		int all = warResourceBean.oilNum * oilWeight + warResourceBean.moneyNum * moneyWeight + warResourceBean.steelNum * steelWeight + warResourceBean.rareNum * rareWeight;
		if (all > max) {//超载
			Language.ERRORCODE.send(player, ErrorCodeEnum.E121_WORLD.CODE20, all);
			return false;
		}
		
		//税率检测
		int needOil = warResourceBean.oilNum;
		int needMoney = warResourceBean.moneyNum;
		int needSteel = warResourceBean.steelNum;
		int needRare = warResourceBean.rareNum;
		RoleCurrency rc = player.roleInfo().getCurrency();
		if (rc.getOil() < needOil || rc.getSteel() < needSteel || rc.getMoney() < needMoney || rc.getRare() < needRare) {
			// 某资源不足
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE11);
			return false;
		}
		
		if(!super.checkMarch()) {
			return false;
		}
		
		//更新我方数据
		CurrencyUtil.decrement(player, needOil, CurrencyEnum.OIL, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.decrement(player, needSteel, CurrencyEnum.STEEL, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.decrement(player, needMoney, CurrencyEnum.GLOD, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.decrement(player, needRare, CurrencyEnum.RARE, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.send(player);
		
		// 发送资源
		InjectorUtil.getInjector().dbCacheService.update(player);
		return  true;
	}
	
	@Override
	public double getMarchSpeed() {
		double speed = Double.valueOf(GlobalPirFactory.getInstance().getDouble(GlobalConstant.TRADE_SPEED));
		return speed;
	}

	@Override
	public void failReturn() {
		Player player = getPlayer();
		WorldMarch worldMarch = this.getWorldMarch();
		WarResourceBean warResourceBean = worldMarch.getWarResourceBean();
		CurrencyUtil.increase(player, warResourceBean.oilNum, CurrencyEnum.OIL, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.increase(player, warResourceBean.steelNum, CurrencyEnum.STEEL, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.increase(player, warResourceBean.moneyNum, CurrencyEnum.GLOD, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.increase(player, warResourceBean.rareNum, CurrencyEnum.RARE, GameLogSource.ALLIANCE_TRADE);
		super.failReturn();
	}

	@Override
	public void toDestination() {
		super.toDestination();
		
		WorldSprite worldSprite = getTargetSpriteInfo().getParam();
		if(worldSprite ==  null) {
			log.error("玩家精灵数据异常,贸易的目标精灵信息为空!!!!!!!!!!!!!!!");
			this.failReturn();
			return;
		}
		
		WorldMarch worldMarch = this.getWorldMarch();
		WarResourceBean warResourceBean = worldMarch.getWarResourceBean();
		SpriteInfo targetInfo = InjectorUtil.getInjector().spriteManager.getGrid(worldMarch.getTargetId()); 
		if(targetInfo == null || targetInfo.getSpriteType() != SpriteType.PLAYER.getType()) {
			this.failReturn();
			this.getPlayer().getPlayerMailInfoManager().sendPositionException(this.getPlayer(), defSignature,EmailTemplet.贸易失败基地_MAIL_ID);
			return;
		}
		
		Player player = getPlayer();
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.TRADE.getTid());
		HashMap<Integer, Float> map1 = buildingPir.getV1();
		Player targetPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(worldSprite.getOwnerId()));
		TradeBuildControl tradeBuildControl = player.getCountryManager().getTradeBuildControl();
		
		//税率检测
		float p = map1.get(tradeBuildControl.getMaxLevelBuild().getLevel());
		int addOil = warResourceBean.oilNum - Math.round(warResourceBean.oilNum * p);
		int addMoney = warResourceBean.moneyNum - Math.round(warResourceBean.moneyNum * p);
		int addSteel = warResourceBean.steelNum - Math.round(warResourceBean.steelNum * p);
		int addRare = warResourceBean.rareNum - Math.round(warResourceBean.rareNum * p);

		// 增加资源
		CurrencyUtil.increase(targetPlayer,addOil, CurrencyEnum.OIL, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.increase(targetPlayer, addSteel, CurrencyEnum.STEEL, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.increase(targetPlayer, addMoney, CurrencyEnum.GLOD, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.increase(targetPlayer, addRare, CurrencyEnum.RARE, GameLogSource.ALLIANCE_TRADE);
		CurrencyUtil.send(targetPlayer);
		
		this.handleReturn();
		
		//发送邮件
		player.getPlayerMailInfoManager().sendTradeEmail(player, targetPlayer.getId(), addMoney, addOil, addRare, addSteel);
		log.error("贸易到达目的地。");
	}
	
	public WarResourceBean getWarResourceBean() {
		return warResourceBean;
	}

	public void setWarResourceBean(WarResourceBean warResourceBean) {
		this.warResourceBean = warResourceBean;
	}
}
