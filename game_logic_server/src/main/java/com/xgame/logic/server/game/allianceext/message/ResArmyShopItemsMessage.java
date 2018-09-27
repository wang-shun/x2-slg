package com.xgame.logic.server.game.allianceext.message;
import com.xgame.logic.server.game.allianceext.bean.GoodsBean;
import com.xgame.logic.server.game.allianceext.bean.GoodsBean;
import java.util.ArrayList;
import java.util.List;
import com.xgame.msglib.ResMessage;

import com.xgame.msglib.able.Communicationable;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * AllianceExt-ResArmyShopItems - 返回军团商店信息
 */
public class ResArmyShopItemsMessage extends ResMessage {
	
	//模块号+消息号
	public static final int ID=1210118;
	//模块号
	public static final int FUNCTION_ID=1210;
	//消息号
	public static final int MSG_ID=118;
	
	/**普通商品*/
	@MsgField(Id = 1)
	public List<GoodsBean> commonItems = new ArrayList<GoodsBean>();
	/**珍稀商品*/
	@MsgField(Id = 2)
	public List<GoodsBean> treasureItems = new ArrayList<GoodsBean>();
		
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
		buf.append("commonItems:{");
		for (int i = 0; i < commonItems.size(); i++) {
			buf.append(commonItems.get(i).toString() +",");
		}
		buf.append("treasureItems:{");
		for (int i = 0; i < treasureItems.size(); i++) {
			buf.append(treasureItems.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}