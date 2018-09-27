package com.xgame.logic.server.game.email.entity;

import java.util.Date;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.email.constant.AttachType;

/**
 * 个人信函对象实体
 * @author jacky
 */
public class UserEmail extends AbstractEntity<Long> implements JBaseTransform{

	private static final long serialVersionUID = 159647280331989552L;
	
	/** 系统发信人 */
	public static final long SYSTEM = 0;
	
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
	private String content = "";
	
	/**
	 * 附件内容
	 */
	private String addition = "";
	
	/**
	 * 附件状态（-1无法提取附件, 0 未提取, 1.已提取）
	 */
	private int additionStatus;
	
	/**
	 * 信函类型（1.系统邮件2.公会邮件3.玩家邮件4报告）
	 */
	private int type;
	
	/**
	 * 发送者
	 */
	private Long senderId = 0L;
	
	/**
	 * 接收者编号
	 */
	private long targetId;
	
	/** 创建时间 */
	private Date createTime = new Date();
	
	/** 信件结束时间*/
	private Date endTime;
	
	/** 已读标记 */
	private boolean readed = false;
	
	/** 1-仅在原始标签页；2-仅在保存标签页；3-同时在原始标签页和保存标签页*/
	private int mailState = 1;
	
	/** 0-忽略；1-属于收发双方共有邮件；2-发送方单方删除；3-接收方单方删除*/
	private int emailDeleteState;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	
	public long getTargetId() {
		return targetId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}
	
	public String getAddition() {
		return addition;
	}

	public int getAdditionStatus() {
		return additionStatus;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setAdditionStatus(int additionStatus) {
		this.additionStatus = additionStatus;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMailState() {
		return mailState;
	}

	public void setMailState(int mailState) {
		this.mailState = mailState;
	}
	
	/**
	 * @return the emailDeleteState
	 */
	public int getEmailDeleteState() {
		return emailDeleteState;
	}

	/**
	 * @param emailDeleteState the emailDeleteState to set
	 */
	public void setEmailDeleteState(int emailDeleteState) {
		this.emailDeleteState = emailDeleteState;
	}

	public void drawAdditionReward() {
		setAdditionStatus(AttachType.REWARD);
		setReaded(true);
		setAddition("");
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("title", title);
		jbaseData.put("templateId", templateId);
		jbaseData.put("content", content);
		jbaseData.put("addition", addition);
		jbaseData.put("additionStatus", additionStatus);
		jbaseData.put("type", type);
		jbaseData.put("senderId", senderId);
		jbaseData.put("targetId", targetId);
		jbaseData.put("createTime", createTime);
		jbaseData.put("endTime", endTime);
		jbaseData.put("readed", readed);
		jbaseData.put("mailState", mailState);
		jbaseData.put("emailDeleteState", emailDeleteState);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		this.title = jBaseData.getString("title","");
		this.templateId = jBaseData.getInt("templateId", 0);
		this.content = jBaseData.getString("content","");
		this.addition = jBaseData.getString("addition","");
		this.additionStatus = jBaseData.getInt("additionStatus", 0);
		this.type = jBaseData.getInt("type", 0);
		this.senderId = jBaseData.getLong("senderId", 0);
		this.targetId = jBaseData.getLong("targetId", 0);
		this.createTime = jBaseData.getDate("createTime",new Date());
		this.endTime = jBaseData.getDate("endTime",new Date());
		this.readed = jBaseData.getBoolean("readed", false);
		this.mailState = jBaseData.getInt("mailState", 0);
		this.emailDeleteState = jBaseData.getInt("emailDeleteState", 0);
	}
}
