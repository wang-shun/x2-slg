package com.xgame.logic.server.game.country.structs.build.tach;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.LibraryConf;
import com.xgame.config.science.SciencePir;
import com.xgame.config.science.SciencePirFactory;
import com.xgame.logic.server.game.country.structs.build.tach.data.Tech;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.IAttributeAddModule;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;

@Component
@Slf4j
public class TachManager implements IAttributeAddModule{
	
	@Override
	public Map<AttributeNodeEnum,Double> attributeAdd(Player player, AttributesEnum attributeEnum,AttributeNodeEnum...attributeNodeEnums) {
		double value = 0;
		TechBuildControl control = player.getCountryManager().getTechBuildControl();
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
		if(control==null)return valueOfNodeMap;
		Iterator<Tech> iterator = control.getTechData().getTechs().values().iterator();
		while (iterator.hasNext()) {
			Tech tech = (Tech) iterator.next();
			SciencePir configModel = SciencePirFactory.get(tech.getId());
			if(configModel==null){
				continue;
			}
			AttributeConfMap confMap = configModel.getV1();
			for(AttributeNodeEnum node : AttributeNodeEnum.values()){
				if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums,node) < 0){
					continue;
				}
				LibraryConf libraryConf = confMap.getLibraryConfs().get(tech.getLevel(),node.getCode());
				if(libraryConf != null){
					if(libraryConf.containsKey(attributeEnum.getId())){
						double nodeValue = libraryConf.get(attributeEnum.getId());
						value += nodeValue;
						if(nodeValue > 0){
							if(!valueOfNodeMap.containsKey(node.getCode())){
								valueOfNodeMap.put(node, nodeValue);
							}else{
								valueOfNodeMap.put(node, valueOfNodeMap.get(node.getCode()) + nodeValue);
							}
						}
					}
				}
			}
		}
		if(valueOfNodeMap.size() > 0){
			log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,SciencePir.class.getSimpleName()));
		}
		return valueOfNodeMap;
	}
	
	@Override
	public Map<AttributeFromEnum, Double> selectAttributeAdd(Player player,
			AttributesEnum attributeEnum, AttributeNodeEnum node) {
		Map<AttributeNodeEnum,Double> nodeAttrMap = attributeAdd(player,attributeEnum, node);
		Map<AttributeFromEnum, Double> resultMap = new HashMap<>();
		if(nodeAttrMap.containsKey(node)){
			resultMap.put(AttributeFromEnum.TECH, nodeAttrMap.get(node));
		}
		return resultMap;
	}
}
