package com.xgame.logic.server.game.gameevent.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Event-EventRankInfoBeanList - 历史排行
 */
public class EventRankInfoBeanList extends XPro {
	/**排行信息*/
	@MsgField(Id = 1)
	public List<EventRankInfoBean> eventRankInfoBean = new ArrayList<EventRankInfoBean>();
	@MsgField(Id = 2)
	public int eventId;
	@MsgField(Id = 3)
	public int itemNo;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("eventId:" + eventId +",");
		buf.append("itemNo:" + itemNo +",");
		buf.append("eventRankInfoBean:{");
		for (int i = 0; i < eventRankInfoBean.size(); i++) {
			buf.append(eventRankInfoBean.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}