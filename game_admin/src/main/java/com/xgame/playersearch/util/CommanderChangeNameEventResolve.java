package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=102
 * @author dingpeng.qu
 *
 */
public class CommanderChangeNameEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "指挥官改名[原名称="+jsonObj.get("oldName")+"]";
	}

}
