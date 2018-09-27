package com.xgame.logic.server.game.playerattribute.fightpower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.equipment.EquipmentPir;
import com.xgame.config.equipment.EquipmentPirFactory;
import com.xgame.config.exp.ExpPir;
import com.xgame.config.exp.ExpPirFactory;
import com.xgame.config.marchingTroops.MarchingTroopsPir;
import com.xgame.config.marchingTroops.MarchingTroopsPirFactory;
import com.xgame.config.science.SciencePir;
import com.xgame.config.science.SciencePirFactory;
import com.xgame.config.talent.TalentPir;
import com.xgame.config.talent.TalentPirFactory;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.commander.bean.StatisticIntegerPro;
import com.xgame.logic.server.game.commander.bean.StatisticLongPro;
import com.xgame.logic.server.game.commander.entity.TalentData;
import com.xgame.logic.server.game.commander.message.ResStatisticMessage;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.structs.build.tach.data.Tech;
import com.xgame.logic.server.game.equipment.entity.Equipment;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.PlayerAttributeManager;
import com.xgame.logic.server.game.playerattribute.entity.dto.SoldierFilterResult;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.FightPowerRefreshEvent;
import com.xgame.logic.server.game.soldier.constant.SoldierConstant;
import com.xgame.logic.server.game.soldier.entity.Soldier;

/**
 *文档值的提取计算工具
 *2016-12-08  10:36:40
 *@author ye.yuan
 *
 */
