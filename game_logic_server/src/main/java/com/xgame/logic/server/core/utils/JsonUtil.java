/**
 * Copyright (c) 2015, http://www.moloong.com/ All Rights Reserved. 
 * 
 */
package com.xgame.logic.server.core.utils;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description: json 工具
 * @author ye.yuan
 * @date 2015年6月19日 下午3:54:27
 * 
 */
public final class JsonUtil {
	
	public static String toJSON(Object paramObject) {
		return JSON.toJSONString(paramObject, new SerializerFeature[] {SerializerFeature.WriteClassName });
    }

	/**
	 * JSON反序列化
	 * 
	 * @param text
	 *            传入被反序列化的对象
	 * @return Object
	 */
	public static <T> T fromJSON(String text, Class<T> clazz) {
		return (T)JSON.parseObject(text.trim(), clazz);
	}
	
	/**
	 * JSON反序列化
	 * @param <E>
	 * 
	 * @param text
	 *            传入被反序列化的对象
	 * @return Object
	 */
	public static <E> E[] parseArray(String text,E[] e) {
		JSONArray array = (JSONArray)JSON.parse(text.trim());
		return (E[])array.toArray(e);
	}
	
	/**
	 * JSON反序列化
	 * @param <E>
	 * 
	 * @param text
	 *            传入被反序列化的对象
	 * @return Object
	 */
	public static Object parseArray(String text) {
		JSONArray array = (JSONArray)JSON.parse(text.trim());
		return array.toArray();
	}

	/**
	 * JSON反序列化
	 * 
	 * @param text
	 *            传入被反序列化的对象
	 * @param clazz
	 *            要被反序列化成的类型
	 * @return <T extends Object> T
	 */
	public static <T extends Object> T parseObject(String text, Class<T> clazz) {
		return (T) JSON.parseObject(text.trim(), clazz);
	}


	/**
	 * JSON反序列化(反序列化为List)
	 * 
	 * @param text
	 *            传入被反序列化的对象
	 * @param clazz
	 *            要被反序列化成的类型
	 * @return <T extends Object> List<T>
	 */
	public static <T extends Object> List<T> parseArray(String text,
			Class<T> clazz) {
		return (List<T>) JSON.parseArray(text.trim(), clazz);
	}

}
