package com.xgame.logic.server.game.world.converter;

import com.xgame.logic.server.game.world.bean.ServerInfoBean;
import com.xgame.logic.server.game.world.entity.ServerGroup;

/**
 * 服务器转换器
 * @author jacky.jiang
 *
 */
public class ServerGroupConverter {
	
	public static ServerInfoBean converterServerGroup(ServerGroup serverGroup) {
		ServerInfoBean serverInfoBean = new ServerInfoBean();
		serverInfoBean.ip = serverGroup.getIp();
		serverInfoBean.gateInfo = serverGroup.getGateInfo();
		serverInfoBean.serverId = serverGroup.getServerId();
		serverInfoBean.thronePic = serverGroup.getThronePic();
		serverInfoBean.throneName = serverGroup.getThroneName();
		serverInfoBean.throneAbbr = serverGroup.getThroneAbbr();
		serverInfoBean.serverOpenTime = serverGroup.getServerOpenTime();
		serverInfoBean.online = serverGroup.getOnlineNum();
		return serverInfoBean;
	}
}
