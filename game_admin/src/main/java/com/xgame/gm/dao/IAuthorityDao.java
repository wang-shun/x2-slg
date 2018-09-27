package com.xgame.gm.dao;

import java.util.List;

import com.xgame.base.dao.IBaseDao;
import com.xgame.gm.entity.Authority;

public interface IAuthorityDao extends IBaseDao<Authority> {
	
	public List<Authority> getParentAuthority();
}
