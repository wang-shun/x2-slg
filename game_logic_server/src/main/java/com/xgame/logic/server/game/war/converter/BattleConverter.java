package com.xgame.logic.server.game.war.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.logic.server.core.utils.geometry.data.transform.BuildTransform;
import com.xgame.logic.server.game.country.CountryMsgGener;
import com.xgame.logic.server.game.country.bean.BuildBean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.constant.WarAttrConverterTypeEnum;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.logic.server.game.soldier.bean.PeijianBean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.converter.SoldierConverter;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.DefendSoldierBean;
import com.xgame.logic.server.game.war.bean.WarAttr;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;

/**
 * 战场转换器
 * @author jacky.jiang
 *
 */
public class BattleConverter {

//	/**
//	 * soldier转换器
//	 * @param soldier
//	 * @param player
//	 * @return
//	 */
//	public static WarSoldierBean valueOf(Soldier soldier, Player player) {
//		if(soldier == null) {
//			return null;
//		}
//		
//		WarSoldierBean warSoldierBean = new WarSoldierBean();
//		warSoldierBean.soldier = SoldierConverter.converterFullSoldierBean(player, soldier, soldier.getNum());
//		warSoldierBean.index = soldier.getIndex();
//		warSoldierBean.position = soldier.getVector();
//		warSoldierBean.playerId = player.getId();
//		warSoldierBean.deadNum = soldier.getDeadNum();
//		
//		//计算兵种属性
//		SoldierAttributeObject soldierAttributeObject = soldier.getAttributeObject(player);
//		warSoldierBean.warAttr = converterWarAttr(soldierAttributeObject);
//		return warSoldierBean;
//	}
	
	/**
	 * soldier转换器
	 * @param soldier
	 * @param player
	 * @return
	 */
	public static WarSoldierBean valueOfNpcWarSoldier(Soldier soldier) {
		if(soldier == null) {
			return null;
		}
		
		WarSoldierBean warSoldierBean = new WarSoldierBean();
		warSoldierBean.soldier = SoldierConverter.converterNpcSoldierBean(soldier);
		warSoldierBean.index = soldier.getIndex();
		warSoldierBean.position = soldier.getVector();
		warSoldierBean.deadNum = soldier.getDeadNum();
		
		//计算兵种属性
		warSoldierBean.warAttr = soldier.getWarAttr();
		return warSoldierBean;
	}
	
	public static List<WarSoldierBean> converterWarSoldierList(List<Soldier> soldiers,  Player player) {
		List<WarSoldierBean> warSoldierBeans = new ArrayList<>();
		for(Soldier soldier : soldiers) {
			warSoldierBeans.add(valueOf(player, soldier));
		}
		return warSoldierBeans;
	}
	
	public static WarSoldierBean valueOf(Player player, Soldier soldier, int index, int num, Vector2Bean vector2Bean) {
		if(soldier == null) {
			return null;
		}
		
		WarSoldierBean warSoldierBean = new WarSoldierBean();
		warSoldierBean.soldier = SoldierConverter.converterFullSoldierBean(player, soldier, num);
		warSoldierBean.index = index;
		warSoldierBean.position = vector2Bean;
		
		//计算兵种属性
//		AttributeObject attributeObject = soldier.getAttributeObject(player);
		warSoldierBean.warAttr = converterWarAttr(player.getId(),soldier.getSoldierId(),WarAttrConverterTypeEnum.SOLDIER);
		return warSoldierBean;
	}
	
	public static WarSoldierBean valueOf(Player player, Soldier soldier) {
		if(soldier == null) {
			return null;
		}
		
		WarSoldierBean warSoldierBean = new WarSoldierBean();
		FullSoldierBean fullSoldierBean = new FullSoldierBean();
		DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
		fullSoldierBean.designMap = CustomWeaponConverter.converterDesignMapBean(designMap);
		fullSoldierBean.soldier = new SoldierBean();
		fullSoldierBean.soldier.soldierId = soldier.getSoldierId();
		fullSoldierBean.soldier.num = soldier.getNum();
		warSoldierBean.soldier = fullSoldierBean;
		warSoldierBean.index = soldier.getIndex();
		warSoldierBean.position = soldier.getVector();
		warSoldierBean.playerId = player.getId();
		
		//计算兵种属性
//		AttributeObject attributeObject = soldier.getAttributeObject(player);
		warSoldierBean.warAttr = converterWarAttr(player.getId(),soldier.getSoldierId(),WarAttrConverterTypeEnum.SOLDIER);
		return warSoldierBean;
	}
	
