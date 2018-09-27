package com.xgame.logic.server.game.notice.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Notice-NoticeBean - 公告bean
 */
public class NoticeBean extends XPro {
	/**公告id*/
	@MsgField(Id = 1)
	public int noticeId;
	/**公告内容*/
	@MsgField(Id = 2)
	public String param;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("noticeId:" + noticeId +",");
		buf.append("param:" + param +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}