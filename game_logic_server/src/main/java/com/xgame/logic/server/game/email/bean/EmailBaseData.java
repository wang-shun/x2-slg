package com.xgame.logic.server.game.email.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-EmailBaseData - 邮件基本数据
 */
public class EmailBaseData extends XPro {
	/**发送时间*/
	@MsgField(Id = 1)
	public long time;
	/**当前状态 */
	@MsgField(Id = 2)
	public int status;
	/**邮件类型（1.普通邮件2.侦查报告,3被侦查,4...）*/
	@MsgField(Id = 3)
	public int type;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("time:" + time +",");
		buf.append("status:" + status +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}