package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=18
 * @author dingpeng.qu
 *
 */
public class SoldierChangeEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "士兵变化[士兵id="+jsonObj.get("soldierId")+",兵工厂类型="+jsonObj.get("campType")+",变化前数量="+jsonObj.get("beforeNum")+
				",当前数量="+jsonObj.get("currentNum")+",出征类型="+jsonObj.get("marchType")+",行军状态="+jsonObj.get("marchState")+
				",来源信息="+jsonObj.get("gameLogSource")+"]";
	}

}
