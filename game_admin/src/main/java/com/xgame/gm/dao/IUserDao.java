package com.xgame.gm.dao;

import java.util.Map;

import com.xgame.base.dao.IBaseDao;
import com.xgame.common.Pagination;
import com.xgame.gm.entity.User;

public interface IUserDao extends IBaseDao<User> {
	
	public User getUserByName(String username);
	
	public Pagination getPage(int pageNumber,int pageSize,Map<String,Object> params);
}
