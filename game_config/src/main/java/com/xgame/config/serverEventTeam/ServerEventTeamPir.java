package com.xgame.config.serverEventTeam;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:22 
 */
public class ServerEventTeamPir extends BaseFilePri{
	
	/**﻿主键*/
	int id;
	/**周一，类型（对应event表id）*/
	int type1;
	/**开始时间*/
	Object openTime1;
	/**周二，类型（对应event表id）*/
	int type2;
	/**开始时间*/
	Object openTime2;
	/**周三，类型（对应event表id）*/
	int type3;
	/**开始时间*/
	Object openTime3;
	/**周四，类型（对应event表id）*/
	int type4;
	/**开始时间*/
	Object openTime4;
	/**周五，类型（对应event表id）*/
	int type5;
	/**开始时间*/
	Object openTime5;
	/**周六，类型（对应event表id）*/
	int type6;
	/**开始时间*/
	Object openTime6;
	/**周日，类型（对应event表id）*/
	int type7;
	/**开始时间*/
	Object openTime7;
	
	
	
	/**﻿主键*/
	public int getId(){
		return id;
	}
	/**周一，类型（对应event表id）*/
	public int getType1(){
		return type1;
	}
	/**开始时间*/
	@SuppressWarnings("unchecked")
	public <T> T getOpenTime1(){
		return (T)openTime1;
	}
	/**周二，类型（对应event表id）*/
	public int getType2(){
		return type2;
	}
	/**开始时间*/
	@SuppressWarnings("unchecked")
	public <T> T getOpenTime2(){
		return (T)openTime2;
	}
	/**周三，类型（对应event表id）*/
	public int getType3(){
		return type3;
	}
	/**开始时间*/
	@SuppressWarnings("unchecked")
	public <T> T getOpenTime3(){
		return (T)openTime3;
	}
	/**周四，类型（对应event表id）*/
	public int getType4(){
		return type4;
	}
	/**开始时间*/
	@SuppressWarnings("unchecked")
	public <T> T getOpenTime4(){
		return (T)openTime4;
	}
	/**周五，类型（对应event表id）*/
	public int getType5(){
		return type5;
	}
	/**开始时间*/
	@SuppressWarnings("unchecked")
	public <T> T getOpenTime5(){
		return (T)openTime5;
	}
	/**周六，类型（对应event表id）*/
	public int getType6(){
		return type6;
	}
	/**开始时间*/
	@SuppressWarnings("unchecked")
	public <T> T getOpenTime6(){
		return (T)openTime6;
	}
	/**周日，类型（对应event表id）*/
	public int getType7(){
		return type7;
	}
	/**开始时间*/
	@SuppressWarnings("unchecked")
	public <T> T getOpenTime7(){
		return (T)openTime7;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}