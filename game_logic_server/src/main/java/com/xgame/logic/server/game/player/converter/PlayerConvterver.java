package com.xgame.logic.server.game.player.converter;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.player.message.ResUserInfoMessage;
import com.xgame.logic.server.game.world.entity.SpriteInfo;
import com.xgame.logic.server.game.world.entity.Vector2;


/**
 * 玩家
 * @author jacky.jiang
 *
 */
public class PlayerConvterver {

	
	public static  ResUserInfoMessage converterPlayerProto(Player player) {
		ResUserInfoMessage userProto = new ResUserInfoMessage();
		userProto.nickname = player.roleInfo().getBasics().getRoleName();
		userProto.resCP = player.roleInfo().getCurrency().getMoney();
		userProto.resGC = player.roleInfo().getCurrency().getSteel();
		userProto.resSY = player.roleInfo().getCurrency().getOil();
		userProto.resXT = player.roleInfo().getCurrency().getRare();
		userProto.resZS = player.roleInfo().getCurrency().getDiamond();
		userProto.spriteId = player.roleInfo().getBasics().getRoleId();
		userProto.beAttackTime = player.roleInfo().getBasics().getAttackTimer();
		userProto.worldLocation = new Vector2Bean();
		Vector2 vector2 = player.getLocation();
		if(vector2 != null) {
			userProto.worldLocation.x = vector2.getX();
			userProto.worldLocation.y = vector2.getY();
		} else {
			// TODO 修复数据...
			if(player.getWorldLogicManager().registerWorld(player)) {
				SpriteInfo spriteInfo = player.getSprite();
				vector2 = new Vector2(spriteInfo.getX(), spriteInfo.getY());
			}
		}
		userProto.logoutTime = player.roleInfo().getBasics().getLoginTime();
		userProto.server = player.roleInfo().getBasics().getServerArea();
		userProto.playerEquipmentBag = player.getEquipmentManager().getMsgPlayerEquipmentBag(player);
		return userProto;
	}
}
