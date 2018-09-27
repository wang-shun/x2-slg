package com.xgame.logic.server.core.sysconfig;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class RedisServerConfig {
	
	private Map<Integer, RedisServerInfo> serverInfoMap = new ConcurrentHashMap<Integer, RedisServerInfo>();

	@PostConstruct
	public void init() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("xgame/cross-redis.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("读取全服redis配置文件出错:", e);
		}
		
		// 初始化跨服redis配置
		Set<Map.Entry<Object,Object>> set = p.entrySet();
		if(set != null) {
			for(Map.Entry<Object,Object> entry : set) {
				String value = entry.getValue().toString();
				String[] data = value.split(":");
				RedisServerInfo redisServerInfo = new RedisServerInfo(data[1], Integer.valueOf(data[2]), data[3]);
				this.serverInfoMap.put(Integer.valueOf(data[0]), redisServerInfo);
			}
		}
		
		log.info("初始化redis配置信息完成....");
	}
	
	/**
	 * 获取redis数据库信息
	 * @param serverId
	 * @return
	 */
	public RedisServerInfo getRedisServerInfo(int serverId) {
		return serverInfoMap.get(serverId);
	}
	
}
