/**
 * 
 */
package com.xgame.logic.server.game.email.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.utils.sequance.UserMailDequence;
import com.xgame.logic.server.game.email.constant.AttachType;

/**
 * @author jacky
 *
 */
@Component
public class UserEmailEntityFactory {
	
	@Autowired
	private UserMailDequence userMailDequence;
	
	/**
	 * 构建用户邮件
	 * @param senderId
	 * @param senderName
	 * @param sendLevel
	 * @param targetId
	 * @param templateId
	 * @param title
	 * @param content
	 * @param attach
	 * @param endTime
	 * @param type
	 * @return
	 */
	public UserEmail create(long senderId, String senderName, int sendLevel, long targetId, int templateId, String title, String content, String attach, Date endTime, int type,int emailDeleteState){
		UserEmail result = createVO(senderId, targetId, templateId, title, content, attach, endTime, type,emailDeleteState);
		result.setId(userMailDequence.genUserMailId());
		return result;
	}
	
	/**
	 * 创建实体的vo对象(没有主键)
	 * @param senderId
	 * @param senderName
	 * @param sendLevel
	 * @param targetId
	 * @param templateId
	 * @param title
	 * @param content
	 * @param attach
	 * @param endTime
	 * @param type
	 * @return
	 */
	public UserEmail createVO(long senderId, long targetId, int templateId, String title, String content, String attach, Date endTime, int type,int emailDeleteState){
		UserEmail result = new UserEmail();
		result.setSenderId(senderId);
		result.setTargetId(targetId);
		if(templateId > 0) {
			result.setTemplateId(templateId);
			result.setType(type);
			result.setContent(content);
		} else {
			result.setTitle(title);
			result.setType(type);
			result.setContent(content);
		}
		if(!StringUtils.isBlank(attach)) {
			result.setAdditionStatus(AttachType.NOT_REWARD);
			result.setAddition(attach);
		} else {
			result.setAdditionStatus(AttachType.NONE);
			result.setAddition("");
		}
		result.setReaded(false);
		result.setCreateTime(new Date());
		result.setEndTime(endTime);
		result.setEmailDeleteState(emailDeleteState);
		return result;
	}
	
	/**
	 * 创建公共邮件的个人邮件记录
	 * @param targetId
	 * @param email
	 * @return
	 */
	public UserEmail create(long targetId, Email email) {
		UserEmail result = createVO(targetId, email);
		result.setId(userMailDequence.genUserMailId());
		return result;
	}
	
	/**
	 * 创建公共邮件的个人邮件vo
	 * @param targetId
	 * @param email
	 * @return
	 */
	public UserEmail createVO(long targetId, Email email) {
		UserEmail result = new UserEmail();
		result.setTargetId(targetId);
		result.setTemplateId(email.getTemplateId());
		result.setTitle(email.getTitle() == null ? "" : email.getTitle());
		result.setContent(email.getContent());
		result.setType(email.getEmailType());
		if(!StringUtils.isBlank(email.getAddition())) {
			result.setAdditionStatus(AttachType.NOT_REWARD);
			result.setAddition(email.getAddition());
		}
		result.setReaded(false);
		result.setCreateTime(email.getCreateTime());
		result.setEndTime(email.getEndTime());
		result.setSenderId(email.getSenderId());
		return result;
	}
	
	/**
	 * 创建公会邮件的个人邮件对象
	 * @param targetId
	 * @param allianceName
	 * @param email
	 * @return
	 */
	public UserEmail create(long targetId, String allianceName, Email email){
		UserEmail result = createVO(targetId, allianceName, email);
		result.setId(userMailDequence.genUserMailId());
		return result;
	}
	
	/**
	 * 创建公会邮件的个人邮件vo对象
	 * @param targetId
	 * @param allianceName
	 * @param email
	 * @return
	 */
	public UserEmail createVO(long targetId, String allianceName, Email email){
		UserEmail result = new UserEmail();
		result.setTargetId(targetId);
		result.setTemplateId(email.getTemplateId());
		result.setType(email.getEmailType());
		if(!StringUtils.isBlank(email.getAddition())) {
			result.setAdditionStatus(AttachType.NOT_REWARD);
			result.setAddition(email.getAddition());
		}
		result.setReaded(false);
		result.setCreateTime(email.getCreateTime());
		result.setEndTime(email.getEndTime());
		return result;
	}
	
}
