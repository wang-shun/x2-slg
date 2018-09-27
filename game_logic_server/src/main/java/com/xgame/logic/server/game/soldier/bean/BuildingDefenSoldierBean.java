package com.xgame.logic.server.game.soldier.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Soldier-BuildingDefenSoldierBean - 驻军信息
 */
public class BuildingDefenSoldierBean extends XPro {
	/**建筑UID*/
	@MsgField(Id = 1)
	public int buildingUid;
	/**兵信息*/
	@MsgField(Id = 2)
	public SoldierBean soldier;
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