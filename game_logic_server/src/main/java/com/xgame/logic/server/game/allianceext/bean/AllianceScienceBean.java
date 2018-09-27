package com.xgame.logic.server.game.allianceext.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-AllianceScienceBean - 联盟科技
 */
public class AllianceScienceBean extends XPro {
	/**联盟建筑唯一id*/
	@MsgField(Id = 1)
	public String uid;
	/**联盟id*/
	@MsgField(Id = 2)
	public long allianceId;
	/**科技配置id*/
	@MsgField(Id = 3)
	public int techTid;
	/**科技等级*/
	@MsgField(Id = 4)
	public int level;
	/**经验*/
	@MsgField(Id = 5)
	public int exp;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("uid:" + uid +",");
		buf.append("allianceId:" + allianceId +",");
		buf.append("techTid:" + techTid +",");
		buf.append("level:" + level +",");
		buf.append("exp:" + exp +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}