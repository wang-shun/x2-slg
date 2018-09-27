package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ResourceExplorerBean - 资源采集bean
 */
public class ResourceExplorerBean extends XPro {
	/**已采集数量*/
	@MsgField(Id = 1)
	public int explorerNum;
	/**总采集负载数量*/
	@MsgField(Id = 2)
	public int totalWeight;
	/**采集开始时间*/
	@MsgField(Id = 3)
	public int explorerStartTime;
	/**采集结束时间*/
	@MsgField(Id = 4)
	public int explorerEndTime;
	/**当前玩家采集速度*/
	@MsgField(Id = 5)
	public double explorerSpeed;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("explorerNum:" + explorerNum +",");
		buf.append("totalWeight:" + totalWeight +",");
		buf.append("explorerStartTime:" + explorerStartTime +",");
		buf.append("explorerEndTime:" + explorerEndTime +",");
		buf.append("explorerSpeed:" + explorerSpeed +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}