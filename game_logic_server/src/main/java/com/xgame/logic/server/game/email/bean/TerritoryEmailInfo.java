package com.xgame.logic.server.game.email.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-TerritoryEmailInfo - 占领报告
 */
public class TerritoryEmailInfo extends XPro {
	/**地点坐标*/
	@MsgField(Id = 1)
	public Vector2Bean targetPosition;
	/**占领方签名*/
	@MsgField(Id = 2)
	public EmailSignature signature;
	/**部队列表*/
	@MsgField(Id = 3)
	public List<SoldierEmailInfo> soldierList = new ArrayList<SoldierEmailInfo>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("targetPosition:" + targetPosition +",");
		buf.append("signature:" + signature +",");
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