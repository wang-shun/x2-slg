package com.xgame.logic.server.game.world.message;

import java.util.ArrayList;
import java.util.List;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;

/** 
 * @author ProtocolEditor
 * World-ResCollectList - 返回收藏列表
 */
public class ResCollectListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121125;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=125;
	
	/**好友信息*/
	@MsgField(Id = 1)
	public List<SpriteBean> friendList = new ArrayList<SpriteBean>();
	/**敌人好友*/
	@MsgField(Id = 2)
	public List<SpriteBean> enmyList = new ArrayList<SpriteBean>();
	/**资源信息*/
	@MsgField(Id = 3)
	public List<SpriteBean> resourceList = new ArrayList<SpriteBean>();
		
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
		buf.append("friendList:{");
		for (int i = 0; i < friendList.size(); i++) {
			buf.append(friendList.get(i).toString() +",");
		}
		buf.append("enmyList:{");
		for (int i = 0; i < enmyList.size(); i++) {
			buf.append(enmyList.get(i).toString() +",");
		}
		buf.append("resourceList:{");
		for (int i = 0; i < resourceList.size(); i++) {
			buf.append(resourceList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}