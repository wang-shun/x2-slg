package com.xgame.logic.server.game.world.message;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.world.bean.SpriteBean;
import com.xgame.logic.server.game.world.bean.VectorInfo;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResViewSprite - 返回周围信息
 */
public class ResViewSpriteMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=121101;
	//模块号
	public static final int FUNCTION_ID=121;
	//消息号
	public static final int MSG_ID=101;
	
	/**显示中心*/
	@MsgField(Id = 1)
	public Vector2Bean viewCenter;
	/**所在服务器id*/
	@MsgField(Id = 2)
	public int serverId;
	/**精灵信息列表(有可能会包含空地信息)*/
	@MsgField(Id = 3)
	public List<SpriteBean> spriteBeanList = new ArrayList<SpriteBean>();
	/**周围行军信息*/
	@MsgField(Id = 4)
	public List<VectorInfo> vectorInfoList = new ArrayList<VectorInfo>();
		
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
		buf.append("viewCenter:" + viewCenter +",");
		buf.append("serverId:" + serverId +",");
		buf.append("spriteBeanList:{");
		for (int i = 0; i < spriteBeanList.size(); i++) {
			buf.append(spriteBeanList.get(i).toString() +",");
		}
		buf.append("vectorInfoList:{");
		for (int i = 0; i < vectorInfoList.size(); i++) {
			buf.append(vectorInfoList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}