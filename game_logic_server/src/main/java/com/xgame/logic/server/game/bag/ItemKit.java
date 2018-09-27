package com.xgame.logic.server.game.bag;

import java.util.List;
import java.util.Map;

import com.xgame.common.ItemConf;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.player.entity.Player;

public class ItemKit {
	

	/**
	 * 判断道具是否足够
	 * 
	 * @param tId
	 *            模板
	 * @param num
	 * @return false 数量不足，true数量满足
	 */
	public static boolean checkRemoveItemByTid(Player player, int tId, int num) {
		return player.getItemManager().deductItem.checkDeductItem(player, tId, num);
	}

	/**
	 * 删除玩家指定模板id 模板道具(可能有多个物品), 推包
	 * <ul>
	 * 说明 : 扣除道具从最少的开始扣除
	 * <ul>
	 * 
	 * @param tId 模板id
	 *            
	 * @param num
	 */
	public static int removeItemByTid(Player player, int tId, int num, GameLogSource gameLogSource) {
		return player.getItemManager().deductItem.deductItem(player, tId, num, gameLogSource);
	}

	/**
	 * 删除玩家道具(单个物品), 推包
	 * <ul>
	 * 说明 : 扣除道具从最少的开始扣除
	 * <ul>
	 * 
	 * @param uid 物品唯一id
	 *            
	 * @param num
	 */
	public static boolean removeItemByUid(Player player, long uId, int num, GameLogSource gameLogSource) {
		return player.getItemManager().deductItem.deductItem(player, uId, num, gameLogSource);
	}
	
	/**
	 * 使用带有来源的道具
	 * @param player
	 * @param sid
	 * @param num
	 * @return
	 */
	public static Map<Integer, Integer> useOriginItem(Player player,int sid, int num, GameLogSource gameLogSource) {
		return player.getItemManager().deductItem.deductOriginItem(player, sid, num, gameLogSource);
	}
	
	/**
	 * 添加多个道具(推送道具变化)
	 * @param player
	 * @param confs
	 */
	public static ItemContext addItems(Player player, List<ItemConf> confs, GameLogSource gameLogSource) {
		ItemContext rewardContext = player.getItemManager().rewardItem.addItems(player, confs, gameLogSource);
		return rewardContext;
	}
	
	/**
	 * 添加道具并且发送更新
	 * @param player
	 * @param tId
	 * @param num
	 * @param itemOrigin
	 */
	public static ItemContext addItemAndTopic(Player player,int tId, int num, SystemEnum itemOrigin, GameLogSource gameLogSource) {
		return player.getItemManager().rewardItem.addItemAndTopic(player , tId , num , itemOrigin, gameLogSource);
	}
	
	/**
	 * 添加道具   不推包
	 * 道具堆叠，先从最多的开始堆叠
	 * @param player
	 * @param tId
	 * @param num
	 * @param itemOrigin
	 * @return
	 */
	public static ItemContext addItem(Player player,int tId, int num, SystemEnum itemOrigin, GameLogSource gameLogSource) {
		return player.getItemManager().rewardItem.addItem(player, tId, num, itemOrigin, gameLogSource);
	}
}
