package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.awardcenter.entity.AwardData;
import com.xgame.logic.server.game.commander.entity.CommanderData;
import com.xgame.logic.server.game.copy.enity.MainCopyInfo;
import com.xgame.logic.server.game.country.entity.BaseCountry;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierData;
import com.xgame.logic.server.game.country.structs.build.tach.data.TechData;
import com.xgame.logic.server.game.customweanpon.entity.PlayerDesignMap;
import com.xgame.logic.server.game.equipment.entity.EquipmentDataManager;
import com.xgame.logic.server.game.playerattribute.entity.PlayerAttribute;
import com.xgame.logic.server.game.radar.entity.RadaData;
import com.xgame.logic.server.game.task.enity.ActiveInfo;
import com.xgame.logic.server.game.task.enity.TaskInfo;

/**
 * Created by vyang on 6/21/16.
 */
public class RoleInfo implements Serializable, JBaseTransform {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	@Tag(1)
	private RoleBasics basics = new RoleBasics();

	@Tag(2)
	private RoleCurrency currency = new RoleCurrency();

	@Tag(3)
	private Map<Long, Long> timerTaskMap = new ConcurrentHashMap<Long, Long>();// 计划任务列表

	@Tag(4)
	private PlayerBag playerBag = new PlayerBag(); // 背包

	@Tag(6)
	private BaseCountry baseCountry = new BaseCountry();
	
	@Tag(8)
	private SoldierData soldierData = new SoldierData();
	
	@Tag(10)
	private EquipmentDataManager equipmentDataManager = new EquipmentDataManager();
	
	@Tag(12)
	private VipInfo vipInfo = new VipInfo();	
	// 活跃
	@Tag(13)
	private ActiveInfo activeInfo = new ActiveInfo();
	
	@Tag(16)
	private CommanderData commanderData = new CommanderData();

	@Tag(19)
	private PlayerBuffData playerBuffData = new PlayerBuffData();
	
	@Tag(20)
	private TechData techs = new TechData();
	
	@Tag(24)
	private AwardData awards = new AwardData();
	
	@Tag(25)
	private RadaData radarData = new RadaData();
	
	// 世界收藏
	@Tag(26)
	private PlayerCollect playerCollect = new PlayerCollect();
	
	// 玩家商城信息
	@Tag(27)
	private PlayerShop playerShop = new PlayerShop();
	
	// 玩家任务
	@Tag(29)
	private TaskInfo taskInfo = new TaskInfo();
	
	// 玩家主线副本信息
	@Tag(30)
	private MainCopyInfo mainCopyInfo = new MainCopyInfo();
	
	// 玩家属性
	@Tag(31)
	private PlayerAttribute playerAttribute = new PlayerAttribute();
	
	/**
	 * 玩家待刷新次数
	 */
	private PlayerDayRefresh playerInfo = new PlayerDayRefresh();
	
	/**
	 * 玩家领地信息
	 */
	private PlayerTerritory playerTerritory = new PlayerTerritory();
	
	/**
	 * 联盟战队信息
	 */
	private PlayerAllianceBattleTeam playerAllianceBattleTeam = new PlayerAllianceBattleTeam();
	
	/**
	 * 图纸信息
	 */
	private PlayerDesignMap playerDesignMap = new PlayerDesignMap();
	
	public RoleBasics getBasics() {
		return basics;
	}

	public void setBasics(RoleBasics basics) {
		this.basics = basics;
	}

	public RoleCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(RoleCurrency currency) {
		this.currency = currency;
	}

	public Map<Long, Long> getTimerTaskMap() {
		return timerTaskMap;
	}

	public void setTimerTaskMap(Map<Long, Long> timerTaskMap) {
		this.timerTaskMap = timerTaskMap;
	}

	public PlayerBag getPlayerBag() {
		return playerBag;
	}

	public void setPlayerBag(PlayerBag playerBag) {
		this.playerBag = playerBag;
	}

	public BaseCountry getBaseCountry() {
		return baseCountry;
	}

	public void setBaseCountry(BaseCountry baseCountry) {
		this.baseCountry = baseCountry;
	}

//	public GameAttribute getGameAttribute() {
//		return gameAttribute;
//	}
//
//	public void setGameAttribute(GameAttribute gameAttribute) {
//		this.gameAttribute = gameAttribute;
//	}

