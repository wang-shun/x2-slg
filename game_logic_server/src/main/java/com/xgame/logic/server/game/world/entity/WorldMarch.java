package com.xgame.logic.server.game.world.entity;

import io.protostuff.Tag;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.xgame.logic.server.core.db.cache.entity.AbstractEntity;
import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.ClassNameUtils;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.timertask.entity.job.data.CollectTimerTaskData;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.world.constant.MarchFightState;
import com.xgame.logic.server.game.world.constant.MarchState;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.converter.WorldConverter;
import com.xgame.logic.server.game.world.entity.march.IMarchExecutor;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;
import com.xgame.utils.EnumUtils;
import com.xgame.utils.TimeUtils;

/**
 * 世界出征信息
 * @author jacky.jiang
 *
 */
public class WorldMarch extends AbstractEntity<Long> implements JBaseTransform {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -713108649820267012L;
	
	// 出征uid
	@Tag(1)
	private long uid;
	// 所属玩家uid
	@Tag(2)
	private String ownerUid;
	// 所属对象名称
	@Tag(3)
	private String ownerName;
	// 联盟信息
	@Tag(4)
	private String allianceId;
	// 组队信息
	@Tag(5)
	private String teamId;
	/**
	 * 目标坐标点信息
	 */
	private int targetId;

	/**
	 * 目标类型
	 */
	private int targetType;
	
	/**
	 * 根据targetType类型不同
	 * 如果targetType是资源,本类型是具体的资源类型
	 */
	private int targetSubType;
	
	/**
	 * 目标唯一id
	 */
	private String targetUid;
	// 行军状态
	@Tag(8)
	private MarchState marchState;
	// 行军类型
	@Tag(9)
	private MarchType marchType;

	// 出征士兵数量
	private WorldMarchSoldier worldMarchSoldier;
	
	// 队列标识
	@Tag(11)
	private long taskId;
	// 行军开始时间
	@Tag(12)
	private long marchStartTime;
	// 行军时间
	@Tag(13)
	private int marchTime;
	// 行军到达时间
	@Tag(14)
	private long marchArrivalTime;
	// 返回开始时间
	@Tag(15)
	private long returnStartTime;
	// 到达时间
	@Tag(16)
	private long arrivalTime;
	// 采集或者占领时间
	@Tag(17)
	private int exploreTime;
	/**
	 * 当前已采集数量，用于保存战斗中间值
	 */
	private int exlplorerNum;
	
	/**
	 * 采集负载重量
	 */
	private int maxNum;
	
	/**
	 * 完成占领进度
	 */
	private double finishOccupyRatio;
	
	/**
	 * 采集资源类型
	 */
	private CurrencyEnum explorerType = CurrencyEnum.DEFAULT;
	
	/**
	 * 行军携带返回信息
	 */
	@Tag(18)
	private Object attach;
	
	/**
	 * 集结剩余时间
	 */
	private int remainTime;
	
	/**
	 * executor
	 */
	public transient IMarchExecutor executor;
	
	/**
	 * 采集任务
	 */
	private long exlporerTaskId;
	
	/**
	 * 占领任务id
	 */
	private long occupyTaskId;
	
	/**
	 * 战斗结束时间
	 */
	private long battleEndTime;
	
	/**
	 * 战斗开始时间
	 */
	private long battleStartTime;
	
	/**
	 * 
	 */
	private MarchFightState marchFight = MarchFightState.NONE;
	
	/**
	 * 贸易资源
	 */
	private WarResourceBean warResourceBean = new WarResourceBean();
	
	private boolean systemCreate;
	
	private EmailSignature defEmailSignature;
	
	/**
	 * 采集资源信息
	 */
	private CollectTimerTaskData collectTimerTaskData = new CollectTimerTaskData();
	

	public void march(int time) {
		// 设置出征时间
		this.marchTime = time;
		this.marchStartTime = TimeUtils.getCurrentTimeMillis();
		
		// 设置行军时间
		this.marchArrivalTime = this.marchStartTime + (this.marchTime * 1000);
		this.arrivalTime = this.marchArrivalTime;
	}
	
