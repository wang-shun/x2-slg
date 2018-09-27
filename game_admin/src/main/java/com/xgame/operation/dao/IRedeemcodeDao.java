package com.xgame.operation.dao;

import com.xgame.base.dao.IBaseDao;
import com.xgame.operation.entity.Redeemcode;

public interface IRedeemcodeDao extends IBaseDao<Redeemcode> {
	
	public int updateStatusByPackageId(String packageId);
}
