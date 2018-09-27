package com.xgame.playersearch.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xgame.base.dao.impl.BaseDaoImpl;
import com.xgame.common.Pagination;
import com.xgame.playersearch.dao.IPlayerDao;
import com.xgame.playersearch.entity.Player;

@Repository(value="playerrDao")
public class PlayerDaoImpl extends BaseDaoImpl<Player> implements IPlayerDao {
	public static final String LOG_TABLE_NAME = "operatorlog";
	
	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		String hql = "from Player bean where 1=1";
		if(null != params.get("vipLevel") && !"".equals(params.get("vipLevel"))){
			hql = hql + " and bean.vipLevel in ("+params.get("vipLevel")+")";
		}
		if(null != params.get("roleId") && !"".equals(params.get("roleId"))){
			hql = hql + " and bean.roleId = :roleId";
			paramsMap.put("roleId", Long.parseLong(params.get("roleId").toString()));
		}
		if(null != params.get("roleName") && !"".equals(params.get("roleName"))){
			hql = hql + " and bean.roleName like :roleName";
			paramsMap.put("roleName", "%"+params.get("roleName")+"%");
		}
		if(null != params.get("serverArea") && !"".equals(params.get("serverArea"))){
			hql = hql + " and bean.serverArea = :serverArea";
			paramsMap.put("serverArea", Long.parseLong(params.get("serverArea").toString()));
		}
		Pagination pagination = getPaginationByHql(hql, paramsMap, pageNumber, pageSize);
		return pagination;
	}
	
	@Override
	public Pagination getLogPage(int pageNumber,int pageSize,Map<String,Object> params){
		//查询对应的用户操作表
		List list = getTableNames(LOG_TABLE_NAME);
		String startLog = LOG_TABLE_NAME+"20000101";
		String endLog = LOG_TABLE_NAME+"99991231";
		if(null != params.get("startDt") && !"".equals(params.get("startDt"))){
			startLog = LOG_TABLE_NAME+params.get("startDt").toString().replaceAll("-", "");
		}
		if(null != params.get("endDt") && !"".equals(params.get("endDt"))){
			endLog = LOG_TABLE_NAME+params.get("endDt").toString().replaceAll("-", "");
		}
		//根据查到的分表进行union查询
		if(null != list && list.size()>0){
			//配合分页规则
			String sql = "select * from (";
			String unionStr = "";
			for(int i=0;i<list.size();i++){
				//此处进行表的拼装,需要特别注意,容易出错
				if(list.get(i).toString().compareTo(startLog)>=0 && list.get(i).toString().compareTo(endLog)<=0){
					String tableName = "t"+i;
					unionStr+="select * from "+list.get(i).toString()+" as "+tableName+" where 1=1 ";
					if(null != params.get("roleId") && !"".equals(params.get("roleId"))){
						unionStr+=" and "+tableName+".playerId = '"+params.get("roleId").toString()+"'";
					}
					unionStr+=" union all ";
				}
			}
			if(!"".equals(unionStr)){
				unionStr = unionStr.substring(0, unionStr.lastIndexOf("union all"));
				sql+=unionStr;
				sql+=") as operator ";
				Pagination pagination = getPaginationBySql(sql, pageNumber, pageSize);
				return pagination;
			}
		}
		return new Pagination();
	}
	
	@Override
	public void batchSaveOrUpdate(List<Player> players){
		if(players != null && players.size()>0){
			for(int i = 0;i<players.size();i++){
				this.saveOrUpdate(players.get(i));
				if(i%100==0){
					getSession().flush();
					getSession().clear();
				}
			}
		}
	}
}
