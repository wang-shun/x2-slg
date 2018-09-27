package com.xgame.logic.server.game.armshop.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Armshop-CampDataPro - 等待收取
 */
public class CampDataPro extends XPro {
	/**建筑唯一id*/
	@MsgField(Id = 1)
	public int buildUid;
	/**等待收取士兵唯一id*/
	@MsgField(Id = 2)
	public List<Integer> waitGiveSoldierIds = new ArrayList<Integer>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("buildUid:" + buildUid +",");
		buf.append("waitGiveSoldierIds:{");
		for (int i = 0; i < waitGiveSoldierIds.size(); i++) {
			buf.append(waitGiveSoldierIds.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}