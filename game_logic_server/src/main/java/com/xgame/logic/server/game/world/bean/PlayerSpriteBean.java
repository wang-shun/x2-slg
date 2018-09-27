package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-PlayerSpriteBean - 玩家精灵信息
 */
public class PlayerSpriteBean extends XPro {
	/**行政大楼等级*/
	@MsgField(Id = 1)
	public int level;
	/**被攻打时间*/
	@MsgField(Id = 2)
	public long attackTime;
	/**保护罩结束时间*/
	@MsgField(Id = 3)
	public long shieldTime;
	/**当前玩家信息*/
	@MsgField(Id = 4)
	public WorldPlayerSignure playerSignure;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("level:" + level +",");
		buf.append("attackTime:" + attackTime +",");
		buf.append("shieldTime:" + shieldTime +",");
		buf.append("playerSignure:" + playerSignure +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}