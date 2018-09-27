package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-SoldierStateEmailInfo - 战后士兵状态
 */
public class SoldierStateEmailInfo extends XPro {
	/**兵种Id*/
	@MsgField(Id = 1)
	public long soldierId;
	/**兵种*/
	@MsgField(Id = 2)
	public String soldierName;
	/**受伤人数*/
	@MsgField(Id = 3)
	public int injuredNum;
	/**死亡人数*/
	@MsgField(Id = 4)
	public int deathNum;
	/**存活人数*/
	@MsgField(Id = 5)
	public int aliveNum;
	/**图纸信息*/
	@MsgField(Id = 6)
	public DesignMapBean designMapBean;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("soldierId:" + soldierId +",");
		buf.append("soldierName:" + soldierName +",");
		buf.append("injuredNum:" + injuredNum +",");
		buf.append("deathNum:" + deathNum +",");
		buf.append("aliveNum:" + aliveNum +",");
		buf.append("designMapBean:" + designMapBean +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}