package com.xgame.logic.server.game.chat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.db.redis.CrossRedisClient;
import com.xgame.logic.server.core.db.redis.RedisClient;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.core.utils.CrossUtils;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.core.utils.scheduler.ScheduleTasks;
import com.xgame.logic.server.core.utils.scheduler.Scheduled;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.game.chat.bean.ChatMessageInfo;
import com.xgame.logic.server.game.chat.bean.ChatRoomBean;
import com.xgame.logic.server.game.chat.bean.ChatRoomMessageInfo;
import com.xgame.logic.server.game.chat.constant.ChatChannelType;
import com.xgame.logic.server.game.chat.constant.ChatConstant;
import com.xgame.logic.server.game.chat.converter.ChatConverter;
import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.chat.entity.GameChatRoom;
import com.xgame.logic.server.game.chat.message.ResApplyAddRoomMessage;
import com.xgame.logic.server.game.chat.message.ResReceiveChatMessage;
import com.xgame.logic.server.game.chat.message.ResRoomChangeMessage;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.constant.CrossConstant;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.rmi.RemoteService;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.msglib.ResMessage;


/**
 * 房间管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class ChatRoomManager {

	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private PrivateChatManager playerChatLogicManager;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private IDFactrorySequencer idSequencer;
	
	private LoadingCache<Long, GameChatRoom> roomCache;
	
	/**
	 * 房间排行
	 */
	private final Set<String> roomRanks = new ConcurrentHashSet<String>();
	
	// 房间名字长度
	public static final int ROOM_NAME_SIZE = 16;
	// 房间描述字数限制
	public static final int ROOM_DESC_SIZE = 140;
	// 房间最大人数
	public static final int ROOM_MAX_PLAYER_NUM_SIZE = 100;
	// 推荐房间数量
	public static final int VIEW_ROOM_SIZE = 10;
	// 房间排行前30
	public static final int ROOM_RANK_SIZE = 30;
	// 房间缓存过期时间
	public static final int ROOM_CACHE_ACCESS_EXPIRE = 10;
	
	@Startup(order = StartupOrder.CHAT_START, desc = "聊天启动加载")
	public void init() {
		this.roomCache = CacheBuilder.newBuilder().maximumSize(1000L).expireAfterAccess(ROOM_CACHE_ACCESS_EXPIRE, TimeUnit.MINUTES).recordStats().build(new CacheLoader<Long, GameChatRoom>() {
			public GameChatRoom load(Long roomId) throws Exception {
				return loadChatRoom(roomId);
			}
		});
	}
	
	/**
	 * 创建房间
	 * @param player
	 * @param roomName
	 * @param desc
	 */
	public void createRoom(Player player, String roomName, String desc, boolean open) {
		// 保存房间信息
		synchronized (player) {
			// 名字超过最大长度
			if(roomName.length() > ROOM_NAME_SIZE) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE9.get());
				return ;
			}
			
			if(desc.length() >= ROOM_DESC_SIZE) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE10.get());
				return ;
			}
			
			if(getPlayerOwnRoomNum(player.getRoleId()) >= Integer.valueOf(GlobalPirFactory.get(500854).getValue())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE13.get());
				return ;
			}
			
			synchronized (this) {
				if(this.roomExist(player.getId(), roomName)) {
					Language.ERRORCODE.send(player, ErrorCodeEnum.E1001_CHAT.CODE15.get());
					return ;
				}
				
				// 房间信息
				GameChatRoom gameChatRoom = new GameChatRoom();
				long roomId = idSequencer.createEnityID(DBKey.CHAT_ROOM_KEY);
				gameChatRoom.setRoomId(roomId);
				gameChatRoom.setRoomName(roomName);
				gameChatRoom.setDesc(desc);
				gameChatRoom.setOpen(open);
				gameChatRoom.getPlayerIds().add(player.getId());
				gameChatRoom.setServerId(InjectorUtil.getInjector().serverId);
				gameChatRoom.setRoomLeaderId(player.getId());
				gameChatRoom.setRoomKey(String.valueOf(gameChatRoom.getRoomId()));
				saveChatRoom(gameChatRoom);
				
				if(roomRanks.size() <= ROOM_RANK_SIZE) {
					roomRanks.add(gameChatRoom.getRoomKey());
				}
				
				// 添加玩家房间列表
				addLocalRoom(player.getRoleId(), gameChatRoom.getRoomId());
			
				ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
				resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
				resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_CREATE;
				player.send(resRoomChangeMessage);
			}
		}
	}
	
	/**
	 * 加入房间
	 * @param player
	 * @param roomId
	 * @param password
	 */
	public void applyAddRoom(CrossPlayer crossPlayer, String roomKey) {
		
		GameChatRoom gameChatRoom = getChatRoom(Long.valueOf(roomKey));
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE6.get()));
			return ;
		}
		// 在禁入列表当中
		if(gameChatRoom.getBanPlayerIds().contains(crossPlayer.getId())) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE10.get()));
			return ;
		}
		// 房间达到最大人数上线
		synchronized (gameChatRoom) {
			if(gameChatRoom.getPlayerIds().size() >= ROOM_MAX_PLAYER_NUM_SIZE) {
				pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE12.get()));
				return ;
			}
			// 加入申请
			if(!gameChatRoom.isOpen()) {
				gameChatRoom.addApply(crossPlayer.getId());
				this.saveChatRoom(gameChatRoom);
			} else {
				gameChatRoom.addRoom(crossPlayer.getId());
				this.saveChatRoom(gameChatRoom);
				// 添加玩家房间列表
				addPlayerRoom(crossPlayer, gameChatRoom.getId());
			}
		}
		
		ResApplyAddRoomMessage resApplyAddRoomMessage = new ResApplyAddRoomMessage();
		// 发送房间消息
		if(gameChatRoom.isOpen()) {
			resApplyAddRoomMessage.success = 1;
			resApplyAddRoomMessage.chatRoomMessageInfo = this.getRoomMessageInfo(Long.valueOf(roomKey));
		} else {
			resApplyAddRoomMessage.success = 0;
		}
		this.pushMessage(crossPlayer, resApplyAddRoomMessage);
		
		ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
		resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
		if(gameChatRoom.isOpen()){//发送所有人
			resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_APPLY_IN;
			pushRoomChange(gameChatRoom.getPlayerIds(), resRoomChangeMessage);
		}else{// 发送房主
			resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_APPLY_ADD;
			CrossPlayer roomPlayer = crossPlayerManager.getCrossPlayer(gameChatRoom.getRoomLeaderId());
			this.pushMessage(roomPlayer, resRoomChangeMessage);
		}
	}
	
	/**
	 * 推送房间变更消息
	 * @param gameChatRoom
	 * @param resRoomChangeMessage
	 */
	private void pushRoomChange(Collection<Long> playerIds, ResRoomChangeMessage resRoomChangeMessage) {
		Set<Long> localPlayerIds = new HashSet<>();
		if(playerIds != null && !playerIds.isEmpty()) {
			for(Long playerId : playerIds) {
				CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(playerId);
				if(crossPlayer != null) {
					if(crossPlayer.getServerId() != InjectorUtil.getInjector().serverId) {
						RemoteService remoteService = InjectorUtil.getInjector().getBean(CrossConstant.CROSS_SERVICE_NAME + crossPlayer.getServerId());
						remoteService.pushMessage(playerId, resRoomChangeMessage);
					} else {
						localPlayerIds.add(playerId);
					}
				}
			}
		}
		
		sessionManager.writePlayers(localPlayerIds, resRoomChangeMessage);
	}
	
	/**
	 * 推送消息
	 * @param crossPlayer
	 * @param message
	 */
	private void pushMessage(CrossPlayer crossPlayer, ResMessage message) {
		if(crossPlayer.getServerId() != InjectorUtil.getInjector().serverId) {
			RemoteService remoteService = InjectorUtil.getInjector().getBean(CrossConstant.CROSS_SERVICE_NAME + crossPlayer.getServerId());
			remoteService.pushMessage(crossPlayer.getId(), message);
		} else {
			PlayerSession playerSession = sessionManager.getSessionByPlayerId(crossPlayer.getId());
			if(playerSession != null) {
				playerSession.send(message);
			}
		}
	}
	
	/**
	 * 处理申请
	 * @param player
	 * @param roomName
	 * @param result
	 */
	public void dealApply(CrossPlayer crossPlayer, long roomId, long targetPlayerId, boolean result) {
		
		GameChatRoom gameChatRoom = getChatRoom(roomId);
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE6.get()));
			return ;
		}
		
		CrossPlayer targetPlayer = crossPlayerManager.getCrossPlayer(targetPlayerId);
		if(targetPlayer == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E001_LOGIN.CODE4.get()));
			return ;
		}
		
		synchronized (gameChatRoom) {
			// 房间达到最大人数上线
			if(!gameChatRoom.getApplyPlayerIds().contains(targetPlayerId)) {
				pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE14.get()));
				return ;
			}
			
			if(gameChatRoom.getPlayerIds().size() >= ROOM_MAX_PLAYER_NUM_SIZE) {
				pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE12.get()));
				return ;
			}
			
			if(result) {// 同意
				gameChatRoom.dealAddRoom(targetPlayerId);
				saveChatRoom(gameChatRoom);
				// 玩家添加房间
				addPlayerRoom(targetPlayer, gameChatRoom.getRoomId());
			} else {// 拒绝
				gameChatRoom.dealRefuse(targetPlayerId);
				saveChatRoom(gameChatRoom);
			}
		}
		
		// 推送对方在线
		ResApplyAddRoomMessage resApplyAddRoomMessage = new ResApplyAddRoomMessage();
		if(result){
			resApplyAddRoomMessage.success = 1;
			resApplyAddRoomMessage.chatRoomMessageInfo = this.getRoomMessageInfo(Long.valueOf(roomId));
		}else{
			resApplyAddRoomMessage.success = 0;
		}
		this.pushMessage(targetPlayer, resApplyAddRoomMessage);
		
		ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
		if(result){//推送给所有人
			resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_APPLY_IN;
			this.pushRoomChange(gameChatRoom.getPlayerIds(), resRoomChangeMessage);
		}else{//推送给房主
			resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_APPLY_REFUSE;
			resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
			CrossPlayer roomPlayer = crossPlayerManager.getCrossPlayer(gameChatRoom.getRoomLeaderId());
			this.pushMessage(roomPlayer, resRoomChangeMessage);
		}
	}
	
	/**
	 * 推出房间
	 * @param player
	 * @param roomId
	 */
	public void exitRoom(CrossPlayer crossPlayer, long roomId) {
		GameChatRoom gameChatRoom = getChatRoom(roomId);
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE6.get()));
			return ;
		}
		if(!gameChatRoom.getPlayerIds().contains(crossPlayer.getId())) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE8.get()));
			return ;
		}
		// 房间信息
		List<Long> playerList = Lists.newArrayList();
		playerList.addAll(gameChatRoom.getPlayerIds());
		
		boolean dimiss = false;
		synchronized (gameChatRoom) {
			if(crossPlayer.getId() == gameChatRoom.getRoomLeaderId()) {
				// 解散房间
				removeChatRoom(roomId);
				Set<Long> playerIds = gameChatRoom.getPlayerIds();
				if(playerIds != null && !playerIds.isEmpty()) {
					for(Long removePlayerId : playerIds) {
						removePlayerRoom(removePlayerId, roomId);
					}
				}
				//移除聊天记录
				this.removeChatRoomMsg(gameChatRoom);
				// 房间解散移除锁
				dimiss = true;
			} else {
				// 退出房间
				gameChatRoom.getPlayerIds().remove(crossPlayer.getId());
				this.saveChatRoom(gameChatRoom);
				// 移除玩家身上的房间信息
				removePlayerRoom(crossPlayer.getId(), roomId);
			}
			
			// 玩家id
			if(dimiss) {
				ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
				resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
				resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_DIMISS;
				this.pushRoomChange(playerList, resRoomChangeMessage);
			} else {
				ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
				resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
				resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_EXIT;
				this.pushRoomChange(playerList, resRoomChangeMessage);
			}
		}
	}
	
	/**
	 * 踢出玩家
	 * @param player
	 * @param roomId
	 * @param targetPlayerId
	 */
	public void kickPlayer(CrossPlayer crossPlayer, long roomId, long targetPlayerId) {
		GameChatRoom gameChatRoom = getChatRoom(roomId);
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE6.get()));
			return ;
		}
		// 踢出自己
		if(crossPlayer.getId() == targetPlayerId) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E001_LOGIN.CODE15.get()));
			return ;
		}
		// 玩家不在房间内
		if(!gameChatRoom.getPlayerIds().contains(crossPlayer.getId())) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE8.get()));
			return ;
		}
		
		synchronized (gameChatRoom) {
			List<Long> newList = Lists.newArrayList();
			newList.addAll(gameChatRoom.getPlayerIds());
			
			// 没有权限
			if(gameChatRoom.getRoomLeaderId() != crossPlayer.getId()) {
				pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E001_LOGIN.CODE13.get()));
				return ;
			}
			
			// 退出房间
			gameChatRoom.getPlayerIds().remove(targetPlayerId);
			
			// 保存房间信息
			this.saveChatRoom(gameChatRoom);
						
			// 踢出玩家
			removePlayerRoom(targetPlayerId, gameChatRoom.getRoomId());
			
			// 推送房间变更
			ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
			resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
			resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_KICK;
			this.pushRoomChange(newList, resRoomChangeMessage);
		}
	}
	
	/**
	 * 加入禁用联系人
	 * @param player
	 * @param roomId
	 * @param targetPlayerId
	 */
	public void addBanPlayerId(CrossPlayer crossPlayer, long roomId, long targetPlayerId) {
		GameChatRoom gameChatRoom = getChatRoom(roomId);
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE6.get()));
			return ;
		}
		
		// 玩家不在房间内
		if(!gameChatRoom.getPlayerIds().contains(crossPlayer.getId())) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE8.get()));
			return ;
		}
		
		if(gameChatRoom.getRoomLeaderId() != crossPlayer.getId()) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E001_LOGIN.CODE13.get()));
			return ;
		}
		
		// 房间信息
		synchronized (gameChatRoom) {
			gameChatRoom.getBanPlayerIds().add(targetPlayerId);
			gameChatRoom.getPlayerIds().remove(targetPlayerId);
			this.saveChatRoom(gameChatRoom);
			// 移除玩家身上的房间信息
			removePlayerRoom(targetPlayerId, gameChatRoom.getRoomId());
		}
		
		ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
		resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
		resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_ADD_BAN;
		this.pushRoomChange(gameChatRoom.getPlayerIds(), resRoomChangeMessage);
	}
	
	/**
	 * 加入禁用联系人
	 * @param player
	 * @param roomId
	 * @param targetPlayerId
	 */
	public void removeBanPlayerId(CrossPlayer crossPlayer, long roomId, long targetPlayerId) {
		GameChatRoom gameChatRoom = getChatRoom(roomId);
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE6.get()));
			return ;
		}
		
		// 玩家不在房间内
		if(!gameChatRoom.getPlayerIds().contains(crossPlayer.getId())) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE8.get()));
			return ;
		}
		
		if(gameChatRoom.getRoomLeaderId() != crossPlayer.getId()) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E001_LOGIN.CODE13.get()));
			return ;
		}
		
		Set<Long> playerIds = gameChatRoom.getPlayerIds();
		synchronized (gameChatRoom) {
			gameChatRoom.getBanPlayerIds().remove(targetPlayerId);
			this.saveChatRoom(gameChatRoom);
		}
		
		ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
		resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
		resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_REMOVE_BAN;
		this.pushRoomChange(playerIds, resRoomChangeMessage);
	}
	
	/**
	 * 编辑房间信息
	 * @param player
	 * @param roomId
	 * @param desc
	 * @param password
	 */
	public void editRoomInfo(CrossPlayer crossPlayer, long roomId, String roomName, String desc, boolean password) {
		GameChatRoom gameChatRoom = getChatRoom(roomId);
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE6.get()));
			return ;
		}
		
		// 玩家不在房间内
		if(!gameChatRoom.getPlayerIds().contains(crossPlayer.getId())) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE8.get()));
			return ;
		}
		
		if(gameChatRoom.getRoomLeaderId() != crossPlayer.getId()) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E001_LOGIN.CODE13.get()));
			return ;
		}
		
		// 房间信息
		synchronized (gameChatRoom) {
			gameChatRoom.edit(desc, roomName, password);
			this.saveChatRoom(gameChatRoom);
		}

		// 推送房间消息
		ResRoomChangeMessage resRoomChangeMessage = new ResRoomChangeMessage();
		resRoomChangeMessage.chatRoom = ChatConverter.converterChatRoom(gameChatRoom);
		resRoomChangeMessage.type = ChatConstant.CHAT_ROOM_MESSAGE_TYPE_EDIT;
		this.pushRoomChange(gameChatRoom.getPlayerIds(), resRoomChangeMessage);
	}
	
	/**
	 * 发送聊天室消息
	 * @param player
	 * @param roomName
	 */
	public void sendChatRoom(CrossPlayer crossPlayer, long roomId, String content, int channel, String source, int messageType) {
		
		GameChatRoom gameChatRoom = this.getChatRoom(roomId);
		if(gameChatRoom == null) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE11.get()));
			return;
		}
		
		if(!gameChatRoom.getPlayerIds().contains(crossPlayer.getId())) {
			pushMessage(crossPlayer, Language.ERRORCODE.getMessage(ErrorCodeEnum.E1001_CHAT.CODE8.get()));
			return;
		}
		
		// 玩家sessionId列表
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setContent(content);
		chatMessage.setSendPlayerId(crossPlayer.getId());
		chatMessage.setTarget(String.valueOf(roomId));
		chatMessage.setSendTime(System.currentTimeMillis());
		chatMessage.setChannel(ChatChannelType.CHAT_ROOM.getType());
		chatMessage.setSource(source);
		chatMessage.setMessageType(messageType);
		
		// 添加聊天室消息
		saveChatRoomMsg(gameChatRoom, chatMessage);
		
		// 广播当前在线玩家
		ChatMessageInfo chatMessageInfo = ChatConverter.converterChatMessageInfo(chatMessage, crossPlayer.getSimpleRoleInfo());
		ResReceiveChatMessage receiveChatMessage = new ResReceiveChatMessage();
		receiveChatMessage.chatMessageInfo = chatMessageInfo;
		sessionManager.writePlayers(gameChatRoom.getPlayerIds(), receiveChatMessage);
	}
	
	/**
	 * 保存房间消息
	 * @param roomId
	 * @param chatMessage
	 */
	private void saveChatRoomMsg(GameChatRoom gameChatRoom, ChatMessage chatMessage) {
		String roomMsg = redisClient.hget(DBKey.ROOM_CHAT_MEESAGE, gameChatRoom.getRoomId());
		synchronized (gameChatRoom) {
			if(!StringUtils.isBlank(roomMsg)) {
				List<ChatMessage> chatMessageList = JsonUtil.parseArray(roomMsg, ChatMessage.class);
				if(chatMessageList != null && chatMessageList.size() >= 200) {
					chatMessageList.remove(0);
				}
				chatMessageList.add(chatMessage);
				roomMsg = JsonUtil.toJSON(chatMessageList);
			} else {
				List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
				chatMessageList.add(chatMessage);
				roomMsg = JsonUtil.toJSON(chatMessageList);
			}
			redisClient.hset(DBKey.ROOM_CHAT_MEESAGE, gameChatRoom.getRoomId(), roomMsg);
		}
	}
	
	/**
	 * 移除房间消息
	 * @param roomId
	 * @param chatMessage
	 */
	private void removeChatRoomMsg(GameChatRoom gameChatRoom) {
		redisClient.hdel(DBKey.ROOM_CHAT_MEESAGE, gameChatRoom.getRoomId());
	}
	
	/**
	 * 搜索房间
	 * @param roomNamePattern
	 */
	public List<ChatRoomBean> serachRoom(String roomNamePattern) {
		List<ChatRoomBean> list = new ArrayList<ChatRoomBean>();
		List<GameChatRoom> gameChatRoomList = queryRoomPattern(roomNamePattern);
		if(gameChatRoomList != null && !gameChatRoomList.isEmpty()) {
			for(GameChatRoom gameChatRoom : gameChatRoomList) {
				list.add(ChatConverter.converterChatRoom(gameChatRoom));
			}
		}
		return list;
	}
	
	/**
	 * 获取推荐房间
	 * @return
	 */
	public List<ChatRoomBean> getViewRooms() {
		List<ChatRoomBean> list = new ArrayList<ChatRoomBean>();
		List<GameChatRoom> gameChatRooms = this.redisClient.queryBatch(GameChatRoom.class, roomRanks);
		if(gameChatRooms != null && !gameChatRooms.isEmpty()) {
			for(GameChatRoom gameChatRoom : gameChatRooms) {
				list.add(ChatConverter.converterChatRoom(gameChatRoom));
			}
		}
		return list;
	}
	
	/**
	 * 保存房间
	 * @param playerChat
	 */
	public void saveChatRoom(GameChatRoom gameChatRoom) {
		redisClient.hset(gameChatRoom);
	}
	
	/**
	 * 移除玩家聊天房间
	 * @param roomId
	 */
	public void removeChatRoom(long roomId) {
		redisClient.hdel(GameChatRoom.class, roomId);
	}
	
	/**
	 * 获取缓存当中房间信息
	 * @param roomName
	 * @return
	 */
	public GameChatRoom getChatRoom(long roomId) {
		GameChatRoom gameChatRoom = null;
		try {
			gameChatRoom = this.roomCache.get(roomId);
		} catch (Exception e) {
			log.error("获取房间信息出错.", e);
		}
		return gameChatRoom;
	}
	
	/**
	 * 返回所有房间(本服搜索)
	 * @param roomNamePattern
	 * @return
	 */
	public List<GameChatRoom> queryRoomPattern(String roomNamePattern) {
		List<GameChatRoom> result = new ArrayList<>();
		List<GameChatRoom> gameChatRoomList = redisClient.hvals(GameChatRoom.class);
		if(gameChatRoomList != null) {
			for(GameChatRoom gameChatRoom : gameChatRoomList) {
				if(gameChatRoom.getRoomName().contains(roomNamePattern)) {
					result.add(gameChatRoom);
				}
			}
		}
		return result;
	}
	
	/**
	 * 添加玩家房间列表
	 * @param playerId
	 * @param roomKey
	 */
	public void addPlayerRoom(CrossPlayer crossPlayer, long roomId) {
		if(crossPlayer.getServerId() == InjectorUtil.getInjector().serverId) {
			addLocalRoom(crossPlayer.getId(), roomId);
		} else {
			new CrossRedisClient(crossPlayer.getServerId()).sadd(DBKey.getPlayerRoomKey(crossPlayer.getId()), String.valueOf(roomId));
		}
	}
	
	/**
	 * 本地玩家加入房间
	 * @param player
	 * @param roomId
	 */
	public void addLocalRoom(long playerId, long roomId) {
		this.redisClient.sadd(DBKey.getPlayerRoomKey(playerId), String.valueOf(roomId));
	}
	
	/**
	 * 获取房间消息列表
	 * @param player
	 * @return
	 */
	public List<ChatRoomMessageInfo> getRoomMessageList(Player player) {
		List<ChatRoomMessageInfo> chatRoomMessageInfoList = new ArrayList<>();
		String roomKey = DBKey.getPlayerRoomKey(player.getRoleId());
		Set<String> valueList = redisClient.smember(roomKey);
		if(valueList != null && !valueList.isEmpty()) {
			for(String target: valueList) {
				chatRoomMessageInfoList.add(getRoomMessageInfo(Long.valueOf(target)));
			}
		}
		return chatRoomMessageInfoList;
	}
	
	/**
	 * 获取房间消息
	 * @param player
	 * @param roomKey
	 * @return
	 */
	public ChatRoomMessageInfo getRoomMessageInfo(long roomId) {
		ChatRoomMessageInfo chatRoomMessageInfo = new ChatRoomMessageInfo();
		List<ChatMessageInfo> chatMessages = new ArrayList<>();
		GameChatRoom gameChatRoom = null;
		int serverId = CrossUtils.getCrossServerByIncrId(roomId);
		if(serverId == InjectorUtil.getInjector().serverId) {
			gameChatRoom = getChatRoom(roomId);
			if(gameChatRoom != null) {
				String messages = redisClient.hget(DBKey.ROOM_CHAT_MEESAGE, roomId);
				if(!StringUtils.isBlank(messages)) {
					List<ChatMessage> chatMessageList = JsonUtil.parseArray(messages, ChatMessage.class);
					if(chatMessageList != null && !chatMessageList.isEmpty()) {
						for(ChatMessage chatMessage : chatMessageList) {
							CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(chatMessage.getSendPlayerId());
							chatMessages.add(ChatConverter.converterChatMessageInfo(chatMessage, crossPlayer.getSimpleRoleInfo()));	
						}
					}
				}
				
				// 玩家私聊列表
				chatRoomMessageInfo.chatRoomList = ChatConverter.converterChatRoom(gameChatRoom);
				chatRoomMessageInfo.chatMessageList = chatMessages;
				return chatRoomMessageInfo;
			}
		} else {
			CrossRedisClient crossRedisClient = new CrossRedisClient(serverId);
			gameChatRoom = crossRedisClient.hget(GameChatRoom.class, roomId);
			if(gameChatRoom != null) {
				String messages = crossRedisClient.hget(DBKey.ROOM_CHAT_MEESAGE, roomId);
				if(!StringUtils.isBlank(messages)) {
					List<ChatMessage> chatMessageList = JsonUtil.parseArray(messages, ChatMessage.class);
					if(chatMessageList != null && !chatMessageList.isEmpty()) {
						for(ChatMessage chatMessage : chatMessageList) {
							CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(chatMessage.getSendPlayerId());
							chatMessages.add(ChatConverter.converterChatMessageInfo(chatMessage, crossPlayer.getSimpleRoleInfo()));	
						}
					}
				}
					
				// 玩家私聊列表
				chatRoomMessageInfo.chatRoomList = ChatConverter.converterChatRoom(gameChatRoom);
				chatRoomMessageInfo.chatMessageList = chatMessages;
				return chatRoomMessageInfo;
			}
		}
		
		chatRoomMessageInfo.chatRoomList = ChatConverter.converterChatRoom(gameChatRoom);
		chatRoomMessageInfo.chatMessageList = chatMessages;
		return chatRoomMessageInfo;
	}
	
	/**
	 * 判断房间是否存在
	 * @param playerId
	 * @param roomKey
	 * @return
	 */
	private boolean roomExist(long playerId, String roomName) {
		List<GameChatRoom> gameChatRooms = this.redisClient.hvals(GameChatRoom.class);
		if(gameChatRooms != null) {
			for(GameChatRoom gameChatRoom : gameChatRooms) {
				if(gameChatRoom.getRoomName().equals(roomName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取玩家创建房间数
	 * @param playerId
	 * @return
	 */
	private int getPlayerOwnRoomNum(long playerId){
		Set<String> playerRoomList = this.redisClient.smember(DBKey.getPlayerRoomKey(playerId));
		int count = 0;
		for(String roomId : playerRoomList){
			GameChatRoom gameChatRoom = this.redisClient.hget(GameChatRoom.class, Long.valueOf(roomId));
			if(gameChatRoom != null && gameChatRoom.getRoomLeaderId() == playerId){
				count ++;
			}
		}
		return count;
	}
	
	/**
	 * 移除玩家房间列表
	 * @param playerId
	 * @param roomKey
	 */
	private void removePlayerRoom(long playerId, long roomId) {
		this.redisClient.srem(DBKey.getPlayerRoomKey(playerId), String.valueOf(roomId));
	}
	
    /**
     * 加载roomId
     * @param roomId
     */
    private GameChatRoom loadChatRoom(long roomId) {
    	return InjectorUtil.getInjector().redisClient.hget(GameChatRoom.class, roomId);
    }

    /**
     * 
     */
    @Startup(order = StartupOrder.CHAT_ROOM_START, desc = "聊天室启动加载")
	public void start() {
		generatorViewRooms();
	}

	/**
	 * 生成推荐房间列表
	 */
    @Scheduled(name = "定时生成房间", value = ScheduleTasks.CHAT_VIEW_ROOM_CREATE)
	public void generatorViewRooms() {
		roomRanks.clear();
		List<GameChatRoom> gameChatRoomList = redisClient.hvals(GameChatRoom.class);
		if(gameChatRoomList != null && !gameChatRoomList.isEmpty()) {
			Collections.sort(gameChatRoomList, new Comparator<GameChatRoom>() {
				@Override
				public int compare(GameChatRoom o1, GameChatRoom o2) {
					return o1.getPlayerIds().size() > o2.getPlayerIds().size() ? 0 :1;
				}
			});
			
			if(gameChatRoomList.size() > ROOM_RANK_SIZE) {
				gameChatRoomList = gameChatRoomList.subList(0, ROOM_RANK_SIZE);
			}
			
			for(GameChatRoom gameChatRoom : gameChatRoomList) {
				roomRanks.add(String.valueOf(gameChatRoom.getRoomId()));
			}
		}
	}
}
