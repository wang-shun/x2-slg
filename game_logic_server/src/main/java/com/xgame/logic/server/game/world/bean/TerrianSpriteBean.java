package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-TerrianSpriteBean - 空地精灵信息
 */
public class TerrianSpriteBean extends XPro {
	/**精灵主权签名*/
	@MsgField(Id = 1)
	public WorldAllianceSignure allianceSignure;
	/**出征玩家签名*/
	@MsgField(Id = 2)
	public WorldPlayerSignure occupyPlayer;
	/**开始时间*/
	@MsgField(Id = 3)
	public long startTime;
	/**结束时间*/
	@MsgField(Id = 4)
	public long endTime;
	/**当前正在扎营或者占领的队伍id*/
	@MsgField(Id = 5)
	public long ownerMarchId;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceSignure:" + allianceSignure +",");
		buf.append("occupyPlayer:" + occupyPlayer +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		buf.append("ownerMarchId:" + ownerMarchId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}