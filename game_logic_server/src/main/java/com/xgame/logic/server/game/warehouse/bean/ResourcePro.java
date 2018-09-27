package com.xgame.logic.server.game.warehouse.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Warehouse-ResourcePro - 资源pro
 */
public class ResourcePro extends XPro {
	/**资源类型*/
	@MsgField(Id = 1)
	public int type;
	/**资源*/
	@MsgField(Id = 2)
	public long resource;
	/**保护数量*/
	@MsgField(Id = 3)
	public long protect;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("type:" + type +",");
		buf.append("resource:" + resource +",");
		buf.append("protect:" + protect +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}