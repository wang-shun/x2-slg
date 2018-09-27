package com.xgame.logic.server.game.modify.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.soldier.bean.ReformSoldierBean;
import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Modify-ArmorsBean - 装甲信息
 */
public class ArmorsBean extends XPro {
	/**正常装甲*/
	@MsgField(Id = 1)
	public List<ReformSoldierBean> soldierBeans = new ArrayList<ReformSoldierBean>();
	/**损伤装甲*/
	@MsgField(Id = 2)
	public List<ReformSoldierBean> hurtBeans = new ArrayList<ReformSoldierBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldierBeans:{");
		for (int i = 0; i < soldierBeans.size(); i++) {
			buf.append(soldierBeans.get(i).toString() +",");
		}
		buf.append("hurtBeans:{");
		for (int i = 0; i < hurtBeans.size(); i++) {
			buf.append(hurtBeans.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}