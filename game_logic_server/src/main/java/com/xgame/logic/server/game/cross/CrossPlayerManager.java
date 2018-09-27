package com.xgame.logic.server.game.cross;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.xgame.logic.server.core.db.redis.GlobalRedisClient;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.cross.constant.CrossConstant;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;

/**
 * 玩家简易信息管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class CrossPlayerManager {
	
	@Autowired
	private GlobalRedisClient gloaGlobalRedisClient;
	@Autowired
	private ObjectFactory<CrossPlayer> factory;
	@Autowired
	private EventBus gameLog;
	
	private LoadingCache<Long, CrossPlayer> crossPlayerCache = null;
	
	public static final int PLAYER_CACHE_ACCESS_EXPIRE = 10;
	
	@PostConstruct
	public void init() {
		this.crossPlayerCache = CacheBuilder.newBuilder().maximumSize(5000L).expireAfterAccess(PLAYER_CACHE_ACCESS_EXPIRE, TimeUnit.MINUTES).recordStats().build(new CacheLoader<Long, CrossPlayer>() {
			public CrossPlayer load(Long playerId) throws Exception {
				SimpleRoleInfo simpleRoleInfo = gloaGlobalRedisClient.hget(SimpleRoleInfo.class, playerId);
				if(simpleRoleInfo != null) {
					CrossPlayer crossPlayer = factory.getObject();
					crossPlayer.init(simpleRoleInfo);
					return crossPlayer;	
				}
				return null;
			}
		});
	}

	/**
	 * 获取单个玩家简易信息
	 * @param playerId
	 * @return
	 */
	public CrossPlayer getCrossPlayer(long playerId) {
		try {
			return crossPlayerCache.get(playerId);
		} catch (Exception e) {
			log.error("加载跨服玩家信息.", e);
		}
		return null;
	}
	
	/**
	 * 获取玩家信息列表
	 * @param playerIds
	 * @return
	 */
	public List<SimpleRoleInfo> getSimpleRoleInfos(Collection<Long> playerIds) {
		Set<String> keys = new HashSet<>();
		if(playerIds != null && !playerIds.isEmpty()) {
			for(Long playerId : playerIds) {
				keys.add(String.valueOf(playerId));
			}
		}
		
		// 批量查询
		List<SimpleRoleInfo> objs = gloaGlobalRedisClient.queryBatch(SimpleRoleInfo.class, keys);
		return objs;
	}
	
	/**
	 * 保存玩家简易信息
	 * @param playerId
	 */
	public void saveSimpleRoleInfo(SimpleRoleInfo simpleRoleInfo) {
		String value = JsonUtil.toJSON(simpleRoleInfo.toJBaseData());
		this.gloaGlobalRedisClient.hset(SimpleRoleInfo.class.getSimpleName(), simpleRoleInfo.getId(), value);
	}
	
	/**
	 * 跨服登录
	 * @param playerId
	 * @param playerSession
	 */
	public void addCrossLogin(long playerId, PlayerSession playerSession) {
		CrossPlayer crossPlayer = this.getCrossPlayer(playerId);
		playerSession.setCross(true);
		playerSession.setPlayerId(playerId);
		crossPlayer.setPlayerSession(playerSession);
	}
	
	/**
	 * 验证跨服请求权限
	 * @param msgId
	 * @return
	 */
	public boolean checkCrossPermission(int msgId) {
		if(ArrayUtils.contains(CrossConstant.CROSS_ALLOW_CMD, msgId)) {
			return true;
		}
		return false;
	}
}
