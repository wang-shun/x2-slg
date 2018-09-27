package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=22
 * @author dingpeng.qu
 *
 */
public class ResearchLevelUpEndEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "科研升级结束[科研ID="+jsonObj.get("scienceId")+",当前等级="+jsonObj.get("currLevel")+",该等级战力加成="+jsonObj.get("addCombat")+"]";
	}

}
