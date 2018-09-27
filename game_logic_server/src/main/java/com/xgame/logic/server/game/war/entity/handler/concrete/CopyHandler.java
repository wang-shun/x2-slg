package com.xgame.logic.server.game.war.entity.handler.concrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.config.copy.CopyPir;
import com.xgame.config.copy.CopyPirFactory;
import com.xgame.config.copyPoint.CopyPointPir;
import com.xgame.config.copyPoint.CopyPointPirFactory;
import com.xgame.config.item.ItemBox;
import com.xgame.drop.DropService;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.BagRewardKit;
import com.xgame.logic.server.game.bag.entity.RewardContext;
import com.xgame.logic.server.game.copy.CopyManager;
import com.xgame.logic.server.game.copy.converter.CopyConverter;
import com.xgame.logic.server.game.copy.enity.MainCopy;
import com.xgame.logic.server.game.copy.enity.MainCopyInfo;
import com.xgame.logic.server.game.copy.enity.MainCopyPoint;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarAttacker;
import com.xgame.logic.server.game.war.entity.WarDefender;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.war.entity.report.WarEndReport;
import com.xgame.logic.server.game.war.message.ResWarDataMessage;
import com.xgame.logic.server.game.war.message.ResWarEndMessage;

/**
 * 副本
 * @author zehong.he
 *
 */
@Component
public class CopyHandler extends AbstractFightHandler {

	@Autowired
	private CopyManager copyManager;
	
	@Override
	public WarType getWarType() {
		return WarType.FUBEN_FIGHT;
	}

	@Override
	public Battle init(WarFightParam warFightParam) {
		Battle battle = new Battle();
		battle.setBattleId(WarManager.BATTLEID_GENERATOR.incrementAndGet());
		battle.setWarType(warFightParam.getBattleType());
		
		// 初始化进攻方
		WarAttacker warAttacker = new WarAttacker();
		warAttacker.initSoldier(warFightParam.getAttackPlayer(), Lists.newArrayList(warFightParam.getAttackSoldiers()), 0);
		warAttacker.setPlayer(warFightParam.getAttackPlayer());
		battle.setWarAttacker(warAttacker);
		
		//初始化防守方
		CopyPointPir pir = CopyPointPirFactory.get(warFightParam.getPointId());
		String copyFile = pir.getCopyFile();
		double monsterTrim = pir.getMonsterTrim();
		double buildingTrim = pir.getBuildingTrim();
		WarDefender warDefender = CopyConverter.warDefendDataBuilder(copyFile,monsterTrim,buildingTrim);
		battle.setWarDefender(warDefender);
		battle.setWarFightParam(warFightParam);
		
		// 初始化
		ResWarDataMessage resWarDataMessage = new ResWarDataMessage();
		resWarDataMessage.battleType = battle.getWarType().getConfigId();
		resWarDataMessage.attackData = battle.getWarAttacker().getWarAttackData();
		resWarDataMessage.defenData = battle.getWarDefender().getWarDefenData();
		resWarDataMessage.battleId = battle.getBattleId();
		battle.setResWarDataMessage(resWarDataMessage);
		return battle;
	}

	@Override
	public void fightEnd(Battle battle, WarEndReport warEndReport) {
		
		battle.setWinPlayerId(warEndReport.getWinUid());
		//处理进攻方士兵
		dealAttackSoldier(battle.getWarAttacker(), warEndReport.getWarEntityReport().getAttackSoldierBean());
		
		//副本记录
		updateCopy(battle,warEndReport);
		
		// 处理战斗奖励
		String rewardStr = dealBattleReward(battle);
		
		// 获取战斗结束报告
		ResWarEndMessage resWarEndMessage = this.converterResWarEndMessage(battle, warEndReport);
		resWarEndMessage.rewards = rewardStr;
		battle.getWarAttacker().getPlayer().send(resWarEndMessage);
		
		// 士兵同步
		battle.getWarAttacker().getPlayer().getSoldierManager().send(battle.getWarAttacker().getPlayer());
		
		super.fightEnd(battle, warEndReport);
	}
	