	/**
	 * 获取建筑
	 * @param countryBuild
	 * @param player
	 * @return
	 */
	public static WarBuilding valueOf(CountryBuild countryBuild, Player player) {
		WarBuilding warBuilding = new WarBuilding();
		BuildBean buildBean = countryBuild.toBuildBean(player);
		warBuilding.building = buildBean;
		
		//　属性计算
//		AttributeObject attributeObject = player.roleInfo().getGameAttribute().attributeObjectInfo(AttributeNodeEnum.BUILD.ordinal(), countryBuild.getUid());
		warBuilding.warAttr = converterWarAttr(player.getId(),countryBuild.getUid(),WarAttrConverterTypeEnum.BUILD);
		
		// 坐标系
		int templateId = player.roleInfo().getBaseCountry().getUseTemplateId();
		BuildTransform buildTransform = player.roleInfo().getBaseCountry().getBuildTransform(templateId, countryBuild.getUid());
		if(buildTransform != null) {
			warBuilding.transform = CountryMsgGener.toTransformBean(buildTransform);
		}
		return warBuilding;
	}
	
//	/**
//	 * 克隆soldierBean
//	 * @param soldier
//	 * @return
//	 */
//	public static WarSoldierBean valueOf(WarSoldierBean soldier) {
//		WarSoldierBean clone = new WarSoldierBean();
//		clone.index = soldier.index;
//		FullSoldierBean fullSoldierBean = new FullSoldierBean();
//		fullSoldierBean.soldier = new SoldierBean();
//		fullSoldierBean.soldier.num = soldier.soldier.soldier.num;
//		fullSoldierBean.soldier.soldierId = soldier.soldier.soldier.soldierId;
//		clone.warAttr = soldier.warAttr;
//		clone.playerId = soldier.playerId;
//		return soldier;
//	}
	
//	/**
//	 * 防守士兵
//	 * @param soldier
//	 * @return
//	 */
//	public static DefendSoldierBean valueOf(DefendSoldierBean soldier) {
//		DefendSoldierBean clone = new DefendSoldierBean();
//		clone.buildingUid = soldier.buildingUid;
//		clone.soldier = valueOf(soldier.soldier);
//		return clone;
//	}
	
	public static DefendSoldierBean valueOfDefenSoldier(WarSoldierBean warSoldierBean) {
		DefendSoldierBean defendSoldier = new DefendSoldierBean();
		defendSoldier.soldier = warSoldierBean;
		defendSoldier.buildingUid = 0;
		return defendSoldier;
	}
	
	public static List<DefendSoldierBean> valueOfDefenSoldier(List<WarSoldierBean> warSoldierBeans) {
		List<DefendSoldierBean> defendSoldierList = new ArrayList<DefendSoldierBean>();
		for(WarSoldierBean warSoldierBean : warSoldierBeans) {
			defendSoldierList.add(valueOfDefenSoldier(warSoldierBean));
		}
		return defendSoldierList;
	}
	
	/**
	 * 获取战斗单元
	 * @param defenSoldierBeans
	 * @return
	 */
	public static List<WarSoldierBean> getWarSoldierList(List<DefendSoldierBean> defenSoldierBeans) {
		List<WarSoldierBean> warSoldierBeans = new ArrayList<WarSoldierBean>();
		for(DefendSoldierBean defenSoldierBean : defenSoldierBeans) {
			warSoldierBeans.add(defenSoldierBean.soldier);
		}
		return warSoldierBeans;
	}
	
	public static List<PeijianBean> converterPeijianBean(List<PartBean> peiJians) {
		List<PeijianBean> peijianList = new ArrayList<>();
		for(PartBean peiJian : peiJians) {
			PeijianBean peijianBean = new PeijianBean();
			peijianBean.peijianId = peiJian.partId;
			peijianBean.location = peiJian.position;
			peijianList.add(peijianBean);
		}
		return  peijianList;
	}

	/**
	 * 转换城warSoldierBean
	 * @param worldMarchSoldier
	 * @return
	 */
	public static List<WarSoldierBean> converterWarSoldierBean(Player player, WorldMarchSoldier worldMarchSoldier) {
		List<WarSoldierBean> resultList = new ArrayList<WarSoldierBean>();
		List<Soldier> soldiers = worldMarchSoldier.querySoldierList();
		for(Soldier Soldier : soldiers) {
			WarSoldierBean warSoldierBean = BattleConverter.valueOf(player, Soldier);
			resultList.add(warSoldierBean);
		}
		return resultList;
	}
	
