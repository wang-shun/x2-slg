package com.xgame.gm.dao;

import java.util.Map;

import com.xgame.base.dao.IBaseDao;
import com.xgame.common.Pagination;
import com.xgame.gm.entity.UserLog;

public interface IUserLogDao extends IBaseDao<UserLog> {
	
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params);
}
