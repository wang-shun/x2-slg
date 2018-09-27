package com.xgame.gm.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xgame.base.dao.impl.BaseDaoImpl;
import com.xgame.common.Pagination;
import com.xgame.gm.dao.IUserDao;
import com.xgame.gm.entity.User;

@Repository(value="userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	
	public User getUserByName(String username){
		List<User> list = findByProperty("username", username);
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String hql = "from User bean where 1=1";
		if(null != params.get("username") && !"".equals(params.get("username"))){
			hql = hql + " and bean.username like :username";
			paramsMap.put("username", "%"+params.get("username")+"%");
		}
		if(null != params.get("startDt") && !"".equals(params.get("startDt"))){
			try {
				hql = hql + " and bean.lastLoginDate >= :startDt";
				paramsMap.put("startDt", sdf.parse(params.get("startDt").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(null != params.get("endDt") && !"".equals(params.get("endDt"))){
			try {
				hql = hql + " and bean.lastLoginDate <= :endDt";
				paramsMap.put("endDt", sdf.parse(params.get("endDt").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Pagination pagination = getPaginationByHql(hql, paramsMap, pageNumber, pageSize);
		return pagination;
	}
}
