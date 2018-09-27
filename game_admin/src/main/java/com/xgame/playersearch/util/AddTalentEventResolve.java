package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=104
 * @author dingpeng.qu
 *
 */
public class AddTalentEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "增加天赋点[天赋ID="+jsonObj.get("talentId")+"]";
	}

}
