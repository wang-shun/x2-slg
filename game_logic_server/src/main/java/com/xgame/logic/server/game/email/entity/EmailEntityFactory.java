/**
 * 
 */
package com.xgame.logic.server.game.email.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.utils.sequance.MailDequence;

/**
 * @author fansth
 *
 */
@Component
public class EmailEntityFactory {
	
	@Autowired
	private MailDequence mailDequence;

	/**
	 * 创建email实体
	 * @param title
	 * @param templateId
	 * @param content
	 * @param addition
	 * @param endTime
	 * @param targetId
	 * @param sendName
	 * @param sendLevel
	 * @param emailType
	 * @return
	 */
	public Email create(String title,int templateId,String content,String addition,Date endTime,long targetId,String sendName,long senderId,int sendLevel,int emailType) {
		Email email =  new Email();
		email.setId(mailDequence.genMailId());
		email.setTitle(title);
		email.setTemplateId(templateId);
		email.setContent(content);
		email.setAddition(addition);
		email.setCreateTime(new Date());
		email.setEndTime(endTime);
		email.setTargetId(targetId);
		email.setSendName(sendName);
		email.setSendLevel(sendLevel);
		email.setEmailType(emailType);
		email.setSenderId(senderId);
		
		return email;
	}
	
//	
//	/**
//	 * 创建email实体
//	 * @param templateId
//	 * @param title
//	 * @param content
//	 * @param addition
//	 * @param endTime
//	 * @param targetId
//	 * @param sendName
//	 * @param sendLevel
//	 * @param emailType
//	 * @return
//	 */
//	public Email create(long targetId, String title, String content, Date endTime, String sendName, int emailType){
//		Email email =  new Email();
//		email.setContent(content);
//		email.setCreateTime(new Date());
//		email.setEndTime(endTime);
//		email.setEmailType(emailType);
//		email.setTitle(title);
//		email.setTargetId(targetId);
//		email.setSendName(sendName);
//		email.setId(mailDequence.genMailId());
//		return email;
//	}
//	
//	/**
//	 * 创建email实体
//	 * @param templateId
//	 * @param title
//	 * @param content
//	 * @param addition
//	 * @param endTime
//	 * @param targetId
//	 * @param sendName
//	 * @param sendLevel
//	 * @param emailType
//	 * @return
//	 */
//	public Email create(int templateId, String title, String content, Date endTime, long targetId, String sendName, int emailType){
//		Email email =  new Email();
//		email.setTemplateId(templateId);
//		email.setContent(content);
//		email.setCreateTime(new Date());
//		email.setEndTime(endTime);
//		email.setEmailType(emailType);
//		email.setTitle(title);
//		email.setTargetId(targetId);
//		email.setSendName(sendName);
//		email.setId(mailDequence.genMailId());
//		return email;
//	}
	
}
