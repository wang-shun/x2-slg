package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.game.world.constant.CollectType;
import com.xgame.logic.server.game.world.constant.WorldConstant;

/**
 * 玩家收藏
 * @author jacky.jiang
 *
 */
public class PlayerCollect implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3312252946783133019L;

	// 好友
	@Tag(1)
	private ConcurrentHashSet<Integer> friendCollect = new ConcurrentHashSet<>();
	
	// 敌人
	@Tag(2)
	private ConcurrentHashSet<Integer> enemyCollect = new ConcurrentHashSet<>();
	
	// 
	@Tag(3)
	private ConcurrentHashSet<Integer> resourceCollect = new ConcurrentHashSet<>();
	
	
	/**
	 * 添加到收藏
	 * @param x
	 * @param y
	 * @param type 收藏类型 {@link CollectType}
	 */
	public void addCollect(int x, int y, int type) {
		int index = y * WorldConstant.X_GRIDNUM + x;
		
		if(type == CollectType.FRIEND.ordinal())
			friendCollect.add(index);
		else if(type == CollectType.ENEMY.ordinal()) 
			enemyCollect.add(index);
		else if(type == CollectType.RESOURCE.ordinal())
			resourceCollect.add(index);
	}
	
	/**
	 * 移除收藏
	 * @param x
	 * @param y
	 * @param type 收藏类型 {@link CollectType}
	 */
	public void removeCollect(int x, int y, int type) {
		int index = y * WorldConstant.X_GRIDNUM + x;
		if(type == CollectType.FRIEND.ordinal())
			friendCollect.remove(index);
		else if(type == CollectType.ENEMY.ordinal()) 
			enemyCollect.remove(index);
		else if(type == CollectType.RESOURCE.ordinal())
			resourceCollect.remove(index);
	}

	public Set<Integer> getFriendCollect() {
		return friendCollect;
	}

	public void setFriendCollect(ConcurrentHashSet<Integer> friendCollect) {
		this.friendCollect = friendCollect;
	}

	public Set<Integer> getEnemyCollect() {
		return enemyCollect;
	}

	public void setEnemyCollect(ConcurrentHashSet<Integer> enemyCollect) {
		this.enemyCollect = enemyCollect;
	}

	public Set<Integer> getResourceCollect() {
		return resourceCollect;
	}

	public void setResourceCollect(ConcurrentHashSet<Integer> resourceCollect) {
		this.resourceCollect = resourceCollect;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("friendCollect", this.friendCollect);
		jbaseData.put("enemyCollect", this.enemyCollect);
		jbaseData.put("resourceCollect", this.resourceCollect);
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.friendCollect = new ConcurrentHashSet<Integer>();
		String friendStr = jBaseData.getString("friendCollect", "");
		if(!StringUtils.isEmpty(friendStr)) {
			this.friendCollect.addAll(JsonUtil.fromJSON(friendStr, ConcurrentHashSet.class));
		}
		
		String enemyStr = jBaseData.getString("enemyCollect", "");
		if(!StringUtils.isEmpty(enemyStr)) {
			this.enemyCollect.addAll(JsonUtil.fromJSON(enemyStr, ConcurrentHashSet.class));
		}
		
		String resourceStr = jBaseData.getString("resourceCollect", "");
		if(!StringUtils.isEmpty(enemyStr)) {
			this.resourceCollect.addAll(JsonUtil.fromJSON(resourceStr, ConcurrentHashSet.class));
		}
	}
}
