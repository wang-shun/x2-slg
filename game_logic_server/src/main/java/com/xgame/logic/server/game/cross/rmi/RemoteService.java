package com.xgame.logic.server.game.cross.rmi;

import com.xgame.logic.server.game.chat.entity.ChatMessage;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.CrossWorldInfo;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.msglib.ResMessage;


/**
 * 跨服请求
 * @author jacky.jiang
 *
 */
public interface RemoteService {
	
	/**
	 * 跨服请求
	 */
	public void sayHello();
	
	//-------------------------chat(跨服聊天)-----------------------------------------------
	/**
	 * 推送消息
	 * @param playerId
	 * @param resMessage
	 */
	public void pushMessage(long playerId, ResMessage resMessage);
	
	/**
	 * 申请加入跨服房间
	 * @param crossPlayer
	 * @param roomKey
	 */
	public void applyAddRoom(CrossPlayer crossPlayer, String roomKey);
	
	/**
	 * 处理申请 
	 * @param crossPlayer
	 * @param roomId
	 * @param targetPlayerId
	 * @param result
	 */
	public void dealApply(CrossPlayer crossPlayer, long roomId, long targetPlayerId, boolean result);

	/**
	 * 推出房间
	 * @param crossPlayer
	 * @param roomId
	 */
	public void exitRoom(CrossPlayer crossPlayer, long roomId);
	
	/**
	 * 剔除玩家
	 * @param crossPlayer
	 * @param roomId
	 * @param targetPlayerId
	 */
	public void kickPlayer(CrossPlayer crossPlayer, long roomId, long targetPlayerId);
	
	/**
	 * 添加到禁入名单
	 * @param crossPlayer
	 * @param roomId
	 * @param targetPlayerId
	 */
	public void addBanPlayerId(CrossPlayer crossPlayer, long roomId, long targetPlayerId);
	
	/**
	 * 移除禁入名单
	 * @param crossPlayer
	 * @param roomId
	 * @param targetPlayerId
	 */
	public void removeBanPlayerId(CrossPlayer crossPlayer, long roomId, long targetPlayerId);
	
	/**
	 * 编辑聊天室
	 * @param crossPlayer
	 * @param roomId
	 * @param roomName
	 * @param desc
	 * @param password
	 */
	public void editRoomInfo(CrossPlayer crossPlayer, long roomId, String roomName, String desc, boolean password);

	/**
	 * 推送私聊消息
	 * @param playerId
	 * @param targetPlayerId
	 * @param chatMessage
	 */
	public void pushPrivateMessage(long playerId, long targetPlayerId, ChatMessage chatMessage);

	//---------------------------------------牵城------------------------------------------------

	/**
	 * 获取当前x,y上的精灵信息
	 * @param x
	 * @param y
	 * @return
	 */
	public CrossWorldInfo getWorldInfo(int x, int y);
	
	/**
	 * 获取单个精灵详细信息
	 * @param x
	 * @param y
	 * @return
	 */
	public SpriteBean getSpriteInfo(int x, int y);
}
