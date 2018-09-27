package com.xgame.config.ziYuanShengCheng;
import com.xgame.config.BaseFilePri;
import com.alibaba.fastjson.JSON;

/** 
 * @author configTool
 * 
 * @version 1.0.0
 * @date 2017-05-12 09:54:22 
 */
public class ZiYuanShengChengPir extends BaseFilePri{
	
	/**﻿id*/
	int id;
	/**范围*/
	int range;
	/**战败是否传送*/
	int chuanSong;
	/**是否为军事管理区*/
	int junShi;
	/**不可走点*/
	int buKeZou;
	/**空地*/
	int kongDi;
	/**1级资源*/
	int Lv1;
	/**2级资源*/
	int Lv2;
	/**3级资源*/
	int Lv3;
	/**4级资源*/
	int Lv4;
	/**5级资源*/
	int Lv5;
	/**6级资源*/
	int Lv6;
	/**7级资源*/
	int Lv7;
	/**石油1*/
	int shiYou;
	/**稀土2*/
	int xiTu;
	/**钢材3*/
	int gangCai;
	/**黄金4*/
	int dianLi;
	/**钻石5*/
	int zuanShi;
	/**平原*/
	int pingYuan;
	/**丘陵*/
	int qiuLing;
	/**山地*/
	int shanDi;
	/**丛林*/
	int congLin;
	/**沼泽*/
	int zhaoZe;
	
	
	
	/**﻿id*/
	public int getId(){
		return id;
	}
	/**范围*/
	public int getRange(){
		return range;
	}
	/**战败是否传送*/
	public int getChuanSong(){
		return chuanSong;
	}
	/**是否为军事管理区*/
	public int getJunShi(){
		return junShi;
	}
	/**不可走点*/
	public int getBuKeZou(){
		return buKeZou;
	}
	/**空地*/
	public int getKongDi(){
		return kongDi;
	}
	/**1级资源*/
	public int getLv1(){
		return Lv1;
	}
	/**2级资源*/
	public int getLv2(){
		return Lv2;
	}
	/**3级资源*/
	public int getLv3(){
		return Lv3;
	}
	/**4级资源*/
	public int getLv4(){
		return Lv4;
	}
	/**5级资源*/
	public int getLv5(){
		return Lv5;
	}
	/**6级资源*/
	public int getLv6(){
		return Lv6;
	}
	/**7级资源*/
	public int getLv7(){
		return Lv7;
	}
	/**石油1*/
	public int getShiYou(){
		return shiYou;
	}
	/**稀土2*/
	public int getXiTu(){
		return xiTu;
	}
	/**钢材3*/
	public int getGangCai(){
		return gangCai;
	}
	/**黄金4*/
	public int getDianLi(){
		return dianLi;
	}
	/**钻石5*/
	public int getZuanShi(){
		return zuanShi;
	}
	/**平原*/
	public int getPingYuan(){
		return pingYuan;
	}
	/**丘陵*/
	public int getQiuLing(){
		return qiuLing;
	}
	/**山地*/
	public int getShanDi(){
		return shanDi;
	}
	/**丛林*/
	public int getCongLin(){
		return congLin;
	}
	/**沼泽*/
	public int getZhaoZe(){
		return zhaoZe;
	}
	
	
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}