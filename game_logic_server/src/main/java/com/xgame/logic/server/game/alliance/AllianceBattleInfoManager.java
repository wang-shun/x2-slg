package com.xgame.logic.server.game.alliance;

import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.AllianceReport;


/**
 * 联盟战报管理器
 * @author jacky.jiang
 *
 */
@Component
public class AllianceBattleInfoManager extends CacheProxy<AllianceReport>  {

	@Override
	public Class<?> getProxyClass() {
		return AllianceReport.class;
	}
	
	/**
	 * 创建联盟报告
	 * @param allianceId
	 * @return
	 */
	public AllianceReport getOrCreate(long allianceId) {
		AllianceReport allianceReport = InjectorUtil.getInjector().dbCacheService.get(AllianceReport.class, allianceId);
		if(allianceReport == null)  {
			allianceReport = new AllianceReport();
			allianceReport.setAllianceId(allianceId);
			allianceReport = InjectorUtil.getInjector().dbCacheService.create(allianceReport);
		}
		return allianceReport;
	}
	
	public void saveAllianceReport(AllianceReport allianceReport) {
		InjectorUtil.getInjector().dbCacheService.update(allianceReport);
	}
	
}
