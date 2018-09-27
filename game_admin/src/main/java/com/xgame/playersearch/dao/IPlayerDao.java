package com.xgame.playersearch.dao;

import java.util.List;
import java.util.Map;

import com.xgame.base.dao.IBaseDao;
import com.xgame.common.Pagination;
import com.xgame.playersearch.entity.Player;

public interface IPlayerDao extends IBaseDao<Player> {
	
	public Pagination getPage(int pageNumber,int pageSize,Map<String,Object> params);
	
	public Pagination getLogPage(int pageNumber,int pageSize,Map<String,Object> params);
	
	public void batchSaveOrUpdate(List<Player> players);
}
