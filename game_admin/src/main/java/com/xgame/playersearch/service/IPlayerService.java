package com.xgame.playersearch.service;

import java.util.List;
import java.util.Map;

import com.xgame.base.service.IBaseService;
import com.xgame.common.Pagination;
import com.xgame.playersearch.entity.Player;

public interface IPlayerService extends IBaseService<Player> {
	
	public Pagination getPage(int pageNumber,int pageSize,Map<String,Object> params);
	
	public Pagination getLogPage(int pageNumber,int pageSize,Map<String,Object> params);
	
	public void batchSaveOrUpdate(List<Player> players);
}
