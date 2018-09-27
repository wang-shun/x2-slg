package com.xgame.logic.server.game.playerattribute.calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.AttributeAddManager;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.IAttributeAddModule;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteAppenderBean;
import com.xgame.logic.server.game.playerattribute.constant.AttributeConstant;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.converter.AttributeConverter;
import com.xgame.logic.server.game.playerattribute.entity.AttributeAdd;
import com.xgame.logic.server.game.playerattribute.entity.PlayerAttribute;
import com.xgame.logic.server.game.soldier.entity.Soldier;

/**
 * 玩家属性计算器
 * @author zehong.he
 *
 */
@Slf4j
public abstract class AttributeCounter{
	
	/**
	 * 属性计算器
	 */
	public static final Map<AttributesEnum,AttributeCounter> counterMap = new HashMap<>();
	
	@PostConstruct
	public void init(){
		counterMap.put(getSelfAttribute(), this);
	}
	
	/**
	 * 自身属性
	 * @return
	 */
	public abstract AttributesEnum getSelfAttribute();
	
	/**
	 * 关联属性
	 * @return
	 */
	public abstract AttributesEnum getRelationAttribute();
	
	/**
	 * 获取属性加成值
	 * @param playerId
	 * @return
	 */
	public Map<AttributeNodeEnum,Double> getAddValue(Player player,boolean isSelfAttr){
		AttributesEnum addAttributesEnum;
		if(isSelfAttr){
			addAttributesEnum = getSelfAttribute();
		}else{
			addAttributesEnum = getRelationAttribute();
		}
		Map<AttributeNodeEnum,Double> attrMap = new HashMap<>();
		if(addAttributesEnum == null){
			log.error("addAttributesEnum can not null!!!");
			return attrMap;
		}
		for(IAttributeAddModule attributeAddModule : AttributeAddManager.get().getAttributeAddMap().values()){
			Map<AttributeNodeEnum,Double> attrMap0 = attributeAddModule.attributeAdd(player,addAttributesEnum);
			for(Entry<AttributeNodeEnum,Double> entry : attrMap0.entrySet()){
				if(!attrMap.containsKey(entry.getKey())){
					attrMap.put(entry.getKey(), entry.getValue());
				}else{
					attrMap.put(entry.getKey(),attrMap.get(entry.getKey()) + entry.getValue());
				}
			}
		}
		return attrMap;
	}
	
	/**
	 * 属性总值
	 * @param playerId
	 * @return
	 */
	public double getValue(Player player,AttributesEnum attributesEnum,AttributeNodeEnum node,double initAddValue){
		double value = 0;
		for(IAttributeAddModule attributeAddModule : AttributeAddManager.get().getAttributeAddMap().values()){
			Map<AttributeNodeEnum,Double> attrMap = attributeAddModule.attributeAdd(player, attributesEnum,node);
			if(attrMap.size() > 0){
				for(Double v : attrMap.values()){
					value += v;
				}
			}
		}
		return value + initAddValue;
	}
	
	/**
	 * 属性最终值
	 * @param playerId
	 * @param node
	 * @param initValue
	 * @return
	 */
	public double getFinalValue(Player player,AttributeNodeEnum node,double initValue,double initAddValue){
		double selfValue = getValue(player,getSelfAttribute(),node,initAddValue) + initValue;
		double relationValue = getValue(player,getRelationAttribute(), node, initAddValue);
		return formula1(selfValue,relationValue);
	}
	
	/**
	 * 自身属性*(1+关联属性)
	 * @param playerId
	 * @return
	 */
	public double formula1(double selfValue,double relationValue){
		return selfValue * (1 + relationValue);
	}
	
	/**
	 * 初始值*(1+自身属性)
	 * @param playerId
	 * @return
	 */
	public double formula2(double selfValue,double initValue){
		return initValue * (1 + selfValue);
	}
	
	/**
	 * 初始值+自身属性
	 * @param playerId
	 * @return
	 */
	public double formula3(double selfValue,double initValue){
		return initValue + selfValue;
	}
	
