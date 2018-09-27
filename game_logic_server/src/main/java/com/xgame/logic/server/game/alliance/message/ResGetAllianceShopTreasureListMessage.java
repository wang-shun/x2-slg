package com.xgame.logic.server.game.alliance.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.AllianceShopItem;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Alliance-ResGetAllianceShopTreasureList - 请求军团商店珍品列表 返回
 */
public class ResGetAllianceShopTreasureListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007152;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=152;
	
	/**珍品信息列表*/
	@MsgField(Id = 1)
	public List<AllianceShopItem> treasureList = new ArrayList<AllianceShopItem>();
		
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
		buf.append("treasureList:{");
		for (int i = 0; i < treasureList.size(); i++) {
			buf.append(treasureList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}