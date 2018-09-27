package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceRankPlayerBean - 联盟玩家展示数据
 */
public class AllianceRankPlayerBean extends XPro {
	/**玩家id*/
	@MsgField(Id = 1)
	public long playerId;
	/**玩家名称*/
	@MsgField(Id = 2)
	public String name;
	/**玩家头像*/
	@MsgField(Id = 3)
	public String img;
	/**战力*/
	@MsgField(Id = 4)
	public long fightPower;
	/**当前贡献*/
	@MsgField(Id = 5)
	public int donate;
	/**周贡献*/
	@MsgField(Id = 6)
	public int weekDonate;
	/**历史贡献*/
	@MsgField(Id = 7)
	public int historyDonate;
	/**杀敌数*/
	@MsgField(Id = 8)
	public int killNum;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("name:" + name +",");
		buf.append("img:" + img +",");
		buf.append("fightPower:" + fightPower +",");
		buf.append("donate:" + donate +",");
		buf.append("weekDonate:" + weekDonate +",");
		buf.append("historyDonate:" + historyDonate +",");
		buf.append("killNum:" + killNum +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}