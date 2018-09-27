package com.xgame.logic.server.game.war.handler;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.message.ReqWarDataMessage;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.logic.server.game.world.message.ResCityStateMessage;
import com.xgame.utils.TimeUtils;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReqWarDataHandler extends PlayerCommand<ReqWarDataMessage>{
	
	@Override
	protected void action() {
		PlayerManager playerManager = InjectorUtil.getInjector().getBean(PlayerManager.class);
		WarManager battleManager = InjectorUtil.getInjector().getBean(WarManager.class);
		Player attackPlayer = this.player;
	
		Player defendPlayer = playerManager.getPlayer(message.enemyUid);
		if(attackPlayer == null || defendPlayer == null) {
			log.error("玩家不存在。。");
			return;
		}
		
		// 验证是否有士兵可以战斗
		List<WorldSimpleSoldierBean> simpleSoldiers = message.soldiers;
		if(player.getSoldierManager().checkSoldierLimitByType(player, simpleSoldiers)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE22);
			return;
		}
		
		// 扣除资源
		WorldMarchSoldier worldMarchSoldier = player.getSoldierManager().rtsFightDeductSoldier(player, simpleSoldiers);
		defendPlayer.roleInfo().getBasics().setAttackTimer(TimeUtils.getCurrentTimeMillis());
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		// 推送被打消息
		ResCityStateMessage resUserInfoMessage = new ResCityStateMessage();
		resUserInfoMessage.beAttackTime = (int)(player.roleInfo().getBasics().getAttackTimer() / 1000);
		player.send(resUserInfoMessage);

		// 初始化
		WarFightParam warFightParam = new WarFightParam();
		warFightParam.setAttackPlayer(attackPlayer);
		warFightParam.setDefendPlayer(defendPlayer);
		warFightParam.setAttackSoldiers(Lists.newArrayList(worldMarchSoldier));
		warFightParam.setOil(message.oilNum);
		warFightParam.setBattleType(WarType.COUNTRY_SEARCH);
		
		// 初始化战场
		Battle battle = battleManager.startBattle(warFightParam);
		
		// 设置战斗信息
		attackPlayer.send(battle.getResWarDataMessage());
		
		Language.SUCCESSTIP.send(attackPlayer, SuccessTipEnum.E500_WAR.CODE2);
	}
	
}
