package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=203
 * @author dingpeng.qu
 *
 */
public class CreateAllianceEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "创建联盟[联盟Id="+jsonObj.get("allianceId")+",联盟名称="+jsonObj.get("allianceName")+",联盟简称="+jsonObj.get("abbrName")+"]";
	}

}