public enum FightPowerKit {
	/**
	 * 战力
	 */
	/**
	 * 总战力
	 */
	FINAL_POWER(101,0,Long.class,FightPowerKit.POWER){
		
		public Number getValue(long playerId){
			return  COMMANDER_TALENT_POWER.getValue(playerId).longValue()+
					COMMANDER_CPOINT_POWER.getValue(playerId).longValue()+
					COMMANDER_LEVEL_POWER.getValue(playerId).longValue()+
					BUILD_POWER.getValue(playerId).longValue()+
					TECH_POWER.getValue(playerId).longValue()+
					CAMP_POWER.getValue(playerId).longValue()+
					PARTY_SOLDER_POWER.getValue(playerId).longValue()+
					EQUIT_POWER.getValue(playerId).longValue();
		}
	},
	/**
	 * 指挥官战力
	 */
	COMMANDER_POWER(102,0,Long.class,FightPowerKit.POWER){
		public Number getValue(long playerId){
			return COMMANDER_TALENT_POWER.getValue(playerId).longValue()+
			COMMANDER_CPOINT_POWER.getValue(playerId).longValue()+
			COMMANDER_LEVEL_POWER.getValue(playerId).longValue()
			;
		}
	},
	/**
	 * 天赋战力
	 */
	COMMANDER_TALENT_POWER(102,1,Long.class,FightPowerKit.POWER){
		 @Override
		 public void math(Player player,GameLogSource gameLogSource) {
			long fightPower=0;
			long oldFightPower = getValue(player.getId()).longValue();
			Iterator<TalentData> iterator = player.getCommanderManager().getDefaultTalentPage().getTalents().values().iterator();
			while (iterator.hasNext()) {
				TalentData t = iterator.next();
				TalentPir c = TalentPirFactory.get(t.getSid());
				Map<Integer, Integer> bonus = c.getGs_bonus();
				Integer integer = bonus.get(t.getLevel());
				if(integer!=null){
					fightPower+=integer;
				}
			}
			InjectorUtil.getInjector().documentSystem.reset(player.getId(), COMMANDER_TALENT_POWER, fightPower);
			EventBus.getSelf().fireEvent(new FightPowerRefreshEvent(player,EventTypeConst.EVENT_REFRESH_FIGHTPOWER,oldFightPower, fightPower,gameLogSource));
			CurrencyUtil.send(player);
		}
		
	},
	/**
	 * 统率力战力
	 */
	COMMANDER_CPOINT_POWER(102,2,Long.class,FightPowerKit.POWER) {
		 @Override
		 public void math(Player player,GameLogSource gameLogSource) {
			long oldFightPower = getValue(player.getId()).longValue();
			MarchingTroopsPir configModel = MarchingTroopsPirFactory.get(player.getCommanderManager().getCommanderData().getcPoint());
			long fightPower = 0;
			if(configModel!=null){
				InjectorUtil.getInjector().documentSystem.reset(player.getId(), COMMANDER_CPOINT_POWER, configModel.getGs_bonus());
				fightPower = configModel.getGs_bonus();
			}
//			COMMANDER_POWER.send(player);
			EventBus.getSelf().fireEvent(new FightPowerRefreshEvent(player,EventTypeConst.EVENT_REFRESH_FIGHTPOWER,oldFightPower, fightPower,gameLogSource));
			CurrencyUtil.send(player);
		}
	},
	/**
	 * 等级战力
	 */
	COMMANDER_LEVEL_POWER(102,3,Long.class,FightPowerKit.POWER){
		 @Override
		 public void math(Player player,GameLogSource gameLogSource) {
			long oldFightPower = getValue(player.getId()).longValue();
			ExpPir configModel = ExpPirFactory.get(player.getCommanderManager().getCommanderData().getLevel());
			long fightPower = 0;
			if(configModel!=null){
				InjectorUtil.getInjector().documentSystem.reset(player.getId(), COMMANDER_LEVEL_POWER, configModel.getGs_bonus());
				fightPower = configModel.getGs_bonus();
			}
			EventBus.getSelf().fireEvent(new FightPowerRefreshEvent(player,EventTypeConst.EVENT_REFRESH_FIGHTPOWER,oldFightPower, fightPower,gameLogSource));
			CurrencyUtil.send(player);
		}
	},
	/**
	 * 建筑战力
	 */
	BUILD_POWER(103,0,Long.class,FightPowerKit.POWER){
		 @Override
		 public void math(Player player,GameLogSource gameLogSource) {
			long oldFightPower = getValue(player.getId()).longValue();
			long power=0;
			Iterator<CountryBuild> iterator = player.roleInfo().getBaseCountry().getAllBuild().values().iterator();
			while (iterator.hasNext()) {
				CountryBuild countryBuild = (CountryBuild) iterator.next();
				BuildingPir buildingPir = BuildingPirFactory.get(countryBuild.getSid());
				if(buildingPir!=null){
					Map<Integer, Integer> map = buildingPir.getStrength();
					if(map!=null&&map.containsKey(countryBuild.getLevel())){
						power += map.get(countryBuild.getLevel());
					}
				}
			}
			
			InjectorUtil.getInjector().documentSystem.reset(player.getId(), BUILD_POWER, power);
			EventBus.getSelf().fireEvent(new FightPowerRefreshEvent(player,EventTypeConst.EVENT_REFRESH_FIGHTPOWER,oldFightPower, power,gameLogSource));
			CurrencyUtil.send(player);
		}
	},
	/**
	 * 科研战力
	 */
	TECH_POWER(104,0,Long.class,FightPowerKit.POWER) {
		 @Override
		 public void math(Player player,GameLogSource gameLogSource) {
			long oldFightPower = getValue(player.getId()).longValue();
			long f=0;
			Iterator<Tech> iterator = player.roleInfo().getTechs().getTechs().values().iterator();
			while (iterator.hasNext()) {
				Tech tech = (Tech) iterator.next();
				SciencePir configModel = SciencePirFactory.get(tech.getId());
				if(configModel!=null){
					Map<Integer, Integer> map = configModel.getStrength();
					if(map!=null&&map.containsKey(tech.getLevel())){
						f+=map.get(tech.getLevel());
					}
				}
			}
			InjectorUtil.getInjector().documentSystem.reset(player.getId(), TECH_POWER, f);
			EventBus.getSelf().fireEvent(new FightPowerRefreshEvent(player,EventTypeConst.EVENT_REFRESH_FIGHTPOWER,oldFightPower, f,gameLogSource));
			CurrencyUtil.send(player);
		}
		
	},
	
