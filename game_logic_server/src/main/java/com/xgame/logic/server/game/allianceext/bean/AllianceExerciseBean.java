package com.xgame.logic.server.game.allianceext.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-AllianceExerciseBean - 联盟演习
 */
public class AllianceExerciseBean extends XPro {
	/**演习阶段*/
	@MsgField(Id = 1)
	public int id;
	/**奖励道具列表*/
	@MsgField(Id = 2)
	public List<AwardBean> rewards = new ArrayList<AwardBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("rewards:{");
		for (int i = 0; i < rewards.size(); i++) {
			buf.append(rewards.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}