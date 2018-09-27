package com.xgame.logic.server.game.player.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;

/**
 * 玩家领地信息
 * @author jacky.jiang
 *
 */
public class PlayerTerritory implements Serializable, JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2675688737298032079L;
	/**
	 * 领地坐标
	 */
	private ConcurrentHashSet<Integer> territory = new ConcurrentHashSet<>();

	public ConcurrentHashSet<Integer> getTerritory() {
		return territory;
	}

	public void setTerritory(ConcurrentHashSet<Integer> territory) {
		this.territory = territory;
	}
	
	public int getTerritoryNum() {
		return territory.size();
	}
	
	public void addTerritory(int index) {
		territory.add(index);
	}
	
	public void removeTerritory(int index) {
		territory.remove(index);
	}
	
	public void addBatchTerrritory(List<Integer> indexList) {
		territory.addAll(indexList);
	}
	
	public void removeBatchTerrritory(List<Integer> indexList) {
		territory.addAll(indexList);
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("territory", JsonUtil.toJSON(territory));
		return jBaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.territory = new ConcurrentHashSet<>();
		
		String territory = jBaseData.getString("territory", "");
		if(!StringUtils.isEmpty(territory)) {
			this.territory.addAll(JsonUtil.fromJSON(territory, ConcurrentHashSet.class));	
		}
	}
}
