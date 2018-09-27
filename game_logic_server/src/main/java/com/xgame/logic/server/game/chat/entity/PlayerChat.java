package com.xgame.logic.server.game.chat.entity;

import java.util.ArrayList;
import java.util.List;

import io.protostuff.Tag;


/**
 * 玩家私聊内容
 * @author jacky.jiang
 *
 */
public class PlayerChat {
	
	/**
	 * 聊天消息队列
	 */
	@Tag(1)
	public long playerId;
	
	
	/**
	 * 私聊离线消息
	 */
	@Tag(2)
	public List<ChatMessage> messageQueue = new ArrayList<>();
	
}
