package com.xgame.logic.server.game.bag.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.bag.entity.eventmodel.ItemChangeEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBag;

/**
 * 道具扣减
 * @author jacky.jiang
 *
 */
@Component
public class DeductItem {
	
	/**
	 * 判断道具是否足够
	 * @param tId 模板id
	 * @param num
	 * @return false 数量不足，true数量满足
	 */
	public boolean checkDeductItem(Player player, int tId, int num) {

		ItemsPir config = ItemsPirFactory.get(tId);
		if(config == null) {
			return false;
		}
		
		int playerNum = player.roleInfo().getPlayerBag().getItemNum(tId);
		if(playerNum < num) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 删除玩家道具, 推包
	 * <ul>说明 : 扣除道具从最少的开始扣除<ul>
	 * @param tId
	 * @param num
	 */
	public int deductItem(Player player, int tId, int num, GameLogSource gameLogSource) {
		PlayerBag bag = player.roleInfo().getPlayerBag();
		List<Item> playerItem = player.roleInfo().getPlayerBag().getPlayerItem(tId);
		Collections.sort(playerItem);
		
		// 扣除道具从最少的开始扣除
		List<Item> resultList = new ArrayList<>();
		Iterator<Item> iterator = playerItem.iterator();
		while (iterator.hasNext()) {
			Item item = (Item) iterator.next();
			int tmp = item.getNum();
			item.setNum(item.getNum() - num);
			num -= tmp;
			
			if (item.getNum() <= 0) {
				// 已使用完，从背包移除道具
				bag.getItemTable().remove(item.getId());
				resultList.add(Item.valueOf(item.getItemId(), 0, item.getId()));
			} else {
				resultList.add(item);
				break;
			}
		}
		
		// 推包
		player.send(ItemConverter.getMsgItems(resultList));
		
		// 记录日志
		int resultNum = player.roleInfo().getPlayerBag().getItemNum(tId);
		EventBus.getSelf().fireEvent(new ItemChangeEventObject(tId, player, resultNum + num, resultNum, ItemChangeEventObject.REMOVE,  gameLogSource, 0,ItemChangeEventObject.ITEM_TYPE));
		return num;
	}
	
	
	/**
	 * 删除玩家道具, 推包
	 * <ul>说明 : 扣除道具从最少的开始扣除<ul>
	 * @param uId
	 * @param num
	 */
	public boolean deductItem(Player player, long uId, int num, GameLogSource gameLogSource) {
		
		PlayerBag bag = player.roleInfo().getPlayerBag();
		Item item= player.roleInfo().getPlayerBag().getPlayerItem(uId);
		if(item.getNum() < num) {
			return false;
		}
		
		int tmp = item.getNum();
		item.setNum(item.getNum() - num);
		num -= tmp;
		List<Item> items = new ArrayList<>();
		if (item.getNum() == 0) {
			// 已使用完，从背包移除道具
			bag.getItemTable().remove(item.getId());
			items.add(Item.valueOf(item.getItemId(), 0, item.getId()));
		} else {
			items.add(item);
		}
		
		// 记录日志
		int resultNum = player.roleInfo().getPlayerBag().getItemNum(item.getItemId());
		EventBus.getSelf().fireEvent(new ItemChangeEventObject(item.getItemId(), player, resultNum + num, resultNum, ItemChangeEventObject.REMOVE,  gameLogSource, 0,ItemChangeEventObject.ITEM_TYPE));
		
		// 推包
		player.send(ItemConverter.getMsgItems(items));
		return true;
	}
	
	/**
	 * 使用带有来源的道具
	 * @param player
	 * @param tId
	 * @param num
	 * @return
	 */
	public Map<Integer, Integer> deductOriginItem(Player player,int tId, int num, GameLogSource gameLogSource) {
		PlayerBag bag = player.roleInfo().getPlayerBag();
		List<Item> playerItem = player.roleInfo().getPlayerBag().getPlayerItem(tId);
		Collections.sort(playerItem);
		Collections.reverse(playerItem);
		// 扣除道具从最少的开始扣除
		Iterator<Item> iterator = playerItem.iterator();
		Map<Integer, Integer> resultMap = new HashMap<>();
		List<Item> items = new ArrayList<>();
		while (iterator.hasNext()) {
			Item item = (Item) iterator.next();
			int tmp = item.getNum();
			int deductNum = item.getNum() - num;
			item.setNum(deductNum);
			num -= tmp;
			items.add(item);
			if (item.getNum() <= 0) {
				// 已使用完，从背包移除道具
				bag.getItemTable().remove(item.getId());
				resultMap.putAll(item.getOriginInfo());
			} else {
				// 处理带有来源的道具信息
				int originDeductNum = deductNum;
				Map<Integer, Integer> originInfo = item.getOriginInfo();
				Iterator<java.util.Map.Entry<Integer, Integer>> iter = originInfo.entrySet().iterator();
				while (iter.hasNext()) {
					java.util.Map.Entry<Integer, Integer> entry = iter.next();
					Integer count = entry.getValue();
					Integer key = entry.getKey();
					if (count != null && key != null) {
						int check = count - originDeductNum;
						originDeductNum -= count;
						if (check <= 0) {
							originInfo.remove(entry.getKey());
						} else {
							originInfo.put(key, originDeductNum);
							break;
						}
					}
				}
				break;
			}
		}
		
		// 记录日志
		int resultNum = player.roleInfo().getPlayerBag().getItemNum(tId);
		EventBus.getSelf().fireEvent(new ItemChangeEventObject(tId, player, resultNum + num, resultNum, ItemChangeEventObject.REMOVE,  gameLogSource, 0,ItemChangeEventObject.ITEM_TYPE));
		player.send(ItemConverter.getMsgItems(items));
		return resultMap;
	}
	
}
