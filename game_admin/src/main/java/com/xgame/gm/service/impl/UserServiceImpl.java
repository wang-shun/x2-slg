package com.xgame.gm.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgame.base.service.impl.BaseServiceImpl;
import com.xgame.common.Pagination;
import com.xgame.gm.dao.IUserDao;
import com.xgame.gm.entity.User;
import com.xgame.gm.service.IUserService;

@Service(value="userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public User getUserByName(String username){
		return userDao.getUserByName(username);
	}

	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		return userDao.getPage(pageNumber, pageSize, params);
	}
}
