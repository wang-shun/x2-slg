package com.xgame.logic.server.game.alliance.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-AllianceFightInfoBean - 军情
 */
public class AllianceFightInfoBean extends XPro {
	/**进攻方玩家id*/
	@MsgField(Id = 1)
	public long attackPlayerId;
	/**进攻方玩家名称*/
	@MsgField(Id = 2)
	public String attackPlayerName;
	/**目标*/
	@MsgField(Id = 3)
	public String target;
	/**战斗结果（1win2lose）*/
	@MsgField(Id = 4)
	public int result;
	/**战斗详情*/
	@MsgField(Id = 5)
	public String detail;
	/**进攻时间*/
	@MsgField(Id = 6)
	public long attackTime;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("attackPlayerId:" + attackPlayerId +",");
		buf.append("attackPlayerName:" + attackPlayerName +",");
		buf.append("target:" + target +",");
		buf.append("result:" + result +",");
		buf.append("detail:" + detail +",");
		buf.append("attackTime:" + attackTime +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}