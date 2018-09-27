package com.xgame.logic.server.core.gamelog.event;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.xgame.logic.server.core.gamelog.executor.GameLogExecutor;
import com.xgame.logic.server.core.gamelog.logs.concrete.ItemLog;
import com.xgame.logic.server.core.gamelog.logs.concrete.LoginLog;
import com.xgame.logic.server.core.gamelog.logs.concrete.OperatorLog;
import com.xgame.logic.server.core.gamelog.logs.concrete.SoldierLog;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.bag.entity.eventmodel.ItemChangeEventObject;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.eventmodel.LoginEventObject;
import com.xgame.logic.server.game.soldier.entity.model.SoldierChangeEventObject;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;


/**
 * 日志系统监听 
 * @author jacky.jiang
 *
 */
public class LogSystemListener implements IListener {
	
	@Autowired
	private GameLogExecutor gameLogExecutor;
	@Autowired
	private EventBus eventBus;
	
	@PostConstruct
	public void init() {
		eventBus.addEventListener(this);
	}

	@Override
	public void onAction(IEventObject e) {
		
		Player player = e.getPlayer();
		if(e instanceof LoginEventObject) {
			// 登录登出日志
			LoginLog loginLog = new LoginLog();
			loginLog.setPlayerId(player.getId());
			loginLog.setRoleName(player.getName());
			loginLog.setUserName(player.roleInfo().getBasics().getRoleName());
			loginLog.setLevel(player.getLevel());
			gameLogExecutor.execute(loginLog );
		}
		
		// 游戏日志
		if(e instanceof GameLogEventObject) {
			// 根据日志类型
			if(e instanceof ItemChangeEventObject) {
				// 道具日志
				ItemChangeEventObject event = (ItemChangeEventObject)e;
				ItemLog itemLog = new ItemLog();
				itemLog.setPlayerId(player.getId());
				itemLog.setRoleName(player.getName());
				itemLog.setUserName(player.roleInfo().getBasics().getRoleName());
				itemLog.setLevel(player.getLevel());
				itemLog.setBeforeNum(event.getBeforeNum());
				itemLog.setItemId(event.getItemTid());
				itemLog.setCurrentNum(event.getAfterNum());
//				itemLog.setLogSource(event.getLogSource().ordinal());
//				itemLog.setLogSource(event.getLogSource());
				itemLog.setLogSource(event.getLogSource().name());
				gameLogExecutor.execute(itemLog);
			} 
			
			if(e instanceof SoldierChangeEventObject) {
				// 士兵日志
				SoldierChangeEventObject event = (SoldierChangeEventObject)e;
				SoldierLog soldierLog = new SoldierLog();
				soldierLog.setPlayerId(player.getId());
				soldierLog.setRoleName(player.getName());
				soldierLog.setUserName(player.roleInfo().getBasics().getRoleName());
				soldierLog.setLevel(player.getLevel());
				soldierLog.setBeforeNum(event.getBeforeNum());
				soldierLog.setCurrentNum(event.getCurrentNum());
				soldierLog.setMarchState(event.getMarchState()==null?MarchState.DEFALUT.ordinal():event.getMarchState().ordinal());
				soldierLog.setMarchType(event.getMarchType()==null?MarchType.DEFAULT.ordinal():event.getMarchType().ordinal());
				soldierLog.setSoldierId(event.getSoldierId());
				soldierLog.setSoldierType(event.getSoldierType());
				gameLogExecutor.execute(soldierLog);
			} 
			
			// 操作日志
			OperatorLog operatorLog = new OperatorLog();
			operatorLog.setPlayerId(player.getId());
			operatorLog.setRoleName(player.roleInfo().getBasics().getRoleName());
			operatorLog.setUserName(player.roleInfo().getBasics().getUserName());
			operatorLog.setLevel(player.getLevel());
			operatorLog.setContent(JsonUtil.toJSON(e));
			operatorLog.setType(e.getType());
			gameLogExecutor.execute(operatorLog);
		}
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.ALL_EVENT};
	}
}