	/**
	 * 执行完成返回
	 * @param marchTime
	 */
	public void backReturn (int marchTime) {
		marchState = MarchState.BACK;
		returnStartTime = TimeUtils.getCurrentTimeMillis();
		marchArrivalTime = returnStartTime + marchTime * 1000;
		arrivalTime = marchArrivalTime;
	}
	
	/**
	 * 计算兵的速度
	 * @param soldiers
	 * @return
	 */
	public static double getSoldierSpeed(Player player, List<Soldier> soldiers) {
		double speed = 0.0;
		for(Soldier soldier : soldiers) {
			double soldierSpeed = soldier.getAttribute(player, AttributesEnum.SPEED_FIGHT);
			if (speed <= 0) {
				speed = soldierSpeed;
			} else {
				speed = Math.min(speed, soldierSpeed);
			}
		}
		return speed;
	}
	
	/**
	 * 任务未执行完返回
	 */
	public void failBackReturn() {
		
		int initTotalTime = Long.valueOf(this.arrivalTime - this.marchStartTime).intValue();
		int totalMarchTime = Long.valueOf(this.marchArrivalTime - this.marchStartTime).intValue();
		int costTime = Long.valueOf(TimeUtils.getCurrentTimeMillis() - this.marchStartTime).intValue();
		
		double ratio = ((double)costTime / totalMarchTime);
		int returnCostTime = Double.valueOf(ratio * initTotalTime).intValue();
		
		this.returnStartTime =TimeUtils.getCurrentTimeMillis();
		this.marchState = MarchState.BACK;
		this.marchArrivalTime = this.returnStartTime + returnCostTime;
		this.arrivalTime = this.marchArrivalTime;
	}
	
