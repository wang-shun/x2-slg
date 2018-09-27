package com.xgame.logic.server.game.war.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-WarAttr - 战斗属性
 */
public class WarAttr extends XPro {
	/**攻击*/
	@MsgField(Id = 1)
	public int attack;
	/**防御*/
	@MsgField(Id = 2)
	public int defend;
	/**血量*/
	@MsgField(Id = 3)
	public int hp;
	/**功率*/
	@MsgField(Id = 4)
	public double power;
	/**负载*/
	@MsgField(Id = 5)
	public double load;
	/**电磁伤害*/
	@MsgField(Id = 6)
	public double electricityDamage;
	/**电磁抗性*/
	@MsgField(Id = 7)
	public double electricityDefense;
	/**动能伤害*/
	@MsgField(Id = 8)
	public double energyDamage;
	/**动能抗性*/
	@MsgField(Id = 9)
	public double energyDefense;
	/**热能抗性*/
	@MsgField(Id = 10)
	public double heatDefense;
	/**热能伤害*/
	@MsgField(Id = 11)
	public double heatDamage;
	/**激光伤害*/
	@MsgField(Id = 12)
	public double laserDamage;
	/**激光抗性*/
	@MsgField(Id = 13)
	public double laserDefense;
	/**命中*/
	@MsgField(Id = 14)
	public double hit;
	/**闪避*/
	@MsgField(Id = 15)
	public double dodge;
	/**暴击*/
	@MsgField(Id = 16)
	public double crit;
	/**暴击等级*/
	@MsgField(Id = 17)
	public double critical;
	/**韧性等级*/
	@MsgField(Id = 18)
	public double toughness;
	/**移动速度*/
	@MsgField(Id = 19)
	public double speedBase;
	/**采集负重*/
	@MsgField(Id = 20)
	public double weight;
	/**索敌距离*/
	@MsgField(Id = 21)
	public double seekingDistance;
	/**索敌数量*/
	@MsgField(Id = 22)
	public double seekingNum;
	/**雷达强度*/
	@MsgField(Id = 23)
	public double radarIntensity;
	/**车体半径*/
	@MsgField(Id = 24)
	public double radius;
	/**消耗功率*/
	@MsgField(Id = 25)
	public double powerConsume;
	/**消耗负载*/
	@MsgField(Id = 26)
	public double loadConsume;
	/**结束字符*/
	@MsgField(Id = 27)
	public String endSplite;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("attack:" + attack +",");
		buf.append("defend:" + defend +",");
		buf.append("hp:" + hp +",");
		buf.append("power:" + power +",");
		buf.append("load:" + load +",");
		buf.append("electricityDamage:" + electricityDamage +",");
		buf.append("electricityDefense:" + electricityDefense +",");
		buf.append("energyDamage:" + energyDamage +",");
		buf.append("energyDefense:" + energyDefense +",");
		buf.append("heatDefense:" + heatDefense +",");
		buf.append("heatDamage:" + heatDamage +",");
		buf.append("laserDamage:" + laserDamage +",");
		buf.append("laserDefense:" + laserDefense +",");
		buf.append("hit:" + hit +",");
		buf.append("dodge:" + dodge +",");
		buf.append("crit:" + crit +",");
		buf.append("critical:" + critical +",");
		buf.append("toughness:" + toughness +",");
		buf.append("speedBase:" + speedBase +",");
		buf.append("weight:" + weight +",");
		buf.append("seekingDistance:" + seekingDistance +",");
		buf.append("seekingNum:" + seekingNum +",");
		buf.append("radarIntensity:" + radarIntensity +",");
		buf.append("radius:" + radius +",");
		buf.append("powerConsume:" + powerConsume +",");
		buf.append("loadConsume:" + loadConsume +",");
		buf.append("endSplite:" + endSplite +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}