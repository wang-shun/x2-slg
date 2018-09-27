package com.xgame.logic.server.game.email.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-AttackMemberInfo - 进攻成员数据
 */
public class AttackMemberInfo extends XPro {
	/**成员签名*/
	@MsgField(Id = 1)
	public EmailSignature signature;
	/**战力损失*/
	@MsgField(Id = 2)
	public long loseFightPower;
	/**进攻部队战况*/
	@MsgField(Id = 3)
	public List<SoldierStateEmailInfo> attackSoldierList = new ArrayList<SoldierStateEmailInfo>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("signature:" + signature +",");
		buf.append("loseFightPower:" + loseFightPower +",");
		buf.append("attackSoldierList:{");
		for (int i = 0; i < attackSoldierList.size(); i++) {
			buf.append(attackSoldierList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}