package com.xgame.logic.server.game.country.entity;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.country.bean.BuildBean;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.player.entity.Player;

import io.protostuff.Tag;

/**
 *
 *2016-12-13  15:19:59
 *@author ye.yuan
 *
 */
public class XBuild implements Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 样本id
	 */
	@Tag(1)
	private int sid;
	
	/**
	 * 唯一id
	 */
	@Tag(2)
	private int uid;
	
	/**
	 * 
	 * 产生时间
	 */
	@Tag(3)
	private long outputTime;
	
	/**
	 * 当前状态
	 * @link TimeState
	 */
	@Tag(4)
	private int state;
	
	/**
	 * 当前sid建筑的 第几个  
	 */
	@Tag(5)
	private int index;
	
	/**
	 * 等级
	 */
	@Tag(6)
	private volatile int level;
	
	
	public BuildBean toBuildBean(Player player){
		BuildBean bean = new BuildBean();
		bean.uid = uid;
		bean.sid = sid;
		bean.state = state;
		bean.level = level;
		if (sid == BuildFactory.MINE_CAR.getTid()) {
			MineRepository mineRepository = player.roleInfo().getBaseCountry().getMineRepository();
			if(mineRepository != null) {
				MineCar mineCar = mineRepository.getCar(uid);
				if(mineCar != null) {
					bean.ext = String.valueOf(mineCar.getResourceType());
				}
			}
		}
		return bean;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public long getOutputTime() {
		return outputTime;
	}

	public void setOutputTime(long outputTime) {
		this.outputTime = outputTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("sid", sid);
		jBaseData.put("uid", uid);
		jBaseData.put("outputTime", outputTime);
		jBaseData.put("state", state);
		jBaseData.put("index", index);
		jBaseData.put("level", level);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.sid = jBaseData.getInt("sid", 0);
		this.uid = jBaseData.getInt("uid", 0);
		this.state = jBaseData.getInt("state", 0);
		this.outputTime = jBaseData.getLong("outputTime", 0);
		this.state = jBaseData.getInt("state", 0);
		this.index = jBaseData.getInt("index", 0);
		this.level = jBaseData.getInt("level", 0);
	}
	
}
