package com.xgame.logic.server.game.email.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ReinforceEmailInfo - 驻防报告
 */
public class ReinforceEmailInfo extends XPro {
	/**驻防方签名*/
	@MsgField(Id = 1)
	public EmailSignature reinforceSignature;
	/**被驻防方签名*/
	@MsgField(Id = 2)
	public EmailSignature beReinforceSignature;
	/**部队战力*/
	@MsgField(Id = 3)
	public long totalFightPower;
	/**部队列表*/
	@MsgField(Id = 4)
	public List<SoldierEmailInfo> soldierList = new ArrayList<SoldierEmailInfo>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("reinforceSignature:" + reinforceSignature +",");
		buf.append("beReinforceSignature:" + beReinforceSignature +",");
		buf.append("totalFightPower:" + totalFightPower +",");
		buf.append("soldierList:{");
		for (int i = 0; i < soldierList.size(); i++) {
			buf.append(soldierList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}