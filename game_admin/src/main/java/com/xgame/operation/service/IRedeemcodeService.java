package com.xgame.operation.service;

import com.xgame.base.service.IBaseService;
import com.xgame.operation.entity.Redeemcode;

public interface IRedeemcodeService extends IBaseService<Redeemcode> {
	
	public int updateStatusByPackageId(String packageId);
}
