package com.xgame.logic.server.game.shop;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.common.ItemConf;
import com.xgame.config.shop.ShopPir;
import com.xgame.config.shop.ShopPirFactory;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.framework.schedule.manager.ScheduleSystem;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.system.GlobalManager;
import com.xgame.logic.server.core.system.entity.GlobalEnity;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.bean.MsgItem;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.player.PlayerManager;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.shop.bean.ItemBuyBean;
import com.xgame.logic.server.game.shop.bean.MsgShopItem;
import com.xgame.logic.server.game.shop.constant.ShopItemType;
import com.xgame.logic.server.game.shop.constant.ShopPhase;
import com.xgame.logic.server.game.shop.message.ResBatchInfoMessage;
import com.xgame.logic.server.game.shop.message.ResBuyItemMessage;
import com.xgame.logic.server.game.shop.message.ResShopInfoMessage;
import com.xgame.logic.server.game.timertask.SystemTimeManager;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.SystemTimerTaskHolder;
import com.xgame.utils.TimeUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 商店
 * @author lin.lin
 *
 */
@Slf4j
@Component
public class ShopManager  {
	
	public static ResShopInfoMessage commonshopInfoMsg; //商店配置信息缓存
	
	// 普通商品
	public List<MsgShopItem> commonItems = new ArrayList<MsgShopItem>();
	// 特卖商品
	public List<MsgShopItem> temaiItems = new ArrayList<MsgShopItem>();
	
	@Autowired
	private PlayerManager playerManager;
	@Autowired
	private ScheduleSystem schedule;
	@Autowired
	private GlobalManager globalManager;
	@Autowired
	private SystemTimeManager systemTimeManager;
	@Autowired
	private SessionManager sessionManager;

	/**
	 * 获取开服时间
	 * @return
	 */
	public long getServerStartTime() {
		GlobalEnity globalEnity = globalManager.getGlobalEntity();
		return globalEnity.getMapGenerateTime();
	}
	
	/**
	 * 推送商品信息
	 * @param player
	 */
	public void topicShopConfig(Player player) {
		ResShopInfoMessage resShopInfoMessage = wrapShopProto(player);
		player.send(resShopInfoMessage);
	}
	
	/**
	 * 获取批次特卖商品
	 * @return
	 */
	private static List<MsgShopItem> getBatchItems(Player player) {
		List<MsgShopItem> shopItems = Lists.newArrayList();
		
		// 特卖批次
		GlobalEnity globalEnity = InjectorUtil.getInjector().globalManager.getGlobalEntity();
		List<ShopPir> teMaiList = ShopPirFactory.getInstance().getBatchList(globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch());
		if (teMaiList != null) {
			// 特卖开始时间
			for(ShopPir item : teMaiList) {
				// 未过期的商品
				if(globalEnity.getBatchStartTime() + item.getTime() > System.currentTimeMillis() / 1000) {
					MsgShopItem msgShopItem = getMsgShopItem(item, globalEnity.getBatchStartTime());
					
					// 购买记录
					if(player != null) {
						int count = player.roleInfo().getPlayerShop().queryItemRecord(globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch(), globalEnity.getRound(), item.getId());
						msgShopItem.buyCount = count;
					}
					
					shopItems.add(msgShopItem);
				}
			}
		}
		return shopItems;
	}
	
	/**
	 * 商店配置
	 * @throws ParseException 
	 */
	public ResShopInfoMessage wrapShopProto(Player player) {
		// 重新加载配置
		ResShopInfoMessage msg = new ResShopInfoMessage();
		//
		GlobalEnity globalEnity = InjectorUtil.getInjector().globalManager.getGlobalEntity();
		for(MsgShopItem msgShopItem : commonItems){
			int count = player.roleInfo().getPlayerShop().queryItemRecord(globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch(), globalEnity.getRound(), msgShopItem.id);
			msgShopItem.buyCount = count;
		}
		msg.shopItems.addAll(commonItems);
		
		List<MsgShopItem> shopTeMai = getBatchItems(player);
		msg.shopItems.addAll(shopTeMai);
		
		// 开服第N天开启第一次特卖，后面就是循环模式
		msg.specialNum = shopTeMai.size();
		return msg;
	}
	
