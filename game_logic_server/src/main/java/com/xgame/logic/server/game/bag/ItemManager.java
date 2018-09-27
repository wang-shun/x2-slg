package com.xgame.logic.server.game.bag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.attribute.LibraryConf;
import com.xgame.config.fastPaid.FastPaidPir;
import com.xgame.config.fastPaid.FastPaidPirFactory;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.bag.entity.DeductItem;
import com.xgame.logic.server.game.bag.entity.Item;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemFactory;
import com.xgame.logic.server.game.bag.entity.RewardItem;
import com.xgame.logic.server.game.bag.entity.eventmodel.UseItemEventObject;
import com.xgame.logic.server.game.buff.constant.BuffAdditionConstant;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBuff;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.IAttributeAddModule;
import com.xgame.logic.server.game.playerattribute.constant.AttributeFromEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;


/**
 * 玩家道具使用
 * @author jacky.jiang
 *
 */
@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemManager extends AbstractComponent<Player> implements IAttributeAddModule {
	
	@Autowired
	public RewardItem rewardItem;
	@Autowired
	public DeductItem deductItem;
	
	/**
	 * TODO 使用指定物品
	 * 修改逻辑 批量使用道具不足 使用完所有剩余道具
	 * @param itemId
	 * @param num
	 * @param autoBuy 自动购买
	 * @param param
	 * @return
	 */
	//道具数量
	public boolean useItem(long id, int itemId, int num, boolean autoBuy, Object ... param) {
		//道具使用标识
		boolean flag = false;
		int tab;
		if(id > 0) {
			Item item = this.getPlayer().roleInfo().getPlayerBag().getPlayerItem(id);
			if(item.getNum() < num) {
				num = item.getNum();
			}
			ItemsPir configModel = ItemsPirFactory.get(itemId);
			tab = configModel.getTab();
			ItemFactory itemFactory = ItemFactory.factory.get(configModel.getType());
			if (itemFactory != null) {
				flag = itemFactory.getControl().use(this.getPlayer(), itemId, num, new ItemContext(), param);
			}
			
			if (flag) {
				ItemKit.removeItemByUid(this.getPlayer(), id, num, GameLogSource.USE_ITEM);
				InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
			}
		} else {
			int deductNum = 0;
			int playerNum = this.getPlayer().roleInfo().getPlayerBag().getItemNum(itemId);
			if(!autoBuy) {
				if(playerNum < num) {
					num = playerNum;
				}
				deductNum = num;
			} else {
				int plusNum = num - playerNum;
				if(plusNum > 0) {
					FastPaidPir configModel = FastPaidPirFactory.get(itemId);
					if(configModel == null) {
						Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE6,FastPaidPir.class.getSimpleName(),itemId);
						return flag;
					}
					if (CurrencyUtil.decrement(this.getPlayer(), configModel.getPrice() * plusNum , CurrencyEnum.DIAMOND, GameLogSource.EDIT_ALLIANCE_INFO)) {
						CurrencyUtil.send(this.getPlayer());
						//加道具防止使用失败
						ItemKit.addItemAndTopic(this.getPlayer(), itemId, plusNum, SystemEnum.FASTPAID, GameLogSource.FAST_PAID);
					} else {
						Language.ERRORCODE.send(this.getPlayer(), ErrorCodeEnum.E001_LOGIN.CODE16);
						return flag;
					}
				}
				deductNum = num;
			}
			
			ItemsPir configModel = ItemsPirFactory.get(itemId);
			tab = configModel.getTab();
			ItemFactory itemFactory = ItemFactory.factory.get(configModel.getType());
			if (itemFactory != null) {
				flag = itemFactory.getControl().use(this.getPlayer(), itemId, deductNum, new ItemContext(), param);
			}
			// 扣除玩家身上的道具
			if(flag){
				ItemKit.removeItemByTid(this.getPlayer(), itemId, deductNum, GameLogSource.USE_ITEM);
			}
			InjectorUtil.getInjector().dbCacheService.update(this.getPlayer());
		}
		EventBus.getSelf().fireEvent(new UseItemEventObject(this.getPlayer(),itemId,tab,num));
		return flag;
	}
	
	/**
	 * 同步玩家背包道具
	 * @param itemIds
	 */
	public void sendPlayerBagClient(List<Integer> itemIds) {
		List<Item> list = new ArrayList<>();
		if(itemIds != null && !itemIds.isEmpty()) {
			for(Integer itemId : itemIds) {
				List<Item> itemList = getPlayer().roleInfo().getPlayerBag().getPlayerItem(itemId);
				list.addAll(itemList);
			}
		}
		this.getPlayer().send(ItemConverter.getMsgItems(list));
	}

	@Override
	public Map<AttributeNodeEnum,Double> attributeAdd(Player player, AttributesEnum attributeEnum,AttributeNodeEnum... attributeNodeEnums) {
		double value = 0;
		Map<String, PlayerBuff> map = player.roleInfo().getPlayerBuffData().getBuffMap();
		Map<AttributeNodeEnum,Double> valueOfNodeMap = new HashMap<>();
		for(PlayerBuff buff : map.values()){
			if (buff.getEndTime() < System.currentTimeMillis()) {
				continue;
			}
			Map<String, Object> buffAddition = buff.getBuffAddition();
			if(buffAddition != null && buffAddition.containsKey(BuffAdditionConstant.ITEM_ID)){
				for(Object itemId : buffAddition.values()){
					ItemsPir pir = ItemsPirFactory.get(Double.valueOf(itemId.toString()).intValue());
					if(pir != null && pir.getType() == 3){
						AttributeConfMap confMap = pir.getV1();
						if(confMap != null) {
							for(AttributeNodeEnum node : AttributeNodeEnum.values()){
								if(attributeNodeEnums.length > 0 && Arrays.binarySearch(attributeNodeEnums,node) < 0) {
									continue;
								}
								
								double nodeValue = 0;
								LibraryConf libraryConf = confMap.getLibraryConfs().get(1, node.getCode());
								if(libraryConf != null && libraryConf.containsKey(attributeEnum.getId())){
									nodeValue = libraryConf.get(attributeEnum.getId());
									value += nodeValue;
									if(nodeValue > 0){
										if(!valueOfNodeMap.containsKey(node)){
											valueOfNodeMap.put(node, nodeValue);
										}else{
											valueOfNodeMap.put(node, valueOfNodeMap.get(node.getCode()) + nodeValue);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(valueOfNodeMap.size() > 0){
			log.info(AttributeUtil.logBuilder(valueOfNodeMap, attributeEnum.getId(), value,ItemsPir.class.getSimpleName()));
		}
		return valueOfNodeMap;
	}

	@Override
	public Map<AttributeFromEnum, Double> selectAttributeAdd(Player player,
			AttributesEnum attributeEnum, AttributeNodeEnum node) {
		Map<AttributeNodeEnum,Double> nodeAttrMap = attributeAdd(player,attributeEnum, node);
		Map<AttributeFromEnum, Double> resultMap = new HashMap<>();
		if(nodeAttrMap.containsKey(node)){
			resultMap.put(AttributeFromEnum.ITEM, nodeAttrMap.get(node));
		}
		return resultMap;
	}
}
