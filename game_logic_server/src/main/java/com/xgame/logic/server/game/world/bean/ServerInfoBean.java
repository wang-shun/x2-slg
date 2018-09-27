package com.xgame.logic.server.game.world.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * World-ServerInfoBean - 服务器信息
 */
public class ServerInfoBean extends XPro {
	/**服务器id*/
	@MsgField(Id = 1)
	public int serverId;
	/**服务器ip*/
	@MsgField(Id = 2)
	public String ip;
	/**端口*/
	@MsgField(Id = 3)
	public int port;
	/**网管信息*/
	@MsgField(Id = 4)
	public String gateInfo;
	/**在线人数*/
	@MsgField(Id = 5)
	public int online;
	/**服务器开服时间*/
	@MsgField(Id = 6)
	public long serverOpenTime;
	/**状态:1.开启2.关闭*/
	@MsgField(Id = 7)
	public int state;
	/**执政盟图片*/
	@MsgField(Id = 8)
	public String thronePic;
	/**政府名称*/
	@MsgField(Id = 9)
	public String throneName;
	/**政府简称*/
	@MsgField(Id = 10)
	public String throneAbbr;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("serverId:" + serverId +",");
		buf.append("ip:" + ip +",");
		buf.append("port:" + port +",");
		buf.append("gateInfo:" + gateInfo +",");
		buf.append("online:" + online +",");
		buf.append("serverOpenTime:" + serverOpenTime +",");
		buf.append("state:" + state +",");
		buf.append("thronePic:" + thronePic +",");
		buf.append("throneName:" + throneName +",");
		buf.append("throneAbbr:" + throneAbbr +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}