	/**
	 * 士兵战力
	 */
	CAMP_POWER(105,0,Long.class,FightPowerKit.POWER) {
		 @Override
		 public void math(Player player,GameLogSource gameLogSource) {
			long oldFightPower = getValue(player.getId()).longValue();
			Map<Long,Integer> soldierNumMap = new HashMap<>();//兵种数量
			Iterator<Soldier> iterator = player.roleInfo().getSoldierData().getSoldiers().values().iterator();
			while (iterator.hasNext()) {
				Soldier soldier = (Soldier) iterator.next();
				if(soldier != null) {
					int totalNum = player.roleInfo().getSoldierData().getDefenseSoldierNum(soldier.getSoldierId()) + soldier.getNum() + soldier.getMarchNum() + soldier.getPveNum();
					soldierNumMap.put(soldier.getSoldierId(), totalNum);
				}
			}
			long totalPower = 0;
			int maxSetOffSoldierNum = PlayerAttributeManager.get().singleMaxSetOffSoldierNum(player.getId());
			for(int cell = 1;cell <= SoldierConstant.MAX_MARCH_CELL;cell++){
				SoldierFilterResult result = soldierFilter(player,soldierNumMap,maxSetOffSoldierNum);
				if(result != null){
					totalPower += result.getTotalFightPower();
					soldierNumMap.put(result.getSoldierId(), soldierNumMap.get(result.getSoldierId()) - result.getNum());
				}
			}
			
			InjectorUtil.getInjector().documentSystem.reset(player.getId(), CAMP_POWER, totalPower);
			EventBus.getSelf().fireEvent(new FightPowerRefreshEvent(player,EventTypeConst.EVENT_REFRESH_FIGHTPOWER,oldFightPower, totalPower,gameLogSource));
		}
		
	},
	
	
	/**
	 * 联盟驻军战力
	 */
	PARTY_SOLDER_POWER(107,0,Long.class,FightPowerKit.POWER) {
		
	},
	/**
	 * 植入体战力
	 */
	EQUIT_POWER(108,0,Long.class,FightPowerKit.POWER) {
		
		 @Override
		 public void math(Player player,GameLogSource gameLogSource) {
			long oldFightPower = getValue(player.getId()).longValue();
			long fightFight = 0;
			Iterator<Entry<Integer, Long>> iterator = player.getCommanderManager().getCommanderData().getEquits().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Integer, Long> next = iterator.next();
				Equipment playerEquipment = player.getEquipmentManager().getPlayerEquipment(next.getValue());
				if(playerEquipment!=null){
					EquipmentPir oldConfigModel = EquipmentPirFactory.get(playerEquipment.getEquipmentId());
					if(oldConfigModel!=null){
						fightFight += oldConfigModel.getGs_bonus();
					}
				}
			}
			InjectorUtil.getInjector().documentSystem.reset(player.getId(), EQUIT_POWER, fightFight);
			EventBus.getSelf().fireEvent(new FightPowerRefreshEvent(player,EventTypeConst.EVENT_REFRESH_FIGHTPOWER,oldFightPower, fightFight,gameLogSource));
			CurrencyUtil.send(player);
		}
	},
	/**
	 * 战斗状态
	 */
	/**
	 * 战斗胜利次数
	 */
	FIGHT_WINNER_COUNT(201,0,Integer.class,FightPowerKit.BATTLE),
	/**
	 * 战斗失败次数
	 */
	FIGHT_FAIL_COUNT(202,0,Integer.class,FightPowerKit.BATTLE),
	/**
	 * 胜率
	 */
	WINNER_ODDS(203,0,Integer.class,FightPowerKit.BATTLE),
	/**
	 * 侦查次数
	 */
	INVESTIGATE_COUNT(204,0,Integer.class,FightPowerKit.BATTLE),
	/**
	 * 消灭部队数量
	 */
	KILL_SOLDIER_NUM(205,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 死亡部队数量
	 */
	BE_KILL_SOLDIER_NUM(206,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 修理部队数量
	 */
	MOD_SOLDIER_NUM(207,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 抓捕指挥官次数
	 */
	CATCH_COMMANDER_NUM(208,0,Integer.class,FightPowerKit.BATTLE),
	/**
	 * 指挥官被捕次数
	 */
	BE_CATCH_COMMANDER_NUM(209,0,Integer.class,FightPowerKit.BATTLE),
	/**
	 * 资源相关
	 */
	/**
	 * 采集黄金
	 */
	READ_GOLD(301,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 采集稀土
	 */
	READ_RATE(302,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 采集石油
	 */
	READ_OIL(303,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 采集钢铁
	 */
	READ_STEEL(304,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 贸易黄金
	 */
	TRADE_GOLD(305,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 贸易稀土
	 */
	TRADE_RATE(306,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 贸易石油
	 */
	TRADE_OIL(307,0,Long.class,FightPowerKit.BATTLE),
	/**
	 * 贸易钢铁
	 */
	TRADE_STEEL(308,0,Long.class,FightPowerKit.BATTLE),
	
	;
	/**
	 * 文档配置表id
	 */
	private int row;
	
	/**
	 * 这条文档下有几个独立的值  (即列)
	 */
	private int column;
	
	/**
	 * 根据行列生成的文档存储id 根据这个id对文档进行持久化存取
	 */
	private int id;
	
	/**
	 * 大类型  
	 */
	private int type;
	
	
	/**
	 * 文档值类型,用于发送消息的时候确定是int还是long 以便用对应的数据结构发消息 减少消息大小
	 */
	Class<?> valueClass;
	
	/**
	 * 自定义参数
	 */
	private Object param;
	
	FightPowerKit(int row,int column,Class<?> valueClass,int type) {
		this.row=row;
		this.column=column;
		this.id=Integer.parseInt(row+""+column);
		this.valueClass=valueClass;
		this.type=type;
	}
	
	FightPowerKit(int row,int column,Class<?> valueClass,int type,Object param){
		this.row=row;
		this.column=column;
		this.id=Integer.parseInt(row+""+column);
		this.valueClass=valueClass;
		this.type=type;
		this.param=param;
	}
	
	/**
	 * 更新这条文档到客户端
	 * @param player
	 */
	public void send(Player player){
		ResStatisticMessage statisticMessage = new ResStatisticMessage(); 
		addStatisticPro(player, statisticMessage);
		player.send(statisticMessage);
	}
	
	/**
	 * 没更改才更新客户端
	 * @param player
	 * @param oldValue 老值
	 */
	public void send(Player player,Number oldValue){
		Number value = getValue(player.getId());
		if(!value.equals(oldValue)){
			ResStatisticMessage statisticMessage = new ResStatisticMessage(); 
			addStatisticPro(player, statisticMessage);
			player.send(statisticMessage);
		}
	}
	
	
	/**
	 * 根据类型添加pro到消息集合里
	 * @param player
	 * @param statisticMessage
	 */
	public void addStatisticPro(Player player,ResStatisticMessage statisticMessage) {
		statisticMessage.statisticLongs.add(toLongPro(player));
	}
	
	/**
	 * 变成long消息pro
	 * @param player
	 * @return
	 */
	public StatisticLongPro toLongPro(Player player){
		StatisticLongPro longPro = new StatisticLongPro();
		longPro.id = row;
		longPro.value = getValue(player.getId()).longValue();
		return longPro;
	}
	
	
	/**
	 * 发送float pro
	 * @param player
	 * @return
	 */
	public StatisticIntegerPro toIntPro(Player player){
		StatisticIntegerPro longPro = new StatisticIntegerPro();
		longPro.id = row;
		longPro.value = getValue(player.getId()).intValue();
		return longPro;
	}
	
	/**
	 * 快捷获取文档值
	 * @param player
	 * @param util
	 * @return
	 */
	public Number getValue(long playerId){
		Number number = InjectorUtil.getInjector().documentSystem.getOrCreateValue(playerId, this);
		if(number==null){
			number = 0;
		}
		return number;
	}
	
	
	/**
	 * 被重写的方法 
	 * @param player
	 * @param param
	 */
	public void math(Player player,GameLogSource gameLogSource,Object ... param){
		
	}
	
	/**
	 * 被重写的方法 如果不需要自带参数可不传
	 * @param player
	 * @param param
	 */
	public void math(Player player,GameLogSource gameLogSource){
		
	}
	
	
	/* static */
	
	
	/**
	 * 发送全部文档到客户端
	 * @param player
	 */
	public static void sendAllFightPower(Player player){
		ResStatisticMessage statisticMessage = new ResStatisticMessage();
		Iterator<FightPowerKit> iterator = valids.values().iterator();
		while (iterator.hasNext()) {
			FightPowerKit documentUtil = (FightPowerKit) iterator.next();
			documentUtil.addStatisticPro(player,statisticMessage);
		}
		
		player.send(statisticMessage);
		
	}
	
	private static final Map<Integer,FightPowerKit> valids = new HashMap<>();
	
	
	private static final Map<Integer,List<FightPowerKit>> documentArrray = new HashMap<>();
	
	
	public static final int POWER = 1, BATTLE = 2, ATTRIBUTE = 3;
	
	
	static {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].column == 0) {
				valids.put(values()[i].row, values()[i]);
			}
		}
		FightPowerKit[] values = values();
		for (int i = 0; i < values.length; i++) {
			List<FightPowerKit> list = documentArrray.get(values[i].type);
			if (list == null) {
				documentArrray.put(values[i].type, list = new ArrayList<>());
			}
			list.add(values[i]);
		}
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Class<?> getValueClass() {
		return valueClass;
	}

	public void setValueClass(Class<?> valueClass) {
		this.valueClass = valueClass;
	}

	public static Map<Integer, FightPowerKit> getValids() {
		return valids;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static Map<Integer, List<FightPowerKit>> getDocumentarrray() {
		return documentArrray;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParam() {
		return (T)param;
	}
	
	/**
	 * 筛选出可出征士兵最大战力方案
	 * @param player
	 * @param soldierNumMap key：soldierId,value:soldierNum
	 * @param maxSetOffSoldierNum 出兵上限
	 * @return
	 */
	public static SoldierFilterResult soldierFilter(Player player,Map<Long,Integer> soldierNumMap,int maxSetOffSoldierNum){
		long playerId = player.getId();
		Map<Long,Long> singlePowerMap = new HashMap<>();//单兵战力
		for(Long soldierId : soldierNumMap.keySet()){
			Soldier soldier = player.roleInfo().getSoldierData().getSoldiers().get(soldierId);
			singlePowerMap.put(soldier.getSoldierId(), soldier.getSinglePower(playerId));
		}
		long maxPower = 0;
		long soldierId = 0;
		int soldierNum = 0;
		for(Entry<Long,Integer> entry : soldierNumMap.entrySet()){
			long currId = entry.getKey();
			int currNum = entry.getValue();
			int num = currNum <= maxSetOffSoldierNum ? currNum : maxSetOffSoldierNum; 
			long currPower = singlePowerMap.get(currId) * num;
			if(currPower > maxPower){
				maxPower = currPower;
				soldierId = currId;
				soldierNum = num;
			}
		}
		if(maxPower > 0){
			SoldierFilterResult result = SoldierFilterResult.valueOf(soldierId, soldierNum, maxPower);
			return result;
		}
		
		return null;
	}
}
