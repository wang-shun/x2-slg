package com.xgame.logic.server.game.timertask.entity.job.data;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.world.entity.MarchUtils;
import com.xgame.logic.server.game.world.entity.WorldMarch;
import com.xgame.logic.server.game.world.entity.sprite.ResourceSprite;
import com.xgame.utils.TimeUtils;


/**
 * 采集任务
 * @author jacky.jiang
 *
 */
public class CollectTimerTaskData implements Serializable, JBaseTransform  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4462766006875882559L;

	/**
	 * 行军任务id
	 */
	private long marchId;
	
	/**
	 * 初始采集速度
	 */
	private double initSpeed;
	
	/**
	 * 采集时间
	 */
	private int explorerTime;
	
	/**
	 * 上一次更新采集速度时间
	 */
	private long lastUpdatTime;
	
	/**
	 * 更新之后的采集速度
	 */
	private double updateSpeed;
	
	/**
	 * 最大采集数量
	 */
	private int maxNum;

	/**
	 * 采集开始时间
	 */
	private long explorerStartTime;
	
	/**
	 * 掠夺过来的资源数量
	 */
	private int invasionNum;
	
	
	public CollectTimerTaskData() {
		
	}
	
	public CollectTimerTaskData(long marchId, int maxNum, int explorerTime, double initSpeed, long explorerStartTime, int invasionNum) {
		super();
		this.marchId = marchId;
		this.explorerTime = explorerTime;
		this.initSpeed = initSpeed;
		this.maxNum = maxNum;
		this.explorerStartTime = explorerStartTime;
		this.invasionNum = invasionNum;
	}

	public long getMarchId() {
		return marchId;
	}

	public void setMarchId(long marchId) {
		this.marchId = marchId;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public double getInitSpeed() {
		return initSpeed;
	}

	public void setInitSpeed(double initSpeed) {
		this.initSpeed = initSpeed;
	}

	public int getExplorerTime() {
		return explorerTime;
	}

	public void setExplorerTime(int explorerTime) {
		this.explorerTime = explorerTime;
	}

	public double getUpdateSpeed() {
		return updateSpeed;
	}

	public void setUpdateSpeed(double updateSpeed) {
		this.updateSpeed = updateSpeed;
	}

	public long getLastUpdatTime() {
		return lastUpdatTime;
	}

	public void setLastUpdatTime(long lastUpdatTime) {
		this.lastUpdatTime = lastUpdatTime;
	}

	public long getExplorerStartTime() {
		return explorerStartTime;
	}

	public void setExplorerStartTime(long explorerStartTime) {
		this.explorerStartTime = explorerStartTime;
	}
	
	public int getInvasionNum() {
		return invasionNum;
	}

	public void setInvasionNum(int invasionNum) {
		this.invasionNum = invasionNum;
	}

	/**
	 * 计算已经采集数量
	 * @param player
	 * @param worldMarch
	 * @param resourceSprite
	 * @return
	 */
	public int getExploreredNum(Player player, WorldMarch worldMarch, ResourceSprite resourceSprite) {
		if(lastUpdatTime > 0) {
			int initTime = (int)((explorerStartTime - lastUpdatTime) / 1000);
			int updateTime = Math.max(0, (int)((TimeUtils.getCurrentTimeMillis() - lastUpdatTime) / 1000));
			updateTime = Math.min(updateTime, explorerTime - initTime);
			int explorerNum = Double.valueOf(Math.ceil(initSpeed * initTime + updateSpeed * updateTime)).intValue();
			
			explorerNum = Math.min(explorerNum + invasionNum, maxNum);
			int weight = worldMarch.getWeight(player);
			int weightNum = MarchUtils.mathGiveNum(resourceSprite.getCurNum(), weight, resourceSprite.getResourceType());
			
			explorerNum = Math.min(explorerNum, weightNum);
			return explorerNum;
			
		} else {
			int updateTime = (int)((TimeUtils.getCurrentTimeMillis() - explorerStartTime) / 1000);
			int explorerNum = Double.valueOf(Math.ceil(initSpeed * updateTime)).intValue();

			int weight = worldMarch.getWeight(player);
			int weightNum = MarchUtils.mathGiveNum(resourceSprite.getCurNum(), weight, resourceSprite.getResourceType());

			explorerNum = Math.min(explorerNum + invasionNum, weightNum);
			return explorerNum;
		}
	}
	
	@Override
	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("marchId", marchId);
		jbaseData.put("initSpeed", initSpeed);
		jbaseData.put("explorerTime", explorerTime);
		jbaseData.put("lastUpdatTime", lastUpdatTime);
		jbaseData.put("updateSpeed", updateSpeed);
		jbaseData.put("maxNum", maxNum);
		jbaseData.put("explorerStartTime", explorerStartTime);
		return jbaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.marchId = jBaseData.getLong("marchId",0);
		this.initSpeed = jBaseData.getDouble("initSpeed",0);
		this.explorerTime = jBaseData.getInt("explorerTime",0);
		this.lastUpdatTime = jBaseData.getLong("lastUpdatTime",0);
		this.updateSpeed = jBaseData.getDouble("updateSpeed",0);
		this.maxNum = jBaseData.getInt("maxNum",0);
		this.explorerStartTime = jBaseData.getLong("explorerStartTime",0);
	}
	
}
