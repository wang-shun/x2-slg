package com.xgame.logic.server.game.alliance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.AbstractEventListener;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 联盟事件监听
 * @author jacky.jiang
 *
 */
@Component
public class AllianceListener extends AbstractEventListener {

	@Autowired
	private AllianceManager allianceManager;
	

	public void onAction(IEventObject event) {
		int type = event.getType();
		switch (type) {
		case EventTypeConst.EVENT_PLAYER_LOGIN:{
			// 登录事件
				Player player = event.getPlayer();
				PlayerAlliance playerAlliance = this.allianceManager.playerAllianceManager.getOrCreate(player.getId());
				if (playerAlliance != null && playerAlliance.getAllianceId() <= 0) {
					long loginTime = player.roleInfo().getBasics().getLoginTime();
					int vipLevel = player.roleInfo().getVipInfo().getVipLevel();
					long fightPower = player.roleInfo().getCurrency().getPower();
					int level = player.roleInfo().getBasics().getLevel();
					this.allianceManager.recommendPlayerManager.refreshRank(player.getId(), loginTime, vipLevel, fightPower, level);
				}
			}
			break;
		case EventTypeConst.EVENT_ATTRIBUTE_REFRESH:{
			//刷新属性
			Player player = event.getPlayer();
			PlayerAlliance playerAlliance = this.allianceManager.playerAllianceManager.getOrCreate(player.getId());
			if (playerAlliance != null && playerAlliance.getAllianceId() > 0) {
				// 刷新联盟战力
				this.allianceManager.refreshAllianceFightPower(playerAlliance.getAllianceId());
			}
			}
			break;
			
		case EventTypeConst.EVENT_COMMANDER_CHANGE_NAME:
			{
				Player player = event.getPlayer();
				PlayerAlliance playerAlliance = this.allianceManager.playerAllianceManager.getOrCreate(player.getId());
				if(playerAlliance != null) {
					Alliance alliance = allianceManager.get(playerAlliance.getAllianceId());
					if(alliance != null && alliance.getLeaderId() == player.getId()) {
						allianceManager.allianceLeaderRename(alliance, player.getName());
					}
				}
			}
			break;
		}
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EVENT_PLAYER_LOGIN, EventTypeConst.EVENT_ATTRIBUTE_REFRESH, EventTypeConst.EVENT_COMMANDER_CHANGE_NAME};
	}
}