	/**
	 * 检验是否还在售卖时间内
	 * @param endTime
	 * @return true 是的可售卖
	 */
	private boolean checkEndTime(long endTime) {
		GlobalEnity globalEnity = globalManager.getGlobalEntity();
		if((globalEnity.getBatchStartTime() + endTime) * 1000L > System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	private static MsgShopItem getMsgShopItem(ShopPir model, long startTime) {
		MsgShopItem item = new MsgShopItem();
		if(model == null) {
			return null;
		}
		
		//
		item.id  = model.getId();	//ID
		item.shop_number = model.getShop_number();
		item.name = model.getName();
		item.quality = model.getQuality();
		item.description = model.getDescription();
		item.type = model.getType();
		item.price = model.getPrice();
		item.tag = model.getTag();
		item.special_price = model.getSpecial_price();
		item.fast_price = model.getFast_price();
		item.ceili = model.getCeili();
		item.icon = model.getIcon();
		item.time = model.getTime() + startTime; // 活动结束时间 = 活动开始时间 +　售卖时间
		List<MsgItem> awards = Lists.newArrayList();
		List<ItemConf> items = model.getItem_id();
		for(ItemConf i : items) {
			MsgItem award = new MsgItem();
			award.itemId = i.getTid();
			award.num = i.getNum();
			awards.add(award);
		}
		item.items = awards;
		
		// 
		return item;
	}

//	/**
//	 * 商店购买，扣费发道具
//	 * @param player
//	 * @param id 商品ID
//	 * @param num 商品数量
//	 */
//	public int buyItem(Player player, int id, int num) {
//		ShopPir configModel = ShopPirFactory.get(id);
//		int code = checkBuy(player, configModel, num);
//		if(code < 0) {
//			return code; // 购买失败消息处理
//		}
//		// 计算金额
//		long money = configModel.getSpecial_price() * num;
//		
//		//1,扣费
//		CurrencyUtil.DIAMOND.decrement(player, money, LogFactory.DIAMOND_DEL);
//		CurrencyUtil.send(player);
//		
//		//2,发道具
//		List<ItemConf> items = configModel.getItem_id();
//		for(ItemConf gift : items) {
//			player.getItemManager().rewardItem.addItemAndTopic(player, gift.getItemId(), gift.getNum() * num, SystemEnum.SHOP);
//		}
//		return 0;
//	}
	
	/**
	 * 商店购买并推包，扣费发道具
	 * @param player
	 * @param id 商品ID
	 * @param num 商品数量
	 */
	public int buyItemAndTopic(Player player, int id, int num) {
		ShopPir configModel = ShopPirFactory.get(id);
		int code = checkBuy(player, configModel, num);
		if(code < 0) {
			return code; // 购买失败消息处理
		}
		
		GlobalEnity globalEnity = globalManager.getGlobalEntity();
		int count = player.roleInfo().getPlayerShop().queryItemRecord(globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch(), globalEnity.getRound(), id);
		if(configModel.getType() == ShopItemType.HOT) {
			if(configModel.getShop_number() < count + num) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E120_SHOP.CODE5);
				return -5;
			}
		}

		// 计算金额
		long money = configModel.getSpecial_price() * num;
		
		//1,扣费
		CurrencyUtil.decrement(player, money, CurrencyEnum.DIAMOND, GameLogSource.SHOP_BUY);
		CurrencyUtil.send(player);
		
		// 更新已购买次数
		count = player.roleInfo().getPlayerShop().addItemRecord(globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch(), globalEnity.getRound(), id, num);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		//2,发道具
		List<Integer> itemIds = Lists.newArrayList();
		List<ItemConf> items = configModel.getItem_id();
		for(ItemConf gift : items) {
			itemIds.add(gift.getTid());
			ItemKit.addItem(player, gift.getTid(), gift.getNum() * num, SystemEnum.SHOP, GameLogSource.SHOP_BUY);
		}
		
		// 返回已购买数量
		ResBuyItemMessage resBuyItemMessage = new ResBuyItemMessage();
		ItemBuyBean itemBuyBean = new ItemBuyBean();
		itemBuyBean.id = id;
		itemBuyBean.num = count;
		resBuyItemMessage.shopBuyItem = itemBuyBean;
		
		// 推送剩余数量
		player.send(resBuyItemMessage);
		
		// 3,推包
		player.getItemManager().sendPlayerBagClient(itemIds);
		return 0;
	}
	
