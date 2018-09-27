
package com.xgame.logic.server.game.timertask.entity.job;

import com.google.common.collect.Lists;
import com.xgame.data.sprite.SpriteType;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.OccupyTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.WorldSprite;


/**
 * 占领时间队列
 * @author jacky.jiang
 *
 */
public class OccupyTimerTask extends AbstractTimerTask {

	public OccupyTimerTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		//
		OccupyTimerTaskData occupyTimerTaskData = (OccupyTimerTaskData)data.getParam();
		if (player.getAllianceId() <= 0) {
			return true;
		}
		
		WorldMarch worldMarch = InjectorUtil.getInjector().dbCacheService.get(WorldMarch.class, occupyTimerTaskData.marchId);
		if(worldMarch != null) {
			
			// 清空占领的任务id
			worldMarch.setOccupyTaskId(0);
			InjectorUtil.getInjector().dbCacheService.update(worldMarch);
			
			SpriteInfo spriteInfo = player.getSpriteManager().getGrid(worldMarch.getTargetId());
			if(spriteInfo != null) {
				WorldSprite worldSprite = spriteInfo.getParam();
				if(worldSprite != null) {
					if(worldSprite.getAllianceId() > 0) {
						if(worldSprite.getTerritoryPlayerId() > 0) {
							Player occupyPlayer =  InjectorUtil.getInjector().dbCacheService.get(Player.class, worldSprite.getTerritoryPlayerId());
							if(occupyPlayer != null) {
								occupyPlayer.roleInfo().getPlayerTerritory().removeTerritory(spriteInfo.getIndex());
								InjectorUtil.getInjector().dbCacheService.update(occupyPlayer);
							}
						}
					}
					
					// 修改世界地图信息
					player.getWorldLogicManager().getWorldExecutor().execute(new Runnable() {
						@Override
						public void run() {
							worldSprite.setAllianceId(player.getAllianceId());
							worldSprite.setTerritoryPlayerId(player.getId());
							spriteInfo.setSpriteType(SpriteType.CAMP.getType());
							
							InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
							
							// 推送世界精灵信息
							player.getWorldLogicManager().pushToWorldSprites(spriteInfo, Lists.newArrayList(player.getId()));
							
							//战报
							player.getPlayerMailInfoManager().sendTerritoryEmail(player, worldMarch, spriteInfo.getX(),spriteInfo.getY());
							
						}
					});
					
					// 联盟信息更改
					Alliance alliance = InjectorUtil.getInjector().dbCacheService.get(Alliance.class, player.getAllianceId());
					if(alliance != null) {
						synchronized (alliance) {
							alliance.addAllianceTerritory(spriteInfo.getIndex());
							InjectorUtil.getInjector().dbCacheService.update(alliance);
						}
					}
					
					// 更新玩家领地信息
					player.roleInfo().getPlayerTerritory().addTerritory(spriteInfo.getIndex());
					InjectorUtil.getInjector().dbCacheService.update(player);
					
					// 提送领地变化
					player.getWorldLogicManager().pushWorldTerritory(player, spriteInfo);
				}
				
			
			}
		}
		return super.onExecute(player, data);
	}

}
