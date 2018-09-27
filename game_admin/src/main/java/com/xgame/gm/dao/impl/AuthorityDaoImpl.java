package com.xgame.gm.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xgame.base.dao.impl.BaseDaoImpl;
import com.xgame.gm.dao.IAuthorityDao;
import com.xgame.gm.entity.Authority;

@Repository(value="authorityDao")
public class AuthorityDaoImpl extends BaseDaoImpl<Authority> implements IAuthorityDao {
	
	@Override
	public List<Authority> getParentAuthority(){
		String hql = "from Authority where parent is null order by itemNo";
		List<Authority> list = findListByHql(hql, new HashMap<String,Object>());
		return list;
	}
}
