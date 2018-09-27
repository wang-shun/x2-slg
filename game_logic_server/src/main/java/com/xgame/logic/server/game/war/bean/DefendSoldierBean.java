package com.xgame.logic.server.game.war.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-DefendSoldierBean - 防守驻军
 */
public class DefendSoldierBean extends XPro {
	/**建筑Uid*/
	@MsgField(Id = 1)
	public int buildingUid;
	/**兵种信息*/
	@MsgField(Id = 2)
	public WarSoldierBean soldier;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("buildingUid:" + buildingUid +",");
		buf.append("soldier:" + soldier +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}