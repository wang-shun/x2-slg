package com.xgame.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtil {
	
	public static String toJSON(Object object){
		return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
	}
}
