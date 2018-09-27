package com.xgame.logic.server.game.cross;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.gamelog.event.IListener;
import com.xgame.logic.server.game.cross.entity.CrossPlayer;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.player.entity.Player;


@Component
public class CrossPlayerListener implements IListener {

	@Autowired
	private CrossPlayerManager crossPlayerManager;
	@Autowired
	private EventBus eventBus;
	
	@PostConstruct
	public void init() {
		eventBus.addEventListener(this);
	}


	public void onAction(IEventObject event) {
			int type = event.getType();
			switch (type) {
				case EventTypeConst.EVENT_COMMANDER_CHANGE_NAME: {
					Player player = event.getPlayer();
					// 玩家改名
					CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getRoleId());
					SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
					crossPlayer.getSimpleRoleInfo().setName(player.roleInfo().getBasics().getRoleName());
					crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
				}
				break;
	
			case EventTypeConst.EVENT_COMMAND_LEVELUP: {
					Player player = event.getPlayer();
					
					// 指挥关升级
					CrossPlayer crossPlayer = crossPlayerManager.getCrossPlayer(player.getRoleId());
					SimpleRoleInfo simpleRoleInfo = crossPlayer.getSimpleRoleInfo();
					simpleRoleInfo.setLevel(player.getCommandLevel());
					crossPlayerManager.saveSimpleRoleInfo(simpleRoleInfo);
				}
			break;
		}
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EVENT_COMMANDER_CHANGE_NAME, EventTypeConst.EVENT_COMMAND_LEVELUP};
	}
}