	/**
	 * 转换士兵
	 * @param worldMarchSoldier
	 * @return
	 */
	public static List<DefendSoldierBean> converterDefendSolderBean(Player player, WorldMarchSoldier worldMarchSoldier) {
		List<DefendSoldierBean> resultList = new ArrayList<DefendSoldierBean>();
		List<Soldier> soldiers = worldMarchSoldier.querySoldierList();
		if(soldiers != null && !soldiers.isEmpty()) {
			for(Soldier soldier : soldiers) {
				WarSoldierBean warSoldierBean = BattleConverter.valueOf(player, soldier);
				resultList.add(valueOfDefenSoldier(warSoldierBean));
			}
		}
		return resultList;
	}
	
	/**
	 * 防守驻军转换器
	 * @param player
	 * @param soldier
	 * @return
	 */
	public static DefendSoldierBean converterDefendSolderBean(Player player, Soldier soldier) {
		DefendSoldierBean resultList = new DefendSoldierBean();
		resultList.soldier = valueOf(player, soldier);
		resultList.buildingUid = soldier.getBuildUid();
		return resultList;
	}
	
	/**
	 * 转换npc
	 * @param soldier
	 * @param partBeans
	 * @return
	 */
	public static DefendSoldierBean converterNpcSoldierBean(Soldier soldier) {
		DefendSoldierBean resultList = new DefendSoldierBean();
		resultList.soldier = valueOfNpcWarSoldier(soldier);
		resultList.buildingUid = soldier.getBuildUid();
		return resultList;
	}
	
	public static WarAttr converterWarAttr(Map<AttributesEnum,Double> attrMap){
		return converterWarAttr(attrMap,1);
	}
	
	public static WarAttr converterWarAttr(Map<AttributesEnum,Double> attrMap,double rate){
		WarAttr warAttr = new WarAttr();
		if(attrMap == null){
			return null;
		}
		for(Entry<AttributesEnum,Double> entry : attrMap.entrySet()){
			double value = entry.getValue() * rate;
			switch(entry.getKey()){
				case ATTACK:
					warAttr.attack = (int)value;
				break;
				case CRIT:
					warAttr.crit = value;
					break;
				case CRITICAL:
					warAttr.critical = value;
					break;
				case DEFENSE:
					warAttr.defend = (int)value;
					break;
				case DODGE:
					warAttr.dodge = value;
					break;
				case ELECTRICITY_DAMAGE:
					warAttr.electricityDamage = value;
					break;
				case ELECTRICITY_DEFENSE:
					warAttr.electricityDefense = value;
					break;
				case ENERGY_DAMAGE:
					warAttr.energyDamage = value;
					break;
				case ENERGY_DEFENSE:
					warAttr.energyDefense = value;
					break;
				case HEAT_DAMAGE:
					warAttr.heatDamage = value;
					break;
				case HEAT_DEFENSE:
					warAttr.heatDefense = value;
					break;
				case HIT:
					warAttr.hit = value;
					break;
				case HP:
					warAttr.hp = (int)value;
					break;
				case LASER_DAMAGE:
					warAttr.laserDamage = value;
					break;
				case LASER_DEFENSE:
					warAttr.laserDefense = value;
					break;
				case LOAD:
					warAttr.load = value;
					break;
				case LOAD_CONSUME:
					warAttr.loadConsume = value;
					break;
				case POWER:
					warAttr.power = value;
					break;
				case POWER_CONSUME:
					warAttr.powerConsume = value;
					break;
				case RADAR_INTENSITY:
					warAttr.radarIntensity = value;
					break;
				case RADIUS:
					warAttr.radius = value;
					break;
				case SEEKING_NUM:
					warAttr.seekingNum = value;
					break;
				case SPEED_BASE:
					warAttr.speedBase = value;
					break;
				case TOUGHNESS:
					warAttr.toughness = value;
					break;
				case WEIGHT:
					warAttr.weight = value;
					break;
				default:
					break;
			}
		}
		return warAttr;
	}
	
