package com.xgame.logic.server.game.copy.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Copy-CopyRaidResult - 扫荡结果
 */
public class CopyRaidResult extends XPro {
	/**副本id*/
	@MsgField(Id = 1)
	public int copyId;
	/**节点ID*/
	@MsgField(Id = 2)
	public int copyPointId;
	/**奖励列表(id_num,id_num...)*/
	@MsgField(Id = 3)
	public String rewardList;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("copyId:" + copyId +",");
		buf.append("copyPointId:" + copyPointId +",");
		buf.append("rewardList:" + rewardList +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}