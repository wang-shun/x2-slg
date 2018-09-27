package com.xgame.config.shop;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:22 
 */
public class ShopPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**出售数量*/
	int shop_number;
	/**道具ID*/
	Object item_id;
	/**名称*/
	int name;
	/**描述*/
	int description;
	/**品质(6个品质，1白，2绿，3蓝，4紫，5金，6橙)*/
	int quality;
	/**分页（1资源；2加速;3战争；4其他;5特卖,99材料；填0表示不显示）*/
	int type;
	/**原价格（道具在商城中的价格，若不填不在商城中显示）*/
	int price;
	/**特价*/
	int special_price;
	/**折扣标签(若不填不显示标签，填写的数字表示标签为几折)*/
	int tag;
	/**快速购买价格（若有数字表示可以购买，不填表示不可快速购买）*/
	int fast_price;
	/**特卖批次*/
	Object special_number;
	/**批次2*/
	Object special_number2;
	/**单次购买上限*/
	int ceili;
	/**图标*/
	Object icon;
	/**售卖时间（单位为秒）*/
	int time;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**出售数量*/
	public int getShop_number(){
		return shop_number;
	}
	/**道具ID*/
	@SuppressWarnings("unchecked")
	public <T> T getItem_id(){
		return (T)item_id;
	}
	/**名称*/
	public int getName(){
		return name;
	}
	/**描述*/
	public int getDescription(){
		return description;
	}
	/**品质(6个品质，1白，2绿，3蓝，4紫，5金，6橙)*/
	public int getQuality(){
		return quality;
	}
	/**分页（1资源；2加速;3战争；4其他;5特卖,99材料；填0表示不显示）*/
	public int getType(){
		return type;
	}
	/**原价格（道具在商城中的价格，若不填不在商城中显示）*/
	public int getPrice(){
		return price;
	}
	/**特价*/
	public int getSpecial_price(){
		return special_price;
	}
	/**折扣标签(若不填不显示标签，填写的数字表示标签为几折)*/
	public int getTag(){
		return tag;
	}
	/**快速购买价格（若有数字表示可以购买，不填表示不可快速购买）*/
	public int getFast_price(){
		return fast_price;
	}
	/**特卖批次*/
	@SuppressWarnings("unchecked")
	public <T> T getSpecial_number(){
		return (T)special_number;
	}
	/**批次2*/
	@SuppressWarnings("unchecked")
	public <T> T getSpecial_number2(){
		return (T)special_number2;
	}
	/**单次购买上限*/
	public int getCeili(){
		return ceili;
	}
	/**图标*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	/**售卖时间（单位为秒）*/
	public int getTime(){
		return time;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}