	/**
	 * 转换属性
	 * @param playerId
	 * @param uuid
	 * @param warAttrConverterTypeEnum
	 * @return
	 */
	public static WarAttr converterWarAttr(long playerId,long uuid,WarAttrConverterTypeEnum warAttrConverterTypeEnum) {
		WarAttr warAttr = new WarAttr();
		Map<AttributesEnum,Double> attrMap = new HashMap<>();
		switch(warAttrConverterTypeEnum){
			case BUILD:
				attrMap = AttributeCounter.getBuildAttribute(playerId, (int)uuid);
				break;
			case SOLDIER:
				attrMap = AttributeCounter.getSoldierAttribute(playerId, uuid);
			break;
		}
		warAttr = converterWarAttr(attrMap);
		return warAttr;
	}
	
	
	/**
	 * 属性钻换
	 * @param attrConfMap
	 * @return
	 */
	public static WarAttr converterAttr(Map<Integer, Double> attrMap) { 
		WarAttr warAttr = new WarAttr();
//		if(attrMap.get(AttributeEnum.DAMAGE.getId()) != null) {
//			warAttr.attack =  Double.valueOf(attrMap.get(AttributeEnum.DAMAGE.getId())).intValue();	
//		}
//		
//		if(attrMap.get(AttributeEnum.CRIT.getId()) != null) {
//			warAttr.crit = Double.valueOf(attrMap.get(AttributeEnum.CRIT.getId())).intValue();
//		}
//		
//		if(attrMap.get(AttributeEnum.CRITICAL.getId()) != null) {
//			warAttr.critical = Double.valueOf(attrMap.get(AttributeEnum.CRITICAL.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.DEFENSE.getId()) != null) {
//			warAttr.defend = Double.valueOf(attrMap.get(AttributeEnum.DEFENSE.getId())).intValue();
//		}
//		
//		if(attrMap.get(AttributeEnum.DODGE.getId()) != null) {
//			warAttr.dodge = Double.valueOf(attrMap.get(AttributeEnum.DODGE.getId())).intValue();
//		}
//		
//		if(attrMap.get(AttributeEnum.ELECTRICITY_DAMAGE.getId()) != null) {
//			warAttr.electricityDamage = Double.valueOf(attrMap.get(AttributeEnum.ELECTRICITY_DAMAGE.getId()));
//		}
//	
//		if(attrMap.get(AttributeEnum.ELECTRICITY_DEFENSE.getId()) != null) {
//			warAttr.electricityDefense = Double.valueOf(attrMap.get(AttributeEnum.ELECTRICITY_DEFENSE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.ENERGY_DAMAGE.getId()) != null) {
//			warAttr.energyDamage = Double.valueOf(attrMap.get(AttributeEnum.ENERGY_DAMAGE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.ENERGY_DEFENSE.getId()) != null) {
//			warAttr.energyDefense = Double.valueOf(attrMap.get(AttributeEnum.ENERGY_DEFENSE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.HEAT_DAMAGE.getId()) != null) {
//			warAttr.heatDamage = Double.valueOf(attrMap.get(AttributeEnum.HEAT_DAMAGE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.HEAT_DEFENSE.getId()) != null) {
//			warAttr.heatDefense = Double.valueOf(attrMap.get(AttributeEnum.HEAT_DEFENSE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.HIT.getId()) != null) {
//			warAttr.hit = Double.valueOf(attrMap.get(AttributeEnum.HIT.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.HP.getId()) != null) {
//			warAttr.hp = Double.valueOf(attrMap.get(AttributeEnum.HP.getId())).intValue();
//		}
//		
//		if(attrMap.get(AttributeEnum.LASER_DAMAGE.getId()) != null) {
//			warAttr.laserDamage = Double.valueOf(attrMap.get(AttributeEnum.LASER_DAMAGE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.LASER_DEFENSE.getId()) != null) {
//			warAttr.laserDefense = Double.valueOf(attrMap.get(AttributeEnum.LASER_DEFENSE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.LOAD.getId()) != null) {
//			warAttr.load = Double.valueOf(attrMap.get(AttributeEnum.LOAD.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.LOAD_CONSUME.getId()) != null) {
//			warAttr.loadConsume = Double.valueOf(attrMap.get(AttributeEnum.LOAD_CONSUME.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.POWER.getId()) != null) {
//			warAttr.power = Double.valueOf(attrMap.get(AttributeEnum.POWER.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.POWER_CONSUME.getId()) != null) {
//			warAttr.powerConsume = Double.valueOf(attrMap.get(AttributeEnum.POWER_CONSUME.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.RADAR_INTENSITY.getId()) != null) {
//			warAttr.radarIntensity = Double.valueOf(attrMap.get(AttributeEnum.RADAR_INTENSITY.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.RADIUS.getId()) != null) {
//			warAttr.radius = Double.valueOf(attrMap.get(AttributeEnum.RADIUS.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.SEEKING_NUM.getId()) != null) {
//			warAttr.seekingNum = Double.valueOf(attrMap.get(AttributeEnum.SEEKING_NUM.getId()));
//		}
//	
//		if(attrMap.get(AttributeEnum.SPEED_BASE.getId()) != null) {
//			warAttr.speedBase = Double.valueOf(attrMap.get(AttributeEnum.SPEED_BASE.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.TOUGHNESS.getId()) != null) {
//			warAttr.toughness = Double.valueOf(attrMap.get(AttributeEnum.TOUGHNESS.getId()));
//		}
//		
//		if(attrMap.get(AttributeEnum.WEIGHT.getId()) != null) {
//			warAttr.weight = Double.valueOf(attrMap.get(AttributeEnum.WEIGHT.getId()));
//		}
//		
		return warAttr;
	}
}
