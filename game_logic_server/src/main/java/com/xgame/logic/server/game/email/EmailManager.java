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
import com.xgame.logic.server.game.email.entity.Email;
import com.xgame.logic.server.game.email.entity.EmailEntityFactory;
import com.xgame.logic.server.game.email.entity.UserEmailEntityFactory;
import com.xgame.logic.server.game.email.entity.eventmodel.SystemEmailEvent;
import com.xgame.logic.server.game.friend.ReleationShipManager;

/**
 * 全服邮件管理器
 * @author jacky.jiang
 *
 */
@Component
public class EmailManager extends CacheProxy<Email> {

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
	
	@Override
	public Class<?> getProxyClass() {
		return Email.class;
	}
	
	public void sendCommonEmail(String title,int templateId,String content,String addition,long targetId,String sendName,long senderId,int sendLevel,int emailType) {
		Date endTime = new Date(System.currentTimeMillis() + MailConstant.EMAIL_DELETE_TIME);
		Email email = emailEntityFactory.create(title, templateId, content, addition, endTime, targetId, sendName,senderId, sendLevel, emailType);
		InjectorUtil.getInjector().dbCacheService.create(email);
		eventBus.fireEvent(SystemEmailEvent.valueOf(null, EventTypeConst.EMAIL_SEND, emailType, email));
	}
}
