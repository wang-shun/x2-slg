package com.xgame.logic.server.game.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.BagRewardKit;
import com.xgame.logic.server.game.bag.entity.RewardContext;
import com.xgame.logic.server.game.copy.converter.CopyConverter;
import com.xgame.logic.server.game.copy.enity.MainCopy;
import com.xgame.logic.server.game.copy.enity.MainCopyInfo;
import com.xgame.logic.server.game.copy.enity.MainCopyPoint;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.WarManager;
import com.xgame.logic.server.game.war.constant.WarType;
import com.xgame.logic.server.game.war.entity.Battle;
import com.xgame.logic.server.game.war.entity.WarFightParam;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;


@Component
public class CopyManager{
	
	@Autowired
	private WarManager warManager;
	
	public void initCopy(Player player){
		MainCopyInfo info = new MainCopyInfo();
		for(CopyPir pir : CopyPirFactory.initCopyPir){
			List<CopyPointPir> list = CopyPointPirFactory.copyMap.get(pir.getId());
			Map<Integer,MainCopyPoint> pointMap = new HashMap<>();
			for(CopyPointPir copyPointPir : list){
				boolean isOpen = copyPointPir.getRequirePoint() == 0;
				MainCopyPoint mainCopyPoint = new MainCopyPoint(copyPointPir.getId(),isOpen);
				pointMap.put(mainCopyPoint.getCopyPointId(), mainCopyPoint);
			}
			MainCopy mainCopy = new MainCopy(pir.getId(),1,pointMap);
			info.getCopyMap().put(mainCopy.getCopyId(), mainCopy);
		}
		player.roleInfo().setMainCopyInfo(info);
	}
	
	/**
	 * 获取副本怪物信息
	 * @param player
	 * @param poinId
	 */
	public void reqWarDefendData(Player player,int poinId){
		player.send(CopyConverter.resWarDefendDataMessageBuilder(poinId));
	}
	
	/**
	 * 玩家主线副本信息
	 * @param player
	 * @return
	 */
	public void queryCopyInfo(Player player){
		player.send(CopyConverter.resQueryCopyInfoMessage(player));
	}
	
	public MainCopy initCopy(int copyId){
		List<CopyPointPir> list = CopyPointPirFactory.copyMap.get(copyId);
		Map<Integer,MainCopyPoint> pointMap = new HashMap<>();
		for(CopyPointPir copyPointPir : list){
			boolean isOpen = copyPointPir.getRequirePoint() == 0;
			MainCopyPoint mainCopyPoint = new MainCopyPoint(copyPointPir.getId(),isOpen);
			pointMap.put(mainCopyPoint.getCopyPointId(), mainCopyPoint);
		}
		MainCopy currCopy = new MainCopy(copyId,1,pointMap);
		return currCopy;
	}
	