	public SoldierData getSoldierData() {
		return soldierData;
	}

	public void setSoldierData(SoldierData soldierData) {
		this.soldierData = soldierData;
	}

	public EquipmentDataManager getEquipmentDataManager() {
		return equipmentDataManager;
	}

	public void setEquipmentDataManager(EquipmentDataManager equipmentDataManager) {
		this.equipmentDataManager = equipmentDataManager;
	}

	public VipInfo getVipInfo() {
		return vipInfo;
	}

	public void setVipInfo(VipInfo vipInfo) {
		this.vipInfo = vipInfo;
	}

	public ActiveInfo getActiveInfo() {
		return activeInfo;
	}

	public void setActiveInfo(ActiveInfo activeInfo) {
		this.activeInfo = activeInfo;
	}

	public CommanderData getCommanderData() {
		return commanderData;
	}

	public void setCommanderData(CommanderData commanderData) {
		this.commanderData = commanderData;
	}

//	public GameDocument getDocument() {
//		return document;
//	}
//
//	public void setDocument(GameDocument document) {
//		this.document = document;
//	}

	public PlayerBuffData getPlayerBuffData() {
		return playerBuffData;
	}

	public void setPlayerBuffData(PlayerBuffData playerBuffData) {
		this.playerBuffData = playerBuffData;
	}

	public TechData getTechs() {
		return techs;
	}

	public void setTechs(TechData techs) {
		this.techs = techs;
	}

	public AwardData getAwards() {
		return awards;
	}

	public void setAwards(AwardData awards) {
		this.awards = awards;
	}

	public RadaData getRadarData() {
		return radarData;
	}

	public void setRadarData(RadaData radarData) {
		this.radarData = radarData;
	}

	public PlayerCollect getPlayerCollect() {
		return playerCollect;
	}

	public void setPlayerCollect(PlayerCollect playerCollect) {
		this.playerCollect = playerCollect;
	}

	public PlayerShop getPlayerShop() {
		return playerShop;
	}

	public void setPlayerShop(PlayerShop playerShop) {
		this.playerShop = playerShop;
	}

	/**
	 * 刷新玩家信息
	 * @return
	 */
	public PlayerDayRefresh getPlayerInfo() {
		playerInfo.refresh();
		return playerInfo;
	}
	

	public void setPlayerInfo(PlayerDayRefresh playerInfo) {
		this.playerInfo = playerInfo;
	}
	
	public PlayerTerritory getPlayerTerritory() {
		return playerTerritory;
	}

	public void setPlayerTerritory(PlayerTerritory playerTerritory) {
		this.playerTerritory = playerTerritory;
	}
	
	public PlayerAllianceBattleTeam getPlayerAllianceBattleTeam() {
		return playerAllianceBattleTeam;
	}

	public void setPlayerAllianceBattleTeam(
			PlayerAllianceBattleTeam playerAllianceBattleTeam) {
		this.playerAllianceBattleTeam = playerAllianceBattleTeam;
	}

//	public PlayerAttributeObject queryPlayerAttributeObject() {
//		AttributeObject attributeObject = getGameAttribute().attributeObjectInfo(AttributeNodeEnum.PLAYER.ordinal(), 0);
//		if(attributeObject != null) {
//			return (PlayerAttributeObject)attributeObject;
//		}
//		return null;
//	}

	public TaskInfo getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(TaskInfo taskInfo) {
		this.taskInfo = taskInfo;
	}

	public MainCopyInfo getMainCopyInfo() {
		return mainCopyInfo;
	}

	public void setMainCopyInfo(MainCopyInfo mainCopyInfo) {
		this.mainCopyInfo = mainCopyInfo;
	}
	
	public PlayerDesignMap getPlayerDesignMap() {
		return playerDesignMap;
	}

	public void setPlayerDesignMap(PlayerDesignMap playerDesignMap) {
		this.playerDesignMap = playerDesignMap;
	}
	
	
	
	public PlayerAttribute getPlayerAttribute() {
		return playerAttribute;
	}

	public void setPlayerAttribute(PlayerAttribute playerAttribute) {
		this.playerAttribute = playerAttribute;
	}
	
	

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData ();
		jBaseData.put("basics", basics.toJBaseData());
		jBaseData.put("currency", currency.toJBaseData());
		jBaseData.put("timerTaskMap", JsonUtil.toJSON(timerTaskMap));
		
