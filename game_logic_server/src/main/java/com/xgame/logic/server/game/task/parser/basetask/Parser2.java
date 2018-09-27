package com.xgame.logic.server.game.task.parser.basetask;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xgame.config.task.TaskPir;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.entity.MineCar;
import com.xgame.logic.server.game.country.entity.MineRepository;
import com.xgame.logic.server.game.country.entity.XBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.country.structs.build.mine.MineCarControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.task.constant.BaseTaskTypeEnum;
import com.xgame.logic.server.game.task.enity.BaseTask;

@Component
public class Parser2 extends BaseParser {

	@Override
	public BaseTaskTypeEnum getTaskTypeEnum() {
		return BaseTaskTypeEnum.TYPE_2;
	}
	
	@Override
	public void onAction(IEventObject event) {
		if(!isMyEvent(event)){
			return;
		}
		Player player = event.getPlayer();
		MineCarControl mineCarControl = (MineCarControl)player.getCountryManager().getBuildControls().get(BuildFactory.MINE_CAR.getTid());
		MineRepository tempRepository = player.roleInfo().getBaseCountry().getMineRepository();
		for(XBuild countryBuild2: mineCarControl.getBuildMap().values()) {
			MineCar mineCar = tempRepository.getOrCreate(countryBuild2.getUid());
			if(mineCar == null) {
				continue;
			}
			int mineProduct = PlayerAttributeManager.get().mineProduct(player.getId(), mineCar.getResourceType(), ((CountryBuild)countryBuild2).getLevel());
			player.roleInfo().getTaskInfo().getTaskRecord().updateMineProduct(mineCar.getResourceType(), mineProduct);
		}
		InjectorUtil.getInjector().dbCacheService.update(player);
		updateBaseTaskProgress(event,0,0);
	}
	
	
	/**
	 * 更新基地任务
	 * @param event
	 */
	public void updateBaseTaskProgress(IEventObject event,long addProgress,long newProgress){
		List<TaskPir> configs = getConfigs();
		Map<Integer,BaseTask> baseTaskMap = event.getPlayer().roleInfo().getTaskInfo().getBaseTask();
		for(TaskPir taskPir : configs){
			BaseTask baseTask = baseTaskMap.get(taskPir.getId());
			if(baseTask == null){
				continue;
			}
			String str = taskPir.getV2();
			long totalProgress = Long.valueOf(str);
			newProgress = getCurrProgress(event,event.getPlayer(),taskPir,baseTask);
			if(baseTask != null && !baseTask.isComplete()){
				if(newProgress > baseTask.getProgress()){
					baseTask.setProgress(newProgress);
				}
				if(baseTask.getProgress() >= totalProgress){
					baseTask.setProgress(totalProgress);
					baseTask.setComplete(true);
				}
				InjectorUtil.getInjector().dbCacheService.update(event.getPlayer());
				pushTaskUpdate(event.getPlayer(),baseTask);
			}
		}
	}

	@Override
	public void subEvent() {
		eventTypes.add(EventTypeConst.MINE_PRODUCT_UPDATE);
	}

	@Override
	public long getCurrProgress(IEventObject event,Player player,TaskPir taskPir,BaseTask baseTask){
		Map<Integer,Integer> mineProduct = player.roleInfo().getTaskInfo().getTaskRecord().getMineProduct();
		int type = getV1(taskPir);
		if(mineProduct.containsKey(type)){
			return mineProduct.get(type);
		}
		return 0;
	}
}
