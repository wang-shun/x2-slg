package com.xgame.logic.server.game.email;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.core.gamelog.event.IListener;
import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.net.gate.session.PlayerSession;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.email.constant.EmailType;
import com.xgame.logic.server.game.email.entity.Email;
import com.xgame.logic.server.game.email.entity.UserEmail;
import com.xgame.logic.server.game.email.entity.UserEmailEntityFactory;
import com.xgame.logic.server.game.email.entity.eventmodel.SystemEmailEvent;


/**
 * 邮件监听
 * @author jacky.jiang
 *
 */
@Component
public class MailListener implements IListener {

	@Autowired
	private AllianceManager allianceManager;
	@Autowired
	private PlayerMailInfoManager playerMailInfoManager;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private EventBus eventBus;
	@Autowired
	private UserEmailEntityFactory userEmailEntityFactory;
	
	@PostConstruct
	public void init() {
		eventBus.addEventListener(this);
	}
	
	
	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EMAIL_SEND};
	}

	@Override
	public void onAction(IEventObject e) {
		Set<Long> room = new HashSet<>();
		SystemEmailEvent event = (SystemEmailEvent)e;
		switch(event.getEmailType()) {
			case EmailType.ALLIANCE:
				Email allianceEmail = (Email)event.getObj();
				Alliance alliance = allianceManager.getRefreshAlliance(allianceEmail.getTargetId());
				if(alliance != null) {
					room.addAll(alliance.getAllianceMember());
				}
				break;
			case EmailType.SYSTEM: 
			case EmailType.PLAYER:
			case EmailType.REPORT:
				UserEmail userEmail = (UserEmail)event.getObj();
				room.add(userEmail.getTargetId());
				break;
		}
		for(Long id : room){
			PlayerSession playerSession = sessionManager.getSessionByPlayerId(id);
			if(playerSession != null) {
				playerMailInfoManager.pushNewEmailFlag(id);
			}
		}
	}
}
