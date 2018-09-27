package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-AttackEmailInfo - 战斗报告
 */
public class AttackEmailInfo extends XPro {
	/**邮件ID*/
	@MsgField(Id = 1)
	public long id;
	/**邮件基础数据*/
	@MsgField(Id = 2)
	public EmailBaseData baseData;
	/**甲方签名*/
	@MsgField(Id = 3)
	public EmailSignature redSignature;
	/**甲方战力损失*/
	@MsgField(Id = 4)
	public int senderLost;
	/**乙方签名*/
	@MsgField(Id = 5)
	public EmailSignature blueSignature;
	/**乙方战力损失*/
	@MsgField(Id = 6)
	public int receiverLost;
	/**胜方ID*/
	@MsgField(Id = 7)
	public long winderId;
	/**地点坐标*/
	@MsgField(Id = 8)
	public Vector2Bean targetPosition;
	/**掠夺金币*/
	@MsgField(Id = 9)
	public long resMoney;
	/**掠夺石油*/
	@MsgField(Id = 10)
	public long resOil;
	/**掠夺稀土*/
	@MsgField(Id = 11)
	public long resRare;
	/**掠夺钢材*/
	@MsgField(Id = 12)
	public long resSteel;
	/**进攻部队战况*/
	@MsgField(Id = 13)
	public List<SoldierStateEmailInfo> attackSoldierList = new ArrayList<SoldierStateEmailInfo>();
	/**防守部队战况*/
	@MsgField(Id = 14)
	public List<SoldierStateEmailInfo> defenSoldierList = new ArrayList<SoldierStateEmailInfo>();
	/**资源类型：1-石油；2-稀土；3-铁矿；4-金矿*/
	@MsgField(Id = 15)
	public int resourceType;
	/**资源等级*/
	@MsgField(Id = 16)
	public int resourceLevel;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("baseData:" + baseData +",");
		buf.append("redSignature:" + redSignature +",");
		buf.append("senderLost:" + senderLost +",");
		buf.append("blueSignature:" + blueSignature +",");
		buf.append("receiverLost:" + receiverLost +",");
		buf.append("winderId:" + winderId +",");
		buf.append("targetPosition:" + targetPosition +",");
		buf.append("resMoney:" + resMoney +",");
		buf.append("resOil:" + resOil +",");
		buf.append("resRare:" + resRare +",");
		buf.append("resSteel:" + resSteel +",");
		buf.append("attackSoldierList:{");
		for (int i = 0; i < attackSoldierList.size(); i++) {
			buf.append(attackSoldierList.get(i).toString() +",");
		}
		buf.append("defenSoldierList:{");
		for (int i = 0; i < defenSoldierList.size(); i++) {
			buf.append(defenSoldierList.get(i).toString() +",");
		}
		buf.append("resourceType:" + resourceType +",");
		buf.append("resourceLevel:" + resourceLevel +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}