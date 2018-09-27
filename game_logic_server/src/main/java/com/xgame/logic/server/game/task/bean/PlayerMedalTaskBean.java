package com.xgame.logic.server.game.task.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-PlayerMedalTaskBean - 勋章任务
 */
public class PlayerMedalTaskBean extends XPro {
	/**使用中的勋章*/
	@MsgField(Id = 1)
	public int useMedal;
	/**玩家勋章任务进度*/
	@MsgField(Id = 2)
	public List<MedalTaskBean> medalTaskList = new ArrayList<MedalTaskBean>();
	/**已完成的勋章id列表*/
	@MsgField(Id = 3)
	public List<Integer> finishMedal = new ArrayList<Integer>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("useMedal:" + useMedal +",");
		buf.append("medalTaskList:{");
		for (int i = 0; i < medalTaskList.size(); i++) {
			buf.append(medalTaskList.get(i).toString() +",");
		}
		buf.append("finishMedal:{");
		for (int i = 0; i < finishMedal.size(); i++) {
			buf.append(finishMedal.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}