	/**
	 * 请求战斗
	 * @param player
	 * @param copyId
	 * @param pointId
	 */
	public void fight(Player player,int copyId,int pointId,List<WorldSimpleSoldierBean> soldiers){
		MainCopyInfo mainCopyInfo = player.roleInfo().getMainCopyInfo();
		MainCopy currCopy = mainCopyInfo.getCopyMap().get(copyId);
		//副本是否解锁
		if(currCopy == null){
			CopyPir pir = CopyPirFactory.get(copyId);
			if(pir == null){
				Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE2); 
				return;
			}
			MainCopy mainCopy = mainCopyInfo.getCopyMap().get(pir.getUnlock());
			if(mainCopy == null || mainCopy.getState() != MainCopy.STATE_2){
				Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE1); 
				return;
			}else{
				currCopy = initCopy(copyId);
				mainCopyInfo.getCopyMap().put(currCopy.getCopyId(), currCopy);
				InjectorUtil.getInjector().dbCacheService.update(player);
			}
		}
		//节点是否解锁
		MainCopyPoint copyPoint = currCopy.getPointMap().get(pointId);
		CopyPointPir copyPointPir = CopyPointPirFactory.get(pointId);
		if(copyPointPir == null){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE2); 
			return;
		}
		
		if(copyPoint == null){
			MainCopyPoint lastCopyPoint = currCopy.getPointMap().get(copyPointPir.getUnlock());
			if(lastCopyPoint == null){
				Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE1); 
				return;
			}else{
				boolean isOpen = false;
				if(copyPointPir.getRequirePoint() > 0){
					MainCopyPoint mainCopyPoint = currCopy.getPointMap().get(copyPointPir.getRequirePoint());
					if(mainCopyPoint != null && mainCopyPoint.getPassNum() > 0){
						isOpen = true;
					}
				}else{
					isOpen = true;
				}
				copyPoint = new MainCopyPoint(pointId,isOpen);
				currCopy.getPointMap().put(copyPoint.getCopyPointId(), copyPoint);
				InjectorUtil.getInjector().dbCacheService.update(player);
			}
		}
		if(!copyPoint.isOpen()){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE1); 
			return;
		}
		//体力是否足够
		if(player.roleInfo().getCurrency().getVitality() < copyPointPir.getPhysical()) {
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE3); 
			return;
		}
		
		// 验证是否有士兵可以战斗  
		if(!player.getSoldierManager().checkSoldierLimitByType(player, soldiers)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE22);
			return;
		}
		
		WorldMarchSoldier worldMarchSoldier = player.getSoldierManager().rtsFightDeductSoldier(player, soldiers);
		
		//扣除体力
		player.roleInfo().getCurrency().setVitality(player.roleInfo().getCurrency().getVitality() - copyPointPir.getPhysical());
		InjectorUtil.getInjector().dbCacheService.update(player);
		// 初始化
		WarFightParam warFightParam = new WarFightParam();
		warFightParam.setBattleType(WarType.FUBEN_FIGHT);
		warFightParam.setAttackPlayer(player);
		warFightParam.setAttackSoldiers(Lists.newArrayList(worldMarchSoldier));
		warFightParam.setCopyId(copyId);
		warFightParam.setPointId(pointId);
		// 初始化战场
		Battle battle = warManager.startBattle(warFightParam);
		// 设置战斗信息
		player.send(battle.getResWarDataMessage());
	}
	
	/**
	 * 领取宝箱
	 * @param player
	 * @param copyId
	 * @param boxId
	 */
	public void getRewardBox(Player player,int copyId,int boxId){
		MainCopyInfo mainCopyInfo = player.roleInfo().getMainCopyInfo();
		MainCopy currCopy = mainCopyInfo.getCopyMap().get(copyId);
		if(currCopy == null){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE2); 
			return;
		}
		if(boxId < 0 || boxId > 3){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE4); 
			return;
		}
		CopyPir pir = CopyPirFactory.get(copyId);
		String rewardNeed = pir.getRewardNeed();
		String[] arr = rewardNeed.split(",");
		int needStar = Integer.parseInt(arr[boxId - 1]);
		if(currCopy.getStar() < needStar){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE5); 
			return;
		}
		int rewardBoxFlag = currCopy.getRewardBoxFlag();
		int flag = 1 << boxId - 1;
		if((rewardBoxFlag & flag) == flag){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE6); 
			return;
		}
		String rewardStr;
		if(boxId == 1){
			rewardStr = pir.getRewards1();
		}else if(boxId == 2){
			rewardStr = pir.getRewards2();
		}else{
			rewardStr = pir.getRewards3();
		}
		RewardContext rewardContext = BagRewardKit.checkReward(player,rewardStr);
		if(!rewardContext.isOk()){
			Language.ERRORCODE.send(player, rewardContext.getErrorCode());  
			return;
		}
		BagRewardKit.sendReward(player,rewardContext,GameLogSource.COPY_BOX);
		
		currCopy.setRewardBoxFlag(rewardBoxFlag | flag);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		player.send(CopyConverter.resGetRewardBoxMessageBuilder(currCopy));
	}
	
	/**
	 * 扫荡
	 * @param player
	 * @param copyId
	 * @param pointId
	 */
	public void raid(Player player,int copyId,int pointId){
		MainCopyInfo mainCopyInfo = player.roleInfo().getMainCopyInfo();
		MainCopy copy = mainCopyInfo.getCopyMap().get(copyId);
		if(copy == null){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE2); 
			return;
		}
		MainCopyPoint point = copy.getPointMap().get(pointId);
		if(point == null){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE2); 
			return;
		}
		if(point.getStar() < 3){
			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE7); 
			return;
		}
		CopyPointPir copyPointPir = CopyPointPirFactory.get(pointId);
		//体力是否足够
		if(player.roleInfo().getCurrency().getVitality() < copyPointPir.getPhysical()){
//			Language.ERRORCODE.send(player,ErrorCodeEnum.E1009_COPY.CODE3); 
			return;
		}
		player.roleInfo().getCurrency().setVitality(player.roleInfo().getCurrency().getVitality() - copyPointPir.getPhysical());
		InjectorUtil.getInjector().dbCacheService.update(player);
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
		RewardContext rewardContext = BagRewardKit.checkReward(player, BagRewardKit.rewardBuilder(list));
		if(rewardContext.isOk()){
			BagRewardKit.sendReward(player,rewardContext,GameLogSource.COPY);
		}
		player.send(CopyConverter.resCopyRaidMessageBuilder(copyId, pointId, BagRewardKit.rewardBuilder(list)));
	}
}

