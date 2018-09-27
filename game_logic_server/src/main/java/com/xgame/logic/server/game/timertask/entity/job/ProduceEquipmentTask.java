package com.xgame.logic.server.game.timertask.entity.job;

import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.timertask.constant.TimerTaskCommand;
import com.xgame.logic.server.game.timertask.entity.job.data.ProduceEquipmentTaskData;
import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;


/**
 * 自建武器生产
 * @author jacky.jiang
 *
 */
public class ProduceEquipmentTask extends AbstractTimerTask {
	
	public ProduceEquipmentTask(TimerTaskCommand timerTaskCommand) {
		super(timerTaskCommand);
	}

//	@Override
//	public TimerTaskData factoryTimerTaskData(Object... params) {
//		TimerTaskData timerTaskData = new TimerTaskData();
//		ProduceEquipmentTaskData equipmentData = new ProduceEquipmentTaskData();
//		timerTaskData.setParam(equipmentData);
//		equipmentData.setEquipmentId((int)params[0]);
//		equipmentData.setBuildId((int)params[1]);
//		return timerTaskData;
//	}

	@Override
	public boolean onExecute(Player player, TimerTaskData data) {
		ProduceEquipmentTaskData produceEquipmentTaskData = (ProduceEquipmentTaskData)data.getParam() ;
		player.getEquipmentManager().buildEquipmentEnd(player, produceEquipmentTaskData.getBuildId(),produceEquipmentTaskData.getEquipId());
		return super.onExecute(player, data);
	}
}
