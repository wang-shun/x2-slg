package com.xgame.logic.server.core.utils.sequance;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;

/**
 * id生成器
 * @author jacky.jiang
 *
 */
@Component
public class IDFactrorySequencer {

    /**
     * 创建角色ID
     * @param serverID 服务器编号
     * @return
     */
    public synchronized long createEnityID(String idkey) {
    	long id = InjectorUtil.getInjector().redisClient.incr(idkey);
    	if(String.valueOf(id).length() > 10) {
    		return -1;
    	}
    	
    	// 4位服表示 +10位自增id
    	int server = InjectorUtil.getInjector().serverId;
    	String serverKey = String.valueOf(server);
    	
    	// 服务器标识
    	@SuppressWarnings("unchecked")
		long returnId = Long.valueOf(StringUtils.join(serverKey, StringUtils.repeat('0', DBKey.ID_INCR_LENGTH - String.valueOf(id).length()), id));
    	return returnId;
    }
}
