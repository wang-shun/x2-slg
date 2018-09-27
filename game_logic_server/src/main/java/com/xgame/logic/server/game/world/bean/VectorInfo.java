package com.xgame.logic.server.game.world.bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;
import com.xgame.logic.server.game.war.bean.WarSoldierBean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-VectorInfo - 行军队列信息
 */
public class VectorInfo extends XPro {
	/**行军队列ID*/
	@MsgField(Id = 1)
	public long marchId;
	/**当前队伍玩家签名*/
	@MsgField(Id = 2)
	public WorldPlayerSignure ownerMarchPlayer;
	/**出征部队名称(玩家名称,精灵类型:等级)*/
	@MsgField(Id = 3)
	public String spriteName;
	/**服标识*/
	@MsgField(Id = 4)
	public int serverId;
	/**目标类型*/
	@MsgField(Id = 5)
	public int spriteType;
	/**目标子类型*/
	@MsgField(Id = 6)
	public int spriteSubType;
	/**位置*/
	@MsgField(Id = 7)
	public Vector2Bean loaction;
	/**目标位置*/
	@MsgField(Id = 8)
	public Vector2Bean destination;
	/**开始时间(毫秒值)*/
	@MsgField(Id = 9)
	public long startTime;
	/**结束时间(毫秒值)*/
	@MsgField(Id = 10)
	public long endTime;
	/**延迟任务id*/
	@MsgField(Id = 11)
	public long taskId;
	/**行军类型*/
	@MsgField(Id = 12)
	public int type;
	/**状态*/
	@MsgField(Id = 13)
	public int state;
	/**0代表不需要战斗1代表需要战斗*/
	@MsgField(Id = 14)
	public int marchFightType;
	/**目标玩家签名*/
	@MsgField(Id = 15)
	public WorldPlayerSignure targetMarchPlayer;
	/**资源信息*/
	@MsgField(Id = 16)
	public WarResourceBean warResourceBean;
	/**最大战力士兵*/
	@MsgField(Id = 17)
	public DesignMapBean designMapBean;
	/**资源采集bean*/
	@MsgField(Id = 18)
	public ResourceExplorerBean resourceExplorerBean;
	/**士兵信息列表*/
	@MsgField(Id = 19)
	public List<WarSoldierBean> warSoldierBeanList = new ArrayList<WarSoldierBean>();
	/**部队是否有多个玩家*/
	@MsgField(Id = 20)
	public int multi;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("marchId:" + marchId +",");
		buf.append("ownerMarchPlayer:" + ownerMarchPlayer +",");
		buf.append("spriteName:" + spriteName +",");
		buf.append("serverId:" + serverId +",");
		buf.append("spriteType:" + spriteType +",");
		buf.append("spriteSubType:" + spriteSubType +",");
		buf.append("loaction:" + loaction +",");
		buf.append("destination:" + destination +",");
		buf.append("startTime:" + startTime +",");
		buf.append("endTime:" + endTime +",");
		buf.append("taskId:" + taskId +",");
		buf.append("type:" + type +",");
		buf.append("state:" + state +",");
		buf.append("marchFightType:" + marchFightType +",");
		buf.append("targetMarchPlayer:" + targetMarchPlayer +",");
		buf.append("warResourceBean:" + warResourceBean +",");
		buf.append("designMapBean:" + designMapBean +",");
		buf.append("resourceExplorerBean:" + resourceExplorerBean +",");
		buf.append("warSoldierBeanList:{");
		for (int i = 0; i < warSoldierBeanList.size(); i++) {
			buf.append(warSoldierBeanList.get(i).toString() +",");
		}
		buf.append("multi:" + multi +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}