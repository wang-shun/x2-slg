package com.xgame.logic.server.gm;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.gm.annotation.Admin;

/**
 *
 *2016-8-23  16:06:37
 *@author ye.yuan
 *
 */
public interface IGMProxySystem {

	/**
	 * 设置服务器开服时间
	 * @param player
	 * @param date
	 */
	@Admin(level = 10)
	void sst(Player player, String date);
	
	/**
	 * 添加全部物品
	 * @param player
	 * @param num
	 */
	@Admin(level = 10)
	void agi(Player player,int num);
	
	/**
	 * 增加所有货币
	 * @param player
	 * @param x
	 */
	@Admin(level = 10)
	void aic(Player player,int x);
	
	/**
	 * 添加指定货币
	 * @param player
	 * @param type
	 * @param x
	 */
	@Admin(level = 10)
	void ic(Player player,int type,int x);
	
	/**
	 * 减少指定货币
	 * @param player
	 * @param type
	 * @param x
	 */
	@Admin(level = 10)
	void dc(Player player,int type,int x);
	
	/**
	 * 添加指定id道具
	 * @param player
	 * @param sid
	 * @param num
	 */
	@Admin(level = 10)
	void ai(Player player,int sid,int num);
	
	@Admin(level = 0)
	void invoke(Player player,String param);
	
	@Admin(level = 0)
	void speak(Player player);
	
	/**
	 * 添加钻石
	 * @param player
	 * @param x
	 */
	@Admin(level = 10)
	void ad(Player player,int x);
	
	/**
	 * 添加石油
	 * @param player
	 * @param x
	 */
	@Admin(level = 10)
	void ao(Player player,int x);
	
	/**
	 * 添加钢铁
	 * @param player
	 * @param x
	 */
	@Admin(level = 10)
	void as(Player player,int x);
	
	/**
	 * 添加稀土
	 * @param player
	 * @param x
	 */
	@Admin(level = 10)
	void ar(Player player,int x);
	
	/**
	 * 添加黄金
	 * @param player
	 * @param x
	 */
	@Admin(level = 10)
	void am(Player player,int x);

	@Admin(level = 0)
	void task(Player player);
	
	@Admin(level = 0)
	void refresh(Player player, String file);
	
	@Admin(level = 0)
	void reloadConfig(Player player, String file);
	
	/**
	 * 触发日志系统
	 * @param eid 事件ID
	 * @param val 值， 如收获钢材1000 ，   alog 30000 1000
	 */
	@Admin(level = 0)
	public void alog(Player player, int eid, int val);
	
	/**
	 * 设置活跃度完成 set active finish
	 * @param aid 活跃ID
	 * @param val 完成次数
	 */
	@Admin(level = 0)
	public void saf(Player player, int aid, int val);
	
	/**
	 * 添加指挥官经验
	 * addCommanderExp
	 * @param player
	 */
	@Admin(level = 0)
	void ace(Player player,long exp);
	
	/**
	 * 添加装备类型1-5
	 * addEquitment1_5
	 * @param player
	 */
	void ae1_5(Player player,int minLevel,int maxLevel);
	
	/**
	 * 从指定来源添加道具   
	 * addFromItem
	 * @param player
	 */
	void afi(Player player,int itemId,int num,int fromId);
	
	/**
	 * 扎营
	 * @param player
	 */
	void camp(Player player);
	
	/**
	 * 清除数据
	 * @param player
	 */
	void clear(Player player);
	
	@Admin(level = 0)
	void tm(Player player,short type,String subject, String content, String adjunctJson);
	
	/**
	 * 从指定来源添加道具   
	 * attributeNodePrint
	 * @param player
	 */
	@Admin(level = 0)
	void anp(Player player,int nodeId);
	
	
	@Admin(level = 0)
	void sbm(Player player,int sampleId);
	
	/**
	 * 建筑升级
	 * @param player
	 * @param level
	 */
	@Admin(level = 0)
	void blu(Player player,int level);
	
	/**
	 * 解锁所有配件
	 * @param player
	 */
	@Admin(level = 0)
	void weapon(Player player);
	
	/**
	 * 增加指定ID道具
	 * @param player
	 * @param itemId
	 * @param num
	 */
	@Admin(level = 0)
	void ui(Player player,int itemId,int num);
	
	/**
	 * 完成指定id任务
	 * finishTask
	 * @param player
	 * @param id
	 */
	@Admin(level = 0)
	public void ft(Player player,int type, int taskId);
	
	/**
	 * 发送属性邮件
	 * @param player
	 */
	@Admin(level = 0)
	public void attr(Player player);
	

	/**
	 * 副本
	 * @param player
	 */
	@Admin(level = 0)
	public void copy(Player player,int maxCopyId,int maxPointIndex,int star);
	
	/**
	 * 重置副本
	 * @param player
	 */
	@Admin(level = 0)
	public void resetCopy(Player player);
}
