package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-BeScoutEmailInfo - 被侦查报告
 */
public class BeScoutEmailInfo extends XPro {
	/**侦查人签名*/
	@MsgField(Id = 1)
	public EmailSignature scoutSignature;
	/**地点坐标*/
	@MsgField(Id = 2)
	public Vector2Bean targetPosition;
	/**资源类型：1-石油；2-稀土；3-铁矿；4-金矿*/
	@MsgField(Id = 3)
	public int resourceType;
	/**资源等级*/
	@MsgField(Id = 4)
	public int resourceLevel;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("scoutSignature:" + scoutSignature +",");
		buf.append("targetPosition:" + targetPosition +",");
		buf.append("resourceType:" + resourceType +",");
		buf.append("resourceLevel:" + resourceLevel +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}