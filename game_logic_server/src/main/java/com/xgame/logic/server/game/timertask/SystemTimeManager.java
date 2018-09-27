package com.xgame.logic.server.game.timertask;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.timertask.constant.SystemTimerCommand;
import com.xgame.logic.server.game.timertask.entity.system.data.SystemTimerTaskData;


/**
 * 系统任务管理器
 * @author jacky.jiang
 *
 */
@Component
public class SystemTimeManager extends CacheProxy<SystemTimerTaskData> {
	

	public SystemTimerTaskData getTimerTaskData(long taskId) {
		return InjectorUtil.getInjector().dbCacheService.get(SystemTimerTaskData.class, taskId);
	}
	
	public void addSystemTimerTaskData(SystemTimerTaskData systemTimerTaskData) {
		InjectorUtil.getInjector().dbCacheService.update(systemTimerTaskData);
	}

	@Override
	public Class<?> getProxyClass() {
		return SystemTimerTaskData.class;
	}

	/**
	 * 是否存在timerTask
	 * @param type
	 * @return
	 */
	public boolean existTimerTask(int type) {
		Collection<SystemTimerTaskData> collection = this.getEntityCache().values();
		if(collection != null) {
			for(SystemTimerTaskData systemTimerTaskData : collection) {
				if(systemTimerTaskData.getQueueId() == SystemTimerCommand.SHOP.ordinal()) {}
			}
		}
		return false;
	}
}
