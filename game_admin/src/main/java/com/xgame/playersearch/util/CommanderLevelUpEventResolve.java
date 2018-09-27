package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=101
 * @author dingpeng.qu
 *
 */
public class CommanderLevelUpEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "指挥官升级[当前等级="+jsonObj.get("currentLevel")+",原来等级="+jsonObj.get("originLevel")+"]";
	}

}
