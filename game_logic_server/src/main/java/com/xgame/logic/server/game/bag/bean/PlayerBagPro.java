package com.xgame.logic.server.game.bag.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Bag-PlayerBagPro - 
 */
public class PlayerBagPro extends XPro {
	/***/
	@MsgField(Id = 1)
	public int size;
	/***/
	@MsgField(Id = 2)
	public List<MsgItem> items = new ArrayList<MsgItem>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("size:" + size +",");
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