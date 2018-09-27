package com.xgame.logic.server.game.cross;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.cross.constant.CrossConstant;
import com.xgame.logic.server.game.cross.rmi.RemoteService;

/**
 * 跨服管理器
 * @author jacky.jiang
 *
 */
@Component
public class CrossManager {

	@SuppressWarnings("unchecked")
	public RemoteService getRemoteService(int serverId) {
		String serviceName = StringUtils.join(CrossConstant.CROSS_SERVICE_NAME, serverId);
		RemoteService remoteService = (RemoteService)InjectorUtil.getInjector().getApplicationContext().getBean(serviceName);
		return remoteService;
	}
}
