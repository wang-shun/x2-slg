package com.xgame.logic.server.game.chat.entity;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.cache.entity.IEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;

import io.protostuff.Tag;


/**
 * 房间信息
 * @author jacky.jiang
 *
 */
public class GameChatRoom implements IEntity<Long>{

	/**
	 * 房间id
	 */
	@Tag(1)
	private long roomId;
	
	/**
	 * 房间名称
	 */
	@Tag(2)
	private String roomName;
	
	/**
	 * 房间key
	 */
	@Tag(3)
	private String roomKey;
	
	/**
	 * 房主id
	 */
	@Tag(4)
	private long roomLeaderId;
	
	/**
	 * 描述
	 */
	@Tag(5)
	private String desc;
	
	/**
	 * 密码
	 */
	@Tag(6)
	private boolean open;
	
	/**
	 * 房间玩家id列表
	 */
	@Tag(7)
	private ConcurrentHashSet<Long> playerIds = new ConcurrentHashSet<>();
	
	/**
	 * 禁止加入名单
	 */
	@Tag(8)
	private ConcurrentHashSet<Long> banPlayerIds = new ConcurrentHashSet<>();
	
	/**
	 * 服标识
	 */
	@Tag(9)
	private int serverId;
	
	/**
	 * 邀请玩家
	 */
	@Tag(10)
	private ConcurrentHashSet<Long> applyPlayerIds = new ConcurrentHashSet<>();
	
	/**
	 * 添加玩家
	 * @param playerId
	 */
	public void addPlayer(long playerId) {
		playerIds.add(playerId);
	}
	
	/**
	 * 添加禁止进入黑名单
	 * @param playerId
	 */
	public void addBanPlayer(long playerId) {
		playerIds.add(playerId);
	}
	
	/**
	 * 编辑房间信息
	 * @param desc
	 * @param roomName
	 * @param password
	 */
	public void edit(String desc, String roomName, boolean open) {
		this.desc = desc;
		this.open = open;
		this.roomName = roomName;
	}
	
	/**
	 * 申请加入
	 * @param playerId
	 */
	public void addApply(long playerId)  {
		applyPlayerIds.add(playerId);
	}
	
	/**
	 * 处理添加房间
	 * @param playerId
	 */
	public void dealAddRoom(long playerId) {
		applyPlayerIds.remove(playerId);
		playerIds.add(playerId);
	}
	
	/**
	 * 加入房间
	 * @param playerId
	 */
	public void addRoom(long playerId) {
		playerIds.add(playerId);
	}
	
	/**
	 * 处理拒绝申请
	 * @param playerId
	 */
	public void dealRefuse(long playerId) {
		applyPlayerIds.remove(playerId);
	}
	
	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomKey() {
		return roomKey;
	}

	public void setRoomKey(String roomKey) {
		this.roomKey = roomKey;
	}

	public long getRoomLeaderId() {
		return roomLeaderId;
	}

	public void setRoomLeaderId(long roomLeaderId) {
		this.roomLeaderId = roomLeaderId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public ConcurrentHashSet<Long> getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(ConcurrentHashSet<Long> playerIds) {
		this.playerIds = playerIds;
	}

	public ConcurrentHashSet<Long> getBanPlayerIds() {
		return banPlayerIds;
	}

	public void setBanPlayerIds(ConcurrentHashSet<Long> banPlayerIds) {
		this.banPlayerIds = banPlayerIds;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public ConcurrentHashSet<Long> getApplyPlayerIds() {
		return applyPlayerIds;
	}

	public void setApplyPlayerIds(ConcurrentHashSet<Long> applyPlayerIds) {
		this.applyPlayerIds = applyPlayerIds;
	}

	@Override
	public Long getId() {
		return roomId;
	}

	@Override
	public void setId(Long k) {
		this.roomId = k;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("roomId", roomId);
		jbaseData.put("roomName", roomName);
		jbaseData.put("roomKey", roomKey);
		jbaseData.put("roomLeaderId", roomLeaderId);
		jbaseData.put("desc", desc);
		jbaseData.put("open", open);
		jbaseData.put("serverId", serverId);
		
		jbaseData.put("playerIds", JsonUtil.toJSON(playerIds));
		jbaseData.put("banPlayerIds", JsonUtil.toJSON(banPlayerIds));
		jbaseData.put("applyPlayerIds", JsonUtil.toJSON(applyPlayerIds));
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.roomId = jBaseData.getLong("roomId", 0);
		this.roomName = jBaseData.getString("roomName","");
		this.roomKey = jBaseData.getString("roomKey","");
		this.roomLeaderId = jBaseData.getLong("roomLeaderId", 0);
		this.desc = jBaseData.getString("desc","");
		this.open = jBaseData.getBoolean("open", false);
		this.serverId = jBaseData.getInt("serverId", 0);
		
		String banPlayerIdstr = jBaseData.getString("banPlayerIds", "");
		if(!StringUtils.isEmpty(banPlayerIdstr)) {
			this.banPlayerIds = JsonUtil.fromJSON(banPlayerIdstr, ConcurrentHashSet.class);
		}
		
		String playerIdstr = jBaseData.getString("playerIds", "");
		if(!StringUtils.isEmpty(playerIdstr)) {
			this.playerIds = JsonUtil.fromJSON(playerIdstr, ConcurrentHashSet.class);
		}
		
		
		String applyPlayerIdstr = jBaseData.getString("applyPlayerIds", "");
		if(!StringUtils.isEmpty(applyPlayerIdstr)) {
			this.applyPlayerIds = JsonUtil.fromJSON(applyPlayerIdstr, ConcurrentHashSet.class);
		}
	}
	
}