	/**
	 * 初始值*(1-自身属性)
	 * @param playerId
	 * @return
	 */
	public double formula4(double selfValue,double initValue){
		return initValue * (1 - selfValue);
	}
	
	/**
	 * 初始值/(1+自身属性)
	 * @param playerId
	 * @return
	 */
	public double formula5(double selfValue,double initValue){
		return initValue / (1 + selfValue);
	}
	
	/**
	 * 初始值/(1-自身属性)
	 * @param playerId
	 * @return
	 */
	public double formula6(double selfValue,double initValue){
		return initValue / (1 - selfValue);
	}
	
	
	
	
	
	//----------------------------------------------------------------------------------------------------
	
	/**
	 * 获取士兵属性列表
	 * @param playerId
	 * @param soldierId
	 * @param initValue
	 * @return
	 */
	public static Map<AttributesEnum,Double> getSoldierAttribute(long playerId,long soldierId){
		Map<AttributesEnum,Double> attrMap = new HashMap<>();
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
		if(player == null){
			return attrMap;
		}
		Soldier soldier = player.getSoldierManager().getSoldier(player,soldierId);
		if(soldier == null){
			return attrMap;
		}
		DesignMap designMap = player.roleInfo().getPlayerDesignMap().getDesignMap(soldierId);
		AttributeNodeEnum nodeEnum = AttributeNodeEnum.getCode(designMap.getType());
		for(AttributeCounter counter : counterMap.values()){
			if(counter.getSelfAttribute().getId() >= AttributeConstant.PLAYER_ATTRIBUTE_MINID_FLAG){
				continue;
			}
			double initValue = AttributeUtil.peijianSelfAttribute(playerId, soldierId, counter.getSelfAttribute());
			double value = counter.getFinalValue(player, nodeEnum, initValue,0);
			if(value > 0){
				attrMap.put(counter.getSelfAttribute(), value);
			}
		}
		
		return attrMap;
	}
	
	/**
	 * 获取建筑属性列表
	 * @param playerId
	 * @param buildId
	 * @return
	 */
	public static Map<AttributesEnum,Double> getBuildAttribute(long playerId,int buildId){
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
		Map<AttributesEnum,Double> attrMap = new HashMap<>();
		for(AttributeCounter counter : counterMap.values()){
			if(counter.getSelfAttribute().getId() >= AttributeConstant.PLAYER_ATTRIBUTE_MINID_FLAG){ 
				continue;
			}
			double initValue = AttributeUtil.buildSelfAttribute(playerId, buildId, counter.getSelfAttribute());
			double value = counter.getFinalValue(player, AttributeNodeEnum.BUILD, initValue,0);
			if(value > 0){
				attrMap.put(counter.getSelfAttribute(), value);
			}
		}
		return attrMap;
	}
	
	/**
	 * 获取玩家属性最终值（属性id>300）
	 * @param playerId
	 * @param attributesEnum
	 * @param initValue
	 * @return
	 */
	public static double getPlayerAttribute(long playerId,AttributesEnum attributesEnum,double initValue,double initAddValue) {
		AttributeCounter counter = counterMap.get(attributesEnum);
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
		return counter.getFinalValue(player, AttributeNodeEnum.PLAYER, initValue,initAddValue);
	}
	
	/**
	 * 获取玩家属性最终值（属性id>300）
	 * @param playerId
	 * @param attributesEnum
	 * @param initValue
	 * @return
	 */
	public static double getPlayerAttribute(long playerId,AttributesEnum attributesEnum,double initValue){
		return getPlayerAttribute(playerId, attributesEnum, initValue,0);
	}
	
	/**
	 * 获取玩家属性值（属性id>300）
	 * @param playerId
	 * @param attributesEnum
	 * @return
	 */
	public static double getAttributeValue(long playerId,AttributesEnum attributesEnum){
		AttributeCounter counter = counterMap.get(attributesEnum);
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
		return counter.getValue(player, attributesEnum, AttributeNodeEnum.PLAYER, 0);
	}
	
