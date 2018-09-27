package com.xgame.logic.server.game.email.entity.eventmodel;

import com.xgame.logic.server.core.gamelog.event.EventObject;
import com.xgame.logic.server.game.player.entity.Player;


/**
 * 发送系统邮件
 * @author jiangxt
 *
 */
public class SystemEmailEvent extends EventObject {
	
	public static final String NAME = "Email:sendSystemEmail";
	
	/**
	 * 系统邮件
	 */
	private Object obj;
	
	/**
	 * 邮件类型
	 */
	private int emailType;
	
	/**
	 * 构建事件
	 * @param obj
	 * @param eventType
	 * @return
	 */
	public static SystemEmailEvent valueOf(Player player, int eventType, int mailType, Object obj) {
		SystemEmailEvent systemEmailEvent = new SystemEmailEvent(eventType);
		systemEmailEvent.setEmailType(mailType);
		systemEmailEvent.setObj(obj);
		return systemEmailEvent;
	}
	
	public SystemEmailEvent(Integer type) {
		super(type);
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public int getEmailType() {
		return emailType;
	}

	public void setEmailType(int emailType) {
		this.emailType = emailType;
	}

}
