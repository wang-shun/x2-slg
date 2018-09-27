package com.xgame.logic.server.game.war.bean;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-WarSoldierBean - 兵种信息
 */
public class WarSoldierBean extends XPro {
	/**序列号*/
	@MsgField(Id = 1)
	public int index;
	/**士兵*/
	@MsgField(Id = 2)
	public FullSoldierBean soldier;
	/**战斗属性*/
	@MsgField(Id = 3)
	public WarAttr warAttr;
	/**死亡士兵数量*/
	@MsgField(Id = 4)
	public int deadNum;
	/**伤亡士兵的战力*/
	@MsgField(Id = 5)
	public int fightPower;
	/**玩家id*/
	@MsgField(Id = 6)
	public long playerId;
	/**位置*/
	@MsgField(Id = 7)
	public Vector2Bean position;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("index:" + index +",");
		buf.append("soldier:" + soldier +",");
		buf.append("warAttr:" + warAttr +",");
		buf.append("deadNum:" + deadNum +",");
		buf.append("fightPower:" + fightPower +",");
		buf.append("playerId:" + playerId +",");
		buf.append("position:" + position +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}