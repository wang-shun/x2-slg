package com.xgame.logic.server.game.playerattribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.ziYuanDian.ZiYuanDianPir;
import com.xgame.config.ziYuanDian.ZiYuanDianPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.resource.ResourceControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.calculation.Counter314;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.entity.description.AttributeDescription;
import com.xgame.logic.server.game.playerattribute.entity.description.ResourceAttrDescription;


/**
 * 
 * @author jacky.jiang
 *
 */
@Component
public class AttributeDescrpitionManager {

	public String attr(Player player) {
		Map<Integer, List<AttributeDescription>>  attributeDescMap = new HashMap<Integer, List<AttributeDescription>>();
		for(CurrencyEnum currencyEnum : CurrencyEnum.values()) {
			if(currencyEnum == CurrencyEnum.DEFAULT) {
				continue;
			}
		
			List<AttributeDescription> attributeDescriptions = new ArrayList<AttributeDescription>();
			
			AttributesEnum attr = null;
			if(currencyEnum == CurrencyEnum.OIL) {
				attr = AttributesEnum.OIL_GATHER_RATE;
			} else if(currencyEnum == CurrencyEnum.GLOD) {
				attr = AttributesEnum.CASH_GATHER_RATE;
			} else if(currencyEnum == CurrencyEnum.RARE) {
				attr = AttributesEnum.EARTH_GATHER_RATE;
			} else if(currencyEnum == CurrencyEnum.STEEL) {
				attr = AttributesEnum.STEEL_GATHER_RATE;
			}
			
			attributeDescMap.put(attr.getId(), attributeDescriptions);
			
			for (int i = 1; i <= 6; i++) {
				double speed = player.getPlayerAttributeManager().getExplorerSpeed(player, currencyEnum, player.getAllianceId(), i);
				ZiYuanDianPir configModel = ZiYuanDianPirFactory.getInstance().getFactory0().get(currencyEnum).get(i);
				double initSpeed = configModel.getValue2();
				ResourceAttrDescription resourceAttrDescription = new ResourceAttrDescription();
				resourceAttrDescription.setAttrId(attr.getId());
				resourceAttrDescription.setResourceType(currencyEnum.ordinal());
				resourceAttrDescription.setLevel(i);
				resourceAttrDescription.setValue(speed - initSpeed);
				attributeDescriptions.add(resourceAttrDescription);
			}
		}
		
		for(CurrencyEnum currencyEnum : CurrencyEnum.values()) {
			if(currencyEnum == CurrencyEnum.DEFAULT) {
				continue;
			}
			
			List<AttributeDescription> attributeDescriptions = new ArrayList<AttributeDescription>();

			AttributesEnum attr = null;
			ResourceControl resourceControl = null;
			if(currencyEnum == CurrencyEnum.OIL) {
				attr = AttributesEnum.OIL_SAFEGUARD_RATE;
				resourceControl = player.getCountryManager().getOilResourceControl();
			} else if(currencyEnum == CurrencyEnum.GLOD) {
				attr = AttributesEnum.CASH_SAFEGUARD_RATE;
				resourceControl = player.getCountryManager().getMoneyResourceControl();
			} else if(currencyEnum == CurrencyEnum.RARE) {
				attr = AttributesEnum.EARTH_SAFEGUARD_RATE;
				resourceControl = player.getCountryManager().getRareResourceCountrol();
			} else if(currencyEnum == CurrencyEnum.STEEL) {
				attr = AttributesEnum.STEEL_SAFEGUARD_RATE;
				resourceControl = player.getCountryManager().getSteelResourceControl();
			}
			
			attributeDescMap.put(attr.getId(), attributeDescriptions);
			
			Map<Integer, XBuild> countryBuild = resourceControl.getBuildMap();
			for(XBuild build : countryBuild.values()) {
				BuildingPir buildingPir = BuildingPirFactory.get(build.getSid());
				if (buildingPir != null) {
					Map<Integer, Integer> map = buildingPir.getV1();
					Integer c = map.get(build.getLevel());
					ResourceAttrDescription resourceAttrDescription = new ResourceAttrDescription();
					resourceAttrDescription.setAttrId(attr.getId());
					resourceAttrDescription.setResourceType(currencyEnum.ordinal());
					resourceAttrDescription.setValue(c * resourceControl.getProtect(player));
					attributeDescriptions.add(resourceAttrDescription);
				}
			}
		}
		
		// 士兵生产
		double army_monster_march_time = Counter314.getAttributeValue(player.getId(), AttributesEnum.ARMY_MONSTER_MARCH_TIME);
		AttributeDescription resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_MONSTER_MARCH_TIME.getId());
		resourceAttrDescription.setValue(army_monster_march_time);
		attributeDescMap.put(AttributesEnum.ARMY_PRODUCE_RATE.getId(), Lists.newArrayList(resourceAttrDescription));
		
