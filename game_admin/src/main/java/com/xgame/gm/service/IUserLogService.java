package com.xgame.gm.service;

import java.util.Map;

import com.xgame.base.service.IBaseService;
import com.xgame.common.Pagination;
import com.xgame.gm.entity.UserLog;

public interface IUserLogService extends IBaseService<UserLog> {
	
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params);
}
