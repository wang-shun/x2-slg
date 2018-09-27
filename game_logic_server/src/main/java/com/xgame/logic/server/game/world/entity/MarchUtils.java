package com.xgame.logic.server.game.world.entity;

import lombok.extern.slf4j.Slf4j;

import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;

/**
 * 行军工具类
 * @author jacky.jiang
 *
 */
@Slf4j
public abstract class MarchUtils {
	
	/**
	 * 采集时间
	 * @param spriteInfo
	 * @return
	 */
	public static int explorer(SpriteInfo spriteInfo, WorldMarch worldMarch, Player player) {
		try {
			ResourceSprite resourceSprite = (ResourceSprite)spriteInfo.getParam();
			double speed = PlayerAttributeManager.get().getExplorerSpeed(player, resourceSprite.getResourceType(), resourceSprite.getAllianceId(), resourceSprite.getLevel());
			int weight = worldMarch.getWeight(player);
			// 采集负载提升
			int max = mathGiveNum(resourceSprite.getCurNum(), weight, resourceSprite.getResourceType());
			int s = (int)Math.ceil(max / speed);
			return s;
		} catch(Exception e) {
			log.error("计算采集时间出错：", e);
		}
		return 0;
	}
	
	/**
	 * 根据速度计算采集时间
	 * @param spriteInfo
	 * @param worldMarch
	 * @param player
	 * @param speed
	 * @return
	 */
	public static int explorer(double speed, int explorerNum) {
		try {
			// 采集负载提升
			int s = (int)Math.ceil((double)explorerNum / speed);
			if(s <= 0) {
				return 0;
			}
			return s;
		} catch(Exception e) {
			log.error("计算采集时间出错：", e);
		}
		return 0;
	}
	
	/**
	 * 计算能采集的资源数量
	 * @param plusNum
	 * @param weight
	 * @param spriteType
	 * @return
	 */
	public static int mathGiveNum(int plusNum, int weight, CurrencyEnum currencyEnum) {
		int mass = getMass(currencyEnum);
		int n = plusNum;
		int m = weight / mass;
		int max = Math.min(m, n);
		return max;
	}
	
	/**
	 * 计算采集资源数量
	 * @param weight
	 * @param resourceType
	 * @return
	 */
	public static int mathGiveNum(int weight, CurrencyEnum spriteType) {
		int mass = getMass(spriteType);
		int m = weight / mass;
		return m;
	}
	
	/**
	 * 获取资源的总量
	 * @param spriteType
	 * @return
	 */
	public static int getMass(CurrencyEnum spriteType) {
		if(spriteType == CurrencyEnum.OIL) {
			GlobalPir configModel = GlobalPirFactory.get(500604);
			return Integer.parseInt(configModel.getValue());
		} else if(spriteType == CurrencyEnum.RARE) {
			GlobalPir configModel = GlobalPirFactory.get(500603);
			return Integer.parseInt(configModel.getValue());
		} else if(spriteType == CurrencyEnum.GLOD) {
			GlobalPir configModel = GlobalPirFactory.get(500602);
			return Integer.parseInt(configModel.getValue());
		} else if(spriteType == CurrencyEnum.DIAMOND) {
			GlobalPir configModel = GlobalPirFactory.get(500606);
			return Integer.parseInt(configModel.getValue());
		} else if(spriteType == CurrencyEnum.STEEL) {
			GlobalPir configModel = GlobalPirFactory.get(500605);
			return Integer.parseInt(configModel.getValue());
		}
		return 0;
	}
	
}
