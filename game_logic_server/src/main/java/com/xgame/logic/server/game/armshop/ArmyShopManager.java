package com.xgame.logic.server.game.armshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.common.CampV2;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.DesignMapSequance;
import com.xgame.logic.server.game.armshop.message.ResProductSoldierMessage;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.camp.data.CampData;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;
import com.xgame.logic.server.game.country.structs.build.tach.constant.CustomWeapon;
import com.xgame.logic.server.game.country.structs.status.TimeState;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.playerattribute.fightpower.FightPowerKit;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.constant.SoldierTypeEnum;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.entity.model.SoldierChangeEventObject;
import com.xgame.logic.server.game.soldier.entity.model.SoldierProductStartEventObject;
import com.xgame.logic.server.game.soldier.message.ResUpdateSoldierMessage;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.ITimerTask;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.CampTimerTaskData;

/**
 * 兵工厂
 * @author jacky.jiang
 *
 */
@Component
public class ArmyShopManager {
	
	@Autowired
	private DesignMapSequance designMapSequance;
	
	/**
	 * 生成系统兵种图纸
	 * @param player
	 * @param uid
	 */
	public void assembly(Player player, int sid) {
		BuildingPir buildingPir = BuildingPirFactory.get(sid);
		Map<Integer, CampV2> map = buildingPir.getV2();
		for(CampV2 campV2 : map.values()) {
			if (campV2 != null) {
				GlobalPir model = GlobalPirFactory.get(campV2.getGlobalId());
				int[] arr = model.getValue();
				List<PartBean> customWeaponPJs = new ArrayList<>(arr.length);
				int xdCount = 0;
				int wwqCount = 0;
				int zwqCount = 0;
				int ctwbCount =0;
				int desingnMapType = 0;
				for (int i = 0; i < arr.length; i++) {
					PeiJianPir peiJianPir = PeiJianPirFactory.get(arr[i]);
					PartBean weaponPJ = new PartBean();
					weaponPJ.partId = arr[i];
					
					if (peiJianPir.getType5() == CustomWeapon.C_CAO1) {
						xdCount += 1;
						weaponPJ.position = xdCount;
					} else if (peiJianPir.getType5() == CustomWeapon.C_CAO3) {
						zwqCount += 1;
						weaponPJ.position = zwqCount;
					} else if (peiJianPir.getType5() == CustomWeapon.C_CAO4) {
						wwqCount += 1;
						weaponPJ.position = wwqCount;
					} else if (peiJianPir.getType5() == CustomWeapon.C_CAO7) {
						ctwbCount += 1;
						weaponPJ.position = ctwbCount;
					} else if (peiJianPir.getType5() == CustomWeapon.C_CAO0) {
						desingnMapType = peiJianPir.getType1();
					} else {
						weaponPJ.position = 1;
					}
					
					customWeaponPJs.add(weaponPJ);
				}
				
				player.getCustomWeaponManager().createDesignMap(player, "", customWeaponPJs, desingnMapType, campV2.getT(), 0, 0);
			}
		}
	}
	