		// 士兵生产
		double army_gather_march_time = Counter314.getAttributeValue(player.getId(), AttributesEnum.ARMY_GATHER_MARCH_TIME);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_GATHER_MARCH_TIME.getId());
		resourceAttrDescription.setValue(army_gather_march_time);
		attributeDescMap.put(AttributesEnum.ARMY_PRODUCE_RATE.getId(), Lists.newArrayList(resourceAttrDescription));
		
		// 士兵生产
		double army_fight_march_time = Counter314.getAttributeValue(player.getId(), AttributesEnum.ARMY_FIGHT_MARCH_TIME);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_FIGHT_MARCH_TIME.getId());
		resourceAttrDescription.setValue(army_fight_march_time);
		attributeDescMap.put(AttributesEnum.ARMY_FIGHT_MARCH_TIME.getId(), Lists.newArrayList(resourceAttrDescription));
		
		// 士兵生产
		double army_spy_march_time = Counter314.getAttributeValue(player.getId(), AttributesEnum.ARMY_SPY_MARCH_TIME);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_PRODUCE_RATE.getId());
		resourceAttrDescription.setValue(army_spy_march_time);
		attributeDescMap.put(AttributesEnum.ARMY_SPY_MARCH_TIME.getId(), Lists.newArrayList(resourceAttrDescription));
		
		// 士兵生产
		double army_spy_march_time_attack = Counter314.getAttributeValue(player.getId(), AttributesEnum.ARMY_SPY_MARCH_TIME_ATTACK);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_SPY_MARCH_TIME_ATTACK.getId());
		resourceAttrDescription.setValue(army_spy_march_time_attack);
		attributeDescMap.put(AttributesEnum.ARMY_SPY_MARCH_TIME_ATTACK.getId(), Lists.newArrayList(resourceAttrDescription));
		
		// 士兵生产
		double army_produce_value = Counter314.getAttributeValue(player.getId(), AttributesEnum.ARMY_PRODUCE_RATE);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_PRODUCE_RATE.getId());
		resourceAttrDescription.setValue(army_produce_value);
		attributeDescMap.put(AttributesEnum.ARMY_PRODUCE_RATE.getId(), Lists.newArrayList(resourceAttrDescription));
		
		// 侦查速度
		double scout_speed_value = player.getPlayerAttributeManager().scoutMarchSpeed(player.getId()) - GlobalPirFactory.getInstance().getDouble(GlobalConstant.SCOUT_SPEED);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_SPY_MARCH_SPEED.getId());
		resourceAttrDescription.setValue(scout_speed_value);
		attributeDescMap.put(AttributesEnum.ARMY_SPY_MARCH_SPEED.getId(), Lists.newArrayList(resourceAttrDescription));
		
		double building_time_value = Counter314.getAttributeValue(player.getId(), AttributesEnum.BUILDING_TIME);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.BUILDING_TIME.getId());
		resourceAttrDescription.setValue(building_time_value);
		attributeDescMap.put(AttributesEnum.BUILDING_TIME.getId(), Lists.newArrayList(resourceAttrDescription));
		
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.MOD.getTid());
		Map<Integer, Integer> v1Map = buildingPir.getV1();
		List<CountryBuild> countryList = player.roleInfo().getBaseCountry().getCountryBuildByTid(BuildFactory.MOD.getTid());
		int n = 0;
		Iterator<CountryBuild> iterator = countryList.iterator();
		while (iterator.hasNext()) {
			CountryBuild countryBuild = (CountryBuild) iterator.next();
			n += v1Map.get(countryBuild.getLevel());
		}
		double hospital_max_num = AttributeCounter.getAttributeValue(player.getId(), AttributesEnum.HOSPITAL_MAX_NUM);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.HOSPITAL_MAX_NUM.getId());
		resourceAttrDescription.setValue(n * hospital_max_num);
		attributeDescMap.put(AttributesEnum.HOSPITAL_MAX_NUM.getId(), Lists.newArrayList(resourceAttrDescription));
		
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.HOSPITAL_RECOVER_RATE.getId());
		resourceAttrDescription.setValue(AttributeCounter.getAttributeValue(player.getId(), AttributesEnum.HOSPITAL_RECOVER_RATE));
		attributeDescMap.put(AttributesEnum.HOSPITAL_RECOVER_RATE.getId(), Lists.newArrayList(resourceAttrDescription));
		
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.MATCH_ARMY_MAX_NUM.getId());
		resourceAttrDescription.setValue(player.getPlayerAttributeManager().singleMaxSetOffSoldierNum(player.getId()));
		attributeDescMap.put(AttributesEnum.MATCH_ARMY_MAX_NUM.getId(), Lists.newArrayList(resourceAttrDescription));

		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.SCIENCE_TIME.getId());
		resourceAttrDescription.setValue(AttributeCounter.getAttributeValue(player.getId(), AttributesEnum.SCIENCE_TIME));
		attributeDescMap.put(AttributesEnum.SCIENCE_TIME.getId(), Lists.newArrayList(resourceAttrDescription));
		
		List<AttributeDescription> attributeDescriptions = new ArrayList<AttributeDescription>();
		ConcurrentSkipListMap<Integer, XBuild> buildMap = player.getCountryManager().getArmyBuildControl().getBuildMap();
		if(buildMap != null && !buildMap.isEmpty()) {
			for(XBuild xBuild : buildMap.values()) {
				BuildingPir armyBuilding = BuildingPirFactory.get(BuildFactory.ARMY.getTid());
				Map<Integer, Integer> map = armyBuilding.getV1();
				int maxNum = map.get(xBuild.getLevel());
				
				resourceAttrDescription = new AttributeDescription();
				resourceAttrDescription.setAttrId(AttributesEnum.ARMORY_CAPACITY.getId());
				resourceAttrDescription.setValue(maxNum * AttributeCounter.getAttributeValue(player.getId(), AttributesEnum.ARMORY_CAPACITY));
				resourceAttrDescription.setUid(xBuild.getUid());
				resourceAttrDescription.setName(String.valueOf(xBuild.getSid()));
				attributeDescriptions.add(resourceAttrDescription);
			}
		}
		attributeDescMap.put(AttributesEnum.ARMORY_CAPACITY.getId(), attributeDescriptions);
		
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.ARMY_PRODUCE_DECREASE.getId());
		resourceAttrDescription.setValue(AttributeCounter.getAttributeValue(player.getId(), AttributesEnum.ARMY_PRODUCE_DECREASE));
		attributeDescMap.put(AttributesEnum.ARMY_PRODUCE_DECREASE.getId(), Lists.newArrayList(resourceAttrDescription));
		
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.MATCH_QUEUE.getId());
		resourceAttrDescription.setValue(PlayerAttributeManager.get().matchQueue(player.getId()));
		attributeDescMap.put(AttributesEnum.MATCH_QUEUE.getId(), Lists.newArrayList(resourceAttrDescription));
		
		CountryBuild mineFactoryBuild = player.getCountryManager().getMineBuildControl().getDefianlBuild();
		BuildingPir mineBuildingPir = BuildingPirFactory.get(BuildFactory.MINE.getTid());
		@SuppressWarnings("unchecked")
		Map<Integer, Integer> maxResourceMap = (Map<Integer, Integer>)mineBuildingPir.getV2();
		Integer maxResource =  maxResourceMap.get(mineFactoryBuild.getLevel());
		maxResource = PlayerAttributeManager.get().capacity(player.getId(), maxResource);
		resourceAttrDescription = new AttributeDescription();
		resourceAttrDescription.setAttrId(AttributesEnum.CAPACITY.getId());
		resourceAttrDescription.setValue(maxResource);
		attributeDescMap.put(AttributesEnum.CAPACITY.getId(), Lists.newArrayList(resourceAttrDescription));
		
		return JsonUtil.toJSON(attributeDescMap);
	}
}
