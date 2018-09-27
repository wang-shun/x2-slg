package com.xgame.logic.server.game.war.entity;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.logic.server.game.country.bean.BuildBean;
import com.xgame.logic.server.game.country.bean.TransformBean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.war.bean.WarBuilding;
import com.xgame.logic.server.game.war.converter.BattleConverter;


/**
 * 战场建筑构建器
 * @author jacky.jiang
 *
 */
public class WarBuildFactory {
	
	/**
	 * 战场建筑生成器
	 */
	private static AtomicInteger warBuildingIdGernerator = new AtomicInteger();
	
	/**
	 * 初始化pve建筑
	 * @param tid
	 * @param level
	 * @param vector2Bean
	 * @param attrRatio
	 * @return
	 */
	public static WarBuilding initWarPVEBuilding(int tid, int level, Vector2Bean vector2Bean, double attrRatio) {
		int uid = -warBuildingIdGernerator.incrementAndGet();
		return initWarBuilding(uid, tid, level, vector2Bean, attrRatio);
	}

	/**
	 * 初始化pve建筑
	 * @param tid
	 * @param level
	 * @param vector2Bean
	 * @param attrRatio
	 * @return
	 */
	public static WarBuilding initWarPVEBuilding (int tid, int level, Vector2Bean vector2Bean) {
		int uid = -warBuildingIdGernerator.incrementAndGet();
		return initWarBuilding(uid, tid, level, vector2Bean, 1.0d);
	}
	
	/**
	 * 初始化建筑
	 * @param sid
	 * @param level
	 * @param vector2Bean
	 * @param attrRatio
	 * @return
	 */
	public static WarBuilding initWarBuilding(int uid, int tid, int level, Vector2Bean vector2Bean, double attrRatio) {
		WarBuilding warBuilding = new WarBuilding();
		BuildBean buildBean = new BuildBean();
		buildBean.uid = uid;
		buildBean.sid = tid;
		buildBean.level = level;
		warBuilding.building = buildBean;
		TransformBean transform = new TransformBean();
		transform.vector2Bean = vector2Bean;
		warBuilding.transform = transform;
		
		BuildingPir buildpir = BuildingPirFactory.get(tid);
		AttributeConfMap attributeConfMap = (AttributeConfMap)buildpir.getAttr();
		Map<AttributesEnum,Double> attrMap = AttributeUtil.converAttrMap(attributeConfMap, level, AttributeNodeEnum.BUILD);
		warBuilding.warAttr = BattleConverter.converterWarAttr(attrMap,attrRatio);
		return warBuilding;
	}
	
}