	/**
	 * 解锁配件
	 * @param player
	 * @param countryBuild
	 * @return
	 */
	public DesignMap unlockDesignMap(Player player, XBuild countryBuild) {
		BuildingPir buildingPir = BuildingPirFactory.get(countryBuild.getSid());
		Map<Integer, CampV2> map = buildingPir.getV2();
		
		CampV2 campV2 = map.get(countryBuild.getLevel());
		if(campV2 != null) {
			int soldierType = player.getSoldierManager().getSoldierTypeByBuildTid(countryBuild.getSid());
			DesignMap designMap = player.roleInfo().getPlayerDesignMap().getSystemDesignMap(campV2.getT(), soldierType);
			if(designMap != null) {
				designMap.setUnlock(true);
				GameLogSource gameLogSource = null;
				if(countryBuild.getSid() == BuildFactory.TANK.getTid()) {
					gameLogSource = GameLogSource.CAMP_OUTPUT_TANK;
				} else if(countryBuild.getSid() == BuildFactory.SUV.getTid()) {
					gameLogSource = GameLogSource.CAMP_OUTPUT_SUV;
				} else if(countryBuild.getSid() == BuildFactory.PLANE.getTid()) {
					gameLogSource = GameLogSource.CAMP_OUTPUT_PLANE;
				}
				
				// 解锁配件
				for(PartBean partBean : designMap.getPartList()) {
					player.getCustomWeaponManager().unlockPeijian(player, partBean.partId, gameLogSource);
				}
				
				InjectorUtil.getInjector().dbCacheService.update(player);
				return designMap;
			}
		}
		return null;
	}

	
	/**
	 * 验证配件合法性, 方法参数自己定义
	 * 
	 * @return -1 代表验证失败 非-1 表示当前兵品质和成功
	 */
	public int verifyPeijian(Player player, int[] peijians) {
		return 0;
	}

	/**
	 * 请求生产士兵
	 * @param player
	 * @param buildUid
	 * @param soldierId
	 * @param num
	 * @param useType
	 */
	public void output(Player player, int buildUid, long soldierId, int num, int useType) {
		
		CountryBuild countryBuild = player.roleInfo().getBaseCountry().getAllBuild().get(buildUid);
		if(countryBuild  == null) {
			return;
		}

		if (countryBuild.getState() == TimeState.CREATEING.ordinal()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE1);
			return;
		}
		
		// 验证哪个建筑是否有兵没收
		CampData campData = player.roleInfo().getSoldierData().getCampData();
		if (campData != null && campData.getSoldierGives().size() > 0) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE2);
			return;
		}
		
		GameLogSource gameLogSource = null;
		TimerTaskCommand timerTaskCommand = null;
		if(countryBuild.getSid() == BuildFactory.TANK.getTid()) {
			gameLogSource = GameLogSource.CAMP_OUTPUT_TANK;
			timerTaskCommand = TimerTaskCommand.SOLDIER_TANK_OUTPUT;
		} else if(countryBuild.getSid() == BuildFactory.SUV.getTid()) {
			gameLogSource = GameLogSource.CAMP_OUTPUT_SUV;
			timerTaskCommand = TimerTaskCommand.SOLDIER_CAR_OUTPUT;
		} else if(countryBuild.getSid() == BuildFactory.PLANE.getTid()) {
			gameLogSource = GameLogSource.CAMP_OUTPUT_PLANE;
			timerTaskCommand = TimerTaskCommand.SOLDIER_PLANT_OUTPUT;
		}
		
		ITimerTask<?> timerTask = TimerTaskHolder.getTimerTask(timerTaskCommand);
		
		// 如果有队列在执行 走
		if (timerTask == null || (useType == CurrencyUtil.USE && timerTask.checkQueueCapacityMax(player))) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE4);
			return;
		}
		
		BuildingPir buildingPir = BuildingPirFactory.get(countryBuild.getSid());
		Map<Integer, Integer> map = buildingPir.getV1();
		int maxNum = map.get(countryBuild.getLevel());
		
//		maxNum = (int)AttributeEnum.ARMORY_CAPACITY.playerMath(player, maxNum);
		maxNum = PlayerAttributeManager.get().armoryCapacity(player.getId(), maxNum);
		if (maxNum < num) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE18);
			return;
		}
		
		double oil = 0;
		double money = 0;
		double steels = 0;
		double rare = 0;
		double time = 0;

		// 系统兵种图纸判定是否解锁
		DesignMap designMap = player.roleInfo().getPlayerDesignMap().getDesignMap(soldierId);
		if(designMap == null || (!designMap.isUnlock() && designMap.getBuildIndex() == 0)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE12);
			return;
		}
		
		for (int i = 0; i < designMap.getPartList().size(); i++) {
			PartBean next = designMap.getPartList().get(i);
			PeiJianPir configModel = PeiJianPirFactory.get(next.partId);
			if (configModel != null) {
				money += configModel.getCost_cash();
				oil += configModel.getCost_oil();
				rare += configModel.getCost_earth();
				steels += configModel.getCost_steel();
				time += configModel.getTime();
			} else {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE6);
				return;
			}
		}

		oil *= num;
		money *= num;
		steels *= num;
		rare *= num;
		time *= num;
		
		// 生产消耗减低
