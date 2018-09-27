package com.xgame.util.rmi;


/**
 * 管理后台rmi入口
 * @author jacky.jiang
 *
 */
public interface AdminRmiService {
	
	/**
	 * 给玩家发送邮件
	 * @param targetPlayerId
	 */
	public void sendMail(long targetPlayerId);
	
	/**
	 * 发送全服邮件
	 */
	public void sendServerEmail();

	/**
	 * 私聊单个玩家
	 * @param targetPlayerId
	 */
	public void sendChat(long targetPlayerId);
}
