package com.xgame.logic.server.game.email.bean;

import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author ProtocolEditor
 * Email-TroopEmailInfo - 扎营报告
 */
public class TroopEmailInfo extends XPro {
	/**邮件ID*/
	@MsgField(Id = 1)
	public long id;
	/**扎营者签名*/
	@MsgField(Id = 2)
	public EmailSignature troopSignature;
	/**邮件基础数据*/
	@MsgField(Id = 3)
	public EmailBaseData baseData;
	/**地点坐标*/
	@MsgField(Id = 4)
	public Vector2Bean targetPosition;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("troopSignature:" + troopSignature +",");
		buf.append("baseData:" + baseData +",");
		buf.append("targetPosition:" + targetPosition +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}