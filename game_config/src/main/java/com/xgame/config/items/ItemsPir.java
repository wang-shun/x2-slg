package com.xgame.config.items;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class ItemsPir extends BaseFilePri{
	
	/**﻿ID*/
	int id;
	/**名称*/
	Object name;
	/**道具描述*/
	Object desc;
	/**角标数值，放在左下角的位置*/
	Object showNum;
	/**是否在背包中使用*/
	int use;
	/**分页标签(1资源，2加速，3战争，4其他)*/
	int tab;
	/**品质（1白2绿3蓝4紫5橙6红）*/
	int quality;
	/**类型（1：使用增加数值类道具，2：加速类道具，3：buff类道具(加属性)，4：buff类道具(非属性)，5：传送类道具（不再使用），6：普通道具，7：宝箱道具（V1=1234通用宝箱面板，5 特定配件模型展示），8：自动使用，9：装备道具，10:碎片类道具，背包内使用合成）*/
	int type;
	/**道具子类(type=3,subtype：1表示世界采集加速，2表示矿车采集加速,3出征士兵上限提升，4屯兵减耗,5加攻击，6加防御)*/
	int subtype;
	/**获得道具时飞向类型(-1:不飞，0，背包，1石油，2稀土，3钢材，4黄金，5钻石，6经验，7体力)*/
	int flyTypeObtain;
	/**使用道具时飞向类型(-1:不飞，0，背包，1石油，2稀土，3钢材，4黄金，5钻石，6经验，7体力)*/
	int flyTypeUse;
	/**V1(type=1或8时v1字段1石油，2稀土，3钢材，4黄金，5钻石，6经验，7体力，8模拟器筹码，9军团贡献)（type=2,1通用加速，2建筑加速，3科研加速，4造兵加速）（type=3,v1字段的配置同属性加成一致）(type=4:1免侦查，2免战护盾，4侦查伪装，5行军加速，6经验加成)（type=7,1固定宝箱，2随机宝箱，3随机兵种箱，4充值活动宝箱）(type=9:1-10表示装备材料的不同类型)（其他类型的typev1字段不需要填写）*/
	Object v1;
	/**V2(宝箱例1（固定宝箱）：111906_5,100131_150表示（道具ID_产出数量），例2（随机宝箱）：111906_5,100131_15;2表示（道具ID_产出数量区间_产出权重;必出类数量）例3（随机兵种箱）：1202;2_100_40,1_100_30;1表示（建筑;倒数位置_产出数量_产出权重;随机次数）例4（充值活动宝箱）：表示对应armyBox表id，玩家获得此道具自动使用激活联盟对应宝箱，玩家若没加入联盟，该奖励无效)*/
	Object v2;
	/**最大叠加数量*/
	int max;
	/**道具说明*/
	Object tips;
	/**图标ID*/
	Object icon;
	/**UI接口*/
	Object UI;
	
	
	
	/**﻿ID*/
	public int getId(){
		return id;
	}
	/**名称*/
	@SuppressWarnings("unchecked")
	public <T> T getName(){
		return (T)name;
	}
	/**道具描述*/
	@SuppressWarnings("unchecked")
	public <T> T getDesc(){
		return (T)desc;
	}
	/**角标数值，放在左下角的位置*/
	@SuppressWarnings("unchecked")
	public <T> T getShowNum(){
		return (T)showNum;
	}
	/**是否在背包中使用*/
	public int getUse(){
		return use;
	}
	/**分页标签(1资源，2加速，3战争，4其他)*/
	public int getTab(){
		return tab;
	}
	/**品质（1白2绿3蓝4紫5橙6红）*/
	public int getQuality(){
		return quality;
	}
	/**类型（1：使用增加数值类道具，2：加速类道具，3：buff类道具(加属性)，4：buff类道具(非属性)，5：传送类道具（不再使用），6：普通道具，7：宝箱道具（V1=1234通用宝箱面板，5 特定配件模型展示），8：自动使用，9：装备道具，10:碎片类道具，背包内使用合成）*/
	public int getType(){
		return type;
	}
	/**道具子类(type=3,subtype：1表示世界采集加速，2表示矿车采集加速,3出征士兵上限提升，4屯兵减耗,5加攻击，6加防御)*/
	public int getSubtype(){
		return subtype;
	}
	/**获得道具时飞向类型(-1:不飞，0，背包，1石油，2稀土，3钢材，4黄金，5钻石，6经验，7体力)*/
	public int getFlyTypeObtain(){
		return flyTypeObtain;
	}
	/**使用道具时飞向类型(-1:不飞，0，背包，1石油，2稀土，3钢材，4黄金，5钻石，6经验，7体力)*/
	public int getFlyTypeUse(){
		return flyTypeUse;
	}
	/**V1(type=1或8时v1字段1石油，2稀土，3钢材，4黄金，5钻石，6经验，7体力，8模拟器筹码，9军团贡献)（type=2,1通用加速，2建筑加速，3科研加速，4造兵加速）（type=3,v1字段的配置同属性加成一致）(type=4:1免侦查，2免战护盾，4侦查伪装，5行军加速，6经验加成)（type=7,1固定宝箱，2随机宝箱，3随机兵种箱，4充值活动宝箱）(type=9:1-10表示装备材料的不同类型)（其他类型的typev1字段不需要填写）*/
	@SuppressWarnings("unchecked")
	public <T> T getV1(){
		return (T)v1;
	}
	/**V2(宝箱例1（固定宝箱）：111906_5,100131_150表示（道具ID_产出数量），例2（随机宝箱）：111906_5,100131_15;2表示（道具ID_产出数量区间_产出权重;必出类数量）例3（随机兵种箱）：1202;2_100_40,1_100_30;1表示（建筑;倒数位置_产出数量_产出权重;随机次数）例4（充值活动宝箱）：表示对应armyBox表id，玩家获得此道具自动使用激活联盟对应宝箱，玩家若没加入联盟，该奖励无效)*/
	@SuppressWarnings("unchecked")
	public <T> T getV2(){
		return (T)v2;
	}
	/**最大叠加数量*/
	public int getMax(){
		return max;
	}
	/**道具说明*/
	@SuppressWarnings("unchecked")
	public <T> T getTips(){
		return (T)tips;
	}
	/**图标ID*/
	@SuppressWarnings("unchecked")
	public <T> T getIcon(){
		return (T)icon;
	}
	/**UI接口*/
	@SuppressWarnings("unchecked")
	public <T> T getUI(){
		return (T)UI;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}