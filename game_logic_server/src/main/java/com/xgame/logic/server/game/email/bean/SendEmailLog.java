package com.xgame.logic.server.game.email.bean;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-SendEmailLog - 发送邮件日志
 */
public class SendEmailLog extends XPro {
	/**邮件ID*/
	@MsgField(Id = 1)
	public long id;
	/**发送多少人*/
	@MsgField(Id = 2)
	public TargetMailClientPro targetMailClients;
	/**发送人签名*/
	@MsgField(Id = 3)
	public EmailSignature senderSignature;
	/**邮件标题*/
	@MsgField(Id = 4)
	public String title;
	/**邮件内容*/
	@MsgField(Id = 5)
	public String content;
	/**邮件基础数据*/
	@MsgField(Id = 6)
	public EmailBaseData baseData;
	/**附件json*/
	@MsgField(Id = 7)
	public String adjunctJson;
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("targetMailClients:" + targetMailClients +",");
		buf.append("senderSignature:" + senderSignature +",");
		buf.append("title:" + title +",");
		buf.append("content:" + content +",");
		buf.append("baseData:" + baseData +",");
		buf.append("adjunctJson:" + adjunctJson +",");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}