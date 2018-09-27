package com.xgame.logic.server.game.shop.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Shop-ItemBuyBean - 物品购买信息
 */
public class ItemBuyBean extends XPro {
	/**物品id*/
	@MsgField(Id = 1)
	public int id;
	/**购买数量*/
	@MsgField(Id = 2)
	public int num;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("num:" + num +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}