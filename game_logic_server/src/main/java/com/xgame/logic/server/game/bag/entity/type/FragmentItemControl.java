package com.xgame.logic.server.game.bag.entity.type;

import java.util.ArrayList;
import java.util.List;

import com.xgame.common.ItemConf;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.game.bag.ItemKit;
import com.xgame.logic.server.game.bag.converter.ItemConverter;
import com.xgame.logic.server.game.bag.entity.Item;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 碎片合成 type=10
 * @author dingpeng.qu
 *
 */
public class FragmentItemControl extends ItemControl {

	public FragmentItemControl(int type) {
		super(type);
	}

	@Override
	public boolean use(Player player, int itemId, int num,ItemContext rewardContext, Object... param) {
		if(num < 1) {
			return false;
		}
		ItemsPir configModel = ItemsPirFactory.get(itemId);
		int v1 = Integer.valueOf(configModel.getV1());
		String v2 = configModel.getV2();
		String[] v2Arr = v2.split("_");
		//新增宝箱
		int boxNum = num/v1;
		List<Item> itemList = new ArrayList<Item>();
		List<ItemConf> itemConfs = new ArrayList<ItemConf>();
		rewardContext = ItemKit.addItem(player, Integer.parseInt(v2Arr[0]), Integer.parseInt(v2Arr[1]) * boxNum, SystemEnum.FRAGMENT, GameLogSource.FRAGMENT_COMPOSE);
		itemList.addAll(rewardContext.getFinalItemList());
		itemConfs.add(new ItemConf(Integer.parseInt(v2Arr[0]),Integer.parseInt(v2Arr[1]) * boxNum));
		//背包更新
		player.send(ItemConverter.getMsgItems(itemList));
		//道具合成结果
		//player.send(ItemConverter.getBoxItems(itemConfs));
		return true;
	}
}
