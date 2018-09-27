package com.xgame.logic.server.game.world.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-TerritoryBean - 领土信息
 */
public class TerritoryBean extends XPro {
	/**联盟id*/
	@MsgField(Id = 1)
	public long allianceId;
	/**简称*/
	@MsgField(Id = 2)
	public String abbr;
	/**排名*/
	@MsgField(Id = 3)
	public int rank;
	/**坐标*/
	@MsgField(Id = 4)
	public List<Vector2Bean> posList = new ArrayList<Vector2Bean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("allianceId:" + allianceId +",");
		buf.append("abbr:" + abbr +",");
		buf.append("rank:" + rank +",");
		buf.append("posList:{");
		for (int i = 0; i < posList.size(); i++) {
			buf.append(posList.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}