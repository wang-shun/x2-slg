package com.xgame.redis;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RedisConfig {
	
	private static Properties props;
	
	private static Map<Integer, RedisServerInfo> serverInfoMap = new ConcurrentHashMap<Integer, RedisServerInfo>();
	
	static{
		loadProps();
	}
	
	synchronized static private void loadProps(){
		props = new Properties();
		InputStream in = null;
		try{
			in = RedisConfig.class.getClassLoader().getResourceAsStream("cross-redis.properties");
			props.load(in);
			
			Set<Map.Entry<Object,Object>> set = props.entrySet();
			if(set != null) {
				for(Map.Entry<Object,Object> entry : set) {
					String value = entry.getValue().toString();
					String[] data = value.split(":");
					RedisServerInfo redisServerInfo = new RedisServerInfo(data[1], Integer.valueOf(data[2]), data[3]);
					serverInfoMap.put(Integer.valueOf(data[0]), redisServerInfo);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(null != in){
					in.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Map<Integer,RedisServerInfo> getAllRedisServerInfo(){
		if(null == props){
			loadProps();
		}
		return serverInfoMap;
	}
	
	public static RedisServerInfo getRedisServerInfo(int serverId){
		if(null == props){
			loadProps();
		}
		return serverInfoMap.get(serverId);
	}
	
	
}
