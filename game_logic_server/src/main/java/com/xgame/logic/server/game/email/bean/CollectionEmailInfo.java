package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-CollectionEmailInfo - 采集报告
 */
public class CollectionEmailInfo extends XPro {
	/**邮件ID*/
	@MsgField(Id = 1)
	public long id;
	/**采集者签名*/
	@MsgField(Id = 2)
	public EmailSignature collectSignature;
	/**地点坐标*/
	@MsgField(Id = 3)
	public Vector2Bean targetPosition;
	/**资源类型*/
	@MsgField(Id = 4)
	public int type;
	/**资源等级*/
	@MsgField(Id = 5)
	public int level;
	/**资源值*/
	@MsgField(Id = 6)
	public long value;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("collectSignature:" + collectSignature +",");
		buf.append("targetPosition:" + targetPosition +",");
		buf.append("type:" + type +",");
		buf.append("level:" + level +",");
		buf.append("value:" + value +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}