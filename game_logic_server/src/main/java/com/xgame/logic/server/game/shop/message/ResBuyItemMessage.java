package com.xgame.logic.server.game.shop.message;
import com.xgame.logic.server.game.shop.bean.ItemBuyBean;
import com.xgame.msglib.ResMessage;
import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Shop-ResBuyItem - 返回商城购买信息
 */
public class ResBuyItemMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=120102;
	//模块号
	public static final int FUNCTION_ID=120;
	//消息号
	public static final int MSG_ID=102;
	
	/**商品购买信息*/
	@MsgField(Id = 1)
	public ItemBuyBean shopBuyItem;
		
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
		buf.append("shopBuyItem:" + shopBuyItem +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}