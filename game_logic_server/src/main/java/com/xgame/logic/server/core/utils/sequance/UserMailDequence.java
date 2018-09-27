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
public class UserMailDequence {
	
	private long userMailid = 0;

	private short _userMailid = -1;

	private void initItemSequance() {
		userMailid = (InjectorUtil.getInjector().redisClient.incr(DBKey.USER_MAIL_ID_KEY) + IdSequancePrefix.USER_MAIL_ID) * 100000;
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

	public long genUserMailId() {
		_userMailid = addShortValue(_userMailid);
		if (_userMailid == 0) {
			initItemSequance();
		}
		return userMailid + _userMailid;
	}
}