		jBaseData.put("playerBag", playerBag.toJBaseData());
		jBaseData.put("baseCountry", baseCountry.toJBaseData());
//		jBaseData.put("gameAttribute", gameAttribute.toJBaseData());
		
		jBaseData.put("soldierData", soldierData.toJBaseData());
		jBaseData.put("equipmentDataManager", equipmentDataManager.toJBaseData());
		jBaseData.put("vipInfo", vipInfo.toJBaseData());
		
		jBaseData.put("activeInfo", activeInfo.toJBaseData());
		jBaseData.put("commanderData", commanderData.toJBaseData());
//		jBaseData.put("document", document.toJBaseData());
		
		jBaseData.put("playerBuffData", playerBuffData.toJBaseData());
		jBaseData.put("techs", techs.toJBaseData());
		jBaseData.put("awards", awards.toJBaseData());
		
		jBaseData.put("radarData", radarData.toJBaseData());
		jBaseData.put("playerCollect", playerCollect.toJBaseData());
		jBaseData.put("playerShop", playerShop.toJBaseData());
		
		jBaseData.put("taskInfo", taskInfo.toJBaseData());
		jBaseData.put("playerInfo", playerInfo.toJBaseData());
		
		jBaseData.put("playerTerritory", playerTerritory.toJBaseData());
		jBaseData.put("playerAllianceBattleTeam", playerAllianceBattleTeam.toJBaseData());
		jBaseData.put("playerDesignMap", playerDesignMap.toJBaseData());
		
		jBaseData.put("playerAttribute", playerAttribute.toJBaseData());
		
		jBaseData.put("mainCopyInfo", mainCopyInfo.toJBaseData());
		
		return jBaseData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		RoleBasics basics = new RoleBasics();
		JBaseData basicJBaseData = jBaseData.getBaseData("basics");
		basics.fromJBaseData(basicJBaseData);
		this.basics = basics;
		
		RoleCurrency roleCurrency = new RoleCurrency();
		JBaseData roleCurrencyJBaseData = jBaseData.getBaseData("currency");
		roleCurrency.fromJBaseData(roleCurrencyJBaseData);
		this.currency = roleCurrency;
		
		String timerTaskMapString = jBaseData.getString("timerTaskMap", "");
		if(!StringUtils.isBlank(timerTaskMapString)) {
			Map<Long, Long> timerTaskMap = JsonUtil.fromJSON(timerTaskMapString, Map.class);
			this.timerTaskMap = timerTaskMap;
		}
		
		PlayerBag playerBag = new PlayerBag();
		JBaseData playerBagJBaseData = jBaseData.getBaseData("playerBag");
		playerBag.fromJBaseData(playerBagJBaseData);
		this.playerBag = playerBag;
		
		BaseCountry baseCountry = new BaseCountry();
		JBaseData baseCountryJBaseData = jBaseData.getBaseData("baseCountry");
		baseCountry.fromJBaseData(baseCountryJBaseData);
		this.baseCountry = baseCountry;
		
//		GameAttribute gameAttribute = new GameAttribute();
//		JBaseData gameAttributeJBaseData = jBaseData.getBaseData("gameAttribute");
//		gameAttribute.fromJBaseData(gameAttributeJBaseData);
//		this.gameAttribute = gameAttribute;
		
		SoldierData soldierData = new SoldierData();
		JBaseData soldierDataJBaseData = jBaseData.getBaseData("soldierData");
		soldierData.fromJBaseData(soldierDataJBaseData);
		this.soldierData = soldierData;
		
		EquipmentDataManager equipmentDataManager = new EquipmentDataManager();
		JBaseData equipmentDataManagerJBaseData = jBaseData.getBaseData("equipmentDataManager");
		equipmentDataManager.fromJBaseData(equipmentDataManagerJBaseData);
		this.equipmentDataManager = equipmentDataManager;
		
		VipInfo vipInfo = new VipInfo();
		JBaseData vipInfoJBaseData = jBaseData.getBaseData("vipInfo");
		vipInfo.fromJBaseData(vipInfoJBaseData);
		this.vipInfo = vipInfo;
		