	/**
	 * 检验购买
	 * @return
	 */
	private int checkBuy(Player player, ShopPir config, int num) {
		if(config == null) {
			return -1;  // FIXME 参数异常:商品不存在
		}
		if(num < 1) {
			return -2;	// FIXME 参数异常:数量异常
		}
		if(config.getType() == 5  && !checkEndTime(config.getTime())) {
			return -3; // FIXME 参数异常:商品已下架
		}
		long money = config.getSpecial_price() * num;
		if(!CurrencyUtil.verify(player, money , CurrencyEnum.DIAMOND)) {
			return -4;	// FIXME 钻石数量不足
		}
		return 0;
	}
	
	
	//-------------------------GM--------------------------
	/**
	 * 指定特卖批次和开始时间
	 */
	public void gmSetTeMaiBatch(int batchNum) {
//		// 重新计算批次并推送
//		ResShopInfoMessage resShopInfoMessage = wrapShopProto();
//		sessionManager.broadcast(resShopInfoMessage);
	}
	

	@Startup(order = StartupOrder.SHOP_START, desc = "商品启动加载")
	public void start() {
		boolean exist = InjectorUtil.getInjector().systemTimeManager.existTimerTask(SystemTimerCommand.SHOP.ordinal());
		if(!exist) {
			GlobalEnity globalEnity = globalManager.getGlobalEntity();

			if(globalEnity.getBatchStartTime() <= 0) {
				synchronized (globalEnity) {
					globalEnity.setShopCurrentPhase(ShopPhase.SERVER_OPEN_PHASE);
					globalEnity.setShopCurrentBatch(1);
					globalEnity.setBatchStartTime(TimeUtils.getCurrentTime());
					globalEnity.setRound(1);
					InjectorUtil.getInjector().dbCacheService.update(globalEnity);
				}
				
				Integer time = ShopPirFactory.getInstance().batch1Time.get(globalEnity.getShopCurrentBatch());
				if(time == null) {
					log.error("");
					return;
				}
				
				// 注册商城活动
				SystemTimerTaskHolder.getTimerTask(SystemTimerCommand.SHOP).register(time);
			} else if(globalEnity.getBatchStartTime() > 0) {
				// 注册商城活动
				SystemTimerTaskHolder.getTimerTask(SystemTimerCommand.SHOP).register(globalEnity.getBatchStartTime() - TimeUtils.getCurrentTime());
			}
		} 
		
		//初始化商城商品
		for(Integer key : ShopPirFactory.getInstance().getFactory().keySet()) {
			ShopPir item = ShopPirFactory.get(key);
			// 不是特卖批次的直接放入
			if(item.getType() == 5) {
				continue;				
			}
			
			commonItems.add(getMsgShopItem(item, 0));
		}
		
		List<MsgShopItem> shopTeMai = getBatchItems(null);
		temaiItems.addAll(shopTeMai);
	}
	
	/**
	 * 刷新批次
	 */
	public void refreshShopBatch()  {
		this.temaiItems = getBatchItems(null);
		ResBatchInfoMessage resBatchInfoMessage = new ResBatchInfoMessage();
		resBatchInfoMessage.shopItems.addAll(temaiItems);
	}
}
