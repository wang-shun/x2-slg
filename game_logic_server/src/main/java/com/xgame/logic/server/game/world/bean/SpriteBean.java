package com.xgame.logic.server.game.world.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-SpriteBean - 精灵信息
 */
public class SpriteBean extends XPro {
	/**ID*/
	@MsgField(Id = 1)
	public long id;
	/**x轴坐标*/
	@MsgField(Id = 2)
	public int x;
	/**y轴坐标*/
	@MsgField(Id = 3)
	public int y;
	/**服务器id*/
	@MsgField(Id = 4)
	public int serverId;
	/**类型(1.玩家 2.资源 3.空地 4.联盟建筑5.野怪)*/
	@MsgField(Id = 5)
	public int type;
	/**数据集合*/
	@MsgField(Id = 6)
	public String data;
	/**地表信息*/
	@MsgField(Id = 7)
	public TerrianBean terrianBean;
	/**战斗结束时间*/
	@MsgField(Id = 8)
	public long battleEndTime;
	/**战斗兵种列表*/
	@MsgField(Id = 9)
	public List<Integer> battleSoldier = new ArrayList<Integer>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("x:" + x +",");
		buf.append("y:" + y +",");
		buf.append("serverId:" + serverId +",");
		buf.append("type:" + type +",");
		buf.append("data:" + data +",");
		buf.append("terrianBean:" + terrianBean +",");
		buf.append("battleEndTime:" + battleEndTime +",");
		buf.append("battleSoldier:{");
		for (int i = 0; i < battleSoldier.size(); i++) {
			buf.append(battleSoldier.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}