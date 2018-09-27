package com.xgame.operation.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xgame.base.dao.impl.BaseDaoImpl;
import com.xgame.common.Pagination;
import com.xgame.operation.dao.IRedeemcodePackageDao;
import com.xgame.operation.entity.RedeemcodePackage;

@Repository(value="redeemcodePackageDao")
public class RedeemcodePackageDaoImpl extends BaseDaoImpl<RedeemcodePackage> implements IRedeemcodePackageDao {

	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String hql = "select new Map(bean.id as id,bean.channel as channel,bean.packageName as packageName,bean.packageDetail as packageDetail,bean.num as num,"
				+ "bean.createDate as createDate,bean.creator as creator,bean.endDate as endDate,bean.creatorRealname as creatorRealname,bean.creatorId as creatorId,bean.startDate as startDate,"
				+ "(select count(1) from Redeemcode where redeemcodePackage = bean and status='N') as unusedNum) ";
		String countHql = "select count(*) ";
		String hqlStr = "from RedeemcodePackage bean where 1=1";
		if(null != params.get("packageName") && !"".equals(params.get("packageName"))){
			hqlStr = hqlStr + " and bean.packageName like :packageName";
			paramsMap.put("packageName", "%"+params.get("packageName")+"%");
		}
		if(null != params.get("channel") && !"".equals(params.get("channel"))){
			hqlStr = hqlStr + " and bean.channel like :channel";
			paramsMap.put("channel", "%"+params.get("channel")+"%");
		}
		if(null != params.get("startDt") && !"".equals(params.get("startDt"))){
			try {
				hqlStr = hqlStr + " and bean.endDate >= :startDt";
				paramsMap.put("startDt", sdf.parse(params.get("startDt").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(null != params.get("endDt") && !"".equals(params.get("endDt"))){
			try {
				hqlStr = hqlStr + " and bean.startDate <= :endDt";
				paramsMap.put("endDt", sdf.parse(params.get("endDt").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		hql += hqlStr;
		countHql += hqlStr;
		Pagination pagination = getPaginationByHqlMultiFrom(hql,countHql, paramsMap, pageNumber, pageSize);
		return pagination;
	}
}
