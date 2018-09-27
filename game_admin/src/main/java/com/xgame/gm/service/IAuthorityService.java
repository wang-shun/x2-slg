package com.xgame.gm.service;

import java.util.List;

import com.xgame.base.service.IBaseService;
import com.xgame.gm.entity.Authority;

public interface IAuthorityService extends IBaseService<Authority>{
	
	public List<Authority> getParentAuthority();
}
