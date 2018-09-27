package com.xgame.logic.server.game.bag.converter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xgame.common.ItemConf;
import com.xgame.logic.server.game.bag.bean.MsgItem;
import com.xgame.logic.server.game.bag.entity.Item;
import com.xgame.logic.server.game.bag.message.ResBoxItemListMessage;
import com.xgame.logic.server.game.bag.message.ResItemListMessage;
import com.xgame.logic.server.game.bag.message.ResPlayerBagMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBag;

public class ItemConverter {
	
	public static MsgItem converter(Item item) {
		MsgItem msgItem = new MsgItem();
		msgItem.id = item.getId();
		msgItem.itemId = item.getItemId();
		msgItem.num = item.getNum();
		msgItem.startTime = item.getStartTime();
		return msgItem;
	}
	
	/**
	 * 返回道具列表
	 * @param items
	 * @return
	 */
	public static ResItemListMessage getMsgItems(List<Item> items) {
		ResItemListMessage msgItems = new ResItemListMessage();
		msgItems.items = Lists.newArrayList();
		for(Item item : items) {
			msgItems.items.add(converter(item));
		}
		return msgItems;
	}
	
	/**
	 * 推送背包消息对象
	 * @param player
	 * @return
	 */
	public static ResPlayerBagMessage getMsgPlayerBag(Player player) {
		ResPlayerBagMessage bagMessage = new ResPlayerBagMessage();
		PlayerBag bag = player.roleInfo().getPlayerBag();
		bagMessage.size= bag.getItemTable().size(); // 道具数量
		//bagMessage.size = bag.getMax();// 背包最大容量，现在版本没有容量设定
		bagMessage.items = Lists.newArrayList();
		for (Item item : bag.getItemTable().values()) {
			bagMessage.items.add(converter(item));
		}
		return bagMessage;
	}
	
	public static MsgItem converterBox(ItemConf itemConf) {
		MsgItem msgItem = new MsgItem();
		msgItem.itemId = itemConf.getTid();
		msgItem.num = itemConf.getNum();
		return msgItem;
	}
	
	/**
	 * 返回宝箱道具列表
	 * @param items
	 * @return
	 */
	public static ResBoxItemListMessage getBoxItems(List<ItemConf> itemConfs) {
		ResBoxItemListMessage resBoxItemListMessage = new ResBoxItemListMessage();
		resBoxItemListMessage.items = Lists.newArrayList();
		for(ItemConf itemConf : itemConfs) {
			resBoxItemListMessage.items.add(converterBox(itemConf));
		}
		return resBoxItemListMessage;
	}
}
