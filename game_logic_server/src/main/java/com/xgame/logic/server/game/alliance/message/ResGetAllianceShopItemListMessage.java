package com.xgame.logic.server.game.alliance.message;


import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.alliance.bean.AllianceShopItem;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;


/** 
 * @author ProtocolEditor
 * Alliance-ResGetAllianceShopItemList - 请求军团商店兑换信息 返回
 */
public class ResGetAllianceShopItemListMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1007150;
	//模块号
	public static final int FUNCTION_ID=1007;
	//消息号
	public static final int MSG_ID=150;
	
	/**物品列表*/
	@MsgField(Id = 1)
	public List<AllianceShopItem> itemList = new ArrayList<AllianceShopItem>();
	/**珍品列表*/
	@MsgField(Id = 2)
	public List<AllianceShopItem> treasureItemList = new ArrayList<AllianceShopItem>();
		
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
		buf.append("itemList:{");
		for (int i = 0; i < itemList.size(); i++) {
			buf.append(itemList.get(i).toString() +",");
		}
		buf.append("treasureItemList:{");
		for (int i = 0; i < treasureItemList.size(); i++) {
			buf.append(treasureItemList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}