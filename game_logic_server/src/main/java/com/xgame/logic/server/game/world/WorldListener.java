package com.xgame.logic.server.game.world;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.gamelog.event.IListener;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.eventmodel.AllianceLeftEventObject;
import com.xgame.logic.server.game.country.entity.eventmodel.LevelUpBulidFinishEventObject;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.entity.SpriteInfo;

/**
 * 世界地图事件监听
 * @author jacky.jiang
 *
 */
@Component
public class WorldListener implements IListener {
	
	@PostConstruct
	public void init() {
		eventBus.addEventListener(this);
	}

	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	private EventBus eventBus;
	
	public void onAction(IEventObject event) {
		int type = event.getType();
		switch (type) {
			case EventTypeConst.EVENT_ALLIANCE_JOIN: {
				// 加入联盟
				Player player = event.getPlayer();
				SpriteInfo spriteInfo = player.getSprite();
				Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, player.getAllianceId());
				if(alliance != null) {
					synchronized (alliance) {
						alliance.addAllianceTerritory(spriteInfo.getIndex());
						InjectorUtil.getInjector().dbCacheService.update(alliance);
					}
				}
				
				player.roleInfo().getPlayerTerritory().addTerritory(spriteInfo.getIndex());
				InjectorUtil.getInjector().dbCacheService.update(player);
				
				// 更新地图精灵
				worldLogicManager.changeAllianceSprite(spriteInfo, player, true);
				
				this.worldLogicManager.pushToWorldSprites(spriteInfo, Lists.newArrayList(player.getId()));
			}
			break;
			case EventTypeConst.EVENT_ALLIANCE_LEFT: {
				// 离开联盟
				Player player = event.getPlayer();
				SpriteInfo spriteInfo = player.getSprite();
				
				// 修改世界地图
				AllianceLeftEventObject allianceLeftEventObject = (AllianceLeftEventObject)event;
				boolean dismiss = player.getId() == allianceLeftEventObject.getLeaderId();
				if(dismiss) {
					worldLogicManager.changeAllianceTerritory(spriteInfo, true, player.getId());
				} else {
					worldLogicManager.changeAllianceTerritory(spriteInfo, false, allianceLeftEventObject.getLeaderId());
				}
				
				// 退出联盟把联盟领地给军团长
				ConcurrentHashSet<Integer> territorySet = player.roleInfo().getPlayerTerritory().getTerritory();
				AllianceLeftEventObject allianceJoinEventObject = (AllianceLeftEventObject) event;
				if(!dismiss) {
					Player leaderPlayer = InjectorUtil.getInjector().dbCacheService.get(Player.class, allianceJoinEventObject.getLeaderId());
					if(leaderPlayer != null) {
						leaderPlayer.roleInfo().getPlayerTerritory().addBatchTerrritory(Lists.newArrayList(territorySet));
						InjectorUtil.getInjector().dbCacheService.update(leaderPlayer);
					}
				}
				
				player.roleInfo().getPlayerTerritory().getTerritory().clear();
				InjectorUtil.getInjector().dbCacheService.update(player);
				
				// 更新地图精灵
				worldLogicManager.changeAllianceSprite(spriteInfo, player, false);
				
				// 处理占领玩家返回
				worldLogicManager.dealOccupy(player, spriteInfo);

				// 处理集结进攻和集结防御
				worldLogicManager.dealDismissAllianceTeamBattle(player);
				
				this.worldLogicManager.pushToWorldSprites(spriteInfo, Lists.newArrayList(player.getId()));
			}
			break;
			case EventTypeConst.EVENT_BUILD_LEVELUP_FINISH: {
				// 主基地升级
				LevelUpBulidFinishEventObject levelEventObject = (LevelUpBulidFinishEventObject)event;
				if(levelEventObject.getTid() == BuildFactory.MAIN.getTid()) {
					Player player = levelEventObject.getPlayer();
					worldLogicManager.changeWorldPlayerLevel(player);
				}
			}
			break;
		}
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EVENT_ALLIANCE_JOIN, EventTypeConst.EVENT_ALLIANCE_LEFT, EventTypeConst.EVENT_BUILD_LEVELUP_FINISH};
	}
}
