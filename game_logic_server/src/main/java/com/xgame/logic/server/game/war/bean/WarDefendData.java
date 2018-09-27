package com.xgame.logic.server.game.war.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-WarDefendData - 防守方数据
 */
public class WarDefendData extends XPro {
	/**防守方Uid*/
	@MsgField(Id = 1)
	public long defendUid;
	/**防守方Uid*/
	@MsgField(Id = 2)
	public List<WarBuilding> buildings = new ArrayList<WarBuilding>();
	/**驻军信息*/
	@MsgField(Id = 3)
	public List<DefendSoldierBean> soldiers = new ArrayList<DefendSoldierBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("defendUid:" + defendUid +",");
		buf.append("buildings:{");
		for (int i = 0; i < buildings.size(); i++) {
			buf.append(buildings.get(i).toString() +",");
		}
		buf.append("soldiers:{");
		for (int i = 0; i < soldiers.size(); i++) {
			buf.append(soldiers.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}