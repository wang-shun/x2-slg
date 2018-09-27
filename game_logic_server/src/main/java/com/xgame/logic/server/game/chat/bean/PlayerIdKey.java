package com.xgame.logic.server.game.chat.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Chat-PlayerIdKey - 玩家key
 */
public class PlayerIdKey extends XPro {
	/**玩家信息*/
	@MsgField(Id = 1)
	public long playerId;
	/**更新信息*/
	@MsgField(Id = 2)
	public long updateTime;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("playerId:" + playerId +",");
		buf.append("updateTime:" + updateTime +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}