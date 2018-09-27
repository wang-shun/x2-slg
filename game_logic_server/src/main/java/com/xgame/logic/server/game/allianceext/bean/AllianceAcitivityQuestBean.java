package com.xgame.logic.server.game.allianceext.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-AllianceAcitivityQuestBean - 联盟活跃任务
 */
public class AllianceAcitivityQuestBean extends XPro {
	/**任务模版ID*/
	@MsgField(Id = 1)
	public int questId;
	/**完成次数*/
	@MsgField(Id = 2)
	public int count;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("questId:" + questId +",");
		buf.append("count:" + count +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}