package com.xgame.operation.dao;

import java.util.Map;

import com.xgame.base.dao.IBaseDao;
import com.xgame.common.Pagination;
import com.xgame.operation.entity.RedeemcodePackage;

public interface IRedeemcodePackageDao extends IBaseDao<RedeemcodePackage> {
	
	public Pagination getPage(int pageNumber,int pageSize,Map<String,Object> params);
}
