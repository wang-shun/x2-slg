package com.xgame.logic.server.game.world.entity.march;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;


/**
 * 世界行军
 * @author jacky.jiang
 *
 */
public interface IMarchExecutor {
	
	
	/**
	 * 验证能否出征
	 */
	public boolean checkMarch();
	
	/**
	 * 出征
	 */
	WorldMarch march();
	
	/**
	 * 到达目的地
	 */
	void toDestination();
	
	/**
	 * 返回
	 */
	void handleReturn();
	
	/**
	 * 到家
	 */
	void backReturnHome();
	
	/**
	 * 非正常返回
	 */
	void failReturn();
	
	/**
	 * 加速
	 */
	void speedUp(double percent);
	
	/**
	 * 结束战斗直接回老家
	 * @param worldMarch
	 */
	void backHomeImmediately(WorldMarch worldMarch);
	
	/**
	 * 推送全服
	 * @param player
	 * @param worldMarch
	 */
	void pushToWorldMarch(Player player, WorldMarch worldMarch);
	
	/**
	 * 设置行军队列
	 * @param worldMarch
	 */
	void setWorldMarch(WorldMarch worldMarch);
	
	/**
	 * 通知自己行军信息发生变化
	 * @param player
	 * @param worldMarch
	 */
	void pushSelfWorldMarch();
	
	/**
	 * 初始化战斗
	 */
	public boolean initBattle();
	
	/**
	 * 刷新精灵年信息
	 * @param spriteInfo
	 */
	void refreshLocation(SpriteInfo spriteInfo);
	
	/**
	 * 处理重新加载
	 */
	void dealReload();
}
