package com.xgame.logic.server.game.buff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.gameconst.BuffType;
import com.xgame.logic.server.core.component.AbstractComponent;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.buff.bean.PlayerBuffDTO;
import com.xgame.logic.server.game.buff.constant.BuffAdditionConstant;
import com.xgame.logic.server.game.buff.message.ResClearBuffMessage;
import com.xgame.logic.server.game.buff.message.ResPlayerBuffMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBuff;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.timertask.TimerTaskManager;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.ITimerTask;
import com.xgame.logic.server.game.timertask.entity.job.TimerTaskHolder;
import com.xgame.logic.server.game.timertask.entity.job.data.BuffTimerTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;

/**
 * buff 管理器
 * @author jacky.jiang
 *
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BuffManager extends AbstractComponent<Player> {
	
	@Autowired
	private TimerTaskManager timerTaskManager;
	
	
	/**
	 * 添加buff
	 * @param itemId
	 * @param buffType
	 * @param update true 更新时间，false 替换buff
	 * @param taskTime
	 * @param addition
	 */
	public void addItemBuff(int itemId, BuffType buffType, int taskTime, Object... obj) {
		ItemsPir imConfigModel = ItemsPirFactory.get(itemId);
		if(imConfigModel == null) {
			log.error("玩家道具基础数据不存在, id:{}", itemId);
			throw new RuntimeException("道具配置id异常。");
		}
		
		Map<String, Object> addition = new HashMap<String, Object>();
		addition.put(BuffAdditionConstant.ITEM_ID, itemId);
		Map<String, PlayerBuff> map = e.roleInfo().getPlayerBuffData().getBuffMap();
		String buffId = PlayerBuff.factoryBuffId(buffType,"");
		PlayerBuff playerBuff = map.get(buffId);
		TimerTaskData timerTaskData = null;
		boolean update = false;
		if(playerBuff != null) {
			// 更新本地buff数据
			Long taskId = e.roleInfo().getTimerTaskMap().get(playerBuff.getTaskId());
			if (taskId != null) {
				timerTaskData = this.e.getTimerTaskManager().getTimerTaskData(taskId);
				if (timerTaskData != null) {
					update = true;
				}
			}
		}
		
		// buff叠加顶替规则
		Double value = (Double)obj[0];
		tag:if(update) {
			Object compareValue = playerBuff.getBuffAddition().get("value");
			if(value != null && compareValue != null) {
				if(!compareValue.equals(value)) {
					removeBuff(e, playerBuff, timerTaskData, map);
					playerBuff = createBuff(buffType, taskTime, imConfigModel, buffId, itemId, addition, compareValue, timerTaskData, map);
					break tag;
				}
			}
			updateBuff(playerBuff, taskTime, imConfigModel, itemId, addition, compareValue, timerTaskData, map);
		} else {
			playerBuff = createBuff(buffType, taskTime, imConfigModel, buffId, itemId, addition, value, timerTaskData, map);
		}
		
		e.send(getPlayerBuffDTO(Lists.newArrayList(playerBuff)));
	}
	
	/**
	 * 移除buff
	 * @param player
	 * @param playerBuff
	 * @param data
	 * @param map
	 */
	private void removeBuff(Player player, PlayerBuff playerBuff, TimerTaskData data, Map<String, PlayerBuff> map) {
		ITimerTask<?> timerTask = TimerTaskHolder.getTimerTask(TimerTaskCommand.BUFF_ITEM);
		timerTask.cancelTask(player, data);
		
		map.remove(playerBuff.getBuffId());
		InjectorUtil.getInjector().dbCacheService.update(player);
	}
	
	/**
	 * 更新buff
	 * @param playerBuff
	 * @param taskTime
	 * @param imConfigModel
	 * @param itemId
	 * @param addition
	 * @param value
	 * @param timerTaskData
	 * @param map
	 */
	private void updateBuff(PlayerBuff playerBuff, int taskTime, ItemsPir imConfigModel, int itemId, Map<String, Object> addition, Object value, TimerTaskData timerTaskData, Map<String, PlayerBuff> map) {
		// 重置时间
		playerBuff.setEndTime(playerBuff.getEndTime() + taskTime * 1000);
		playerBuff.setTaskId(timerTaskData.getId());
		playerBuff.setBuffAddition(addition);
		
		Object itemAddition = addition.get(String.valueOf(itemId));
		if(itemAddition != null) {
			addition.put(String.valueOf(itemId), (((Integer)itemAddition) + Integer.valueOf(imConfigModel.getV2())));
			addition.put("value", value);
		} else {
			addition.put(String.valueOf(itemId), Integer.valueOf(imConfigModel.getV2()));
			addition.put("value", value);
		}
		
		// 注册buff
		ITimerTask<?> timerTask = TimerTaskHolder.getTimerTask(TimerTaskCommand.BUFF_ITEM);
		
		//重置结束时间
		timerTaskData.setTriggerTime(timerTaskData.getTriggerTime() + taskTime * 1000);
		playerBuff.setTaskId(timerTaskData.getId());
		
		timerTask.resetTimerTask(e, timerTaskData);
		
		// 更新玩家信息
		map.put(playerBuff.getBuffId(), playerBuff);
		InjectorUtil.getInjector().dbCacheService.update(e);
	}
	
	/**
	 * 创建buff
	 * @param buffType
	 * @param taskTime
	 * @param imConfigModel
	 * @param buffId
	 * @param itemId
	 * @param addition
	 * @param value
	 * @param timerTaskData
	 * @param map
	 */
	private PlayerBuff createBuff(BuffType buffType, int taskTime, ItemsPir imConfigModel, String buffId, int itemId, Map<String, Object> addition, Object value, TimerTaskData timerTaskData, Map<String, PlayerBuff> map) {
		// buff信息
		PlayerBuff playerBuff = new PlayerBuff();
		playerBuff.setBuffType(buffType);
		playerBuff.setBuffId(buffId);
		
		playerBuff.setStartTime(System.currentTimeMillis());
		playerBuff.setEndTime(System.currentTimeMillis() + taskTime * 1000);
		playerBuff.setBuffAddition(addition);
		addition.put(String.valueOf(itemId), Integer.valueOf(imConfigModel.getV2()));
		addition.put("value", value);
		
		// 注册时间
		timerTaskData = TimerTaskHolder.getTimerTask(TimerTaskCommand.BUFF_ITEM).register(e, taskTime, new BuffTimerTaskData(buffId, itemId));
		playerBuff.setTaskId(timerTaskData.getId());
		
		// 创建buff
		map.put(playerBuff.getBuffId(), playerBuff);
		InjectorUtil.getInjector().dbCacheService.update(e);
		return playerBuff;
	}
	
	/**
	 * buff道具是否存在
	 * @param buffType
	 * @param buffkey
	 * @return
	 */
	public boolean existBuff(BuffType buffType, String buffkey) {
		Map<String, PlayerBuff> map = e.roleInfo().getPlayerBuffData().getBuffMap();
		PlayerBuff playerBuff = map.get(PlayerBuff.factoryBuffId(buffType, buffkey));
		if(playerBuff != null) {
			return true;
		}
		return false;
	}
	
	/***
	 * 判断buff类型是否存在
	 * @param buffType
	 * @return
	 */
	public PlayerBuff existBuffType(BuffType buffType) {
		Map<String, PlayerBuff> map = e.roleInfo().getPlayerBuffData().getBuffMap();
		if(map != null) {
			Collection<PlayerBuff> collection = map.values();
			if(collection != null) {
				for(PlayerBuff playerBuff : collection) {
					if(playerBuff.getBuffType() == buffType) {
						return playerBuff;
					}
				}
			}
		}
		return null;
	}
	
	public List<PlayerBuff> getBuffListByType(BuffType buffType) {
		List<PlayerBuff> buffList = new ArrayList<PlayerBuff>();
		Map<String, PlayerBuff> map = e.roleInfo().getPlayerBuffData().getBuffMap();
		if(map != null) {
			Collection<PlayerBuff> collection = map.values();
			if(collection != null) {
				for(PlayerBuff playerBuff : collection) {
					if(playerBuff.getBuffType() == buffType) {
						buffList.add(playerBuff);
					}
				}
			}
		}
		return buffList;
	}

	/**
	 * 移除任务id
	 * @param taskId
	 * @return
	 */
	public boolean removeBuffByTaskId(long taskId) {
		Map<String, PlayerBuff> map = e.roleInfo().getPlayerBuffData().getBuffMap();
		if(map != null) {
			Collection<PlayerBuff> collection = map.values();
			if(collection != null) {
				for(PlayerBuff playerBuff : collection) {
					if(playerBuff.getTaskId() == taskId) {
						map.remove(playerBuff.getTaskId());
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void loginLoad(Object... param) {
		Map<String, PlayerBuff> map = e.roleInfo().getPlayerBuffData().getBuffMap();
		if (map != null && !map.isEmpty()) {
			e.send(getPlayerBuffDTO(map.values()));
		}
	}
	
	/**
	 * 
	 * @param playerBuffs
	 * @return
	 */
	private ResPlayerBuffMessage getPlayerBuffDTO(Collection<PlayerBuff> playerBuffs) {
		ResPlayerBuffMessage resPlayerBuffMessage = new ResPlayerBuffMessage();
		if(playerBuffs != null) {
			for(PlayerBuff playerBuff : playerBuffs) {
				PlayerBuffDTO playerBuffDTO = new PlayerBuffDTO();
				playerBuffDTO.buffId = playerBuff.getBuffId();
				playerBuffDTO.buffKey = playerBuff.getBuffKey();
				playerBuffDTO.buffType = playerBuff.getBuffType().ordinal();
				playerBuffDTO.endTime = playerBuff.getEndTime();
				playerBuffDTO.startTime = playerBuff.getStartTime();
				playerBuffDTO.addition = JSON.toJSONString(playerBuff.getBuffAddition());
				
				System.out.println((playerBuffDTO.endTime - playerBuffDTO.startTime) / (1000 * 60 * 60));
				resPlayerBuffMessage.buffList.add(playerBuffDTO);
			}
		}
		return resPlayerBuffMessage;
	}
	
	/**
	 * 获取玩家buff
	 * @param buffId
	 * @return
	 */
	public PlayerBuff getPlayerBuff(String buffId) {
		PlayerBuff playerBuff = e.roleInfo().getPlayerBuffData().queryPlayerBuff(buffId);
		return playerBuff;
	}
	
	/**
	 * 移除buff根据buffId
	 * @param buffId
	 */
	public void removeBuffById(String buffId) {
		e.roleInfo().getPlayerBuffData().getBuffMap().remove(buffId);
		InjectorUtil.getInjector().dbCacheService.update(e);
	}
	
	/**
	 * buff到期
	 * @param player
	 * @param buffId
	 */
	public void playerBuffEnd(Player player, String buffId, int itemId) {
		PlayerBuff playerBuff = this.getPlayerBuff(buffId);
		
//		ItemsPir itemsPir = ItemsPirFactory.get(itemId);
		if(playerBuff != null && playerBuff.getBuffType() == BuffType.MINE_CAR_SPEED_UP || playerBuff.getBuffType() == BuffType.EXPLORER_RESOURCE_SPEED_UP) {
//			player.getAttributeAppenderManager().deleteAppenderLibrary(AttributeAppenderEnum.ITEM.ordinal(), itemsPir.getV1());
			EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		}
		
		player.getBuffManager().removeBuffById(buffId);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		// 删除buff
		ResClearBuffMessage resClearBuffMessage = new ResClearBuffMessage();
		resClearBuffMessage.buffId = buffId;
		player.send(resClearBuffMessage);
	}
}
