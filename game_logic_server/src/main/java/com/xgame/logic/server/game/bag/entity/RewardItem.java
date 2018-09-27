package com.xgame.logic.server.game.bag.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.common.ItemConf;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.data.item.ItemType;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.ItemSequance;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.bag.entity.eventmodel.ItemChangeEventObject;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBag;


/**
 * 道具奖励
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class RewardItem {
	
	@Autowired
	private ItemSequance itemSequance;
	
	/**
	 * 添加多个道具(推送道具变化)
	 * @param player
	 * @param confs
	 */
	public ItemContext addItems(Player player, List<ItemConf> confs, GameLogSource gameLogSource) {
		ItemContext rewardContext = new ItemContext();
		if(confs!=null) {
			Iterator<ItemConf> iterator = confs.iterator();
			while (iterator.hasNext()) {
				ItemConf itemConf = (ItemConf) iterator.next();
				ItemContext oneItemContext = addItem(player, itemConf.getTid(), itemConf.getNum(), SystemEnum.COMMON, gameLogSource);
				rewardContext.combine(oneItemContext);
			}
			InjectorUtil.getInjector().dbCacheService.update(player);
		}
		player.send(ItemConverter.getMsgItems(rewardContext.getFinalItemList()));
		return rewardContext;
	}
	
	/**
	 * 添加道具并且发送更新
	 * @param player
	 * @param tId
	 * @param num
	 * @param itemOrigin
	 */
	public ItemContext addItemAndTopic(Player player,int tId, int num, SystemEnum itemOrigin, GameLogSource gameLogSource) {
		ItemContext context = addItem(player, tId, num, itemOrigin, gameLogSource);
		player.send(ItemConverter.getMsgItems(context.getFinalItemList()));
		return context;
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
	public ItemContext addItem(Player player,int tId, int num, SystemEnum itemOrigin, GameLogSource gameLogSource) {
		List<Item> addList = new ArrayList<Item>(2);
		ItemsPir cfg = ItemsPirFactory.get(tId);
		if(cfg == null) {
			return null;
		}
		
		ItemContext rewardContext = new ItemContext();
		if(cfg.getType() == ItemType.REWARD_AND_USE_ITEM.ordinal()) {
			ItemFactory itemFactory = ItemFactory.factory.get(cfg.getType());
			if(itemFactory == null) {
				log.error("程序异常：道具类型出错:"+cfg.getType());
				return null;
			}
			
			itemFactory.getControl().use(player, tId, num, rewardContext);
			return rewardContext;
		}
		
		if(cfg.getType() != ItemType.ORIGIN_ITEM.ordinal() && itemOrigin != SystemEnum.COMMON) {
			PlayerBag bag = player.roleInfo().getPlayerBag();
			// 已有道具
			List<Item> hasItems = bag.getPlayerItem(tId);
			for (Item i : hasItems) {
				if (num > 0) {
					int addSize = cfg.getMax() - i.getNum();
					if (num <= addSize) { // 本格可以放入所有
						i.setNum(i.getNum() + num);
						num = 0;
						addList.add(i); // 添加到返回的列表中
						break;
					} else {
						i.setNum(cfg.getMax());
						addList.add(i);
						num = num - addSize;
					}
				}
			}
			
			// 是否继续放入新格子
			if (num > 0) {
				int objSize = num / cfg.getMax(); // 需要放满的格子数
				int singleSize = num % cfg.getMax(); // 单个的大小
				//
				for (int i = 0; i < objSize; i++) {
					Item item = Item.valueOf(tId, cfg.getMax(), itemSequance.genItemId());
					bag.getItemTable().put(item.getId(), item);
					addList.add(item);
				}
				
				// 独占不满的格子
				if (singleSize > 0) {
					Item item = Item.valueOf(tId, singleSize,itemSequance.genItemId());
					bag.getItemTable().put(item.getId(), item);
					addList.add(item);
					
				}
			}
		} else {
			PlayerBag bag = player.roleInfo().getPlayerBag();
			// 已有道具
			List<Item> hasItems = bag.getPlayerItem(tId);
			for (Item i : hasItems) {
				if (num > 0) {
					int addSize = cfg.getMax() - i.getNum();
					if (num <= addSize) { // 本格可以放入所有
						i.setNum(i.getNum() + num);
						num = 0;
						addList.add(i); // 添加到返回的列表中
						break;
					} else {
						i.setNum(cfg.getMax());
						num = num - addSize;
					}
					
					// 背包当中道具叠加
					addMap(i.getOriginInfo(), itemOrigin.ordinal(), num);
				}
			}
			
			// 是否继续放入新格子
			if (num > 0) {
				int objSize = num / cfg.getMax(); // 需要放满的格子数
				int singleSize = num % cfg.getMax(); // 单个的大小
				//
				for (int i = 0; i < objSize; i++) {
					Item item = Item.valueOf(tId, cfg.getMax(), itemSequance.genItemId());
					bag.getItemTable().put(item.getId(), item);
					addList.add(item);
					
					// 处理带有来源的道具信息叠加
					addMap(item.getOriginInfo(), itemOrigin.ordinal(), num);
				}
				
				// 独占不满的格子
				if (singleSize > 0) {
					Item item = Item.valueOf(tId, singleSize, itemSequance.genItemId());
					bag.getItemTable().put(item.getId(), item);
					addList.add(item);
					
					// 处理带有来源的道具信息叠加
					addMap(item.getOriginInfo(), itemOrigin.ordinal(), num);
				}
			}
		}
		
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		// 记录日志
		int resultNum = player.roleInfo().getPlayerBag().getItemNum(tId);
		EventBus.getSelf().fireEvent(new ItemChangeEventObject(tId, player, resultNum - num, resultNum, ItemChangeEventObject.ADD,  gameLogSource, 0,ItemChangeEventObject.ITEM_TYPE));
		
		//返回
		rewardContext.addItemConf(tId, num, addList);
		return rewardContext;
	}
	
	/**
	 * 添加结果集
	 * @param originMap
	 * @param key
	 * @param value
	 */
	private void addMap(Map<Integer, Integer> originMap, Integer key, Integer value) {
		if(key != null && value != null) {
			if(originMap == null) {
				originMap = new HashMap<>();
			}
			
			Integer originValue = originMap.get(key);
			if(originValue != null) {
				originMap.put(key, originValue + value);
			} else {
				originMap.put(key, value);
			}
		}
	}
}
