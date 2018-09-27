package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.game.bag.entity.eventmodel.MaterialsProductionEventObject;
import com.xgame.logic.server.game.country.structs.build.industry.IndustryBuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.ProduceFragmentTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 实验室生产材料
 * @author jacky.jiang
 *
 */
public class ProduceFragmentTask extends AbstractTimerTask {

	public ProduceFragmentTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object... params) {
//		TimerTaskData timerTaskData = new TimerTaskData();
//		LabTimerTaskData labTimerTaskData = new LabTimerTaskData();
//		timerTaskData.setParam(labTimerTaskData);
//		labTimerTaskData.setFragmentId((int)params[0]);
//		labTimerTaskData.setUid((int)params[1]);
//		return timerTaskData;
//	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		IndustryBuildControl industryBuildControl = player.getCountryManager().getIndustryBuildControl();
		if (industryBuildControl != null) {
			ProduceFragmentTaskData labTimerTaskData = (ProduceFragmentTaskData)data.getParam(); 
			industryBuildControl.produceFragmentEnd(player, labTimerTaskData.getItemId(), labTimerTaskData.getUid());
			EventBus.getSelf().fireEvent(new MaterialsProductionEventObject(player));
		}
		return true;
	}
	
	@Override
	public boolean onRemove(Player player, TimerTaskData data) {
		IndustryBuildControl industryBuildControl = player.getCountryManager().getIndustryBuildControl();
		if (industryBuildControl != null) {
			ProduceFragmentTaskData labTimerTaskData = (ProduceFragmentTaskData)data.getParam(); 
			industryBuildControl.nextEquipment(player, labTimerTaskData.getUid());
		}
		return super.onRemove(player, data);
	}

}
