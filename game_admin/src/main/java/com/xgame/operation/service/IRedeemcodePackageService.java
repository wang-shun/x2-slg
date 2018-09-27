package com.xgame.operation.service;

import java.util.List;
import java.util.Map;

import com.xgame.base.service.IBaseService;
import com.xgame.common.Pagination;
import com.xgame.operation.entity.Redeemcode;
import com.xgame.operation.entity.RedeemcodePackage;

public interface IRedeemcodePackageService extends IBaseService<RedeemcodePackage> {
	
	public Pagination getPage(int pageNumber,int pageSize,Map<String,Object> params);
	
	public RedeemcodePackage save(RedeemcodePackage redeemcodePackage,List<Redeemcode> redeemcodes);
}
