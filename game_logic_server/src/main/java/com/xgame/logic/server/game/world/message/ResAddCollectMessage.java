package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResAddCollect - 返回收藏信息
 */
public class ResAddCollectMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121124;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=124;
	
	/**精灵信息*/
	@MsgField(Id = 1)
	public SpriteBean spriteBean;
	/**服务器标识*/
	@MsgField(Id = 2)
	public int serverId;
	/**收藏类型*/
	@MsgField(Id = 3)
	public int type;
		
	@Override
	public int getId() {
		return ID;
	}

	@Override
	public String getQueue() {
		return "s2s";
	}
	
	@Override
	public String getServer() {
		return null;
	}
	
	@Override
	public boolean isSync() {
		return false;
	}

	@Override
	public CommandEnum getCommandEnum() {
		return Communicationable.CommandEnum.PLAYERMSG;
	}
	
	@Override
	public HandlerEnum getHandlerEnum() {
		return Communicationable.HandlerEnum.SC;
	}
	
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("spriteBean:" + spriteBean +",");
		buf.append("serverId:" + serverId +",");
		buf.append("type:" + type +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}