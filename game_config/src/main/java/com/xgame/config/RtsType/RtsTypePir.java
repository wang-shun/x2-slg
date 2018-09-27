package com.xgame.config.RtsType;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:21 
 */
public class RtsTypePir extends BaseFilePri{
	
	/**﻿战斗类型id*/
	int id;
	/**进攻方：伤兵强制死亡比例(百分比，结果除100)（受伤的兵种直接判断死亡的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	int deathRateAtk;
	/**进攻方：伤兵直接返回军营的比例(百分比，结果除100)（受伤的兵种直接放回军营的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	int campRateAtk;
	/**进攻方：伤兵是否无视修理厂上限（受伤士兵全部返回修理厂，无视修理厂容量）*/
	int IgnoreHospitalLimitAtk;
	/**防守方：伤兵强制死亡比例(百分比，结果除100)（受伤的兵种直接判断死亡的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	int deathRateDef;
	/**防守方：伤兵直接返回军营的比例(百分比，结果除100)（受伤的兵种直接放回军营的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	int campRateDef;
	/**防守方：伤兵是否无视修理厂上限（受伤士兵全部返回修理厂，无视修理厂容量）*/
	int IgnoreHospitalLimitDef;
	/**战斗时长(单位秒)*/
	int time;
	
	
	
	/**﻿战斗类型id*/
	public int getId(){
		return id;
	}
	/**进攻方：伤兵强制死亡比例(百分比，结果除100)（受伤的兵种直接判断死亡的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	public int getDeathRateAtk(){
		return deathRateAtk;
	}
	/**进攻方：伤兵直接返回军营的比例(百分比，结果除100)（受伤的兵种直接放回军营的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	public int getCampRateAtk(){
		return campRateAtk;
	}
	/**进攻方：伤兵是否无视修理厂上限（受伤士兵全部返回修理厂，无视修理厂容量）*/
	public int getIgnoreHospitalLimitAtk(){
		return IgnoreHospitalLimitAtk;
	}
	/**防守方：伤兵强制死亡比例(百分比，结果除100)（受伤的兵种直接判断死亡的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	public int getDeathRateDef(){
		return deathRateDef;
	}
	/**防守方：伤兵直接返回军营的比例(百分比，结果除100)（受伤的兵种直接放回军营的比例，剩余的受伤兵种返回修理厂,受到修理厂容量的限制，超过修理厂容量的部分，判断死亡）*/
	public int getCampRateDef(){
		return campRateDef;
	}
	/**防守方：伤兵是否无视修理厂上限（受伤士兵全部返回修理厂，无视修理厂容量）*/
	public int getIgnoreHospitalLimitDef(){
		return IgnoreHospitalLimitDef;
	}
	/**战斗时长(单位秒)*/
	public int getTime(){
		return time;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}