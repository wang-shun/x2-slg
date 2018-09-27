package com.xgame.logic.server.game.world.bean;


import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author messageGenerator
 * 
 * @version 1.0.0
 * 
 * 队伍中玩家信息
 */
public class ViewSpriteCenter extends XPro {

	//显示中心
	@MsgField(Id = 1)
	public Vector2Bean viewCenter;
	
	//服务器标识
	@MsgField(Id = 2)
	public int serverId;
	
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		//显示中心
		if(this.viewCenter!=null) buf.append("viewCenter:" + viewCenter.toString() +",");
		//服务器标识
		buf.append("serverId:" + serverId +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}