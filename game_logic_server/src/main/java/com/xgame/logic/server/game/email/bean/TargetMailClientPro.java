package com.xgame.logic.server.game.email.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-TargetMailClientPro - 发送邮件日志
 */
public class TargetMailClientPro extends XPro {
	/**目标id*/
	@MsgField(Id = 1)
	public long targetId;
	/**目标名字*/
	@MsgField(Id = 2)
	public String targetName;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("targetId:" + targetId +",");
		buf.append("targetName:" + targetName +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}