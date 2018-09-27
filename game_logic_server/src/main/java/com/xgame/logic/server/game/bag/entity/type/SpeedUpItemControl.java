package com.xgame.logic.server.game.bag.entity.type;

import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.player.entity.Player;

/**
 * 加速道具 type=2
 * @author lmj
 *
 */
public class SpeedUpItemControl extends ItemControl {
	
	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param){
		if(num < 1){
			return false;
		}
		
		int type = (int)param[0];
		long timerId = (long)param[1];
		ItemsPir configModel = ItemsPirFactory.get(itemId);
		int t = Integer.parseInt(configModel.getV2());
		if(type == 0){
			player.changeTaskTime(timerId, t*num);
			return true;
		}else if(type == 1){
			long taskUid = timerId;
			//任务加速
			player.getTaskManager().speeTask(player, taskUid,t*num);
			return true;
		}
		return false;
	}

	public SpeedUpItemControl(int type) {
		super(type);
	}
}
