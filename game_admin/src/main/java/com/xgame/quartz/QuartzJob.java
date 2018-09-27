package com.xgame.quartz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xgame.playersearch.entity.Player;
import com.xgame.playersearch.service.IPlayerService;
import com.xgame.redis.CrossRedisClient;
import com.xgame.redis.RedisConfig;
import com.xgame.redis.RedisServerInfo;

@Component
public class QuartzJob {
	
	@Resource
	private IPlayerService playerService;
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);
	
	//为了避免数据库锁的竞争,使用单线程executor
	private ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
	
	private static final String PLAYER_KEY = "Player";
	
	public void loadPlayer(){
		logger.info("开始读取用户数据:"+Calendar.getInstance().getTimeInMillis());
		Map<Integer, RedisServerInfo> serverInfoMap =  RedisConfig.getAllRedisServerInfo();
		for(int key : serverInfoMap.keySet()){
			singleThreadPool.execute(new Runnable(){
				public void run(){
					CrossRedisClient crossRedisClient = new CrossRedisClient(serverInfoMap.get(key));
					List<Player> players = new ArrayList<Player>();
					Set<String> keys = crossRedisClient.hkeys(PLAYER_KEY);
					for(String roleId : keys){
						String playerInfo = crossRedisClient.hget(PLAYER_KEY, roleId);
						players.add(parseJson(playerInfo,key));
					}
					playerService.batchSaveOrUpdate(players);
				}
			});
		}
		logger.info("结束读取用户数据:"+Calendar.getInstance().getTimeInMillis());
	}
	
	private Player parseJson(String playerInfo,int key){
		JSONObject obj = JSON.parseObject(playerInfo,Feature.DisableSpecialKeyDetect);
		JSONObject basics = obj.getJSONObject("basics");
		JSONObject vipInfo = obj.getJSONObject("vipInfo");
		Player player = new Player();
		player.setRoleId(basics.getLong("roleId"));
		player.setUserName(basics.getString("userName"));
		player.setServerArea(key);
		player.setLevel(basics.getInteger("level"));
		player.setCreateTime(basics.getDate("createTime"));
		player.setDeleteTime(basics.getDate("deleteTime"));
		player.setRoleName(basics.getString("roleName"));
		player.setVipLevel(vipInfo.getInteger("vipLevel"));
		player.setExp(vipInfo.getLong("exp"));
		return player;
	}
	
	public static void main(String[] args) {
		System.out.println(Calendar.getInstance().getTimeInMillis());
		CrossRedisClient crossRedisClient = new CrossRedisClient(1002);
		String player = crossRedisClient.hget("Player", "10060000000020");
		JSONObject obj = JSON.parseObject(player,Feature.DisableSpecialKeyDetect);
		System.out.println(obj.getJSONObject("basics"));
		System.out.println(obj.getJSONObject("vipInfo"));
		System.out.println(Calendar.getInstance().getTimeInMillis());
	}
}
