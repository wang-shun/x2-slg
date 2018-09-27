package com.xgame.logic.server.game.timertask.entity.system;

import java.util.TreeSet;

import com.xgame.config.shop.ShopPirFactory;
import com.xgame.logic.server.core.system.entity.GlobalEnity;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.shop.constant.ShopPhase;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;
import com.xgame.utils.TimeUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * 商城时间任务
 * 商城活动（开服一个月为第一阶段，开服一个月之后为第二个阶段，开服一个月之后按轮数不停的循环）
 * @author jacky.jiang
 *
 */
@Slf4j
public class ShopTimerTask extends AbstractSystemTimerTask<SystemTimerTaskData> {
	
	//商城任务id
	public ShopTimerTask(SystemTimerCommand timerTaskCommand) {
		super(timerTaskCommand);
	}
	
	@Override
	public SystemTimerTaskData getSystemTimerTaskData(Object... params) {
		SystemTimerTaskData systemTimerTaskData = new SystemTimerTaskData();
		return systemTimerTaskData;
	}
	
	@Override
	public void initSystemTimerTaskData(SystemTimerTaskData t, int taskTime) {
		super.initSystemTimerTaskData(t, taskTime);
	}
	
	@Override
	public boolean onExecute(SystemTimerTaskData data) {
		// 商城批次刷新规则
		GlobalEnity globalEnity = InjectorUtil.getInjector().globalManager.getGlobalEntity();
		if(globalEnity != null) {
			Integer batch = null;
			if(globalEnity.getShopCurrentPhase() == ShopPhase.SERVER_OPEN_PHASE) {
				TreeSet<Integer> treeSet = new TreeSet<>();
				treeSet.addAll(ShopPirFactory.getInstance().batch1Time.keySet());
				batch = treeSet.last();
			} else {
				TreeSet<Integer> treeSet = new TreeSet<>();
				treeSet.addAll(ShopPirFactory.getInstance().batch2Time.keySet());
				batch = treeSet.last();
			}
			
			if(globalEnity.getShopCurrentPhase() == ShopPhase.SERVER_OPEN_PHASE && globalEnity.getShopCurrentBatch() == batch) {
				
				Integer time = ShopPirFactory.getInstance().batch2Time.get(globalEnity.getShopCurrentBatch());
				if(time == null) {
					log.error("策划配置商城开服活动出错,阶段:[{}],批次:[{}]", globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch());
					return false;
				}
				
				synchronized (globalEnity) {
					globalEnity.setShopCurrentBatch(1);
					globalEnity.setShopCurrentPhase(ShopPhase.COMMON_PHASE);
					globalEnity.setBatchStartTime(TimeUtils.getCurrentTime());
					InjectorUtil.getInjector().globalManager.update(globalEnity);
				}
				
				
				register(time);
			} else if(globalEnity.getShopCurrentPhase() == ShopPhase.COMMON_PHASE && globalEnity.getShopCurrentBatch() == batch) {
				Integer time = ShopPirFactory.getInstance().batch2Time.get(globalEnity.getShopCurrentBatch());
				if(time == null) {
					log.error("策划配置商城开服活动出错,阶段:[{}],批次:[{}]", globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch());
					return false;
				}
				
				synchronized (globalEnity) {
					globalEnity.setShopCurrentBatch(1);
					globalEnity.setShopCurrentPhase(ShopPhase.COMMON_PHASE);
					globalEnity.setRound(globalEnity.getRound() + 1);
					
					globalEnity.setBatchStartTime(TimeUtils.getCurrentTime());
					InjectorUtil.getInjector().globalManager.update(globalEnity);
				}
				
				register(time);
			} else if(globalEnity.getShopCurrentPhase() == ShopPhase.SERVER_OPEN_PHASE) {
				Integer time = ShopPirFactory.getInstance().batch1Time.get(globalEnity.getShopCurrentBatch());
				if(time == null) {
					log.error("策划配置商城开服活动出错,阶段:[{}],批次:[{}]", globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch());
					return false;
				}
				
				synchronized (globalEnity) {
					globalEnity.setShopCurrentBatch(globalEnity.getShopCurrentBatch() + 1);
					globalEnity.setBatchStartTime(TimeUtils.getCurrentTime());
					InjectorUtil.getInjector().globalManager.update(globalEnity);
				}
				
				register(time);
			} else if(globalEnity.getShopCurrentPhase() == ShopPhase.COMMON_PHASE)  {
			
				Integer time = ShopPirFactory.getInstance().batch2Time.get(globalEnity.getShopCurrentBatch());
				if(time == null) {
					log.error("策划配置商城开服活动出错,阶段:[{}],批次:[{}]", globalEnity.getShopCurrentPhase(), globalEnity.getShopCurrentBatch());
					return false;
				}
				
				synchronized (globalEnity) {
					globalEnity.setShopCurrentBatch(globalEnity.getShopCurrentBatch() + 1);
					globalEnity.setBatchStartTime(TimeUtils.getCurrentTime());
					InjectorUtil.getInjector().globalManager.update(globalEnity);
				}
				
				register(time);
			}
			
			// 推送当前批次数据
			InjectorUtil.getInjector().shopManager.refreshShopBatch();
		}
		return true;
	}
}
