package com.xgame.utils;

import com.xgame.framework.rpc.RPC_Sprite;
import com.xgame.utils.bean.PositionPro;
import com.xgame.utils.bean.SingleMsgSpritePro;
import com.xgame.utils.message.ResSingleMsgSpriteMessage;

public class ProtoWrapHelper {

	public static ResSingleMsgSpriteMessage wrapSpriteProto(RPC_Sprite sprite) {
		ResSingleMsgSpriteMessage single = new ResSingleMsgSpriteMessage();
		single.singleSpritePro=getSpritePro(sprite);
		return single;
	}
	
	public static SingleMsgSpritePro getSpritePro(RPC_Sprite sprite){
		SingleMsgSpritePro spritePro = new SingleMsgSpritePro();
		spritePro.level = sprite.getLevel();

		spritePro.name = sprite.getSpriteName();

		spritePro.spriteId = sprite.getUniqueId();

		spritePro.type = sprite.getSpriteType();

		spritePro.worldLocation = new PositionPro();

		spritePro.worldLocation.x = sprite.getTx();

		spritePro.worldLocation.y = sprite.getTy();
		spritePro.attackTime = sprite.attackTime;

		return spritePro;
	}
	
}
