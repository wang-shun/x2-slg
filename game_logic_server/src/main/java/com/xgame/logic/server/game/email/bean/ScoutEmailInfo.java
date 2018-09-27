package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-ScoutEmailInfo - 侦查报告
 */
public class ScoutEmailInfo extends XPro {
	/**被侦查人签名*/
	@MsgField(Id = 1)
	public EmailSignature beScoutSignature;
	/**邮件基础数据*/
	@MsgField(Id = 2)
	public EmailBaseData baseData;
	/**地点坐标*/
	@MsgField(Id = 3)
	public Vector2Bean targetPosition;
	/**可掠夺金币*/
	@MsgField(Id = 4)
	public long resMoney;
	/**可掠夺石油*/
	@MsgField(Id = 5)
	public long resOil;
	/**可掠夺稀土*/
	@MsgField(Id = 6)
	public long resRare;
	/**可掠夺钢材*/
	@MsgField(Id = 7)
	public long resSteel;
	/**防御塔*/
	@MsgField(Id = 8)
	public List<DefenseTowerPro> defenseTowers = new ArrayList<DefenseTowerPro>();
	/**防守部队列表*/
	@MsgField(Id = 9)
	public List<SoldierEmailInfo> soldierList = new ArrayList<SoldierEmailInfo>();
	/**预计占领完成时间（时间戳，侦查领地用）*/
	@MsgField(Id = 10)
	public long finishedTime;
	/**资源类型：1-石油；2-稀土；3-铁矿；4-金矿*/
	@MsgField(Id = 11)
	public int resourceType;
	/**资源等级*/
	@MsgField(Id = 12)
	public int resourceLevel;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("beScoutSignature:" + beScoutSignature +",");
		buf.append("baseData:" + baseData +",");
		buf.append("targetPosition:" + targetPosition +",");
		buf.append("resMoney:" + resMoney +",");
		buf.append("resOil:" + resOil +",");
		buf.append("resRare:" + resRare +",");
		buf.append("resSteel:" + resSteel +",");
		buf.append("defenseTowers:{");
		for (int i = 0; i < defenseTowers.size(); i++) {
			buf.append(defenseTowers.get(i).toString() +",");
		}
		buf.append("soldierList:{");
		for (int i = 0; i < soldierList.size(); i++) {
			buf.append(soldierList.get(i).toString() +",");
		}
		buf.append("finishedTime:" + finishedTime +",");
		buf.append("resourceType:" + resourceType +",");
		buf.append("resourceLevel:" + resourceLevel +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}