	/**
	 * 获取士兵列表
	 * @return
	 */
	public List<Soldier> querySoldierList() {
		if(worldMarchSoldier != null) {
			return worldMarchSoldier.querySoldierList();
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 获取士兵数量
	 * @return
	 */
	public int getSoldierNum() {
		int num = 0;
		List<Soldier> soldierList = this.querySoldierList();
		if(soldierList != null && !soldierList.isEmpty()) {
			for(Soldier soldier : soldierList) {
				num += soldier.getNum();
			}
		}
		return num;
	}
	
	/**
	 * 获取行军负载
	 * @return
	 */
	public int getWeight(Player player) {
		int weight = 0;
		List<Soldier> soldiers = this.querySoldierList();
		for (Soldier soldier : soldiers) {
			weight += soldier.getNum() * soldier.getAttribute(player, AttributesEnum.WEIGHT);
		}
		return weight;
	}
	/**
	 * 返回加速时间 不是返回剩余行军时间
	 * @param percent
	 * @return
	 */
	public int speedUp(double percent) {
		int speedTime = Math.max(Double.valueOf((this.marchArrivalTime / 1000 - TimeUtils.getCurrentTime()) * percent).intValue(), 1);
		int marchTime = Math.max(Double.valueOf((this.marchArrivalTime / 1000 - TimeUtils.getCurrentTime()) * (1 - percent)).intValue(), 1);
		this.marchTime = marchTime;
		this.marchArrivalTime = TimeUtils.getCurrentTimeMillis() + marchTime * 1000;
		return speedTime;
	}
	
	/**
	 * 是否在采集中
	 * @return
	 */
	public boolean inExplorerTime() {
		if(marchState == MarchState.OCCUPY) {
			if(this.marchArrivalTime + exploreTime * 1000 > TimeUtils.getCurrentTimeMillis()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 部队采集资源最大数量
	 * @param player
	 * @param resourceType
	 * @return
	 */
	public int getMaxWeightNum(Player player, CurrencyEnum resourceType) {
		int weight = getWeight(player);
		int maxNum = MarchUtils.mathGiveNum(weight, resourceType);
		return maxNum;
	}

	@Override
	public Long getId() {
		return uid;
	}

	@Override
	public void setId(Long k) {
		this.uid = k;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getOwnerUid() {
		return ownerUid;
	}

	public void setOwnerUid(String ownerUid) {
		this.ownerUid = ownerUid;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(String allianceId) {
		this.allianceId = allianceId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public String getTargetUid() {
		return targetUid;
	}

	public void setTargetUid(String targetUid) {
		this.targetUid = targetUid;
	}

	public MarchState getMarchState() {
		return marchState;
	}

	public void setMarchState(MarchState marchState) {
		this.marchState = marchState;
	}

	public MarchType getMarchType() {
		return marchType;
	}

	public void setMarchType(MarchType marchType) {
		this.marchType = marchType;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getMarchStartTime() {
		return marchStartTime;
	}

	public void setMarchStartTime(long marchStartTime) {
		this.marchStartTime = marchStartTime;
	}

	public int getMarchTime() {
		return marchTime;
	}

	public void setMarchTime(int marchTime) {
		this.marchTime = marchTime;
	}

	public long getMarchArrivalTime() {
		return marchArrivalTime;
	}

	public void setMarchArrivalTime(long marchArrivalTime) {
		this.marchArrivalTime = marchArrivalTime;
	}

	public long getReturnStartTime() {
		return returnStartTime;
	}

	public void setReturnStartTime(long returnStartTime) {
		this.returnStartTime = returnStartTime;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getExploreTime() {
		return exploreTime;
	}

	public void setExploreTime(int exploreTime) {
		this.exploreTime = exploreTime;
	}

	public Object getAttach() {
		return attach;
	}

	public void setAttach(Object attach) {
		this.attach = attach;
	}

	public IMarchExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(IMarchExecutor executor) {
		this.executor = executor;
	}
	
	public int getOwnerPoint() {
		Player player  = InjectorUtil.getInjector().dbCacheService.get(Player.class, Long.valueOf(ownerUid));
		if(player != null) {
			return player.getSprite().getIndex();
		}
		return 0;
	}
	
	public Vector2Bean getDestination() {
		return WorldConverter.getVector2Bean(targetId);
	}
	
	public Vector2Bean getOwnerPosition() {
		return WorldConverter.getVector2Bean(getOwnerPoint());
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public WorldMarchSoldier getWorldMarchSoldier() {
		return worldMarchSoldier;
	}

	public void setWorldMarchSoldier(WorldMarchSoldier worldMarchSoldier) {
		this.worldMarchSoldier = worldMarchSoldier;
	}

	public int getExlplorerNum() {
		return exlplorerNum;
	}

	public void setExlplorerNum(int exlplorerNum) {
		this.exlplorerNum = exlplorerNum;
	}

	public long getOccupyTaskId() {
		return occupyTaskId;
	}

	public void setOccupyTaskId(long occupyTaskId) {
		this.occupyTaskId = occupyTaskId;
	}

	public long getExlporerTaskId() {
		return exlporerTaskId;
	}

	public void setExlporerTaskId(long exlporerTaskId) {
		this.exlporerTaskId = exlporerTaskId;
	}

	public CurrencyEnum getExplorerType() {
		return explorerType;
	}

	public void setExplorerType(CurrencyEnum explorerType) {
		this.explorerType = explorerType;
	}

	public int getTargetSubType() {
		return targetSubType;
	}

	public void setTargetSubType(int targetSubType) {
		this.targetSubType = targetSubType;
	}
	
	public WarResourceBean getWarResourceBean() {
		return warResourceBean;
	}

	public void setWarResourceBean(WarResourceBean warResourceBean) {
		this.warResourceBean = warResourceBean;
	}
	
	public long getBattleEndTime() {
		return battleEndTime;
	}

	public void setBattleEndTime(long battleEndTime) {
		this.battleEndTime = battleEndTime;
	}
	
	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public double getFinishOccupyRatio() {
		return finishOccupyRatio;
	}

	public void setFinishOccupyRatio(double finishOccupyRatio) {
		this.finishOccupyRatio = finishOccupyRatio;
	}
	
	public long getBattleStartTime() {
		return battleStartTime;
	}

	public void setBattleStartTime(long battleStartTime) {
		this.battleStartTime = battleStartTime;
	}
	
	public MarchFightState getMarchFight() {
		return marchFight;
	}

	public void setMarchFight(MarchFightState marchFight) {
		this.marchFight = marchFight;
	}

	/**
	 * 
	 * @return
	 */
	public boolean fight() {
		return this.battleEndTime > TimeUtils.getCurrentTimeMillis();
	}
	
	public boolean isSystemCreate() {
		return systemCreate;
	}

	public void setSystemCreate(boolean systemCreate) {
		this.systemCreate = systemCreate;
	}
	
	public EmailSignature getDefEmailSignature() {
		return defEmailSignature;
	}

	public void setDefEmailSignature(EmailSignature defEmailSignature) {
		this.defEmailSignature = defEmailSignature;
	}
	
	public CollectTimerTaskData getCollectTimerTaskData() {
		return collectTimerTaskData;
	}

	public void setCollectTimerTaskData(CollectTimerTaskData collectTimerTaskData) {
		this.collectTimerTaskData = collectTimerTaskData;
	}

	public JBaseData toJBaseData() {
		JBaseData jbaseData = new JBaseData();
		jbaseData.put("uid", uid);
		jbaseData.put("ownerUid", ownerUid);
		jbaseData.put("ownerName", ownerName);
		jbaseData.put("allianceId", allianceId);
		jbaseData.put("teamId", teamId);
		jbaseData.put("targetId", targetId);
		jbaseData.put("targetType", targetType);
		jbaseData.put("targetUid", targetUid);
		jbaseData.put("taskId", taskId);
		jbaseData.put("marchStartTime", marchStartTime);
		jbaseData.put("marchTime", marchTime);
		jbaseData.put("marchArrivalTime", marchArrivalTime);
		jbaseData.put("returnStartTime", returnStartTime);
		jbaseData.put("arrivalTime", arrivalTime);
		jbaseData.put("exploreTime", exploreTime);
		jbaseData.put("exlplorerNum", exlplorerNum);
		jbaseData.put("maxNum", maxNum);
		jbaseData.put("finishOccupyRatio", finishOccupyRatio);
		
		jbaseData.put("remainTime", remainTime);
		jbaseData.put("exlporerTaskId", exlporerTaskId);
		jbaseData.put("occupyTaskId", occupyTaskId);
		jbaseData.put("targetSubType", targetSubType);
		
		jbaseData.put("marchState", marchState.ordinal());
		jbaseData.put("marchType", marchType.ordinal());
		if(explorerType != null) {
			jbaseData.put("explorerType", explorerType.ordinal());
		}
		
		jbaseData.put("battleEndTime", battleEndTime);
		jbaseData.put("battleStartTime", battleStartTime);
		jbaseData.put("marchFight", marchFight.ordinal());
		
		// 行军状态
		if(worldMarchSoldier != null) {
			jbaseData.put("worldMarchSoldier", worldMarchSoldier.toJBaseData());
			if(attach != null) {
				jbaseData.put("attach", attach);
				jbaseData.put("clazz", attach.getClass().getName());
			}
		}
		
		jbaseData.put("resourceBean", JsonUtil.toJSON(warResourceBean));
		jbaseData.put("systemCreate", systemCreate);
		
		jbaseData.put("defEmailSignature", JsonUtil.toJSON(defEmailSignature));
		
		jbaseData.put("collectTimerTaskData", collectTimerTaskData);
		return jbaseData;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void fromJBaseData(JBaseData jBaseData) {
		this.uid = jBaseData.getLong("uid", 0);
		this.ownerUid = jBaseData.getString("ownerUid", "");
		this.ownerName = jBaseData.getString("ownerName", "");
		this.allianceId = jBaseData.getString("allianceId", "");
		this.teamId = jBaseData.getString("teamId", "");
		this.targetId = jBaseData.getInt("targetId", 0);
		this.targetType = jBaseData.getInt("targetType", 0);
		this.targetUid = jBaseData.getString("targetUid", "");
		this.taskId = jBaseData.getLong("taskId", 0);
		this.marchStartTime = jBaseData.getLong("marchStartTime", 0);
		this.marchTime = jBaseData.getInt("marchTime", 0);
		this.marchArrivalTime = jBaseData.getLong("marchArrivalTime", 0);
		this.returnStartTime = jBaseData.getLong("returnStartTime", 0);
		this.arrivalTime = jBaseData.getLong("arrivalTime", 0);
		this.exploreTime = jBaseData.getInt("exploreTime", 0);
		this.exlplorerNum = jBaseData.getInt("exlplorerNum", 0);
		this.maxNum = jBaseData.getInt("maxNum", 0);
		this.finishOccupyRatio = jBaseData.getDouble("finishOccupyRatio", 0);
		
		this.remainTime = jBaseData.getInt("remainTime", 0);
		this.exlporerTaskId = jBaseData.getLong("exlporerTaskId", 0);
		this.occupyTaskId = jBaseData.getLong("occupyTaskId", 0);
		this.targetSubType = jBaseData.getInt("targetSubType", 0);
		
		int marchstat = jBaseData.getInt("marchState", 0);
		this.marchState = EnumUtils.getEnum(MarchState.class, marchstat);
		
		int marchTyp = jBaseData.getInt("marchType", 0);
		this.marchType = EnumUtils.getEnum(MarchType.class, marchTyp);
		
		int explorerTyp = jBaseData.getInt("explorerType", 0);
		this.explorerType = EnumUtils.getEnum(CurrencyEnum.class, explorerTyp);
		this.battleEndTime = jBaseData.getLong("battleEndTime", 0);
		this.battleStartTime = jBaseData.getLong("battleStartTime", 0);
		
		int marchFight = jBaseData.getInt("marchFight", 0);
		this.marchFight = EnumUtils.getEnum(MarchFightState.class, marchFight);
		
		this.systemCreate = jBaseData.getBoolean("systemCreate", false);
		
		JBaseData collectJBaseData = jBaseData.getBaseData("collectTimerTaskData");
		if(collectJBaseData != null) {
			CollectTimerTaskData collectTimerTaskData = new CollectTimerTaskData();
			collectTimerTaskData.fromJBaseData(collectJBaseData);
			this.collectTimerTaskData = collectTimerTaskData;
		}
	
		
		JBaseData worldMarchBaseData = jBaseData.getBaseData("worldMarchSoldier");
		if(worldMarchBaseData != null) {
			WorldMarchSoldier worldMarchSoldier = new WorldMarchSoldier();
			worldMarchSoldier.fromJBaseData(worldMarchBaseData);
			this.worldMarchSoldier = worldMarchSoldier;
		}

		
		String paramStr = jBaseData.getString("param", "");
		if(!StringUtils.isEmpty(paramStr)) {
			String clazzStr = jBaseData.getString("clazz", "");
			Class clazz = ClassNameUtils.getClass(clazzStr);
			this.attach = JsonUtil.fromJSON(clazzStr, clazz);
		}
		
		// 贸易资源
		String warResourceBeanStr = jBaseData.getString("warResourceBean", "");
		if(!StringUtils.isEmpty(warResourceBeanStr)) {
			this.attach = JsonUtil.fromJSON(warResourceBeanStr, WarResourceBean.class);
		}
		
		// 防守方签名
		String defEmailString = jBaseData.getString("defEmailSignature", "");
		if(!StringUtils.isEmpty(defEmailString)) {
			this.defEmailSignature = JsonUtil.fromJSON(defEmailString, EmailSignature.class);
		}
	}
	
}
