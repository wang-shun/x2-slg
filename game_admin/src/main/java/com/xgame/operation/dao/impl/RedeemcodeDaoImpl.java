package com.xgame.operation.dao.impl;

import org.springframework.stereotype.Repository;

import com.xgame.base.dao.impl.BaseDaoImpl;
import com.xgame.operation.dao.IRedeemcodeDao;
import com.xgame.operation.entity.Redeemcode;

@Repository(value="redeemcodeDao")
public class RedeemcodeDaoImpl extends BaseDaoImpl<Redeemcode> implements IRedeemcodeDao {
	
	public int updateStatusByPackageId(String packageId){
		String hql ="update Redeemcode set status='Y' where redeemcodePackage.id ='"+packageId+"'";
		return getSession().createQuery(hql).executeUpdate();
	}
}
