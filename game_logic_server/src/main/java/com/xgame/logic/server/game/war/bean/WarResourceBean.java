package com.xgame.logic.server.game.war.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-WarResourceBean - 资源bean
 */
public class WarResourceBean extends XPro {
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**钢铁数量*/
	@MsgField(Id = 2)
	public int steelNum;
	/**石油数量*/
	@MsgField(Id = 3)
	public int oilNum;
	/**稀土数量*/
	@MsgField(Id = 4)
	public int rareNum;
	/**黄金数量*/
	@MsgField(Id = 5)
	public int moneyNum;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("steelNum:" + steelNum +",");
		buf.append("oilNum:" + oilNum +",");
		buf.append("rareNum:" + rareNum +",");
		buf.append("moneyNum:" + moneyNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}