//		oil = AttributeEnum.ARMY_PRODUCE_DECREASE.playerMath(player, oil);
//		money = AttributeEnum.ARMY_PRODUCE_DECREASE.playerMath(player, money);
//		steels = AttributeEnum.ARMY_PRODUCE_DECREASE.playerMath(player, steels);
//		rare = AttributeEnum.ARMY_PRODUCE_DECREASE.playerMath(player, rare);
		oil = PlayerAttributeManager.get().armyProduceCost(player.getId(), oil);
		money = PlayerAttributeManager.get().armyProduceCost(player.getId(), money);
		steels = PlayerAttributeManager.get().armyProduceCost(player.getId(), steels);
		rare = PlayerAttributeManager.get().armyProduceCost(player.getId(), rare);
		
		if (!CurrencyUtil.decrementCurrency(player, money, steels, oil, rare, useType, time, gameLogSource)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE7);
			return;
		}
		
		EventBus.getSelf().fireEvent(new SoldierProductStartEventObject(player,designMap.getType(),num,designMap.partBeanIdList()));
		if (useType == CurrencyUtil.USE) {
			// 走时间队列
//			time = AttributeEnum.ARMY_PRODUCE_RATE.playerMath(player, time);
			time = PlayerAttributeManager.get().armyRroduceTime(player.getId(), time);
			
			timerTask.register(player, (int) time, new CampTimerTaskData(countryBuild.getSid(), countryBuild.getUid(), soldierId, num));
			InjectorUtil.getInjector().dbCacheService.update(player);
			
			ResProductSoldierMessage resProductSoldierMessage = new ResProductSoldierMessage();
			resProductSoldierMessage.buildUid = buildUid;
			resProductSoldierMessage.useType = useType;
			player.send(resProductSoldierMessage);
			
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E101_CAMP.CODE4);
		} else if (useType == CurrencyUtil.FAST_USE) {
			// 直接收取
			Soldier soldier = player.getSoldierManager().getOrCreateSoldier(player, soldierId);
			player.roleInfo().getSoldierData().addSoldier(soldierId, num, SoldierChangeType.COMMON);
			SoldierChangeEventObject sce = new SoldierChangeEventObject(player, EventTypeConst.SOLDIER_CHANGE, soldierId,  designMap.getType(), 
																		soldier.getNum(), soldier.getNum()+ num, null, null, 
																		gameLogSource, designMap.getSystemIndex(), designMap.partBeanIdList());
			sce.setLevel(designMap.getSystemIndex());
			EventBus.getSelf().fireEvent(sce);
			
			FightPowerKit.CAMP_POWER.math(player,gameLogSource);
			CurrencyUtil.send(player);
//			if(soldier.isFisrtOutput()) {
				soldier.setFisrtOutput(false);
				// 处理属性计算
//				SoldierAttributeObject soldierAttributeObject = new SoldierAttributeObject();
//				int soldierType = player.getSoldierManager().getSoldierTypeByBuildTid(countryBuild.getSid());
//				
//				soldierAttributeObject.setCampType(soldierType);
//				soldierAttributeObject.uid = soldier.getSoldierId();
//				player.getAttributeAppenderManager().addAttributeObject(campBuildControl.getAttributeNode(), soldierId, soldierAttributeObject);
//				
//				// 
//				player.getAttributeAppenderManager().rebuildObject(campBuildControl.getAttributeNode(), soldier.getSoldierId(), true);
//			}
			
			InjectorUtil.getInjector().dbCacheService.update(player);
			EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
			ResUpdateSoldierMessage resUpdateSoldierMessage = new ResUpdateSoldierMessage();
			SoldierBean soldierBean = new SoldierBean();
			soldierBean.soldierId = soldier.getSoldierId();
			soldierBean.num = soldier.getNum();
			resUpdateSoldierMessage.campSoldierList.add(soldierBean);
			player.send(resUpdateSoldierMessage);
			
			ResProductSoldierMessage resProductSoldierMessage = new ResProductSoldierMessage();
			resProductSoldierMessage.buildUid = buildUid;
			resProductSoldierMessage.soldier = new SoldierBean();
			resProductSoldierMessage.soldier.soldierId = soldierId;
			resProductSoldierMessage.soldier.num = num;
			player.send(resProductSoldierMessage);
			
			Language.SUCCESSTIP.send(player, SuccessTipEnum.E101_CAMP.CODE5);
		}
	}

	public void outputSuccess(Player player, int buildUid, long soldierId, int num) {
		CountryBuild countryBuild = player.roleInfo().getBaseCountry().getAllBuild().get(buildUid);
		if(countryBuild == null) {
			return;
		}
		
		CampData campData = player.roleInfo().getSoldierData().getCampData();
		campData.addSoldier(soldierId, num);
		sendOutputSucceed(player, soldierId);
		InjectorUtil.getInjector().dbCacheService.update(player);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E101_CAMP.CODE5);
	}

	public void sendOutputSucceed(Player player, long soldierId) {
		ResUpdateSoldierMessage campOutputMessage = new ResUpdateSoldierMessage();
		CampData campData = player.roleInfo().getSoldierData().getCampData();
		SoldierBrief soldierBrief = campData.getSoldierGives().get(soldierId);
		SoldierBean soldierBean = new SoldierBean();
		soldierBean.soldierId = soldierBrief.getSoldierId();
		soldierBean.num = soldierBrief.getNum();
		campOutputMessage.collectSoldierList.add(soldierBean);
		player.send(campOutputMessage);
	}

