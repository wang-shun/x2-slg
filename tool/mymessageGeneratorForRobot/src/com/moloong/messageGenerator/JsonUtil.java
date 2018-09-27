/**
 * Copyright (c) 2015, http://www.moloong.com/ All Rights Reserved. 
 * 
 */
package com.moloong.messageGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	
	public static String toJSONString(Object paramObject){
		return JSON.toJSONString(paramObject, new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect });
    }

	/**
	 * JSON反序列化
	 * 
	 * @param text
	 *            传入被反序列化的对象
	 * @return Object
	 */
	public static Object parse(String text) {
		return JSON.parse(text.trim());
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
	
	/**
	 * 获得值类型
	 * @author ye.yuan
	 *
	 */
	enum DataEnum{
		
		BYTE{
			public Object  changeType(String param){
				return Byte.parseByte(param);
			}
		},
		
		CHAR{
			
		},
		
		SHORT{
			public Object  changeType(String param){
				return Short.parseShort(param);
			}
		},
		
		INT{
			public Object  changeType(String param){
				return Integer.parseInt(param);
			}
		},
		
		LONG{
			public Object  changeType(String param){
				return Long.parseLong(param);
			}
		},
		
		DOUBLE(){
			public Object  changeType(String param){
				return Double.parseDouble(param);
			}
		},
		
		FLOAT(){
			public Object  changeType(String param){
				return Float.parseFloat(param);
			}
		},
		
		BOOLEAN(){
			public Object  changeType(String param){
				return Boolean.parseBoolean(param);
			}
		},
		;
		
		
		public Object changeType(String param){
			throw new AbstractMethodError();
		}
		
		private static final Map<Class<?>, DataEnum> baseClazzMap = new HashMap<>();
		
		static{
			baseClazzMap.put(byte.class, BYTE);
			baseClazzMap.put(char.class, CHAR);
			baseClazzMap.put(short.class, SHORT);
			baseClazzMap.put(int.class, INT);
			baseClazzMap.put(long.class,LONG);
			baseClazzMap.put(double.class,  DOUBLE);
			baseClazzMap.put(float.class, FLOAT);
			baseClazzMap.put(boolean.class, BOOLEAN);
		}
	}
	
    
    public static Object getDataType(Class<?> class1,String v){
    	DataEnum dataEnum = DataEnum.baseClazzMap.get(class1);
    	if(dataEnum==null){
    		return null;
    	}
    	return dataEnum.changeType(v);
    }


}
