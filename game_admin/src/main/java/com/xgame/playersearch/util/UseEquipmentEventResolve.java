package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=105
 * @author dingpeng.qu
 *
 */
public class UseEquipmentEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "穿上装备[装备ID="+jsonObj.get("equipmentId")+",玩家装备ID="+jsonObj.get("userEquipmentId")+"]";
	}

}
