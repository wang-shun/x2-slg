package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceApplyBean - 联盟信息
 */
public class AllianceApplyBean extends XPro {
	/**玩家id*/
	@MsgField(Id = 1)
	public AlliancePlayerViewBean alliancePlayerViewBean;
	/**申请描述*/
	@MsgField(Id = 2)
	public String applyDesc;
	/**申请加入时间*/
	@MsgField(Id = 3)
	public long applyTime;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("alliancePlayerViewBean:" + alliancePlayerViewBean +",");
		buf.append("applyDesc:" + applyDesc +",");
		buf.append("applyTime:" + applyTime +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}