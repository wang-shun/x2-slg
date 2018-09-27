package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResourceSpriteBean - 资源精灵信息
 */
public class ResourceSpriteBean extends XPro {
	/**UID*/
	@MsgField(Id = 1)
	public long uid;
	/**资源等级*/
	@MsgField(Id = 2)
	public int level;
	/**资源类型(1石油2.稀土3.钢材4.黄金5.钻石)*/
	@MsgField(Id = 3)
	public int type;
	/**当前剩余数量*/
	@MsgField(Id = 4)
	public long num;
	/**资源采集bean*/
	@MsgField(Id = 5)
	public ResourceExplorerBean resourceExplorerBean;
	/**总采集负载数量*/
	@MsgField(Id = 6)
	public int totalWeight;
	/**精灵主权签名*/
	@MsgField(Id = 7)
	public WorldAllianceSignure allianceSignure;
	/**出征玩家签名*/
	@MsgField(Id = 8)
	public WorldPlayerSignure occupyPlayer;
	/**当前正在采集的id*/
	@MsgField(Id = 9)
	public long ownerMarchId;
	/**采集记录*/
	@MsgField(Id = 10)
	public String record;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("uid:" + uid +",");
		buf.append("level:" + level +",");
		buf.append("type:" + type +",");
		buf.append("num:" + num +",");
		buf.append("resourceExplorerBean:" + resourceExplorerBean +",");
		buf.append("totalWeight:" + totalWeight +",");
		buf.append("allianceSignure:" + allianceSignure +",");
		buf.append("occupyPlayer:" + occupyPlayer +",");
		buf.append("ownerMarchId:" + ownerMarchId +",");
		buf.append("record:" + record +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}