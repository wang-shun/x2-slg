package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=17
 * @author dingpeng.qu
 *
 */
public class UnlockPeijianEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "解锁配件[配件="+jsonObj.get("peijianId")+"]";
	}

}
