package com.xgame.logic.server.core.net.process;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.msglib.XMessage;
import com.xgame.msglib.able.Communicationable;

@NoArgsConstructor
@Slf4j
public abstract class PlayerCommand<E extends XMessage> extends StatefulCommand<Communicationable>{
	
	
	protected Player player;
	
	protected PlayerSession playerSession;
	
	protected E message;
	
	protected CrossPlayer crossPlayer;

	public void execute() {
		try {
			action();
		} catch(Exception e) {
			e.printStackTrace();
			log.error("服务器未知业务异常:",e);
			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE1);
		}
	}
	
	protected void action() {
		
	}
	
	public Player getPlayer() {
		return player;
	}

	public E  getMessage() {
		return message;
	}

	@SuppressWarnings("unchecked")
	public void setMessage(Communicationable message) {
		this.message = (E)message;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerSession getPlayerSession() {
		return playerSession;
	}

	public void setPlayerSession(PlayerSession playerSession) {
		this.playerSession = playerSession;
	}

	public CrossPlayer getCrossPlayer() {
		return crossPlayer;
	}

	public void setCrossPlayer(CrossPlayer crossPlayer) {
		this.crossPlayer = crossPlayer;
	}
	
	public boolean isCallback() {
		return false;
	}
	
}
