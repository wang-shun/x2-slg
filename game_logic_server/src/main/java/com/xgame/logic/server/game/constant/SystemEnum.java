package com.xgame.logic.server.game.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *2016-10-18  12:12:18
 *@author ye.yuan
 *
 */
public enum SystemEnum {
	
	COMMON(0,"缺省的"),

	SHOP(120801,"商店"),
	
	COMMONDER(130801,"指挥官"),
	
	TECH(107801,"科技馆"),
	
	MOD(105801,"修理厂"),
	
	ATTR(111801,"属性"),
	
	CAMP(101801,"军营"),
	
	LOGIN(104801,"登录"),
	
	EQUIP(300801,"装备"),
	
	COUNTRY(100801,"家园系统"),
	
	BOX(109801,"宝箱"),
	
	ALLIANCE(1007801, "首次加入军团奖励"),
	
	EVENT(1009,"事件"),
	
	FRAGMENT(109200,"碎片合成"),
	
	FASTPAID(1002218,"快速购买"),
	
	ALLIANCESHOP(1210219,"军团商店"),
	;
	
	private static Map<Integer, SystemEnum> table = new HashMap<>();
	
	static{
		for(int i=0;i<values().length;i++){
			table.put(values()[0].id, values()[0]);
		}
	}
	
	int id;
	
	String see;
	
	private SystemEnum(int id,String see) {
		this.id = id;
		this.see = see;
	}
	
	public static SystemEnum valueOf(int id) {
		return table.get(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
