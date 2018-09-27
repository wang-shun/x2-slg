package com.xgame.gm.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xgame.base.dao.impl.BaseDaoImpl;
import com.xgame.common.Pagination;
import com.xgame.gm.dao.IUserLogDao;
import com.xgame.gm.entity.UserLog;

@Repository(value="userLogDao")
public class UserLogDaoImpl extends BaseDaoImpl<UserLog> implements IUserLogDao {

	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String hql = "from UserLog bean where 1=1";
		if(null != params.get("realName") && !"".equals(params.get("realName"))){
			hql = hql + " and bean.realName like :realName";
			paramsMap.put("realName", "%"+params.get("realName")+"%");
		}
		if(null != params.get("actionDetail") && !"".equals(params.get("actionDetail"))){
			hql = hql + " and bean.actionDetail like :actionDetail";
			paramsMap.put("actionDetail", "%"+params.get("actionDetail")+"%");
		}
		if(null != params.get("startDt") && !"".equals(params.get("startDt"))){
			try {
				hql = hql + " and bean.createDate >= :startDt";
				paramsMap.put("startDt", sdf.parse(params.get("startDt").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(null != params.get("endDt") && !"".equals(params.get("endDt"))){
			try {
				hql = hql + " and bean.createDate <= :endDt";
				paramsMap.put("endDt", sdf.parse(params.get("endDt").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Pagination pagination = getPaginationByHql(hql, paramsMap, pageNumber, pageSize);
		return pagination;
	}

}
