package com.xgame.logic.server.core.utils.sequance;

import org.springframework.stereotype.Component;

import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.utils.InjectorUtil;


/**
 * 邮件id
 * @author jacky.jiang
 *
 */
@Component
public class MailDequence {
	
	private long mailid = 0;

	private short _mailid = -1;

	private void initItemSequance() {
		mailid = (InjectorUtil.getInjector().redisClient.incr(DBKey.MAIL_ID_KEY) + IdSequancePrefix.MAIL_ID) * 100000;
	}

	public void init() {
		initItemSequance();
	}

	private short addShortValue(short value) {
		value++;
		if (value == Short.MAX_VALUE) {
			value = 0;
		}
		return value;
	}

	public long genMailId() {
		_mailid = addShortValue(_mailid);
		if (_mailid == 0) {
			initItemSequance();
		}
		return mailid + _mailid;
	}
}
