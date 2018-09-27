package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-WorldAllianceSignure - 联盟主权签名
 */
public class WorldAllianceSignure extends XPro {
	/**联盟id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**联盟名称*/
	@MsgField(Id = 2)
	public String allianceName;
	/**简称*/
	@MsgField(Id = 3)
	public String allianceSimpleName;
	/**联盟头像*/
	@MsgField(Id = 4)
	public String allianceImg;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceId:" + allianceId +",");
		buf.append("allianceName:" + allianceName +",");
		buf.append("allianceSimpleName:" + allianceSimpleName +",");
		buf.append("allianceImg:" + allianceImg +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}