package com.xgame.logic.server.game.email.entity;

import java.util.Date;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.email.constant.EmailType;

/**
 * 非个人信函
 * @author jacky.jiang
 *
 */
public class Email extends AbstractEntity<Long> implements JBaseTransform{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5301985847941498182L;

	/**
	 * 主键编号
	 */
	private Long id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 固定信函编号
	 */
	private int templateId;
	
	/**
	 * 信函内容
	 */
	private String content;
	
	/**
	 * 附件内容
	 */
	private String addition = "";
	
	/**
	 * 创建时间
	 */
	private Date createTime = new Date();
	
	/**
	 * 结束时间
	 */
	private Date endTime = new Date();
	
	/**
	 * 目标编号（<=0系统邮件, >0 公会邮件）
	 */
	private long targetId;
	
	/**
	 * 发送方名称
	 */
	private String sendName;
	
	/**
	 * 发送者Id
	 */
	private long senderId;
	
	/**
	 * 发送方等级
	 */
	private int sendLevel;
	
	/**
	 * 邮件类型 {@linkplain EmailType}}
	 */
	private int emailType;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	/**
	 * 不带附件的邮件
	 * @param templateId
	 * @param title
	 * @param content
	 * @param addition
	 * @param endTime
	 * @param emailType
	 * @param targetId
	 * @return
	 */
	public static Email valueOf(int templateId, String title, String content, String addition, Date endTime, int emailType, long targetId) {
		Email email =  new Email();
		email.setTemplateId(templateId);
		email.setTitle("");
		email.setAddition(addition);
		email.setContent(content);
		email.setCreateTime(new Date());
		email.setEndTime(endTime);
		email.setEmailType(emailType);
		email.setTitle(title);
		email.setTargetId(targetId);
		return email;
	}
	
	public static Email valueOf(int templateId, String title, String content, String addition, Date endTime, long targetId, String sendName,long senderId, int sendLevel, int emailType){
		Email email =  new Email();
		email.setTemplateId(templateId);
		email.setTitle("");
		email.setAddition(addition);
		email.setContent(content);
		email.setCreateTime(new Date());
		email.setEndTime(endTime);
		email.setEmailType(emailType);
		email.setTitle(title);
		email.setTargetId(targetId);
		email.setSendName(sendName);
		email.setSendLevel(sendLevel);
		email.setSenderId(senderId);
		return email;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public int getEmailType() {
		return emailType;
	}

	public void setEmailType(int emailType) {
		this.emailType = emailType;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public int getSendLevel() {
		return sendLevel;
	}

	public void setSendLevel(int sendLevel) {
		this.sendLevel = sendLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("title", title);
		jbaseData.put("templateId", templateId);
		jbaseData.put("content", content);
		jbaseData.put("addition", addition);
		jbaseData.put("createTime", createTime);
		jbaseData.put("endTime", endTime);
		jbaseData.put("targetId", targetId);
		jbaseData.put("sendName", sendName);
		jbaseData.put("sendLevel", sendLevel);
		jbaseData.put("emailType", emailType);
		jbaseData.put("senderId", senderId);
		
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.title = jBaseData.getString("title","");
		this.templateId = jBaseData.getInt("templateId", 0);
		this.content = jBaseData.getString("content","");
		this.addition = jBaseData.getString("addition","");
		this.createTime = jBaseData.getDate("createTime",new Date());
		this.endTime = jBaseData.getDate("endTime",new Date());
		this.targetId = jBaseData.getLong("targetId", 0);
		this.sendName = jBaseData.getString("sendName","");
		this.sendLevel = jBaseData.getInt("sendLevel", 0);
		this.emailType = jBaseData.getInt("emailType", 0);
		this.senderId = jBaseData.getLong("senderId", 0);
	}
}
