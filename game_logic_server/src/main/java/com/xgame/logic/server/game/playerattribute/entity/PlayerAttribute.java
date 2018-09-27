package com.xgame.logic.server.game.playerattribute.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 * 玩家属性
 * @author zehong.he
 *
 */
public class PlayerAttribute implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5844011878772099954L;
	
	/**
	 * 玩家属性(key:属性ID)
	 */
	@Tag(1)
	private Map<Integer,AttributeAdd> atttibuteMap = new HashMap<>();
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		List<JBaseData> atttibuteList = new ArrayList<JBaseData>();
		for(Map.Entry<Integer, AttributeAdd> entry:atttibuteMap.entrySet()) {
			atttibuteList.add(((JBaseTransform)entry.getValue()).toJBaseData());
		}
		jbaseData.put("atttibutes", atttibuteList);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		List<JBaseData> atttibuteList = jBaseData.getSeqBaseData("atttibutes");
		for(JBaseData jBaseData2 : atttibuteList) {
			AttributeAdd atttibute = new AttributeAdd();
			atttibute.fromJBaseData(jBaseData2);
			this.atttibuteMap.put(atttibute.getAtttibuteId(), atttibute);
		}
	}

	public Map<Integer, AttributeAdd> getAtttibuteMap() {
		return atttibuteMap;
	}

	public void setAtttibuteMap(Map<Integer, AttributeAdd> atttibuteMap) {
		this.atttibuteMap = atttibuteMap;
	}

	@Override
	public String toString() {
		return "PlayerAttribute [atttibuteMap=" + atttibuteMap + "]";
	}
	
	
}
