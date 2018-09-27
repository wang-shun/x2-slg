package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceHelpeBean - 联盟帮助
 */
public class AllianceHelpeBean extends XPro {
	/**唯一id*/
	@MsgField(Id = 1)
	public String helpid;
	/**联盟id*/
	@MsgField(Id = 2)
	public long allianceId;
	/**发送方id*/
	@MsgField(Id = 3)
	public Long sendPlayerId;
	/**发送方*/
	@MsgField(Id = 4)
	public String playerName;
	/**发送方*/
	@MsgField(Id = 5)
	public String allianceName;
	/**帮助方玩家name*/
	@MsgField(Id = 6)
	public String helpName;
	/**帮助方玩家id*/
	@MsgField(Id = 7)
	public Long helpPlayerId;
	/**援建类型（1创建建筑物2建筑升级3科技升级4士兵修改）*/
	@MsgField(Id = 8)
	public int type;
	/**id标识*/
	@MsgField(Id = 9)
	public int sid;
	/**等级*/
	@MsgField(Id = 10)
	public int level;
	/**当前次数*/
	@MsgField(Id = 11)
	public int nowcount;
	/**最大次数*/
	@MsgField(Id = 12)
	public int maxcount;
	/**援建减少时间*/
	@MsgField(Id = 13)
	public int reduceSec;
	/**开始时间*/
	@MsgField(Id = 14)
	public long startTime;
	/**结束时间*/
	@MsgField(Id = 15)
	public long endTime;
	/**任务id*/
	@MsgField(Id = 16)
	public long taskId;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("helpid:" + helpid +",");
		buf.append("allianceId:" + allianceId +",");
		buf.append("sendPlayerId:" + sendPlayerId +",");
		buf.append("playerName:" + playerName +",");
		buf.append("allianceName:" + allianceName +",");
		buf.append("helpName:" + helpName +",");
		buf.append("helpPlayerId:" + helpPlayerId +",");
		buf.append("type:" + type +",");
		buf.append("sid:" + sid +",");
		buf.append("level:" + level +",");
		buf.append("nowcount:" + nowcount +",");
		buf.append("maxcount:" + maxcount +",");
		buf.append("reduceSec:" + reduceSec +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		buf.append("taskId:" + taskId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}