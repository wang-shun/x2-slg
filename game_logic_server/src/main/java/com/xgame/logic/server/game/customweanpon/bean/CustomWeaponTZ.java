package com.xgame.logic.server.game.customweanpon.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * CustomWeanpon-CustomWeaponTZ - 
 */
public class CustomWeaponTZ extends XPro {
	/***/
	@MsgField(Id = 1)
	public int tZId;
	/**绑定位置*/
	@MsgField(Id = 2)
	public int bindPos;
	/**建造顺序*/
	@MsgField(Id = 3)
	public int buildIndex;
	/***/
	@MsgField(Id = 4)
	public int tzType;
	/***/
	@MsgField(Id = 5)
	public String tZName;
	/***/
	@MsgField(Id = 6)
	public List<CustomWeaponPJ> fragments = new ArrayList<CustomWeaponPJ>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("tZId:" + tZId +",");
		buf.append("bindPos:" + bindPos +",");
		buf.append("buildIndex:" + buildIndex +",");
		buf.append("tzType:" + tzType +",");
		buf.append("tZName:" + tZName +",");
		buf.append("fragments:{");
		for (int i = 0; i < fragments.size(); i++) {
			buf.append(fragments.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}