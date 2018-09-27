package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-SoldierEmailInfo - 驻军部队
 */
public class SoldierEmailInfo extends XPro {
	/**兵种Id*/
	@MsgField(Id = 1)
	public long soldierId;
	/**人数*/
	@MsgField(Id = 2)
	public int num;
	/**防御塔位置*/
	@MsgField(Id = 3)
	public Vector2Bean loaction;
	/**图纸信息*/
	@MsgField(Id = 4)
	public DesignMapBean designMapBean;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldierId:" + soldierId +",");
		buf.append("num:" + num +",");
		buf.append("loaction:" + loaction +",");
		buf.append("designMapBean:" + designMapBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}