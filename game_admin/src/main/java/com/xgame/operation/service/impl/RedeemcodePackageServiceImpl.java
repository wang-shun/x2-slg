package com.xgame.operation.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgame.base.service.impl.BaseServiceImpl;
import com.xgame.common.Pagination;
import com.xgame.operation.dao.IRedeemcodePackageDao;
import com.xgame.operation.entity.Redeemcode;
import com.xgame.operation.entity.RedeemcodePackage;
import com.xgame.operation.service.IRedeemcodePackageService;

@Service(value="redeemcodePackageService")
@Transactional
public class RedeemcodePackageServiceImpl extends BaseServiceImpl<RedeemcodePackage> implements IRedeemcodePackageService {
	
	@Autowired
	private IRedeemcodePackageDao redeemcodePackageDao;

	public void setRedeemcodePackageDao(IRedeemcodePackageDao redeemcodePackageDao) {
		this.redeemcodePackageDao = redeemcodePackageDao;
	}
	
	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		return redeemcodePackageDao.getPage(pageNumber, pageSize, params);
	}
	
	@Override
	public RedeemcodePackage save(RedeemcodePackage redeemcodePackage,List<Redeemcode> redeemcodes){
		redeemcodePackage = redeemcodePackageDao.save(redeemcodePackage);
		for(Redeemcode redeemcode : redeemcodes){
			redeemcode.setRedeemcodePackage(redeemcodePackage);
		}
		redeemcodePackage.setRedeemcodes(redeemcodes);
		return redeemcodePackage;
	}
}
