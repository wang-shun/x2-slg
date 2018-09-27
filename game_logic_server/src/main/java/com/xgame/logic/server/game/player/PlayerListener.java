package com.xgame.logic.server.game.player;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.gamelog.event.IListener;
import com.xgame.logic.server.game.commander.entity.eventmodel.CommanderChangeNameEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;


/**
 * 玩家事件监听
 * @author jacky.jiang
 *
 */
@Component
public class PlayerListener implements IListener {

	@Autowired
	private PlayerManager playerManager;
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
				if (player != null) {
					CommanderChangeNameEventObject commanderChangeNameEventObject = (CommanderChangeNameEventObject) event;
					// 更新改名
					this.playerManager.getPlayerNameIdMap().remove(commanderChangeNameEventObject.getOldName());
					this.playerManager.addPlayerNameMap(player.roleInfo().getBasics().getRoleName(), player.getId());
					this.playerManager.getPlayerIdNameMap().put(player.getId(), player.roleInfo().getBasics().getRoleName());
					
					// 推送野外精灵变更
					SpriteInfo spriteInfo = player.getSprite();
					if (spriteInfo != null && spriteInfo.getEnumSpriteType() == SpriteType.PLAYER) {
						PlayerSprite playerSprite = spriteInfo.getParam();
						if (playerSprite != null) {
							// 推送精灵更新
							player.getWorldLogicManager().pushToWorldSprites(spriteInfo, Lists.newArrayList(player.getId()));
						}
					}
				}
			}
			break;
		}
	}
	
	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EVENT_COMMANDER_CHANGE_NAME};
	}
}
