package com.xgame.logic.server.game.soldier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.camp.CampBuildControl;
import com.xgame.logic.server.game.country.structs.build.camp.data.CampData;
import com.xgame.logic.server.game.country.structs.build.camp.data.ReformSoldier;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierData;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.soldier.bean.BuildingDefenSoldierBean;
import com.xgame.logic.server.game.soldier.bean.ReformSoldierBean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.constant.SoldierConstant;
import com.xgame.logic.server.game.soldier.constant.SoldierTypeEnum;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.entity.model.SoldierChangeEventObject;
import com.xgame.logic.server.game.soldier.message.ResUpdateSoldierMessage;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.CampTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;

/**
 *
 *2016-8-05  10:34:46
 *@author ye.yuan
 *
 */
@Component
public class SoldierManager{
	
	public static final int RUN=1,DEFEBSE=2,HURT=3;

	/**
	 * 查询士兵信息
	 * @param player
	 * @param soldierId
	 * @return
	 */
	public Soldier getOrCreateSoldier(Player player, long soldierId) {
		Soldier soldier = player.roleInfo().getSoldierData().querySoldierById(soldierId);
		if(soldier == null) {
			soldier = new Soldier();
			soldier.setFisrtOutput(true);
			soldier.setSoldierId(soldierId);
			player.roleInfo().getSoldierData().newSoldier(soldier);
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
		
		return soldier;
	}
	
	/**
	 * 获取士兵信息
	 * @param player
	 * @param soldierId
	 * @return
	 */
	public Soldier getSoldier(Player player, long soldierId) {
		Soldier soldier = player.roleInfo().getSoldierData().querySoldierById(soldierId);
		return soldier;
	}
	
	public void loginSend(Player player) {
		send(player);
	}

	/**
	 * 总出征数量
	 * @return
	 */
	public int getMaxSetOffNum(Player player) {
		return (int)AttributeCounter.getPlayerAttribute(player.getId(), AttributesEnum.MATCH_ARMY_MAX_NUM, 0) * SoldierConstant.MAX_MARCH_CELL;
	}
	
	/**
	 * 根据建筑id获取兵种类型
	 * @param buildId
	 * @return
	 */
	public int getSoldierTypeByBuildTid(int buildId) {
		if(buildId == BuildFactory.TANK.getTid()) {
			return SoldierTypeEnum.TANK.getCode();
		} else if(buildId == BuildFactory.PLANE.getTid()) {
			return SoldierTypeEnum.PLAIN.getCode();
		} else if(buildId == BuildFactory.SUV.getTid()) {
			return SoldierTypeEnum.SUV.getCode();
		}
		return 0;
	}
	
	/**
	 * 建筑模版id
	 * @param player
	 * @param buildId
	 * @return
	 */
	public CampBuildControl getCampBuildControlByBuildTid(Player player, int buildTid) {
		if (buildTid == BuildFactory.TANK.getTid()) {
			return player.getCountryManager().getTankBuildControl();
		} else if (buildTid == BuildFactory.SUV.getTid()) {
			return player.getCountryManager().getSuvBuildControl();
		} else if (buildTid == BuildFactory.PLANE.getTid()) {
			return player.getCountryManager().getSuvBuildControl();
		}
		return null;
	}
	
	public GameLogSource getGameLogSourceByBuildTid(Player player, int buildTid) {
		if (buildTid == BuildFactory.TANK.getTid()) {
			return GameLogSource.CAMP_OUTPUT_TANK;
		} else if (buildTid == BuildFactory.SUV.getTid()) {
			return  GameLogSource.CAMP_OUTPUT_SUV;
		} else if (buildTid == BuildFactory.PLANE.getTid()) {
			return  GameLogSource.CAMP_OUTPUT_PLANE;
		}
		return null;
	}
	

	/**
	 * 添加系统兵种
	 * @param player
	 * @param soldierType
	 * @param systemIndex
	 * @param num
	 */
	public void addSystemSoldier(Player player, int soldierType, int systemIndex, int num) {
		DesignMap designMap = player.getCustomWeaponManager().queryLastestDesignMap(player, soldierType, systemIndex, 0);
		if(designMap == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E120_SHOP.CODE6);
			return;
		}
		
		Soldier soldier = player.getSoldierManager().getOrCreateSoldier(player, designMap.getId());
		soldier.updateSoldierByType(num, SoldierChangeType.COMMON);
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	
	/**
	 * 判断当前士兵数量是否够
	 * @param player
	 * @param soldierId
	 * @param num
	 * @param soldierChangeType
	 * @return true 不足，false 足够
	 */
	public boolean checkSoldierLimitByType(Player player, long soldierId, long num, int soldierChangeType) {
		Soldier soldier = player.getSoldierManager().getSoldier(player, soldierId);
		switch (soldierChangeType) {
		case SoldierChangeType.COMMON:
			if(soldier.getNum() < num) {
				return true;
			}
			break;
		case SoldierChangeType.MARCH:
			if(soldier.getMarchNum() < num) {
				return true;
			}
			break;
		case SoldierChangeType.RTS:
			if(soldier.getPveNum() < num) {
				return true;
			}
			break;
		case SoldierChangeType.INJURE:
			if(soldier.getInjureNum() < num) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	/**
	 * 发送更新
	 * @param player
	 */
	public void send(Player player) {
		ResUpdateSoldierMessage resUpdateSoldierMessage = new ResUpdateSoldierMessage();
		// 可出战士兵信息
		SoldierData soldierData = player.roleInfo().getSoldierData();
		Map<Long, Soldier> soldiers = soldierData.getSoldiers();
		if(soldiers != null && !soldiers.isEmpty()) {
			for(Soldier soldier : soldiers.values()) {
				SoldierBean soldierBean = new SoldierBean();
				soldierBean.soldierId = soldier.getSoldierId();
				soldierBean.num = soldier.getNum();
				//  军营当中的士兵
				resUpdateSoldierMessage.campSoldierList.add(soldierBean);
				
				SoldierBean hurtSoldierBean = new SoldierBean();
				hurtSoldierBean.soldierId = soldier.getSoldierId();
				hurtSoldierBean.num = soldier.getInjureNum();
				resUpdateSoldierMessage.hurtSoldierList.add(hurtSoldierBean);
			}
		}
		
		// 可收取的士兵信息
		CampData campData = soldierData.getCampData();
		Map<Long, SoldierBrief> soldierGives = campData.getSoldierGives();
		if(soldierGives != null) {
			for(SoldierBrief soldierBrief : soldierGives.values()) {
				SoldierBean soldierBean = new SoldierBean();
				soldierBean.soldierId = soldierBrief.getSoldierId();
				soldierBean.num = soldierBrief.getNum();
				resUpdateSoldierMessage.collectSoldierList.add(soldierBean);
			}
		}
		
		// 防御驻地士兵信息
		Map<Long, ReformSoldier> reformSoldierMap = soldierData.getReformSoldierTable();
		for(ReformSoldier reformSoldier: reformSoldierMap.values()) {
			ReformSoldierBean reformSoldierBean = new ReformSoldierBean();
			reformSoldierBean.type = 1;
			SoldierBean soldierBean = new SoldierBean();
			soldierBean.soldierId = reformSoldier.getSolderId();
			soldierBean.num = reformSoldier.getNum();
			reformSoldierBean.soldier = soldierBean;
			reformSoldierBean.id = reformSoldier.getId();
			resUpdateSoldierMessage.reformSoldierList.add(reformSoldierBean);
		}
		
		// 改装厂
		Map<Long, ReformSoldier> hurtReformSoldierMap = soldierData.getReformSoldierHurtTable();
		for(ReformSoldier reformSoldier: hurtReformSoldierMap.values()) {
			ReformSoldierBean reformSoldierBean = new ReformSoldierBean();
			reformSoldierBean.type = 0;
			SoldierBean soldierBean = new SoldierBean();
			soldierBean.soldierId = reformSoldier.getSolderId();
			soldierBean.num = reformSoldier.getNum();
			reformSoldierBean.soldier = soldierBean;
			reformSoldierBean.id = reformSoldier.getId();
			resUpdateSoldierMessage.reformSoldierList.add(reformSoldierBean);
		}
		
		// 驻防士兵
		for(Entry<Integer, Map<Long, Integer>> entry0 : soldierData.getDefenseSoldierMap().entrySet()){
			int buildUid = entry0.getKey();
			for(Entry<Long, Integer> entry1 : entry0.getValue().entrySet()){
				BuildingDefenSoldierBean buildingDefenSoldierBean = new BuildingDefenSoldierBean();
				SoldierBean soldier = new SoldierBean();
				soldier.soldierId = entry1.getKey();
				soldier.num = entry1.getValue();
				buildingDefenSoldierBean.buildingUid = buildUid;
				buildingDefenSoldierBean.soldier = soldier;
				resUpdateSoldierMessage.defenSoldierList.add(buildingDefenSoldierBean);
			}
		}
		
		
		player.send(resUpdateSoldierMessage);
	}
	
	/**
	 * 验证士兵产出
	 * @param player
	 * @param soldierId
	 * @param type
	 * @return true 生产中，false不存在。
	 */
	public boolean checkSoldierOutput(Player player,long soldierId, int type) {
		Map<Long, Long> tiemrTaskMap = player.roleInfo().getTimerTaskMap();
		if(tiemrTaskMap != null) {
			for(Long taskId : tiemrTaskMap.keySet()) {
				TimerTaskData timerTaskData = player.getTimerTaskManager().get(taskId);
				if(timerTaskData != null) {
					if(TimerTaskCommand.isSoldierOutputQueue(timerTaskData.getQueueId())) {
						CampTimerTaskData campTimerTaskData = (CampTimerTaskData)timerTaskData.getParam();
						if(campTimerTaskData.getSoldierId() == soldierId) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 修改士兵
	 * @param player
	 * @param soldierId
	 * @param type
	 * @return true 士兵修理中
	 */
	public boolean checkSoldierRepair(Player player,long soldierId, int type) {
		Map<Long, Long> tiemrTaskMap = player.roleInfo().getTimerTaskMap();
		if(tiemrTaskMap != null) {
			for(Long taskId : tiemrTaskMap.keySet()) {
				TimerTaskData timerTaskData = player.getTimerTaskManager().get(taskId);
				if(timerTaskData != null) {
					if(timerTaskData.getQueueId() == TimerTaskCommand.MOD_SOLODIER.getId()) {
						CampTimerTaskData campTimerTaskData = (CampTimerTaskData)timerTaskData.getParam();
						if(campTimerTaskData.getSoldierId() == soldierId) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 验证士兵数量是否足够
	 * @param player
	 * @param simpleSoldiers
	 * @return
	 */
	public boolean checkSoldierLimitByType(Player player, List<WorldSimpleSoldierBean> simpleSoldiers) {
		for(WorldSimpleSoldierBean warSimpleSoldierBean : simpleSoldiers) {
			if(player.getSoldierManager().checkSoldierLimitByType(player, warSimpleSoldierBean.soldiers.soldierId, warSimpleSoldierBean.soldiers.num, SoldierChangeType.COMMON)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * rts战斗扣除士兵
	 * @param player
	 * @param soldierBriefBeans
	 * @return
	 */
	public WorldMarchSoldier rtsFightDeductSoldier(Player player, List<WorldSimpleSoldierBean> soldierBriefBeans) {
		WorldMarchSoldier worldMarchSoldier = new WorldMarchSoldier();
		worldMarchSoldier.setPlayerId(player.getRoleId());
		if(soldierBriefBeans != null) {
			Iterator<WorldSimpleSoldierBean> deductIterator = soldierBriefBeans.iterator();
			while (deductIterator.hasNext()) {
				WorldSimpleSoldierBean soldierBriefPro = deductIterator.next();
				Soldier tmpSoldier = player.getSoldierManager().getSoldier(player, soldierBriefPro.soldiers.soldierId);
				int beforeNum = tmpSoldier.getNum();
				
				// 士兵信息
				Soldier soldier = tmpSoldier.cloneFullSoldier(player, soldierBriefPro.soldiers.num);
				soldier.setIndex(soldierBriefPro.index);
				soldier.setVector(soldierBriefPro.position);

				worldMarchSoldier.getSoldiers().put(Soldier.factoryBattleSoldierId(soldier.getSoldierId(), soldier.getIndex()), soldier);
				
				// 扣除普通士兵，rts战斗
				player.roleInfo().getSoldierData().decrementSoldier(soldier.getSoldierId(), soldier.getNum(), SoldierChangeType.COMMON);
				player.roleInfo().getSoldierData().addSoldier(soldier.getSoldierId(), soldier.getNum(), SoldierChangeType.RTS);
			
				DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldierBriefPro.soldiers.soldierId);
				EventBus.getSelf().fireEvent(new SoldierChangeEventObject(player, EventTypeConst.SOLDIER_CHANGE, tmpSoldier.getSoldierId(), designMap.getType(), 
						beforeNum, tmpSoldier.getNum(), MarchType.DEFAULT, MarchState.DEFALUT, GameLogSource.COUNTRY_WAR,
						designMap.getSystemIndex(), designMap.partBeanIdList()));
			}
			
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
		return worldMarchSoldier;
	}

	public Map<AttributeNodeEnum,Double> attributeAdd(long playerId,AttributesEnum attributeEnum,AttributeNodeEnum...attributeNodeEnums) {
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
//		if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums, AttributeNodeEnum.PEIJIAN) < 0){
//			return valueOfNodeMap;
//		}
//		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
//		double value = 0;
//		for(DesignMap designMap : player.roleInfo().getPlayerDesignMap().getDesignMap().values()){
//			Iterator<PartBean> iterator = designMap.getPartList().iterator();
//			while (iterator.hasNext()) {
//				PartBean peiJian = (PartBean) iterator.next();
//				PeiJianPir configModel = PeiJianPirFactory.get(peiJian.partId);
//				if(configModel!=null){
//					Iterator<AttributeAppenderConf> iterator2 = ((AttributeConfMap)configModel.getAttr()).values().iterator();
//					while (iterator2.hasNext()) {
//						AttributeAppenderConf conf = iterator2.next();
//						if(conf.getId() == attributeEnum.getId()){
//							// 这个属性取值根据等级，但是配件已经写死1，这个地方很容易出错。
//							value += conf.get(1);
//						}
//					}
//				}
//			}
//		}
//		if(value > 0){
//			valueOfNodeMap.put(AttributeNodeEnum.PEIJIAN, value);
//			log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,PeiJianPir.class.getSimpleName()));
//		}
		return valueOfNodeMap;
	}
}
