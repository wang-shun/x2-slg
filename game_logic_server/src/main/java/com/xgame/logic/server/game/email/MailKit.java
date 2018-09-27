package com.xgame.logic.server.game.email;

import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.email.constant.EmailType;


/**
 * 邮件工具
 * @author jacky.jiang
 *
 */
public class MailKit {
	
	/**
	 * 发送不带奖励公会邮件(无模板Id)
	 * @param title
	 * @param templateId
	 * @param content
	 * @param targetId
	 * @param sendName
	 * @param sendLevel
	 * @param emailType
	 */
	public static void sendAllianceEmail(String title,String content,long allianceId,String sendName,long senderId,int sendLevel) {
		InjectorUtil.getInjector().emailManager.sendCommonEmail(title, 0, content, "", allianceId, sendName,senderId, sendLevel, EmailType.ALLIANCE);
	}
	
	/**
	 * 发送公会邮件(无附件)
	 * @param title
	 * @param templateId
	 * @param content
	 * @param targetId
	 * @param sendName
	 * @param sendLevel
	 * @param emailType
	 */
	public static void sendAllianceEmail(String title,int templateId,String content,long allianceId,String sendName,long senderId,int sendLevel) {
		InjectorUtil.getInjector().emailManager.sendCommonEmail(title, templateId, content, "", allianceId, sendName,senderId, sendLevel, EmailType.ALLIANCE);
	}
	
	/**
	 * 发送公会邮件（有附件）
	 * @param title
	 * @param templateId
	 * @param content
	 * @param addition
	 * @param targetId
	 * @param sendName
	 * @param sendLevel
	 * @param emailType
	 */
	public static void sendAllianceEmail(String title,int templateId,String content,String addition,long allianceId,String sendName,long senderId,int sendLevel) {
		InjectorUtil.getInjector().emailManager.sendCommonEmail(title, templateId, content,addition, allianceId, sendName,senderId, sendLevel, EmailType.ALLIANCE);
	}
	
	/**
	 * 系统邮件（无附件）
	 * @param targetId
	 * @param templateId
	 * @param content
	 */
	public static void sendSystemEmail(long targetId,int templateId,String content) {
		InjectorUtil.getInjector().userEmailManager.sendUserEmail(targetId, templateId, content, "",EmailType.SYSTEM);
	}
	
	/**
	 * 系统邮件(带附件)
	 * @param title
	 * @param targetId
	 * @param content
	 * @param attach
	 */
	public static void sendSystemEmail(long targetId,int templateId,String content, String attach) {
		InjectorUtil.getInjector().userEmailManager.sendUserEmail(targetId, templateId, content, attach,EmailType.SYSTEM);
	}
	
	/**
	 * 玩家间发送邮件
	 * @param senderId
	 * @param senderName
	 * @param senderLevel
	 * @param recePlayerId
	 * @param title
	 * @param content
	 */
	public static void sendUserEmail(long senderId,String senderName,int senderLevel,long recePlayerId,String title,String content) {
		InjectorUtil.getInjector().userEmailManager.sendUserEmail(senderId, senderName, senderLevel, recePlayerId, title, content, EmailType.PLAYER, 1);
	}
	
	/**
	 * 发送战报邮件
	 * @param targetId
	 * @param templateId
	 * @param content
	 */
	public static void sendReportEmail(long targetId,int templateId,String content){
		InjectorUtil.getInjector().userEmailManager.sendUserEmail(targetId, templateId, content, "",EmailType.REPORT);
	}
}
