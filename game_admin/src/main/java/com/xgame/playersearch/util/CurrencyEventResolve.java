package com.xgame.playersearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * type=1 type=2
 * @author dingpeng.qu
 *
 */
public class CurrencyEventResolve implements ILogResolve {
	
	@Override
	public String resolve(String content) {
		JSONObject jsonObj = JSON.parseObject(content,Feature.DisableSpecialKeyDetect);
		for(CurrencyTypeEnum cte : CurrencyTypeEnum.values()){
			if(cte.name().equals(jsonObj.get("currencyType"))){
				jsonObj.put("currencyType", cte.getDescribe());
			}
		}
		if(jsonObj.getBooleanValue("isIncrease")){
			jsonObj.put("isIncrease", "增加");
		}else{
			jsonObj.put("isIncrease", "减少");
		}
		
		for(GameLogSource gls : GameLogSource.values()){
			if(gls.name().equals(jsonObj.get("gameLogSource"))){
				jsonObj.put("gameLogSource", gls.getDescribe());
			}
		}
		return "资源变化[资源类型="+jsonObj.get("currencyType")+",增减="+jsonObj.get("isIncrease")+",原值="+jsonObj.get("oldValue")+",新值="+jsonObj.get("newValue")+",日志来源="+jsonObj.get("gameLogSource")+"]";
	}
	
}
