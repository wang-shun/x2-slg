package com.xgame.logic.server.game.email.bean;
import java.util.ArrayList;
import java.util.List;

import com.xgame.msglib.XPro;
import com.xgame.msglib.annotation.MsgField;
/** 
 * @author ProtocolEditor
 * Email-BillboardEmailInfo - 邮件信息
 */
public class BillboardEmailInfo extends XPro {
	/**邮件ID*/
	@MsgField(Id = 1)
	public long id;
	/**发送人签名*/
	@MsgField(Id = 2)
	public EmailSignature senderSignature;
	/**邮件基础数据*/
	@MsgField(Id = 3)
	public EmailBaseData baseData;
	/**邮件模版id*/
	@MsgField(Id = 4)
	public int sampleId;
	/**自定义参数*/
	@MsgField(Id = 5)
	public List<String> params = new ArrayList<String>();
	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("[");
		buf.append("id:" + id +",");
		buf.append("senderSignature:" + senderSignature +",");
		buf.append("baseData:" + baseData +",");
		buf.append("sampleId:" + sampleId +",");
		buf.append("params:{");
		for (int i = 0; i < params.size(); i++) {
			buf.append(params.get(i).toString() +",");
		}
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("},");
		if(buf.charAt(buf.length()-1)==',') buf.deleteCharAt(buf.length()-1);
		buf.append("]");
		return buf.toString();
	}
}