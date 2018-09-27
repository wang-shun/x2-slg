package com.xgame.logic.server.game.war.entity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import com.xgame.logic.server.game.war.converter.BattleConverter;


/**
 * 战场上的士兵构建器
 * @author jacky.jiang
 *
 */
public class WarSoldierFactory {
	
	
	private static final AtomicInteger soldierGenerator = new AtomicInteger();
	
	/**
	 * 初始化pve副本怪物
	 * @param num
	 * @param peijinList
	 * @param attributeConfMap
	 * @param position
	 * @return
	 */
	public static WarSoldierBean initPVEWarSoldier(int num, List<Integer> peijinList, AttributeConfMap attributeConfMap, Vector2Bean position) {
		int soldierId = -soldierGenerator.incrementAndGet();
		return initWarSoldier(soldierId, 0, num, peijinList, attributeConfMap, 0, position, 1.0d);
	}
	
	/**
	 * 初始化pve副本怪物
	 * @param num
	 * @param peijinList
	 * @param attributeConfMap
	 * @param position
	 * @param attrRatio
	 * @return
	 */
	public static WarSoldierBean initPVEWarSoldier(int num, List<Integer> peijinList, AttributeConfMap attributeConfMap, Vector2Bean position, double attrRatio) {
		int soldierId = -soldierGenerator.incrementAndGet();
		return initWarSoldier(soldierId, 0, num, peijinList, attributeConfMap, 0, position, attrRatio);
	}
	
	/**
	 * 初始化pve士兵
	 * @param num
	 * @param attributeConfMap
	 * @param position
	 * @param attrRatio
	 * @param designMap
	 * @return
	 */
	public static Soldier initSoldier(int num, AttributeConfMap attributeConfMap,Vector2Bean position, double attrRatio,DesignMap designMap) {
		int soldierId = -soldierGenerator.incrementAndGet();
		return initSoldier(soldierId,num, attributeConfMap, position, attrRatio,designMap);
	}
	
	/**
	 * 初始化pve兵种
	 * @param sid
	 * @param level
	 * @param vector2Bean
	 * @param attrRatio
	 * @return
	 */
	public static WarSoldierBean initWarSoldier(int soldierId, int campType, int num, List<Integer> peijinList, AttributeConfMap attributeConfMap, int index, Vector2Bean position, double attrRatio) {
		WarSoldierBean warSoldierBean = new WarSoldierBean();
		warSoldierBean.position = position;
		FullSoldierBean soldierBean = new FullSoldierBean();
		soldierBean.soldier = new SoldierBean();
		soldierBean.soldier.soldierId = soldierId;
		soldierBean.soldier.num = num;
		warSoldierBean.soldier = soldierBean;
		Map<AttributesEnum,Double> attrMap = AttributeUtil.converAttrMap(attributeConfMap, 1, AttributeNodeEnum.PEIJIAN);
		warSoldierBean.warAttr = BattleConverter.converterWarAttr(attrMap, attrRatio);
		return warSoldierBean;
	}
	
	public static Soldier initSoldier(int soldierId, int num, AttributeConfMap attributeConfMap, Vector2Bean position, double attrRatio,DesignMap designMap){
		Soldier soldier = new Soldier();
		Map<AttributesEnum,Double> attrMap = AttributeUtil.converAttrMap(attributeConfMap, 1, AttributeNodeEnum.PEIJIAN);
		soldier.setWarAttr(BattleConverter.converterWarAttr(attrMap, attrRatio));
		soldier.setSoldierId(soldierId);
		soldier.setNum(num);
		designMap.setId(soldierId);
		soldier.setDesignMap(designMap);
		soldier.setVector(position);
		
		return soldier;
	}
	
}
