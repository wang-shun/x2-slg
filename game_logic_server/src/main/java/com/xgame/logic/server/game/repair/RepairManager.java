package com.xgame.logic.server.game.repair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.xgame.config.RtsType.RtsTypePir;
import com.xgame.config.RtsType.RtsTypePirFactory;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;
import com.xgame.logic.server.game.country.structs.build.mod.ModBuildControl;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.RepairSoldierEventObject;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.ModTimerTaskData;
import com.xgame.logic.server.game.war.constant.WarResultType;
import com.xgame.logic.server.game.war.constant.WarType;


/**
 * 修理厂
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class RepairManager {
	
	/**
	 * 插入到修理厂
	 * @param player
	 * @param soldierId
	 * @param num
	 * @param soldierChangeType
	 * @return
	 */
	public int insertModFactory(Player player, long soldierId,int num, int soldierChangeType){
		return insertModFactory(player,soldierId,num,soldierChangeType,0);
	}
	
	/**
	 * 插入到修理厂
	 * @param player
	 * @param soldierId
	 * @param num
	 * @param soldierChangeType
	 * @param buildUid 建筑uid只有是驻防士兵的时候有用
	 * @return
	 */
	public int insertModFactory(Player player, long soldierId,int num, int soldierChangeType,int buildUid) {
		//获得修理厂剩余可插入
		int n = verifyModFactory(player,soldierId,num);
		//报废数量
		int retired = num;
		if (n > 0) {
			if(buildUid > 0){
				player.roleInfo().getSoldierData().decrDefenseSoldierNum(buildUid, soldierId, num);
			}else{
				//如果有剩余空间  插入伤兵数量
				player.roleInfo().getSoldierData().decrementSoldier(soldierId, num, soldierChangeType);
			}
			//剩余的就是报废数量
			retired = num - n;
		}
		
		player.roleInfo().getSoldierData().addSoldier(soldierId, num, SoldierChangeType.INJURE);
		
//		DocumentUtil.CAMP_POWER.send(player);
		CurrencyUtil.send(player);
		InjectorUtil.getInjector().dbCacheService.update(player);
		return retired;
	}
	
	/**
	 * 战斗插入伤病
	 * @param player
	 * @param soldierId
	 * @param num
	 * @param source
	 * @param warType
	 * @param warResultType
	 * @return 返还插入修理厂后死亡的士兵数量
	 */
	public int fightInsertModFactory(Player player, long soldierId, int num, WarType warType, WarResultType warResultType, int soldierChangeType) {
		RtsTypePir rtsTypePir = RtsTypePirFactory.get(warType.getConfigId());
		if(rtsTypePir == null) {
			log.error("战斗的配置出错，有战斗类型未配置{}", warType);
			return num;
		}
		
		
		// 根据战斗类型计算剩余士兵数量
		int deadNum = 0;
		int plusNum = num;
		int egnoreModCapacity = 0;
		if(warResultType == WarResultType.ATTACK) {
			if(rtsTypePir.getDeathRateAtk() > 0) {
				plusNum = num - num * rtsTypePir.getDeathRateAtk() / 100;
				deadNum = num - plusNum;
			}
			egnoreModCapacity = rtsTypePir.getIgnoreHospitalLimitAtk();
		} else if(warResultType == WarResultType.DEFEND) {
			if(rtsTypePir.getDeathRateDef() > 0) {
				plusNum = num - num * rtsTypePir.getDeathRateDef() / 100;
				deadNum = num - plusNum;
			}
			egnoreModCapacity = rtsTypePir.getIgnoreHospitalLimitDef();
		}
		
		// 直接扣除的比例
		if(deadNum > 0) {
			player.roleInfo().getSoldierData().decrementSoldier(soldierId, deadNum, soldierChangeType);
		}
		
		if (plusNum <= 0) {
			return deadNum;
		}
		
		// 直接返还兵的比例
		int backCampNum = 0;
		if(warResultType == WarResultType.ATTACK) {
			if(rtsTypePir.getCampRateAtk() > 0) {
				backCampNum = plusNum * rtsTypePir.getCampRateAtk() / 100;
				plusNum = plusNum - backCampNum;
			}
			
			
		} else if(warResultType == WarResultType.DEFEND) {
			if(rtsTypePir.getCampRateDef() > 0) {
				backCampNum = plusNum * rtsTypePir.getCampRateDef() / 100;
				plusNum = plusNum - backCampNum;
			}
		}
		
		if(backCampNum > 0) {
			player.roleInfo().getSoldierData().decrementSoldier(soldierId, backCampNum, soldierChangeType);
			player.roleInfo().getSoldierData().addSoldier(soldierId, backCampNum, SoldierChangeType.COMMON);
		}
		
		if (plusNum <= 0) {
			return deadNum;
		}
		
		//获得修理厂剩余可插入
		int insertDeadNum = 0;
		if(egnoreModCapacity <= 0) {
			insertDeadNum = verifyModFactory(player, soldierId, plusNum);
		}
		
		//报废数量
		if (insertDeadNum > 0) {
			player.roleInfo().getSoldierData().decrementSoldier(soldierId, insertDeadNum, soldierChangeType);
		} 
		
		// 伤病的数量
		if(plusNum - insertDeadNum > 0) {
			log.info("伤兵数量：[{}}", plusNum - insertDeadNum);
			player.roleInfo().getSoldierData().addSoldier(soldierId, plusNum - insertDeadNum, SoldierChangeType.INJURE);
		}
		
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		// 同步士兵信息
		player.getSoldierManager().send(player);
		return deadNum + insertDeadNum;
	}
	
	/**
	 * 更新客户端
	 * @param player
	 */
	public void updataClient(Player player){
		player.getSoldierManager().send(player);
	}
	
	/**
	 * 获得伤兵总数
	 * @return
	 */
	private int getHurtSoldierSize(Player player) {
		int i = 0;
		Iterator<Soldier> iterator = player.roleInfo().getSoldierData().getSoldiers().values().iterator();
		while (iterator.hasNext()) {
			Soldier soldier = (Soldier) iterator.next();
			i += soldier.getInjureNum();
		}
		return i;
	}

	/**
	 * 验证插入修理厂的士兵信息
	 * @param player
	 * @param soldierId
	 * @param num
	 * @return 返回插入之后死亡的士兵数量
	 */
	public int verifyModFactory(Player player, long soldierId, int num) {
		Soldier soldier = player.getSoldierManager().getSoldier(player, soldierId);
		if (soldier == null||num <= 0) {
			return 0;
		}
		
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.MOD.getTid());
		if (buildingPir == null) {
			return num;
		}
		
		int len = this.getHurtSoldierSize(player);
		int maxCache = this.getMaxHurtNum(player);
		if(maxCache <=0) {
			return num;
		}
		
		if (num > maxCache - len) {
			return num-(maxCache-len);
		} else {
			return 0;
		}
	}

	/**
	 * 修改士兵
	 * @param player
	 * @param beans
	 * @param useType
	 */
	public void mod(Player player, List<SoldierBean> beans, int useType) {
		ModBuildControl modBuildControl = player.getCountryManager().getModBuildControl();
		if(modBuildControl == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E105_MOD.CODE1.get());
			return;
		}
		
		if (beans == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E105_MOD.CODE1.get());
			return;
		}

		// 如果有队列在执行 走
		if (useType == CurrencyUtil.USE && TimerTaskHolder.getTimerTask(TimerTaskCommand.MOD_SOLODIER).checkQueueCapacityMax(player)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E105_MOD.CODE2.get());
			return;
		}
		
		int oil = 0;
		int money = 0;
		int steels = 0;
		int rare = 0;
		float time = 0f;
		int subNum = 0;
		List<SoldierBrief> soldierList = new ArrayList<>(beans.size());
		Iterator<SoldierBean> iterator = beans.iterator();
		while (iterator.hasNext()) {
			SoldierBean modSoldierBean = iterator.next();
			subNum+=modSoldierBean.num;
			Soldier soldier = player.getSoldierManager().getSoldier(player, modSoldierBean.soldierId);
			if (soldier.getInjureNum() < modSoldierBean.num || modSoldierBean.num <= 0) {
				// 发送来的兵种数量与服务器不符 走
				Language.ERRORCODE.send(player, ErrorCodeEnum.E105_MOD.CODE3.get());
				return;
			}
			
			DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, modSoldierBean.soldierId);
			List<PartBean> peijians = designMap.getPartList();
			for(int k=0;k<peijians.size();k++) {	
				PartBean peiJian = peijians.get(k);
				PeiJianPir peijianConf = PeiJianPirFactory.get(peiJian.partId);
				if (peijianConf != null) {
					money += peijianConf.getFix_cost_cash()*modSoldierBean.num;
					oil += peijianConf.getFix_cost_oil()*modSoldierBean.num;
					rare += peijianConf.getFix_cost_earth()*modSoldierBean.num;
					steels += peijianConf.getFix_cost_steel()*modSoldierBean.num;
					time += peijianConf.getFix_time()*modSoldierBean.num;
				} else {
					// 没找到配件配置 走
					Language.ERRORCODE.send(player, ErrorCodeEnum.E105_MOD.CODE4.get());
					return;
				}
			}
			
			SoldierBrief brief = new SoldierBrief();
			brief.setSoldierId(modSoldierBean.soldierId);
			brief.setNum(modSoldierBean.num);
			soldierList.add(brief);
		}
		
		int maxCache = getMaxHurtNum(player);
		if (maxCache < subNum || subNum <= 0) {
			// 超过最大缓存数量
			Language.ERRORCODE.send(player, ErrorCodeEnum.E105_MOD.CODE5.get());
			return;
		}
		money = PlayerAttributeManager.get().hospitalResourceCost(player.getId(), money);
		steels = PlayerAttributeManager.get().hospitalResourceCost(player.getId(),steels);
		oil = PlayerAttributeManager.get().hospitalResourceCost(player.getId(), oil);
		rare = PlayerAttributeManager.get().hospitalResourceCost(player.getId(), rare);
		//验证资源是否足
		if (!CurrencyUtil.decrementCurrency(player, money, steels, oil, rare,useType, time, GameLogSource.MODIFY_SOLDIER)){
			Language.ERRORCODE.send(player, ErrorCodeEnum.E105_MOD.CODE6.get());
			return;
		}
		
		FightPowerKit.CAMP_POWER.math(player,GameLogSource.MODIFY_SOLDIER);
		CurrencyUtil.send(player);
		
		if (useType == CurrencyUtil.USE) {
			TimerTaskHolder.getTimerTask(TimerTaskCommand.MOD_SOLODIER).register(player, (int) Math.ceil(time), new ModTimerTaskData(0, soldierList));
		} else if (useType == CurrencyUtil.FAST_USE) {
			modSuccess(player, soldierList);
		}
		
		InjectorUtil.getInjector().dbCacheService.update(player);
		EventBus.getSelf().fireEvent(new RepairSoldierEventObject(player,subNum));
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E105_MOD.CODE1.get());
	}

	/**
	 * 修理成功
	 * @param player
	 * @param modCampArr
	 */
	public void modSuccess(Player player, List<SoldierBrief> soldierList) {
		Iterator<SoldierBrief> iterator = soldierList.iterator();
		while (iterator.hasNext()) {
			SoldierBrief next = iterator.next();
			player.roleInfo().getSoldierData().decrementSoldier(next.getSoldierId(), next.getNum(), SoldierChangeType.INJURE);
			player.roleInfo().getSoldierData().addSoldier(next.getSoldierId(), next.getNum(), SoldierChangeType.COMMON);
		}
		
		FightPowerKit.CAMP_POWER.math(player,GameLogSource.MODIFY_SOLDIER);
		InjectorUtil.getInjector().dbCacheService.update(player);
		updataClient(player);
//		send(player);
	}
	
	
	public void send(Player player) {
//		ResModSoldierMessage message = new ResModSoldierMessage();
//		Iterator<Soldier> iterator = player.getSoldierManager().getSoldierData().soldiers.values().iterator();
//		while (iterator.hasNext()) {
//			Soldier soldier = (Soldier) iterator.next();
//			if(soldier.getHurtNum()>0) {
//				ModSoldierBean mBean = new ModSoldierBean();
//				mBean.soldierId = soldier.getSoldierId();
//				mBean.num = soldier.getHurtNum();
//				message.mfb.add(mBean);
//			}
//		}
//		player.send(message);
//		RefittingData refittingData = player.roleInfo().soldierData.refittingData;
//		if (refittingData.num > 0) {
//			ResRecoveringArmorMessage recoveryMsg = new ResRecoveringArmorMessage();
//			recoveryMsg.id = refittingData.sid;
//			recoveryMsg.num = refittingData.num;
//			player.send(recoveryMsg);
//		}
//		ResArmorsInfoMessage armorInfoMessage = new ResArmorsInfoMessage();
		//全部坦克修改厂信息
//		armorInfoMessage.armorBeans = new ArmorsBean();
//		rebuildArmorInfo(player,armorInfoMessage.armorBeans);
//		//全部飞机修改厂信息
//		armorInfoMessage.armorBeans2 = new ArmorsBean();
//		armorInfoMessage.armorBeans2.campType = BuildFactory.PLANE.getSid();
//		rebuildArmorInfo(armorInfoMessage.armorBeans2);
//		
//		//全部suv修改厂信息
//		armorInfoMessage.armorBeans3 = new ArmorsBean();
//		armorInfoMessage.armorBeans3.campType = BuildFactory.SUV.getSid();
//		rebuildArmorInfo(armorInfoMessage.armorBeans3);
//		player.send(armorInfoMessage);
//		player.getSoldierManager().send();
	}
	
