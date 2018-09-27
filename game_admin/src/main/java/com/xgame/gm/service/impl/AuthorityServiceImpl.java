package com.xgame.gm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgame.base.service.impl.BaseServiceImpl;
import com.xgame.gm.dao.IAuthorityDao;
import com.xgame.gm.entity.Authority;
import com.xgame.gm.service.IAuthorityService;

@Service(value="authorityService")
@Transactional
public class AuthorityServiceImpl extends BaseServiceImpl<Authority> implements IAuthorityService {
	
	@Autowired
	private IAuthorityDao authorityDao;

	public void setAuthorityDao(IAuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	
	@Override
	public List<Authority> getParentAuthority(){
		return authorityDao.getParentAuthority();
	}
}