	/**
	 * 副本记录
	 * @param battle
	 * @param warEndReport
	 */
	private void updateCopy(Battle battle,WarEndReport warEndReport){
		if(battle.getWarAttacker().getAttackId() == battle.getWinPlayerId()) {
			Player player = battle.getWarAttacker().getPlayer();
			WarFightParam warFightParam = battle.getWarFightParam();
			int pointId = warFightParam.getPointId();
			int copyId = warFightParam.getCopyId();
			MainCopyInfo mainCopyInfo = player.roleInfo().getMainCopyInfo();
			MainCopy mainCopy = mainCopyInfo.getCopyMap().get(copyId);
			if(mainCopy == null){
				return;
			}
			MainCopyPoint point = mainCopy.getPointMap().get(pointId);
			if(point == null){
				return;
			}
			point.setPassNum(point.getPassNum() + 1);
			int star = 0;
			if(warEndReport.getDestroyLevel() >= 20 && warEndReport.getDestroyLevel() < 50){
				star = 1;
			}else if(warEndReport.getDestroyLevel() >= 50 && warEndReport.getDestroyLevel() < 100){
				star =2;
			}else if(warEndReport.getDestroyLevel() >= 100){
				star = 3;
			}
			point.setStar(star);
			//解锁下一个副本章节或节点
			unlocukNextCopy(mainCopyInfo,copyId,pointId);
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
	}
	
	/**
	 * 解锁下一个副本章节或节点
	 * @param mainCopyInfo
	 * @param copyId
	 * @param pointId
	 */
	private void unlocukNextCopy(MainCopyInfo mainCopyInfo,int copyId,int pointId){
		List<CopyPointPir> list = CopyPointPirFactory.copyMap.get(copyId);
		List<CopyPointPir> newList = new ArrayList<>();
		for(CopyPointPir point : list){
			if(point.getRequirePoint() == pointId){
				newList.add(point);
			}
		}
		if(newList.size() > 0){
			for(CopyPointPir point : newList){
				MainCopy mainCopy = mainCopyInfo.getCopyMap().get(copyId);
				MainCopyPoint mainCopyPoint = mainCopy.getPointMap().get(point.getId());
				if(mainCopyPoint != null){
					mainCopyPoint.setOpen(true);
				}else{
					MainCopyPoint newPoint = new MainCopyPoint(point.getId(),true);
					mainCopy.getPointMap().put(newPoint.getCopyPointId(), newPoint);
				}
			}
		}else{
			List<CopyPir> newCopys = new ArrayList<>();
			for(CopyPir copyPir : CopyPirFactory.getInstance().getFactory().values()){
				if(copyPir.getUnlock() == copyId){
					newCopys.add(copyPir);
				}
			}
			if(newCopys.size() > 0){
				for(CopyPir copyPir : newCopys){
					MainCopy mainCopy = copyManager.initCopy(copyPir.getId());
					mainCopyInfo.getCopyMap().put(mainCopy.getCopyId(), mainCopy);
				}
			}
		}
	}
	
	/**
	 * 处理战斗奖励
	 * @param battle
	 */
	public String dealBattleReward(Battle battle) {
		if(battle.getWarAttacker().getAttackId() == battle.getWinPlayerId()) {
			WarFightParam warFightParam = battle.getWarFightParam();
			int pointId = warFightParam.getPointId();
			//奖励1
			List<ItemBox> list = DropService.getDrop(CopyPointPirFactory.reward1Boxs.get(pointId), CopyPointPirFactory.reward1Num.get(pointId));
			List<ItemBox> reward2sConfig = CopyPointPirFactory.reward2Boxs.get(pointId);
			if(list == null){
				list = new ArrayList<>();
			}
			for(ItemBox box : reward2sConfig){
				Random random = new Random();
				int num = random.nextInt();
				if(box.getOdds() >= num){
					list.add(box);
				}
			}
			RewardContext rewardContext = BagRewardKit.checkReward(battle.getWarAttacker().getPlayer(), BagRewardKit.rewardBuilder(list));
			if(rewardContext.isOk()){
				BagRewardKit.sendReward(battle.getWarAttacker().getPlayer(),rewardContext,GameLogSource.COPY);
			}else{
				return "";
			}
			return BagRewardKit.rewardBuilder(list);
		}
		return "";
	}
}
