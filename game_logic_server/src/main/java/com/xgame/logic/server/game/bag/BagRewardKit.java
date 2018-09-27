package com.xgame.logic.server.game.bag;

import java.util.List;
import java.util.StringTokenizer;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.xgame.common.ItemConf;
import com.xgame.config.item.ItemBox;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.game.awardcenter.AwardUtil;
import com.xgame.logic.server.game.bag.entity.RewardContext;
import com.xgame.logic.server.game.constant.SystemEnum;
import com.xgame.logic.server.game.player.entity.Player;



/**
 * 奖励信息
 * @author jacky.jiang
 *
 */
@Slf4j
public class BagRewardKit {
	
	public static String rewardBuilder(List<ItemBox> list){
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(ItemBox box : list){
			index++;
			sb.append(box.gettId()).append("_").append(box.getNum());
			if(index < list.size()){
				sb.append(",");
			}
		}
		
		return sb.toString();
	}
	
	public static final String SPLIT_REWARD = ",";
	
	public static final String SPLIT_REWARD_COLUMN = "_";
	
	/**
	 * 检查是否能够领取奖励
	 * @param player
	 * @param rewards
	 * @return
	 */
	public static RewardContext checkReward(Player player, String rewardString) {
		RewardContext rewardContext = new RewardContext();
		StringTokenizer tokenizer = new StringTokenizer(rewardString, SPLIT_REWARD);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			String[] tokens = StringUtils.split(token, SPLIT_REWARD_COLUMN);;
			if(tokens == null || tokens.length < 2) {
				log.error(String.format("奖励配置出错:[%s]", rewardString));
				return null;
			}
			
			if(tokens[0].length() == AwardUtil.EQUIT.idType) {
				ItemConf itemConf = new ItemConf(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
				rewardContext.getEquipmentList().add(itemConf);
			} else if(tokens[0].length() == AwardUtil.ITEM.idType) {
				ItemConf itemConf = new ItemConf(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
				rewardContext.getItemConfList().add(itemConf);
			} else if(tokens[0].length() == AwardUtil.PEIJIAN.idType) {
				ItemConf itemConf = new ItemConf(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]));
				rewardContext.getPeijianLsit().add(itemConf);
			}
		}
		return rewardContext;
	}
	
	/**
	 * 发放奖励
	 * @return
	 */
	public static void sendReward(Player player, RewardContext rewardContext, GameLogSource gameLogSource) { 
		
		// 装备
		List<ItemConf> equipments = rewardContext.getEquipmentList();
		if(equipments != null && !equipments.isEmpty()) {
			for(ItemConf itemConf : equipments) {
				for(int i = 0; i < itemConf.getNum();i++) {
					player.getEquipmentManager().addEquipment(itemConf.getTid(), gameLogSource);
				}
			}
		}
		
		// 道具
		List<ItemConf> items = rewardContext.getItemConfList();
		if(items != null && !items.isEmpty()) {
			for(ItemConf itemConf : items) {
				ItemKit.addItemAndTopic(player, itemConf.getTid(), itemConf.getNum(), SystemEnum.COMMON, gameLogSource);
			}
		}
		
		//  配件
		List<ItemConf> peijians = rewardContext.getPeijianLsit();
		if(peijians != null && !peijians.isEmpty()) {
			for(ItemConf itemConf : peijians) {
				player.getCustomWeaponManager().unlockPeijian(player, itemConf.getTid(), gameLogSource);
			}
		}
	}
}