	/**
	 * 查询某个属性来源与各个模块的值
	 * @param playerId
	 * @param attributeEnum
	 * @param node
	 * @return
	 */
	public static List<AttrbuteAppenderBean> selectAttributeAdd(long playerId,AttributesEnum attributeEnum,AttributeNodeEnum node){
		Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class,playerId);
		Map<AttributeFromEnum,Double> map = new HashMap<>();
		for(IAttributeAddModule attributeAddModule : AttributeAddManager.get().getAttributeAddMap().values()){
			Map<AttributeFromEnum,Double> attrAddMap = attributeAddModule.selectAttributeAdd(player, attributeEnum, node);
			for(Entry<AttributeFromEnum,Double> entry : attrAddMap.entrySet()){
				if(!map.containsKey(entry.getKey())){
					map.put(entry.getKey(), entry.getValue());
				}else{
					map.put(entry.getKey(), map.get(entry.getKey()) + entry.getValue());
				}
			}
		}
		List<AttrbuteAppenderBean> list = new ArrayList<>();
		for(Entry<AttributeFromEnum,Double> entry : map.entrySet()){
			AttrbuteAppenderBean attrbuteAppenderBean = new AttrbuteAppenderBean();
			attrbuteAppenderBean.appenderId = entry.getKey().ordinal();
			attrbuteAppenderBean.value = entry.getValue();
			list.add(attrbuteAppenderBean);
		}
		return list;
	}
	
	private static AttributeAdd getAttributeAdd(Player player,AttributeCounter counter,boolean isSelfAttr){
		AttributeAdd selfAttributeAdd = null;
		Map<AttributeNodeEnum,Double> selfAttrMap = counter.getAddValue(player,isSelfAttr);
		if(selfAttrMap.size() > 0){
			AttributesEnum attributesEnum;
			if(isSelfAttr){
				attributesEnum = counter.getSelfAttribute();
			}else{
				attributesEnum = counter.getRelationAttribute();
			}
			selfAttributeAdd = new AttributeAdd();
			selfAttributeAdd.setAtttibuteId(attributesEnum.getId());
			selfAttributeAdd.setAttrMap(selfAttrMap);
			
		}
		return selfAttributeAdd;
	}
	
	/**
	 * 刷新玩家所有属性加成
	 * @param playerId
	 */
	public static boolean refreshAttributeAdd(Player player){
		PlayerAttribute playerAttribute = new PlayerAttribute();
		PlayerAttribute playerAttributeOldData = player.roleInfo().getPlayerAttribute();
		for(AttributeCounter counter : counterMap.values()){
			AttributeAdd attributeAdd = getAttributeAdd(player,counter,false);
			if(attributeAdd != null){
				playerAttribute.getAtttibuteMap().put(attributeAdd.getAtttibuteId(), attributeAdd);
			}
			if(counter.getRelationAttribute() != counter.getSelfAttribute()){//300以后的 SelfAttribute等于RelationAttribute
				AttributeAdd selfAttributeAdd = getAttributeAdd(player,counter,true);
				if(selfAttributeAdd != null){
					playerAttribute.getAtttibuteMap().put(selfAttributeAdd.getAtttibuteId(), selfAttributeAdd);
				}
			}
		}
		boolean hasUpdate = AttributeUtil.hasUpdate(playerAttributeOldData, playerAttribute);
		if(hasUpdate){
			log.info(AttributeUtil.log(player,playerAttributeOldData, playerAttribute));
		}
		player.roleInfo().setPlayerAttribute(playerAttribute);
		InjectorUtil.getInjector().dbCacheService.update(player);
		if(hasUpdate){
			pushAttributeUpdate(player);
		}
		return hasUpdate;
	}
	
	/**
	 * 推送玩家所有属性更新
	 * @param player
	 */
	public static void pushAttributeUpdate(Player player){
		player.send(AttributeConverter.attrbutesMessageBuilder(player.roleInfo().getPlayerAttribute()));
	}
	
	/**
	 * 推送玩家单个属性更新
	 * @param player
	 * @param attributeEnum
	 */
	public static void pushAttributeUpdate(Player player,AttributesEnum attributeEnum){
		//TODO
	}
}
