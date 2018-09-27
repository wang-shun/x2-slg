package com.xgame.logic.server.game.email.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-EmailInfo - 邮件信息
 */
public class EmailInfo extends XPro {
	/**邮件ID*/
	@MsgField(Id = 1)
	public long id;
	/**模板ID*/
	@MsgField(Id = 2)
	public int templateId;
	/**发送人签名*/
	@MsgField(Id = 3)
	public EmailSignature senderSignature;
	/**邮件标题*/
	@MsgField(Id = 4)
	public String title;
	/**邮件内容*/
	@MsgField(Id = 5)
	public String content;
	/**是否已读*/
	@MsgField(Id = 6)
	public boolean isRead;
	/**邮件类型（1.系统邮件2.公会邮件3.玩家邮件4战斗报告）*/
	@MsgField(Id = 7)
	public int type;
	/**发送时间*/
	@MsgField(Id = 8)
	public long time;
	/**物品列表*/
	@MsgField(Id = 9)
	public List<AwardEmailInfo> awardList = new ArrayList<AwardEmailInfo>();
	/**1-仅在原始标签页；2-仅在保存标签页；3-同时在原始标签页和保存标签页*/
	@MsgField(Id = 10)
	public int state;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("templateId:" + templateId +",");
		buf.append("senderSignature:" + senderSignature +",");
		buf.append("title:" + title +",");
		buf.append("content:" + content +",");
		buf.append("isRead:" + isRead +",");
		buf.append("type:" + type +",");
		buf.append("time:" + time +",");
		buf.append("awardList:{");
		for (int i = 0; i < awardList.size(); i++) {
			buf.append(awardList.get(i).toString() +",");
		}
		buf.append("state:" + state +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}