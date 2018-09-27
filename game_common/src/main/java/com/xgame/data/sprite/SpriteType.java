
package com.xgame.data.sprite;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *2016-9-13  18:25:24
 *@author ye.yuan
 */
public enum SpriteType {
	BLOCK(-1),// 障碍点
	NONE(0), // 空地
	PLAYER(1), // 玩家
	RESOURCE(2), // 资源
	TERRITORY(3), // 领地
	BUILDING(4),// 联盟建筑
	MONSTER(5), // 野怪
	CAMP(6), // 扎营
	;
	int type;
	
	public static Map<Integer, SpriteType>  factory = new HashMap<>();
	
	static{
		for(int i=0;i<values().length;i++){
			factory.put(values()[i].type, values()[i]);
		}
	}
	
	public static SpriteType valueOf(int type) {
		return factory.get(type);
	}
	
	
	private SpriteType(int type) {
		this.type=type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
