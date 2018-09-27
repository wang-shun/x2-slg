package com.xgame.logic.server.game.shop.message;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.shop.bean.MsgShopItem;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Shop-ResShopInfo - 返回商城信息
 */
public class ResShopInfoMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=120100;
	//模块号
	public static final int FUNCTION_ID=120;
	//消息号
	public static final int MSG_ID=100;
	
	/***/
	@MsgField(Id = 1)
	public int specialNum;
	/***/
	@MsgField(Id = 2)
	public List<MsgShopItem> shopItems = new ArrayList<MsgShopItem>();
		
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
		buf.append("specialNum:" + specialNum +",");
		buf.append("shopItems:{");
		for (int i = 0; i < shopItems.size(); i++) {
			buf.append(shopItems.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}