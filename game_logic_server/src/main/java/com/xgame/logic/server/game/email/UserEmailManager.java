package com.xgame.logic.server.game.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.db.cache.DbCacheService;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.email.constant.MailConstant;
import com.xgame.logic.server.game.email.entity.EmailEntityFactory;
import com.xgame.logic.server.game.email.entity.PlayerEmaiInfo;
import com.xgame.logic.server.game.email.entity.UserEmail;
import com.xgame.logic.server.game.email.entity.UserEmailEntityFactory;
import com.xgame.logic.server.game.email.entity.eventmodel.SystemEmailEvent;
import com.xgame.logic.server.game.friend.ReleationShipManager;


/**
 * 玩家邮件管理器
 * @author jacky.jiang
 *
 */
@Component
public class UserEmailManager extends CacheProxy<UserEmail> {
	
	@Autowired
	private DbCacheService dbCacheService;
	@Autowired
	private EventBus eventBus;
	@Autowired
	private ReleationShipManager releationShipManager;
	@Autowired
	private UserEmailEntityFactory userEmailEntityFactory;
	@Autowired
	private EmailEntityFactory emailEntityFactory;
	@Autowired
	private PlayerMailInfoManager playerMailInfoManager;
	
	@Override
	public Class<?> getProxyClass() {
		return UserEmail.class;
	}
	
	
	private void createEmail(long senderId, String senderName, int sendLevel, long targetId, int templateId, String title, String content, String attach, int type,int emailDeleteState){
		Date endTime = new Date(System.currentTimeMillis() + MailConstant.EMAIL_DELETE_TIME);
		UserEmail userEmail = userEmailEntityFactory.create(senderId,senderName, sendLevel, targetId, templateId, title, content, attach, endTime,type,emailDeleteState);
		InjectorUtil.getInjector().dbCacheService.create(userEmail);
		PlayerEmaiInfo targetPlayerEmaiInfo = playerMailInfoManager.getOrCreate(targetId);
		targetPlayerEmaiInfo.addReceEmail(userEmail);
		InjectorUtil.getInjector().dbCacheService.update(targetPlayerEmaiInfo);
		if(emailDeleteState == 1){
			PlayerEmaiInfo senderPlayerEmaiInfo = playerMailInfoManager.getOrCreate(senderId);
			senderPlayerEmaiInfo.addSendEmail(userEmail);
			InjectorUtil.getInjector().dbCacheService.update(senderPlayerEmaiInfo);
		}
		eventBus.fireEvent(SystemEmailEvent.valueOf(null, EventTypeConst.EMAIL_SEND, type, userEmail));
	}
	

	public void sendUserEmail(long targetId,int templateId, String content, String attach,int type) {
		createEmail(-1,"",0, targetId,templateId,"",content,attach,type,0);
	}
	
	public void sendUserEmail(long senderId,String senderName,int senderLevel,long recePlayerId,String title,String content,int type,int emailDeleteState) {
		createEmail(senderId,senderName,senderLevel, recePlayerId,0,title,content,"",type,emailDeleteState);
	}
}
