package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=3
 * @author dingpeng.qu
 *
 */
public class CreateRoleEventResolve implements ILogResolve {

	@Override
	public String resolve(String content) {
		
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		return "创建角色[指挥官名称="+jsonObj.get("commanderName")+",坐标="+jsonObj.get("vector2Bean")+"]";
	}

}
