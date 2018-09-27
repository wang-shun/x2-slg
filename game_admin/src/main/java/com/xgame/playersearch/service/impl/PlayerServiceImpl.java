package com.xgame.playersearch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xgame.base.service.impl.BaseServiceImpl;
import com.xgame.common.Pagination;
import com.xgame.playersearch.dao.IPlayerDao;
import com.xgame.playersearch.entity.Player;
import com.xgame.playersearch.service.IPlayerService;

@Service(value="playerService")
@Transactional
public class PlayerServiceImpl extends BaseServiceImpl<Player> implements IPlayerService {
	
	@Autowired
	private IPlayerDao playerDao;

	public void setPlayerDao(IPlayerDao playerDao) {
		this.playerDao = playerDao;
	}

	@Override
	public Pagination getPage(int pageNumber, int pageSize, Map<String, Object> params) {
		return playerDao.getPage(pageNumber, pageSize, params);
	}
	
	@Override
	public Pagination getLogPage(int pageNumber,int pageSize,Map<String,Object> params){
		return playerDao.getLogPage(pageNumber, pageSize, params);
	}
	
	@Override
	public void batchSaveOrUpdate(List<Player> players){
		playerDao.batchSaveOrUpdate(players);
	}
}
