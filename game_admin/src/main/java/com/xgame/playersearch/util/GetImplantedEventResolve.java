package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=45
 * @author dingpeng.qu
 *
 */
public class GetImplantedEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "获得植入体[配置ID="+jsonObj.get("equipmentId")+",数量="+jsonObj.get("num")+",总数="+jsonObj.get("totalNum")+"]";
	}

}