		ActiveInfo activeInfo = new ActiveInfo();
		JBaseData activeInfoJBaseData = jBaseData.getBaseData("activeInfo");
		activeInfo.fromJBaseData(activeInfoJBaseData);
		this.activeInfo = activeInfo;
		
		CommanderData commanderData = new CommanderData();
		JBaseData commanderDataJBaseData = jBaseData.getBaseData("commanderData");
		commanderData.fromJBaseData(commanderDataJBaseData);
		this.commanderData = commanderData;
		
//		GameDocument gameDocument = new GameDocument();
//		JBaseData gameDocumentJBaseData = jBaseData.getBaseData("document");
//		gameDocument.fromJBaseData(gameDocumentJBaseData);
//		this.document = gameDocument;
		
		PlayerBuffData playerBuffData = new PlayerBuffData();
		JBaseData playerBuffDataJBaseData = jBaseData.getBaseData("playerBuffData");
		playerBuffData.fromJBaseData(playerBuffDataJBaseData);
		this.playerBuffData = playerBuffData;
		
		TechData techData = new TechData();
		JBaseData techDataJBaseData = jBaseData.getBaseData("techs");
		techData.fromJBaseData(techDataJBaseData);
		this.techs = techData;
		
		AwardData awardData = new AwardData();
		JBaseData awardDataJBaseData = jBaseData.getBaseData("awards");
		awardData.fromJBaseData(awardDataJBaseData);
		this.awards = awardData;
		
		RadaData radaData = new RadaData();
		JBaseData radaDataJBaseData = jBaseData.getBaseData("radarData");
		radaData.fromJBaseData(radaDataJBaseData);
		this.radarData = radaData;
		
		PlayerCollect playerCollect = new PlayerCollect();
		JBaseData playerCollectJBaseData = jBaseData.getBaseData("playerCollect");
		playerCollect.fromJBaseData(playerCollectJBaseData);
		this.playerCollect = playerCollect;
		
		PlayerShop playerShop = new PlayerShop();
		JBaseData playerShopJBaseData = jBaseData.getBaseData("playerShop");
		playerShop.fromJBaseData(playerShopJBaseData);
		this.playerShop = playerShop;
		
		TaskInfo taskInfo = new TaskInfo();
		JBaseData taskInfoJBaseData = jBaseData.getBaseData("taskInfo");
		taskInfo.fromJBaseData(taskInfoJBaseData);
		this.taskInfo = taskInfo;
		
		PlayerDayRefresh playerInfo = new PlayerDayRefresh();
		JBaseData playerInfoJBaseData = jBaseData.getBaseData("playerInfo");
		playerInfo.fromJBaseData(playerInfoJBaseData);
		this.playerInfo = playerInfo;
	
		PlayerTerritory playerTerritory = new PlayerTerritory();
		JBaseData playerTerritoryJBaseData = jBaseData.getBaseData("playerTerritory");
		playerTerritory.fromJBaseData(playerTerritoryJBaseData);
		this.playerTerritory = playerTerritory;
		
		PlayerAllianceBattleTeam playerAllianceBattleTeam = new PlayerAllianceBattleTeam();
		JBaseData playerAllianceBattleTeamInfoJBaseData = jBaseData.getBaseData("playerAllianceBattleTeam");
		playerAllianceBattleTeam.fromJBaseData(playerAllianceBattleTeamInfoJBaseData);
		this.playerAllianceBattleTeam = playerAllianceBattleTeam;
		
		PlayerDesignMap playerDesignMap = new PlayerDesignMap();
		JBaseData playerDesignMapJBaseData = jBaseData.getBaseData("playerDesignMap");
		playerDesignMap.fromJBaseData(playerDesignMapJBaseData);
		this.playerDesignMap = playerDesignMap;
		
		PlayerAttribute playerAttribute = new PlayerAttribute();
		JBaseData playerAttributeJBaseData = jBaseData.getBaseData("playerAttribute");
		playerAttribute.fromJBaseData(playerAttributeJBaseData);
		this.playerAttribute = playerAttribute;
		
		MainCopyInfo mainCopyInfo = new MainCopyInfo();
		JBaseData mainCopyInfoJBaseData = jBaseData.getBaseData("mainCopyInfo");
		mainCopyInfo.fromJBaseData(mainCopyInfoJBaseData);
	}
}
