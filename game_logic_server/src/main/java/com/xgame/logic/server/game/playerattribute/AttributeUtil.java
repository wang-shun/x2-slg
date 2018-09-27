package com.xgame.logic.server.game.playerattribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.LibraryConf;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.library.LibraryPir;
import com.xgame.config.library.LibraryPirFactory;
import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteAppenderBean;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteAppenderListBean;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteProBean;
import com.xgame.logic.server.game.playerattribute.bean.AttributeNodeBean;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributeConstant;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.entity.AttributeAdd;
import com.xgame.logic.server.game.playerattribute.entity.PlayerAttribute;
import com.xgame.logic.server.game.playerattribute.message.ResAttrbutesAddFromMessage;
import com.xgame.logic.server.game.playerattribute.message.ResAttrbutesAddMessage;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.world.constant.MarchType;

public class AttributeUtil {
	
	
	/**
	 * building 配置表v1标识属性
	 * @param sId
	 * @return
	 */
	public static boolean isAttrOfBuildingV1(int sId){
		return Arrays.binarySearch(AttributeConstant.BUILD_ATTR_V1_IDS, sId) >= 0;
	}
	
	/**
	 * building 配置表v2标识属性
	 * @param sId
	 * @return
	 */
	public static boolean isAttrOfBuildingV2(int sId){
		return Arrays.binarySearch(AttributeConstant.BUILD_ATTR_V2_IDS, sId) >= 0;
	}
	
	/**
	 * 组装日志
	 * @param valueOfNodeMap
	 * @param attrId
	 * @param value
	 * @return
	 */
	public static String logBuilder(Map<AttributeNodeEnum,Double> valueOfNodeMap,int attrId,double value,String desc){
		if(valueOfNodeMap.size() > 0){
			StringBuffer logStr = new StringBuffer();
			logStr.append("========================").append(desc).append("表 属性ID:").append(attrId).append("属性加成值:");
			for(Entry<AttributeNodeEnum,Double> entry : valueOfNodeMap.entrySet()){
				if(entry.getValue() > 0){
					logStr.append("node=").append(entry.getKey()).append(";value:").append(entry.getValue()).append(";");
				}
			}
			logStr.append("总值:").append(value).append("=======================");
			return logStr.toString();
		}
		return "";
	}
	
	/**
	 * 计算属性加成
	 * @param attributeConfMap
	 * @param level
	 * @param node
	 * @param attrId
	 * @return
	 */
	public static double attributeAddCounter(AttributeConfMap attributeConfMap,int level,int node,int attrId){
		double value = 0;
		if(attributeConfMap == null){
			return value;
		}
		
		LibraryConf libraryConf = attributeConfMap.getLibraryConfs().get(level, node);
		if(libraryConf != null){
			if(libraryConf.containsKey(attrId)){
				value += libraryConf.get(attrId);
			}
		}
		return value;
	}
	
	/**
	 * 属性转换map
	 * @param attributeConfMap
	 * @param level
	 * @param attributeNodeEnum
	 * @return
	 */
	public static Map<AttributesEnum,Double> converAttrMap(AttributeConfMap attributeConfMap,int level,AttributeNodeEnum attributeNodeEnum){
		Map<AttributesEnum,Double> attrMap = new HashMap<>();
		if(attributeConfMap == null){
			return attrMap;
		}
		for(AttributesEnum attributesEnum : AttributesEnum.values()){
			LibraryConf libraryConf = attributeConfMap.getLibraryConfs().get(level, attributeNodeEnum.getCode());
			if(libraryConf != null){
				if(libraryConf.containsKey(attributesEnum.getId())){
					attrMap.put(attributesEnum, libraryConf.get(attributesEnum.getId()));
				}
			}
		}
		
		return attrMap;
	}
	
	/**
	 * 配件自身属性
	 * @param playerId
	 * @param soldierId
	 * @param attributeEnum
	 * @return
	 */
	public static double peijianSelfAttribute(long playerId,long soldierId, AttributesEnum attributeEnum) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
		if(player == null){
			return 0;
		}
		Soldier soldier = player.getSoldierManager().getSoldier(player,soldierId);
		if(soldier == null){
			return 0;
		}
		double value = 0;
		DesignMap designMap = player.roleInfo().getPlayerDesignMap().getDesignMap(soldierId);
		Iterator<PartBean> iterator = designMap.getPartList().iterator();
		while (iterator.hasNext()) {
			PartBean peiJian = (PartBean) iterator.next();
			PeiJianPir configModel = PeiJianPirFactory.get(peiJian.partId);
			if(configModel!=null){
				AttributeConfMap attributeConfMap = configModel.getAttr();
				value += attributeAddCounter(attributeConfMap,1,AttributeNodeEnum.PEIJIAN.getCode(),attributeEnum.getId());
			}
		}
		
