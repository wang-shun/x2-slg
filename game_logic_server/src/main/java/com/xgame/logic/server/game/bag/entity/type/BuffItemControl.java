package com.xgame.logic.server.game.bag.entity.type;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xgame.config.attribute.AttributeConfMap;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.gameconst.BuffType;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.entity.PlayerBuff;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.march.concrete.ExplorerWorldMarch;

/**
 * Buff道具 加属性 type=3
 * @author lmj
 *
 */
public class BuffItemControl extends ItemControl {
	
	public BuffItemControl(int type) {
		super(type);
	}
	
	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object ... param) {
		if(num < 1){
			return false;
		}
		//使用buff加属性道具 判断属性效果 提示是否用低效buff顶替现有buff(客户端判断) buff到期删除buff效果
		ItemsPir configModel = ItemsPirFactory.get(itemId);
		// 添加属性类型buff
		String v2 = configModel.getV2();
		if(!StringUtils.isEmpty(v2)) {
			
			double value = 0d;
			//先删掉之前道具所增加的所有属性
			AttributeConfMap confMap = configModel.getV1();
			if(confMap != null) {
//				player.getAttributeAppenderManager().deleteAppenderLibrary(AttributeAppenderEnum.ITEM.ordinal(), confMap);
				//在把本次道具加的数值加进去
//				player.getAttributeAppenderManager().rebuild(configModel.getV1(), 1, AttributeAppenderEnum.ITEM.ordinal(), true);
				EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
				value = confMap.getDefaultLibrary();
			}
			
			if(configModel.getSubtype() == 2) {//超时空矿车
				//管理buff生命周期
				for (int i = 0; i < num; i++) {
					player.getBuffManager().addItemBuff(itemId, BuffType.MINE_CAR_SPEED_UP, Integer.valueOf(v2.toString()), value);
				}
				return true;
			} else if(configModel.getSubtype() == 1) {//采集专家
				PlayerBuff playerBuff = player.getBuffManager().existBuffType(BuffType.EXPLORER_RESOURCE_SPEED_UP);
				if(playerBuff == null) {
					//由于采集速度是针对所有资源采集的，取其中一个就可以了
//					double ratio = AttributeEnum.OIL_GATHER_RATE.playerAttribute(player);
					double ratio = AttributeCounter.getAttributeValue(player.getId(), AttributesEnum.OIL_GATHER_RATE);
					List<WorldMarch> worldMarchsList = player.getWorldMarchManager().getWorldMarchByPlayerId(player.getId());
					if(worldMarchsList != null && !worldMarchsList.isEmpty()) {
						for(WorldMarch worldMarch : worldMarchsList) {
							if(worldMarch.getMarchType() == MarchType.EXPLORER && worldMarch.getExlporerTaskId() > 0) {
								((ExplorerWorldMarch)worldMarch.getExecutor()).speedUpExplorer(ratio, Integer.valueOf(v2.toString()));
							}
						}
					}
				}
				
				//管理buff生命周期
				for (int i = 0; i < num; i++) {
					player.getBuffManager().addItemBuff(itemId, BuffType.EXPLORER_RESOURCE_SPEED_UP, Integer.valueOf(v2.toString()), value);
				}
				
				return true;
			}else if(configModel.getSubtype() == 3){//出征提升
				//管理buff生命周期
				for(int i=0;i<num;i++){
					player.getBuffManager().addItemBuff(itemId, BuffType.CAMP_SOLDIERS, Integer.valueOf(v2.toString()), value);
				}
				return true;
			}else if(configModel.getSubtype() == 4){//屯兵减耗
				//管理buff生命周期
				for(int i=0;i<num;i++){
					player.getBuffManager().addItemBuff(itemId, BuffType.TUNBING_JIANHAO, Integer.valueOf(v2.toString()), value);
				}
				return true;
			}else if(configModel.getSubtype() == 5){//天启攻击
				//管理buff生命周期
				for(int i=0;i<num;i++){
					player.getBuffManager().addItemBuff(itemId, BuffType.TIANQI_GONGJI, Integer.valueOf(v2.toString()), value);
				}
				return true;
			}else if(configModel.getSubtype() == 6){//铁幕防御
				//管理buff生命周期
				for(int i=0;i<num;i++){
					player.getBuffManager().addItemBuff(itemId, BuffType.TIEMU_FANGYU, Integer.valueOf(v2.toString()), value);
				}
				return true;
			}
		}
		return false;
	}
}
