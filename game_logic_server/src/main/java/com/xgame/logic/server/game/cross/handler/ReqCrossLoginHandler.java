package com.xgame.logic.server.game.cross.handler;

import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.process.PlayerCommand;
import com.xgame.logic.server.core.utils.MD5;
import com.xgame.logic.server.game.cross.CrossPlayerManager;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.message.ReqCrossLoginMessage;

import lombok.extern.slf4j.Slf4j;


/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class ReqCrossLoginHandler extends PlayerCommand<ReqCrossLoginMessage>{

	// token失效时间
	public static final int TOKEN_APPLY_TIME = 3 * 60 * 1000;
	
	@Autowired
	private CrossPlayerManager crossManager;
	@Value("${xgame.server.loginKey}")
	private String serverKey;
	
	@Override
    public void action() {
		if(message.time + TOKEN_APPLY_TIME < System.currentTimeMillis()) {
			log.error("跨服登录时间不正确");
			return;
		}
		
		@SuppressWarnings("unchecked")
		String enStr = StringUtils.join(message.playerId, message.time, serverKey);
		byte[] bytes = Base64.getEncoder().encode(enStr.getBytes());
		String encode = MD5.md5(bytes);
		if(!encode.equals(message.token)) {
			log.error("跨服登陆加密验证失败.");
			return;
		}
		
		CrossPlayer crossPlayer = crossManager.getCrossPlayer(message.playerId);
		if(crossPlayer == null) {
			log.error("玩家数据不存在。");
			return;
		}
		
		// 登陆成功
		crossManager.addCrossLogin(message.playerId, getPlayerSession());
    }
}