		return value;
	}
	
	/**
	 * 建筑自身属性
	 * @param playerId
	 * @param buildId
	 * @param attributeEnum
	 * @return
	 */
	public static double buildSelfAttribute(long playerId,int buildId,AttributesEnum attributeEnum) {
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
		CountryBuild build = player.roleInfo().getBaseCountry().getAllBuild().get(buildId);
		BuildingPir buildingPir = BuildingPirFactory.get(build.getSid());
		AttributeConfMap attributeConfMapOfAttr = buildingPir.getAttr();
		double value = AttributeUtil.attributeAddCounter(attributeConfMapOfAttr, build.getLevel(),AttributeNodeEnum.BUILD.getCode(), attributeEnum.getId());
		return value;
	}
	
	/**
	 * 判断属性是否有更新
	 * @param playerAttribute1
	 * @param playerAttribute2
	 * @return
	 */
	public static boolean hasUpdate(PlayerAttribute playerAttribute1,PlayerAttribute playerAttribute2){
		if(playerAttribute1.getAtttibuteMap().size() != playerAttribute2.getAtttibuteMap().size()){
			return true;
		}
		for(AttributesEnum attrEnum : AttributesEnum.values()){
			AttributeAdd attributeAdd1 = playerAttribute1.getAtttibuteMap().get(attrEnum.getId());
			AttributeAdd attributeAdd2 = playerAttribute2.getAtttibuteMap().get(attrEnum.getId());
			if((attributeAdd1 == null && attributeAdd2 != null) || (attributeAdd1 != null && attributeAdd2 == null)){
				return true;
			}
			if(attributeAdd1 != null && attributeAdd2 != null){
				if(attributeAdd1.getAttrMap().size() != attributeAdd2.getAttrMap().size()){
					return true;
				}
				for(AttributeNodeEnum nodeEnum : AttributeNodeEnum.values()){
					Double value1 = attributeAdd1.getAttrMap().get(nodeEnum);
					Double value2 = attributeAdd2.getAttrMap().get(nodeEnum);
					if((value1 == null && value2 != null) || (value1 != null && value2 == null)){
						return true;
					}
					if(value1 != null && value2 != null){
						if(value1.doubleValue() != value2.doubleValue()){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 资源仓库保护比例属性
	 * @param currencyId
	 * @return
	 */
	public static AttributesEnum getResourceSaveRateAttr(int currencyId){
		if(currencyId == CurrencyEnum.GLOD.ordinal()){
			return AttributesEnum.CASH_SAFEGUARD_RATE;
		}else if(currencyId == CurrencyEnum.RARE.ordinal()){
			return AttributesEnum.EARTH_SAFEGUARD_RATE;
		}else if(currencyId == CurrencyEnum.OIL.ordinal()){
			return AttributesEnum.OIL_SAFEGUARD_RATE;
		}else if(currencyId == CurrencyEnum.STEEL.ordinal()){
			return AttributesEnum.STEEL_SAFEGUARD_RATE;
		}
		return null;
	}
	
	/**
	 * 行军时间减少属性
	 * @param marchType
	 * @return
	 */
	public static AttributesEnum getMarchTimeAttr(MarchType marchType){
		switch(marchType){
		case EXPLORER:
			return AttributesEnum.ARMY_GATHER_MARCH_TIME;
		case CAMP://扎营
		case TERRITORY://占领
		case CITY_FIGHT://攻打玩家
			return AttributesEnum.ARMY_FIGHT_MARCH_TIME;
		case TRADE://交易
			return AttributesEnum.ARMY_MONSTER_MARCH_TIME;
		case SCOUT://侦查
			return AttributesEnum.ARMY_SPY_MARCH_TIME;
		case  MARCH_REINFORCE://集结防御
		case TEAM_ATTACK://集结进攻
			return AttributesEnum.ARMY_SPY_MARCH_TIME_ATTACK;
		default:
			break;
		}
		//TODO 
		return null;
	}
	
	public static String log(ResAttrbutesAddFromMessage info){
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for(AttrbuteAppenderListBean aalb : info.list){
			sb.append("节点：").append(aalb.nodeId).append(" ; ").append("属性ID：").append(aalb.attributeId).append("来自模块：[");
			for(AttrbuteAppenderBean bean : aalb.list){
				sb.append("模块ID:").append(bean.appenderId).append(" ; ").append("属性值:").append(bean.value);
			}
			sb.append("]\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	public static String log(ResAttrbutesAddMessage info){
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for(AttributeNodeBean nodeBean : info.attributeNodes){
			sb.append("节点：").append(nodeBean.nodeId).append("属性：[");
			for(AttrbuteProBean attr : nodeBean.attributes){
				sb.append("属性ID:").append(attr.attributeId).append(" ; ").append("属性值：").append(attr.value).append("|");
			}
			sb.append("]\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * 属性变化日志
	 * @param playerAttribute1
	 * @param playerAttribute2
	 * @return
	 */
	public static String log(Player player,PlayerAttribute playerAttribute1,PlayerAttribute playerAttribute2){
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("玩家：").append(player.getName()).append(" 属性变化:");
		for(AttributeAdd add2 : playerAttribute2.getAtttibuteMap().values()){
			AttributeAdd add1 = playerAttribute1.getAtttibuteMap().get(add2.getAtttibuteId());
			if(add2.getAttrMap().size() > 0){
				if(add1 == null){
					sb.append("\n");
					sb.append("属性ID:").append(add2.getAtttibuteId()).append(":");
					for(AttributeNodeEnum node : AttributeNodeEnum.values()){
						Double value2 = add2.getAttrMap().get(node);
						if(value2 != null){
							sb.append("节点：").append(node.getCode())
							.append("[").append("原值：").append(0).append(",")
							.append("新值：").append(value2).append("];");
						}
					}
				}else{
					StringBuffer sb0 = new StringBuffer();
					for(AttributeNodeEnum node : AttributeNodeEnum.values()){
						Double value1 = add1.getAttrMap().get(node);
						Double value2 = add2.getAttrMap().get(node);
						if(value2 != null){
							if(value1 == null){
								sb0.append("节点：").append(node.getCode())
								.append("[").append("原值：").append(0).append(",")
								.append("新值：").append(value2).append("];");
							}else{
								if(value1.doubleValue() != value2.doubleValue()){
									sb0.append("节点：").append(node.getCode())
									.append("[").append("原值：").append(value1).append(",")
									.append("新值：").append(value2).append("];");
								}
							}
						}
					}
					if(sb0.toString().length() > 0){
						sb.append("\n");
						sb.append("属性ID:").append(add2.getAtttibuteId()).append(":").append(sb0.toString());
					}
				}
			}
		}
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * 单兵战力
	 * @param playerId
	 * @param soldierId
	 * @return
	 */
	public static long getSingleSoldierFightPower(long playerId,long soldierId){
		Map<AttributesEnum,Double> attrMap = AttributeCounter.getSoldierAttribute(playerId, soldierId);
		double fightPower = 0;
		for(Entry<AttributesEnum,Double> entry : attrMap.entrySet()){
			LibraryPir pir = LibraryPirFactory.get(entry.getKey().getId());
			fightPower += entry.getValue() * pir.getGs_para();
		}
		return (long)fightPower;
	}
	
	public static double getPlayerAttriInitValue(AttributesEnum attributesEnum){
		switch(attributesEnum){
		case CASH_SAFEGUARD_RATE:
		case EARTH_SAFEGUARD_RATE:
		case OIL_SAFEGUARD_RATE:
		case STEEL_SAFEGUARD_RATE:
			Map<Integer,Double> map = GlobalPirFactory.get(GlobalConstant.RESOURCE_BAG_SAVE_RATE).getValue();
			if(attributesEnum == AttributesEnum.CASH_SAFEGUARD_RATE){
				return map.get(CurrencyEnum.GLOD.ordinal());
			}else if(attributesEnum == AttributesEnum.EARTH_SAFEGUARD_RATE){
				return map.get(CurrencyEnum.RARE.ordinal());
			}else if(attributesEnum == AttributesEnum.OIL_SAFEGUARD_RATE){
				return map.get(CurrencyEnum.OIL.ordinal());
			}else if(attributesEnum == AttributesEnum.STEEL_SAFEGUARD_RATE){
				return map.get(CurrencyEnum.STEEL.ordinal());
			}
			break;
		case ARMY_SPY_MARCH_SPEED:
			return GlobalPirFactory.getInstance().getDouble(GlobalConstant.SCOUT_SPEED);
		case MATCH_ARMY_MAX_NUM:
			break;
		case MATCH_QUEUE:
			return GlobalPirFactory.getInstance().initQueueSize[0];
		case BUILDING_QUEUE:
			return GlobalPirFactory.getInstance().initQueueSize[1];
		case TECH_QUEUE:
			return GlobalPirFactory.getInstance().initQueueSize[2];
		case HELP_REDUCE_BUILDING_TIME:
		case GUARD_MAX_NUM:
		case CONCENTRATE_ARMY:
			return 0;
			default:
		return 0;
		}
		return 0;
	}
}
