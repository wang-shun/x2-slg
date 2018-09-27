package com.xgame.logic.server.game.soldier.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Soldier-ReformSoldierBean - 驻军信息
 */
public class ReformSoldierBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public long id;
	/**0伤兵1正常兵*/
	@MsgField(Id = 2)
	public int type;
	/**兵信息*/
	@MsgField(Id = 3)
	public SoldierBean soldier;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("type:" + type +",");
		buf.append("soldier:" + soldier +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}