//	public ResCampDesitoryMessage desitorySoldier(Player player, long id,int num) {
//		
//		ResCampDesitoryMessage campOutputMessage = new ResCampDesitoryMessage();
//		ReformSoldier reformSoldier = player.roleInfo().getSoldierData().getReformSoldier(id);
//		if (reformSoldier == null || reformSoldier.getNum() <= 0) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE3.get());
//			return campOutputMessage;
//		}
//		
//		if (num <= 0 || num > soldier.getNum()) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E101_CAMP.CODE10.get());
//			return campOutputMessage;
//		}
//		
//		cancelOutput(player, soldier, num, Float.parseFloat(GlobalPirFactory.get(GlobalConstant.CANCEL_OUTPUT_RATIO).getValue()));
//		player.roleInfo().getSoldierData().decrementSoldier(soldierId, num, SoldierChangeType.COMMON);
//
//		CurrencyUtil.send(player);
//		InjectorUtil.getInjector().dbCacheService.update(player);
//		
//		// 返回更新数据
//		ResUpdateSoldierMessage resUpdateSoldierMessage = new ResUpdateSoldierMessage();
//		SoldierBean soldierBean = new SoldierBean();
//		soldierBean.soldierId = soldier.getSoldierId();
//		soldierBean.num = soldier.getNum();
//		resUpdateSoldierMessage.campSoldierList.add(soldierBean);
//		player.send(resUpdateSoldierMessage);
//		
//		Map<Long,Integer> damageSideSoldier = new HashMap<Long,Integer>();
//		damageSideSoldier.put(soldierId, num);
//		EventBus.getSelf().fireEvent(new DestroySideEventObject(player,false,damageSideSoldier));
//		Language.SUCCESSTIP.send(player, SuccessTipEnum.E101_CAMP.CODE2.get());
//		
//		return campOutputMessage;
//	}

	/**
	 * 取消生产
	 * @param player
	 * @param soldier
	 * @param num
	 * @param x
	 */
	public void cancelOutput(Player player, Soldier soldier, int num, float x) {
		double oil = 0;
		double money = 0;
		double steels = 0;
		double rare = 0;

		DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
		List<PartBean> peijians = designMap.getPartList();
		for (int i = 0; i < peijians.size(); i++) {
			PartBean next = peijians.get(i);
			PeiJianPir configModel = PeiJianPirFactory.get(next.partId);
			if (configModel != null) {
				money += configModel.getCost_cash();
				oil += configModel.getCost_oil();
				rare += configModel.getCost_earth();
				steels += configModel.getCost_steel();
			}
		}
		oil = oil * x * num;
		money = money * x * num;
		steels = steels * x * num;
		rare = rare * x * num;
		
		// 资源扣除失败
		GameLogSource gameLogSource = null;
		if(designMap.getType() == SoldierTypeEnum.SUV.getCode()) {
			gameLogSource = GameLogSource.CANCEL_CAMP_OUTPUT_SUV;
		} else if(designMap.getType() == SoldierTypeEnum.TANK.getCode()) {
			gameLogSource = GameLogSource.CANCEL_CAMP_OUTPUT_TANK;
		} else if(designMap.getType() == SoldierTypeEnum.PLAIN.getCode()) {
			gameLogSource = GameLogSource.CANCEL_CAMP_OUTPUT_PLANE;
		}
		
		CurrencyUtil.increaseCurrency(player, money, steels, oil, rare, gameLogSource);
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
//	public void send(Player player) {
//		ResCampInfoMessage campInfoMessage = new ResCampInfoMessage();
//		Iterator<Soldier> iterator = player.roleInfo().getSoldierData().getSoldiers().values().iterator();
//		while (iterator.hasNext()) {
//			Soldier soldier = (Soldier) iterator.next();
//			if(soldier.getCampType()==sid){
//				SoldierBean soldierBean = new SoldierBean();
//				soldierBean.soldierId = soldier.getSoldierId();
//				soldierBean.name = soldier.getName();
//				soldierBean.num = soldier.getNum();
//				soldierBean.buildIndex = soldier.getBuildIndex();
//				soldierBean.campType = soldier.getCampType();
//				List<PeiJian> peijians = soldier.getPeijianList();
//				for (int i = 0; i < peijians.size(); i++) {
//					PeiJian peiJian = peijians.get(i);
//					PeijianBean peijianBean = new PeijianBean();
//					peijianBean.location = peiJian.getLocation();
//					peijianBean.peijianId = peiJian.getSid();
//					soldierBean.peijians.add(peijianBean);
//				}
//				campInfoMessage.soldierBeans.add(soldierBean);
//			}
//		}
//		
//		Map<Integer, CampData> innerMap = player.getSoldierManager().getSoldierData().getCampDatas().get(sid);
//		if(innerMap != null) {
//			Iterator<CampData> iterator2 = innerMap.values().iterator();
//			while (iterator2.hasNext()) {
//				CampData campData = iterator2.next();
//				CampDataPro campDataPro = new CampDataPro();
//				campInfoMessage.campDatas.add(campDataPro);
//				campDataPro.buildUid = campData.getBuildUid();
//				Iterator<SoldierBrief> iterator3 = campData.getSoldierGives().values().iterator();
//				while (iterator3.hasNext()) { 
//					SoldierBrief soldierBrief = (SoldierBrief) iterator3.next();
//					campDataPro.waitGiveSoldierIds.add(soldierBrief.getSoldierId());
//				}
//			}	
//		}
//		
//		player.send(campInfoMessage);
//	}

	/**
	 * 士兵类型
	 * @param type1
	 * @return
	 */
	public static int changeBuildId(int type1) {
		switch (type1) {
		case 1:
			return BuildFactory.TANK.getTid();
		case 2:
			return BuildFactory.SUV.getTid();
		case 3:
			return BuildFactory.PLANE.getTid();
		default:
			break;
		}
		return type1;
	}
	
}
