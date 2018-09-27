package com.xgame.logic.server.game.soldier.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteProBean;
import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Soldier-FullSoldierBean - 全士兵信息
 */
public class FullSoldierBean extends XPro {
	/**简单士兵信息*/
	@MsgField(Id = 1)
	public SoldierBean soldier;
	/**图纸信息*/
	@MsgField(Id = 2)
	public DesignMapBean designMap;
	/**属性列表*/
	@MsgField(Id = 3)
	public List<AttrbuteProBean> attrList = new ArrayList<AttrbuteProBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldier:" + soldier +",");
		buf.append("designMap:" + designMap +",");
		buf.append("attrList:{");
		for (int i = 0; i < attrList.size(); i++) {
			buf.append(attrList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}