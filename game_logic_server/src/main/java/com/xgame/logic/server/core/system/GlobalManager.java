package com.xgame.logic.server.core.system;

import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.system.entity.GlobalEnity;
import com.xgame.logic.server.core.utils.InjectorUtil;

/**
 * 全局缓存(需要用到重新修改)
 * @author jacky.jiang
 *
 */
@Component
public class GlobalManager extends CacheProxy<GlobalEnity> {
	
	
	private GlobalEnity globalEnity = null;
	
	/**
	 * 获取全局key
	 * @return
	 */
	public GlobalEnity getGlobalEntity() {
		if(globalEnity == null) {
			globalEnity = InjectorUtil.getInjector().dbCacheService.get(GlobalEnity.class, DBKey.GLOBAL_KEY);
			if(globalEnity == null) {
				globalEnity = new GlobalEnity();
				globalEnity.setId(DBKey.GLOBAL_KEY);
				InjectorUtil.getInjector().dbCacheService.create(globalEnity);	
			}
		}
		return globalEnity;
	}
	
	/**
	 * 获取地图时间
	 * @return
	 */
	public long getMapGeneratorTime() {
		GlobalEnity globalEnity = InjectorUtil.getInjector().dbCacheService.get(GlobalEnity.class, DBKey.GLOBAL_KEY);
		if(globalEnity != null) {
			return globalEnity.getMapGenerateTime();
		}
		return 0;
	}
	
	/**
	 * 全局变量
	 * @param globalEnity
	 */
	public synchronized  void saveGlobalEntity(GlobalEnity globalEnity) {
		InjectorUtil.getInjector().dbCacheService.update(globalEnity);
	}

	@Override
	public Class<?> getProxyClass() {
		return GlobalEnity.class;
	}
}
