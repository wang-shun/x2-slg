package com.xgame.logic.server.game.task.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Task-PlayerTimeRefreshTaskBean - 倒计时刷新任务
 */
public class PlayerTimeRefreshTaskBean extends XPro {
	/**刷新时间*/
	@MsgField(Id = 1)
	public long refreshTime;
	/**每日任务*/
	@MsgField(Id = 2)
	public List<TimeTaskInfoBean> dayTask = new ArrayList<TimeTaskInfoBean>();
	/**军团任务*/
	@MsgField(Id = 3)
	public List<TimeTaskInfoBean> guildTask = new ArrayList<TimeTaskInfoBean>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("refreshTime:" + refreshTime +",");
		buf.append("dayTask:{");
		for (int i = 0; i < dayTask.size(); i++) {
			buf.append(dayTask.get(i).toString() +",");
		}
		buf.append("guildTask:{");
		for (int i = 0; i < guildTask.size(); i++) {
			buf.append(guildTask.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}