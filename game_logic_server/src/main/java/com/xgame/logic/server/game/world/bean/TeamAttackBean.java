package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-TeamAttackBean - 地貌信息
 */
public class TeamAttackBean extends XPro {
	/**队伍id*/
	@MsgField(Id = 1)
	public String teamId;
	/**进攻方id*/
	@MsgField(Id = 2)
	public long attackId;
	/**进攻方名称*/
	@MsgField(Id = 3)
	public String attackName;
	/**进攻方头像*/
	@MsgField(Id = 4)
	public String attackImg;
	/**进攻方联盟简称*/
	@MsgField(Id = 5)
	public String attackAllianceAbbr;
	/**防守方id*/
	@MsgField(Id = 6)
	public long defendId;
	/**防守方名称*/
	@MsgField(Id = 7)
	public String defendName;
	/**防守方图片*/
	@MsgField(Id = 8)
	public String defendImg;
	/**防守方联盟简称*/
	@MsgField(Id = 9)
	public String defendAllianceAbbr;
	/**当前容量*/
	@MsgField(Id = 10)
	public int currentValue;
	/**最大容量*/
	@MsgField(Id = 11)
	public int maxCapacity;
	/**召集结束时间*/
	@MsgField(Id = 12)
	public long remainTime;
	/**创建时间*/
	@MsgField(Id = 13)
	public long createTime;
	/**成员数量*/
	@MsgField(Id = 14)
	public int memberNum;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("teamId:" + teamId +",");
		buf.append("attackId:" + attackId +",");
		buf.append("attackName:" + attackName +",");
		buf.append("attackImg:" + attackImg +",");
		buf.append("attackAllianceAbbr:" + attackAllianceAbbr +",");
		buf.append("defendId:" + defendId +",");
		buf.append("defendName:" + defendName +",");
		buf.append("defendImg:" + defendImg +",");
		buf.append("defendAllianceAbbr:" + defendAllianceAbbr +",");
		buf.append("currentValue:" + currentValue +",");
		buf.append("maxCapacity:" + maxCapacity +",");
		buf.append("remainTime:" + remainTime +",");
		buf.append("createTime:" + createTime +",");
		buf.append("memberNum:" + memberNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}