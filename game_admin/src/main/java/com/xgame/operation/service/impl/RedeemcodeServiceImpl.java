package com.xgame.operation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgame.base.service.impl.BaseServiceImpl;
import com.xgame.operation.dao.IRedeemcodeDao;
import com.xgame.operation.entity.Redeemcode;
import com.xgame.operation.service.IRedeemcodeService;

@Service(value="redeemcodeService")
@Transactional
public class RedeemcodeServiceImpl extends BaseServiceImpl<Redeemcode> implements IRedeemcodeService {
	
	@Autowired
	private IRedeemcodeDao redeemcodeDao;

	public void setRedeemcodeDao(IRedeemcodeDao redeemcodeDao) {
		this.redeemcodeDao = redeemcodeDao;
	}
	
	@Override
	public int updateStatusByPackageId(String packageId){
		return redeemcodeDao.updateStatusByPackageId(packageId);
	}
}
