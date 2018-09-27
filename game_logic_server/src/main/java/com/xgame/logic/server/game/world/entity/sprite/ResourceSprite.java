package com.xgame.logic.server.game.world.entity.sprite;

import io.protostuff.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.utils.EnumUtils;

/**
 * 资源精灵
 * @author jacky.jiang
 *
 */
public class ResourceSprite extends WorldSprite {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9071367274776907792L;

	public static final int explorerRecordNum = 10;
	
	/**
	 * 剩余数量
	 */
	@Tag(11)
	private int curNum;

	/**
	 * 资源等级
	 */
	@Tag(12)
	private int level; 

	/**
	 * 所属生成半径
	 */
	@Tag(13)
	private int range;

	/**
	 * 上一次刷新时间
	 */
	@Tag(14)
	private long lastRefreshTime;
	
	/**
	 * 采集速度,占领速度
	 */
	private double explorerSpeed;
	
	/**
	 * 世界资源类型
	 */
	@Tag(17)
	private CurrencyEnum resourceType;
	
	/**
	 * 采集记录
	 */
	@Tag(18)
	private List<ExplorerRecord> explorer = new CopyOnWriteArrayList<>();
	
	public ResourceSprite() {
		super();
	}

	public ResourceSprite(int curNum, int level, int range, CurrencyEnum currencyEnum) {
		super();
		this.curNum = curNum;
		this.level = level;
		this.range = range;
		this.resourceType = currencyEnum;
	}

	public int getCurNum() {
		return curNum;
	}

	public void setCurNum(int curNum) {
		this.curNum = curNum;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public long getLastRefreshTime() {
		return lastRefreshTime;
	}

	public void setLastRefreshTime(long lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}
	
	/**
	 * 添加采集记录
	 * @param record
	 */
	public synchronized void addRecord(ExplorerRecord record) {
		if(explorer == null) {
			explorer = new ArrayList<ExplorerRecord>();
		}
		
		if(explorer.size() >= explorerRecordNum) {
			explorer.remove(0);
		}
		
		explorer.add(record);
	}
	
	/**
	 * 返回world
	 */
	public WorldSprite refresh() {
		WorldSprite worldSprite = new WorldSprite();
		worldSprite.setAllianceId(this.getAllianceId());
		worldSprite.setEndTime(this.getEndTime());
		worldSprite.setStartTime(this.getStartTime());
		worldSprite.setOwnerMarchId(this.getOwnerMarchId());
		worldSprite.setOwnerId(this.getOwnerId());
		worldSprite.setBattleWaitQueue(this.getBattleWaitQueue());
		// 清空信息
		worldSprite.clean();
		return worldSprite;
	}

	public List<ExplorerRecord> getExplorer() {
		return explorer;
	}

	public void setExplorer(List<ExplorerRecord> explorer) {
		this.explorer = explorer;
	}

	public double getExplorerSpeed() {
		return explorerSpeed;
	}

	public void setExplorerSpeed(double explorerSpeed) {
		this.explorerSpeed = explorerSpeed;
	}

	public CurrencyEnum getResourceType() {
		return resourceType;
	}

	public void setResourceType(CurrencyEnum resourceType) {
		this.resourceType = resourceType;
	}
	
	public JBaseData toJBaseData() {
		JBaseData jbaseData = super.toJBaseData();
		jbaseData.put("curNum", curNum);
		jbaseData.put("level", level);
		jbaseData.put("range", range);
		jbaseData.put("lastRefreshTime", lastRefreshTime);
		jbaseData.put("explorerSpeed", explorerSpeed);
		jbaseData.put("resourceType", resourceType.ordinal());
		
		List<JBaseData> explorerList = new ArrayList<JBaseData>();
		for (int i = 0; i < explorer.size(); i++) {
			ExplorerRecord explorerRecord = explorer.get(i);
			explorerList.add(explorerRecord.toJBaseData());
		}
		jbaseData.put("explorer", explorerList);
		return jbaseData;
	}

	public void fromJBaseData(JBaseData jBaseData) {
		super.fromJBaseData(jBaseData);
		this.curNum = jBaseData.getInt("curNum", 0);
		this.level = jBaseData.getInt("level", 0);
		this.range = jBaseData.getInt("range", 0);
		this.lastRefreshTime = jBaseData.getLong("lastRefreshTime", 0);
		this.explorerSpeed = jBaseData.getDouble("explorerSpeed", 0);
		this.resourceType = EnumUtils.getEnum(CurrencyEnum.class, jBaseData.getInt("resourceType", 0));
		
		List<JBaseData> explorerList = jBaseData.getSeqBaseData("explorer");
		for(JBaseData jBaseData2 : explorerList) {
			ExplorerRecord explorerRecord = new ExplorerRecord();
			explorerRecord.fromJBaseData(jBaseData2);
			this.explorer.add(explorerRecord);
		}
	}

	
}