//	/**
//	 * 构建修改场bean
//	 * @param armorBeans
//	 */
//	private void rebuildArmorInfo(Player player,ArmorsBean armorBeans){
//		Iterator<ReformSoldier> iterator1 = player.roleInfo().getSoldierData().getReformSoldierTable().values().iterator();
//		while (iterator1.hasNext()) {
//			ReformSoldier soldier = (ReformSoldier) iterator1.next();
//			ReformSoldierBean soldierBean = new ReformSoldierBean();
//			soldierBean.id = soldier.getId();
//			soldierBean.soldierId = soldier.getSolderId();
//			soldierBean.name = soldier.getName();
//			soldierBean.num = soldier.getNum();
//			soldierBean.buildIndex = soldier.getBuildIndex();
//			soldierBean.state = 0;
//			Iterator<PeiJian> iterator2 = soldier.getPeijianMap().values()
//					.iterator();
//			while (iterator2.hasNext()) {
//				PeiJian peiJian = (PeiJian) iterator2.next();
//				PeijianBean peijianBean = new PeijianBean();
//				peijianBean.location = peiJian.getLocation();
//				peijianBean.peijianId = peiJian.getSid();
//				soldierBean.peijians.add(peijianBean);
//			}
//			armorBeans.soldierBeans.add(soldierBean);
//		}
//		
//		iterator1 = player.roleInfo().getSoldierData().getReformSoldierHurtTable().values().iterator();
//		while (iterator1.hasNext()) {
//			ReformSoldier soldier = (ReformSoldier) iterator1.next();
//			ReformSoldierBean soldierBean = new ReformSoldierBean();
//			soldierBean.id = soldier.getId();
//			soldierBean.soldierId = soldier.getSolderId();
//			soldierBean.name = soldier.getName();
//			soldierBean.num = soldier.getNum();
//			soldierBean.buildIndex = soldier.getBuildIndex();
//			soldierBean.state = 1;
//			Iterator<PeiJian> iterator2 = soldier.getPeijianMap().values()
//					.iterator();
//			while (iterator2.hasNext()) {
//				PeiJian peiJian = (PeiJian) iterator2.next();
//				PeijianBean peijianBean = new PeijianBean();
//				peijianBean.location = peiJian.getLocation();
//				peijianBean.peijianId = peiJian.getSid();
//				soldierBean.peijians.add(peijianBean);
//			}
//			armorBeans.hurtBeans.add(soldierBean);
//		}
//	}

	/**
	 * 计算
	 * @param player
	 */
	private int getMaxHurtNum(Player player) {
		return PlayerAttributeManager.get().getMaxHurtNum(player);
	}
}
