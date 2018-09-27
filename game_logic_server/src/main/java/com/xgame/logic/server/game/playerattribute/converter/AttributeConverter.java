package com.xgame.logic.server.game.playerattribute.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteProBean;
import com.xgame.logic.server.game.playerattribute.bean.AttributeNodeBean;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.entity.AttributeAdd;
import com.xgame.logic.server.game.playerattribute.entity.PlayerAttribute;
import com.xgame.logic.server.game.playerattribute.message.ResAttrbutesAddMessage;

@Slf4j
public class AttributeConverter {

	
	
	public static ResAttrbutesAddMessage attrbutesMessageBuilder(PlayerAttribute playerAttribute){
		ResAttrbutesAddMessage info = new ResAttrbutesAddMessage();
		List<AttributeNodeBean> attributeNodes = new ArrayList<AttributeNodeBean>();
		Map<Integer,List<AttrbuteProBean>> map = new HashMap<>();//key node
		for(AttributeAdd attributeAdd : playerAttribute.getAtttibuteMap().values()){
			for(Entry<AttributeNodeEnum,Double> entry : attributeAdd.getAttrMap().entrySet()){
				int node = entry.getKey().getCode();
				if(!map.containsKey(node)){
					map.put(node, new ArrayList<>());
				}
				AttrbuteProBean attrbuteProBean = new AttrbuteProBean();
				attrbuteProBean.attributeId = attributeAdd.getAtttibuteId();
				attrbuteProBean.value = entry.getValue();
				map.get(node).add(attrbuteProBean);
			}
		}
		for(Entry<Integer,List<AttrbuteProBean>> entry : map.entrySet()){
			AttributeNodeBean attributeNodeBean = new AttributeNodeBean();
			attributeNodeBean.nodeId = entry.getKey();
			attributeNodeBean.attributes = entry.getValue();
			attributeNodes.add(attributeNodeBean);
		}
		info.attributeNodes = attributeNodes;
		log.info(AttributeUtil.log(info));
		return info;
	}
}
