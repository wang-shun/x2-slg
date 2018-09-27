package com.xgame.logic.server.game.country.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Country-SwitchCarBean - 切换矿车bean
 */
public class SwitchCarBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public int uid;
	/**矿车切换bean*/
	@MsgField(Id = 2)
	public int resourceId;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("uid:" + uid +",");
		buf.append("resourceId:" + resourceId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}