package com.xgame.logic.server.game.allianceext.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-AllianceBuildBean - 联盟建筑
 */
public class AllianceBuildBean extends XPro {
	/**联盟建筑唯一id*/
	@MsgField(Id = 1)
	public String uid;
	/**联盟id*/
	@MsgField(Id = 2)
	public long allianceId;
	/**建筑id*/
	@MsgField(Id = 3)
	public int buildTid;
	/**坐标位置*/
	@MsgField(Id = 4)
	public Vector2Bean position;
	/**创建时间*/
	@MsgField(Id = 5)
	public long createTime;
	/**建筑等级*/
	@MsgField(Id = 6)
	public int level;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("uid:" + uid +",");
		buf.append("allianceId:" + allianceId +",");
		buf.append("buildTid:" + buildTid +",");
		buf.append("position:" + position +",");
		buf.append("createTime:" + createTime +",");
		buf.append("level:" + level +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}