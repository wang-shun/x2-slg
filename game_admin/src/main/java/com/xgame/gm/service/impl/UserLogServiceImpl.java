package com.xgame.gm.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgame.base.service.impl.BaseServiceImpl;
import com.xgame.common.Pagination;
import com.xgame.gm.dao.IUserLogDao;
import com.xgame.gm.entity.UserLog;
import com.xgame.gm.service.IUserLogService;

@Service(value="userLogService")
@Transactional
public class UserLogServiceImpl extends BaseServiceImpl<UserLog> implements IUserLogService {
	
	@Autowired
	private IUserLogDao userLogDao;

	public void setUserLogDao(IUserLogDao userLogDao) {
		this.userLogDao = userLogDao;
	}

	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		return userLogDao.getPage(pageNumber, pageSize, params);
	}
}
