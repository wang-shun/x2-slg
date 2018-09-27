package com.xgame.logic.server.game.war.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * War-WarAttackData - 进攻方数据
 */
public class WarAttackData extends XPro {
	/**进攻方Uid*/
	@MsgField(Id = 1)
	public long attackUid;
	/**带燃油数量*/
	@MsgField(Id = 2)
	public int oilNum;
	/**驻军信息*/
	@MsgField(Id = 3)
	public List<WarSoldierBean> soldiers = new ArrayList<WarSoldierBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("attackUid:" + attackUid +",");
		buf.append("oilNum:" + oilNum +",");
		buf.append("soldiers:{");
		for (int i = 0; i < soldiers.size(); i++) {
			buf.append(soldiers.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}