package com.xgame.logic.server.game.war;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Delayed;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.framework.lifecycle.Shutdown;
import com.xgame.framework.lifecycle.ShutdownOrder;
import com.xgame.framework.lifecycle.Startup;
import com.xgame.framework.lifecycle.StartupOrder;
import com.xgame.logic.server.core.utils.scheduler.impl.FixTimeDelayQueue;
import com.xgame.logic.server.game.war.entity.queue.WarFinishQueue;
import com.xgame.logic.server.game.world.WorldLogicManager;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;



/**
 * 战斗结束处理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
public class WarFinishQueueManager {
	
	@Autowired
	private WorldLogicManager worldLogicManager;
	
	/**
	 * 延迟队列
	 */
	private FixTimeDelayQueue<Delayed> delayQueue = new FixTimeDelayQueue<>(30000);
	
	/**
	 * 野外战斗等待队列
	 */
	private Thread thread = null;
	
	@Startup(order = StartupOrder.WILD_BATTLE_REPORT_PUSH, desc = "战斗推送线程初始化")
	public void init() {
		if(thread != null) {
			thread.interrupt();
		}
		
		// 野外战斗推送线程
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Delayed delayed = delayQueue.take();
						if(delayed != null && delayed instanceof WarFinishQueue) {
							WarFinishQueue battlePushQueue = (WarFinishQueue)delayed;

							// 世界地图线程处理
							worldLogicManager.getWorldExecutor().execute(new Runnable() {
								@Override
								public void run() {
									// 处理野外迁城
									SpriteInfo spriteInfo = battlePushQueue.getSpriteInfo();
									if(spriteInfo != null) {
										WorldSprite worldSprite = spriteInfo.getParam();
										if(worldSprite != null && worldSprite.isMoveCity()) {
											CountDownLatch countDownLatch = worldSprite.getCountDownLatch();
											if(countDownLatch != null) {
												countDownLatch.countDown();
											}
										}
										
										//　下一场战斗不存在就推送
										worldLogicManager.dealNextBattleAction(spriteInfo, battlePushQueue);
									}
								
								}
							});
						}
					} catch (Exception e) {
						log.error("战斗延迟推送线程异常:", e);
					}
				}
			}
		});
		
		thread.setDaemon(true);
		thread.start();
	}
	
	public void addBattleFinishQueue(WarFinishQueue battlePushQueue) {
		this.delayQueue.add(battlePushQueue);
	}
	
	@Shutdown(order = ShutdownOrder.WILD_BATTLE_REPORT_PUSH, desc = "战斗推送线程关闭")
	public void stop() {
		thread.interrupt();
	}
}
