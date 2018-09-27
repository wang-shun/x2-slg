package com.xgame.logic.server.game.shop.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.bag.bean.MsgItem;
import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Shop-MsgShopItem - 商品信息
 */
public class MsgShopItem extends XPro {
	/***/
	@MsgField(Id = 1)
	public int id;
	/***/
	@MsgField(Id = 2)
	public int shop_number;
	/***/
	@MsgField(Id = 3)
	public int name;
	/***/
	@MsgField(Id = 4)
	public int quality;
	/***/
	@MsgField(Id = 5)
	public int description;
	/***/
	@MsgField(Id = 6)
	public int type;
	/***/
	@MsgField(Id = 7)
	public int price;
	/***/
	@MsgField(Id = 8)
	public int tag;
	/***/
	@MsgField(Id = 9)
	public int special_price;
	/***/
	@MsgField(Id = 10)
	public int fast_price;
	/***/
	@MsgField(Id = 11)
	public int ceili;
	/**已购买次数*/
	@MsgField(Id = 12)
	public int buyCount;
	/***/
	@MsgField(Id = 13)
	public String icon;
	/***/
	@MsgField(Id = 14)
	public long time;
	/***/
	@MsgField(Id = 15)
	public List<MsgItem> items = new ArrayList<MsgItem>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("shop_number:" + shop_number +",");
		buf.append("name:" + name +",");
		buf.append("quality:" + quality +",");
		buf.append("description:" + description +",");
		buf.append("type:" + type +",");
		buf.append("price:" + price +",");
		buf.append("tag:" + tag +",");
		buf.append("special_price:" + special_price +",");
		buf.append("fast_price:" + fast_price +",");
		buf.append("ceili:" + ceili +",");
		buf.append("buyCount:" + buyCount +",");
		buf.append("icon:" + icon +",");
		buf.append("time:" + time +",");
		buf.append("items:{");
		for (int i = 0; i < items.size(); i++) {
			buf.append(items.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}