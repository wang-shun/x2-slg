package com.xgame.logic.server.game.playerattribute.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.SplitUtil;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;

/**
 * 属性加成对象
 * @author zehong.he
 *
 */
public class AttributeAdd implements Serializable, JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1904886903199493781L;

	@Tag(1)
	private int atttibuteId;													//属性ID
	
	@Tag(2)
	private Map<AttributeNodeEnum,Double> attrMap = new HashMap<>();			//属性值 key: 0-建筑物;1-坦克;2-战车;3-飞机;4-玩家;-1:配件

	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("atttibuteId", atttibuteId);
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(Entry<AttributeNodeEnum,Double> entry : attrMap.entrySet()){
			index++;
			sb.append(entry.getKey().getCode()).append(SplitUtil.ATTRIBUTE_SPLIT).append(entry.getValue());
			if(index < attrMap.size()){
				sb.append(SplitUtil.ELEMENT_DELTMITER);
			}
		}
		jbaseData.put("attrMap", sb.toString());
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.atttibuteId = jBaseData.getInt("atttibuteId", 0);
		String attrListStr = jBaseData.getString("attrMap", "");
		if(!"".equals(attrListStr)){
			String[] attrArray = attrListStr.split(SplitUtil.ELEMENT_SPLIT);
			for(String attrStr : attrArray){
				String[] attr = attrStr.split(SplitUtil.ATTRIBUTE_SPLIT);
				this.attrMap.put(AttributeNodeEnum.getCode(Integer.parseInt(attr[0])), Double.valueOf(attr[1]));
			}
		}
	}

	public int getAtttibuteId() {
		return atttibuteId;
	}

	public Map<AttributeNodeEnum, Double> getAttrMap() {
		return attrMap;
	}

	public void setAtttibuteId(int atttibuteId) {
		this.atttibuteId = atttibuteId;
	}

	public void setAttrMap(Map<AttributeNodeEnum, Double> attrMap) {
		this.attrMap = attrMap;
	}

	@Override
	public String toString() {
		return "AttributeAdd [atttibuteId=" + atttibuteId + ", attrMap="
				+ attrMap + "]";
	}
}
