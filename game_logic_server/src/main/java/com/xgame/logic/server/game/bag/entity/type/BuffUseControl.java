package com.xgame.logic.server.game.bag.entity.type;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.xgame.config.items.ItemsPir;
import com.xgame.config.items.ItemsPirFactory;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.bag.entity.ItemContext;
import com.xgame.logic.server.game.bag.entity.ItemControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.sprite.PlayerSprite;

/**
 * buff 使用 不加属性 type=4
 * @author jacky.jiang
 *
 */
public class BuffUseControl extends ItemControl {

	public BuffUseControl(int type) {
		super(type);
	}

	@Override
	public boolean use(Player player, int itemId, int num, ItemContext rewardContext, Object... param) {
		if(num < 1){
			return false;
		}
		ItemsPir itemsPir = ItemsPirFactory.get(itemId);
		if(itemsPir != null) {
			int v1 = Integer.parseInt(itemsPir.getV1());
			if(v1 == 1) {//侦查保护
				for(int i=0;i<num;i++){
					long avoidInvestTime = player.roleInfo().getRadarData().getAvoidInvestTime();
					if(avoidInvestTime > 0 && avoidInvestTime > System.currentTimeMillis()){
						player.roleInfo().getRadarData().setAvoidInvestTime(avoidInvestTime + Long.valueOf(itemsPir.getV2().toString())*1000);
					}else{
						player.roleInfo().getRadarData().setAvoidInvestTime(System.currentTimeMillis() + Long.valueOf(itemsPir.getV2().toString())*1000);
					}
				}
				return true;
			} else if (v1 == 2) {// 能量护盾
				SpriteInfo spriteInfo = player.getSprite();
				if(spriteInfo.getParam() != null) {
					for(int i=0;i<num;i++){
						PlayerSprite playerSprite = spriteInfo.getParam();
						if(playerSprite.getShieldTime() > 0 && playerSprite.getShieldTime() > System.currentTimeMillis()) {
							playerSprite.setShieldTime(playerSprite.getShieldTime() + Long.valueOf(itemsPir.getV2().toString())*1000);
						} else {
							playerSprite.setShieldTime(System.currentTimeMillis() + Long.valueOf(itemsPir.getV2().toString()) * 1000);
						}
						InjectorUtil.getInjector().dbCacheService.update(spriteInfo);
						// 推送通知世界
						player.getWorldLogicManager().pushToWorldSprites(spriteInfo,Lists.newArrayList(player.getId()));
					}
					return true;
				}
			} else if (v1 == 4) {// 侦查伪装
				for (int i = 0; i < num; i++) {
					long investPretendTime = player.roleInfo().getRadarData().getInvestPretendTime();
					if(investPretendTime > 0 && investPretendTime > System.currentTimeMillis()){
						player.roleInfo().getRadarData().setInvestPretendTime(investPretendTime + Long.valueOf(itemsPir.getV2().toString())*1000);
					}else{
						player.roleInfo().getRadarData().setInvestPretendTime(System.currentTimeMillis() + Long.valueOf(itemsPir.getV2().toString())*1000);
					}
				}
				return true;
			} else if (v1 == 6) {// 经验加成
				String v2 = itemsPir.getV2().toString();
				if(!StringUtils.isEmpty(v2) && v2.split(",").length == 2){
					String[] v2Arr = v2.split(",");
					for(int i=0;i<num;i++){
						long expAddTime = player.roleInfo().getCommanderData().getExpAddTime();
						if(expAddTime > 0 && expAddTime > System.currentTimeMillis()){
							player.roleInfo().getCommanderData().setExpAddTime(expAddTime + Long.valueOf(v2Arr[1])*1000);
							player.roleInfo().getCommanderData().setExpAddPercent(Double.valueOf(v2Arr[0]));
						}else{
							player.roleInfo().getCommanderData().setExpAddTime(System.currentTimeMillis() + Long.valueOf(v2Arr[1])*1000);
							player.roleInfo().getCommanderData().setExpAddPercent(Double.valueOf(v2Arr[0]));
						}
					}
					return true;
				}
			}
		}
		return false;
	}
}
