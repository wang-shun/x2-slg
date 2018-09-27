package com.xgame.logic.server.game.task.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-PlayerActiveTaskBean - 玩家活跃度任务
 */
public class PlayerActiveTaskBean extends XPro {
	/**已获得活跃度数量*/
	@MsgField(Id = 1)
	public int rewardActiveNum;
	/**已领取的刻度*/
	@MsgField(Id = 2)
	public List<Integer> rewardTag = new ArrayList<Integer>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("rewardActiveNum:" + rewardActiveNum +",");
		buf.append("rewardTag:{");
		for (int i = 0; i < rewardTag.size(); i++) {
			buf.append(rewardTag.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}