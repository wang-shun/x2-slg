package com.xgame.logic.server.game.email.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.core.utils.collection.ConcurrentHashSet;

/**
 * 非个人信函标签
 * @author jiangxt
 *
 */
public class PlayerEmaiInfo extends AbstractEntity<Long> implements JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7443697815046171416L;

	/**
	 * 玩家编号
	 */
	private Long id;
	
	/**
	 * 全服邮件领取标识
	 */
	private Map<Long, Long> mailTag = new ConcurrentHashMap<Long, Long>();
	
	/**
	 * 发送出去的邮件列表
	 */
	private ConcurrentHashSet<Long> sendMailTag = new ConcurrentHashSet<Long>();
	
	/**
	 * 已接受的邮件列表
	 */
	private ConcurrentHashSet<Long> receMailTag = new ConcurrentHashSet<Long>();

	
	/**
	 * 移除掉指定的邮件
	 * @param emailId
	 * @return
	 */
	public boolean removeEmail(Long emailId) {
		if(mailTag.remove(emailId) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 1、清掉已经删除掉的邮件
	 * @param currentEmails 当前有效的id集合
	 */
	public synchronized boolean refresh(Collection<Long> currentEmails) {
		boolean refreshed = false;
		Set<Long> set = new HashSet<>(mailTag.keySet());
		if(set != null && !set.isEmpty()) {
			for(Long id : set) {
				if(!currentEmails.contains(id)) {//记录的邮件已经不再当前有效的邮件id集合中(可能被删掉了)
					mailTag.remove(id);
					refreshed = true;
				}
			}
		}
		return refreshed;
	}
	
	/**
	 * 添加非个人信函标示
	 * @param playerEmailTag
	 * @param emailId
	 * @param userEmailId
	 */
	public void addTag(Long emailId, Long userEmailId) {
		mailTag.put(emailId, userEmailId);
	}
	
	
	public void addSendEmail(UserEmail userEmail) {
		sendMailTag.add(userEmail.getId());
	}
	
	public void addReceEmail(UserEmail userEmail) {
		getReceMailTag().add(userEmail.getId());
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long k) {
		this.id = k;
	}

	public Map<Long, Long> getMailTag() {
		return mailTag;
	}

	public void setMailTag(Map<Long, Long> mailTag) {
		this.mailTag = mailTag;
	}

	public Set<Long> getSendMailTag() {
		return sendMailTag;
	}

	public void setSendMailTag(ConcurrentHashSet<Long> sendMailTag) {
		this.sendMailTag = sendMailTag;
	}

	public Set<Long> getReceMailTag() {
		return receMailTag;
	}

	public void setReceMailTag(ConcurrentHashSet<Long> receMailTag) {
		this.receMailTag = receMailTag;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("id", id);
		jbaseData.put("mailTag", JsonUtil.toJSON(mailTag));
		jbaseData.put("sendMailTag", JsonUtil.toJSON(sendMailTag));
		jbaseData.put("receMailTag", JsonUtil.toJSON(receMailTag));
		return jbaseData;
	}

	@SuppressWarnings("unchecked")
	public void fromJBaseData(JBaseData jBaseData) {
		this.id = jBaseData.getLong("id", 0);
		String mailTagStr = jBaseData.getString("mailTag", "");
		if(!StringUtils.isEmpty(mailTagStr)) {
			this.mailTag = JsonUtil.fromJSON(mailTagStr, Map.class);
		}
		
		String sendMailTagStr = jBaseData.getString("sendMailTag", "");
		if(!StringUtils.isEmpty(sendMailTagStr)) {
			this.sendMailTag = JsonUtil.fromJSON(sendMailTagStr, ConcurrentHashSet.class);
		}
		
		String receMailTag = jBaseData.getString("receMailTag", "");
		if(!StringUtils.isEmpty(receMailTag)) {
			this.receMailTag = JsonUtil.fromJSON(receMailTag, ConcurrentHashSet.class);
		}
	}
	
}
