package com.xgame.logic.server.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SplitUtil {
	
	/**
	 * "_"
	 */
	public static final String ATTRIBUTE_SPLIT = "_";
	
	/**
	 * "|"(用该符号拼接，在分割时要用"\\|"分割)
	 */
	public static final String ELEMENT_DELTMITER = "|";
	
	/**
	 * "\\|"
	 */
	public static final String ELEMENT_SPLIT = "\\|";
	
	/**
	 * ":"
	 */
	public static final String DELIMITER_ARGS = ":";
	
	/**
	 * ","
	 */
	public static final String BETWEEN_ITEMS = ",";
	
	public static String map2String0(Map<Integer,Integer> map){
		if(map == null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(Entry<Integer,Integer> entry : map.entrySet()){
			index++;
			sb.append(entry.getKey()).append(ATTRIBUTE_SPLIT).append(entry.getValue());
			if(index < map.size()){
				sb.append(ELEMENT_DELTMITER);
			}
		}
		return sb.toString();
	}
	
	public static Map<Integer,Integer> string2Map0(String str,Map<Integer,Integer> map){
		if(map == null){
			map = new HashMap<>();
		}
		if(str == null || "".equals(str)){
			return map;
		}
		String[] arr = str.split(ELEMENT_SPLIT);
		for(String elementStr : arr){
			String[] item = elementStr.split(ATTRIBUTE_SPLIT);
			map.put(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
		}
		return map;
	}
	
	
	public static String map2String1(Map<Integer,Map<Long,Integer>> map){
		if(map == null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int index0 = 0;
		for(Entry<Integer,Map<Long,Integer>> entry1 : map.entrySet()){
			index0++;
			int baseId = entry1.getKey();
			sb.append(baseId).append(DELIMITER_ARGS);
			int index1 = 0;
			for(Entry<Long,Integer> entry2 : entry1.getValue().entrySet()){
				index1++;
				long subId = entry2.getKey();
				int value = entry2.getValue();
				sb.append(subId).append(ATTRIBUTE_SPLIT)
				.append(value);
				if(index1 < entry1.getValue().size()){
					sb.append(BETWEEN_ITEMS);
				}
			}
			if(index0 < map.size()){
				sb.append(ELEMENT_DELTMITER);
			}
		}
		
		return sb.toString();
	}

	public static Map<Integer,Map<Long,Integer>> string2Map1(String str,Map<Integer,Map<Long,Integer>> map){
		if(map == null){
			map = new HashMap<>();
		}
		if(str == null || "".equals(str)){
			return map;
		}
		String[] arr = str.split(ELEMENT_SPLIT);
		for(String elementStr : arr){
			String[] element = elementStr.split(DELIMITER_ARGS);
			int baseId = Integer.parseInt(element[0]);
			String[] items = element[1].split(BETWEEN_ITEMS);
			for(String itemStr : items){
				String[] item = itemStr.split(ATTRIBUTE_SPLIT);
				long subId = Long.parseLong(item[0]);
				int value = Integer.parseInt(item[1]);
				if(!map.containsKey(baseId)){
					map.put(baseId, new HashMap<>());
				}
				map.get(baseId).put(subId, value);
			}
		}
		
		return map;
	}
}
