package com.xgame.logic.server.game.email.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-TeamAttackEmailInfo - 集结进攻战报
 */
public class TeamAttackEmailInfo extends XPro {
	/**进攻方签名*/
	@MsgField(Id = 1)
	public EmailSignature redSignature;
	/**防守方签名*/
	@MsgField(Id = 2)
	public EmailSignature blueSignature;
	/**基地非安全总资源*/
	@MsgField(Id = 3)
	public int totalResource;
	/**金币*/
	@MsgField(Id = 4)
	public long resMoney;
	/**石油*/
	@MsgField(Id = 5)
	public long resOil;
	/**稀土*/
	@MsgField(Id = 6)
	public long resRare;
	/**钢材*/
	@MsgField(Id = 7)
	public long resSteel;
	/**1-进攻方胜；2-防守方胜*/
	@MsgField(Id = 8)
	public int winner;
	/**进攻方部队列表*/
	@MsgField(Id = 9)
	public List<AttackMemberInfo> redSoldierList = new ArrayList<AttackMemberInfo>();
	/**防守方部队列表*/
	@MsgField(Id = 10)
	public List<AttackMemberInfo> blueSoldierList = new ArrayList<AttackMemberInfo>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("redSignature:" + redSignature +",");
		buf.append("blueSignature:" + blueSignature +",");
		buf.append("totalResource:" + totalResource +",");
		buf.append("resMoney:" + resMoney +",");
		buf.append("resOil:" + resOil +",");
		buf.append("resRare:" + resRare +",");
		buf.append("resSteel:" + resSteel +",");
		buf.append("winner:" + winner +",");
		buf.append("redSoldierList:{");
		for (int i = 0; i < redSoldierList.size(); i++) {
			buf.append(redSoldierList.get(i).toString() +",");
		}
		buf.append("blueSoldierList:{");
		for (int i = 0; i < blueSoldierList.size(); i++) {
			buf.append(blueSoldierList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}