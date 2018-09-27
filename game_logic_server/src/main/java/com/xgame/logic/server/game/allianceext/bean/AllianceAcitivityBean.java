package com.xgame.logic.server.game.allianceext.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-AllianceAcitivityBean - 联盟活跃
 */
public class AllianceAcitivityBean extends XPro {
	/**联盟id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**钢铁*/
	@MsgField(Id = 2)
	public int steel;
	/**稀土*/
	@MsgField(Id = 3)
	public int rare;
	/**石油*/
	@MsgField(Id = 4)
	public int oil;
	/**黄金*/
	@MsgField(Id = 5)
	public int gold;
	/**活跃度等级*/
	@MsgField(Id = 6)
	public int activityLv;
	/**活跃度积分*/
	@MsgField(Id = 7)
	public int activityScore;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceId:" + allianceId +",");
		buf.append("steel:" + steel +",");
		buf.append("rare:" + rare +",");
		buf.append("oil:" + oil +",");
		buf.append("gold:" + gold +",");
		buf.append("activityLv:" + activityLv +",");
		buf.append("activityScore:" + activityScore +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}