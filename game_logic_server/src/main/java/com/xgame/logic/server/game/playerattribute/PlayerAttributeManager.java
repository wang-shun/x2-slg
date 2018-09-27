package com.xgame.logic.server.game.playerattribute;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.config.global.GlobalPir;
import com.xgame.config.global.GlobalPirFactory;
import com.xgame.config.ziYuanDian.ZiYuanDianPir;
import com.xgame.config.ziYuanDian.ZiYuanDianPirFactory;
import com.xgame.gameconst.GlobalConstant;
import com.xgame.logic.server.core.gamelog.event.AbstractEventListener;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.gamelog.event.IEventObject;
import com.xgame.logic.server.game.bag.entity.eventmodel.MineProductUpdateEventObject;
import com.xgame.logic.server.game.constant.CurrencyEnum;
import com.xgame.logic.server.game.country.entity.CountryBuild;
import com.xgame.logic.server.game.country.structs.build.BuildFactory;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteAppenderBean;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteAppenderListBean;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributeNodeEnum;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.message.ResAttrbutesAddFromMessage;
import com.xgame.logic.server.game.world.constant.MarchType;

@Slf4j
@Component
public class PlayerAttributeManager extends AbstractEventListener{
	
	private static PlayerAttributeManager injector;
	
	@PostConstruct
	public void init(){
		injector = this;
		super.init();
	}
	
	public static PlayerAttributeManager get(){
		return injector;
	}
	
	/**
	 * 推送属性
	 * @param player
	 */
	public void pushAttribute(Player player){
		if(!AttributeCounter.refreshAttributeAdd(player)){
			AttributeCounter.pushAttributeUpdate(player);
		}
	}
	
	/**
	 * 获取属性加成列表
	 * @param player
	 */
	public void queryAttrbutesAdd(Player player) {
		pushAttribute(player);
	}
	
	/**
	 * 显示数来源模块
	 * @param player
	 * @param attributeId
	 * @param nodeId
	 */
	public void selectAttrbutesAddFrom(Player player) {
		ResAttrbutesAddFromMessage info = new ResAttrbutesAddFromMessage();
		List<AttrbuteAppenderListBean> attrbuteAppenderBeanList = new ArrayList<AttrbuteAppenderListBean>();
		for(AttributeNodeEnum node : AttributeNodeEnum.values()){
			for(AttributesEnum attributeId : AttributesEnum.values()){
				List<AttrbuteAppenderBean> list = AttributeCounter.selectAttributeAdd(player.getId(), attributeId, node);
				if(list.size() > 0){
					AttrbuteAppenderListBean attrbuteAppenderListBean = new AttrbuteAppenderListBean();
					attrbuteAppenderListBean.nodeId = node.getCode();
					attrbuteAppenderListBean.attributeId = attributeId.getId();
					attrbuteAppenderListBean.list = list;
					attrbuteAppenderBeanList.add(attrbuteAppenderListBean);
				}
			}
		}
		info.list = attrbuteAppenderBeanList;
		player.send(info);
		log.info(AttributeUtil.log(info));
	}

	@Override
	public int[] focusActions() {
		return new int[]{EventTypeConst.EVENT_ATTRIBUTE_REFRESH};
	}

	@Override
	public void onAction(IEventObject e) {
		Player player = e.getPlayer();
		AttributeCounter.refreshAttributeAdd(player);
		EventBus.getSelf().fireEvent(new MineProductUpdateEventObject(player));
	}
	
	/**
	 * 矿车cd时间
	 * @param playerId
	 * @return
	 */
	public int mineCarCdTime(long playerId){
		GlobalPir config = GlobalPirFactory.get(GlobalConstant.MINE_CAR_CD_TIME);
		int cdTime = Integer.valueOf(config.getValue());
		int cdTimePlus = cdTime;
		double rate = AttributeCounter.getAttributeValue(playerId, AttributesEnum.CAR_GATHER_RATE);
		return cdTimePlus = (int)(cdTimePlus / (1 + rate));
	}
	
	/**
	 * 单个格子带兵上限
	 * @param playerId
	 * @return
	 */
	public int singleMaxSetOffSoldierNum(long playerId){
		return (int)AttributeCounter.getPlayerAttribute(playerId,AttributesEnum.MATCH_ARMY_MAX_NUM, 0);
	}
	
	/**
	 * 采集速度
	 * @param player
	 * @param resourceType
	 * @param level
	 * @param alliance 是否是同盟玩家采集
	 * @return
	 */
	public double getExplorerSpeed(Player player, CurrencyEnum resourceType, long allianceId, int level) {
		try {
			ZiYuanDianPir configModel = ZiYuanDianPirFactory.getInstance().getFactory0().get(resourceType.ordinal()).get(level);
			double speed = configModel.getValue2();
			AttributesEnum attr = null;
			if(resourceType == CurrencyEnum.OIL) {
				attr = AttributesEnum.OIL_GATHER_RATE;
			} else if(resourceType == CurrencyEnum.GLOD) {
				attr = AttributesEnum.CASH_GATHER_RATE;
			} else if(resourceType == CurrencyEnum.RARE) {
				attr = AttributesEnum.EARTH_GATHER_RATE;
			} else if(resourceType == CurrencyEnum.STEEL) {
				attr = AttributesEnum.STEEL_GATHER_RATE;
			}
			
			// 本联盟的资源,采集加速
			double ratio = 0;
			if(player.getAllianceId() == allianceId) {
				GlobalPir globalPir = GlobalPirFactory.get(GlobalConstant.SAME_ALLIANCE_EXPLORER_SPEED);
				if(globalPir != null) {
					ratio = Double.valueOf(globalPir.getValue().toString());
				}
			}
		
			speed = AttributeCounter.getPlayerAttribute(player.getId(), attr, speed, ratio);
			return speed;
		} catch (Exception e) {
			log.error("采集速度计算出错:", e);
		}
		
		return 0;
		
	}
	
	/**
	 * 资源仓库保护比例
	 * @param playerId
	 * @param resourceId
	 * @return
	 */
	public double getWareHourseProtectRate(long playerId,int resourceId){
		GlobalPir globalPir = GlobalPirFactory.get(GlobalConstant.RESOURCE_BAG_SAVE_RATE);
		Map<Integer, Double> map2 = globalPir.getValue();
		Double v = map2.get(resourceId);
		double protectOdds = 1;
		if (v != null) {
			AttributesEnum attributesEnum = AttributeUtil.getResourceSaveRateAttr(resourceId);
			protectOdds = AttributeCounter.getPlayerAttribute(playerId, attributesEnum, v);
		}
		return protectOdds;
	}
	
	/**
	 * 装甲生产时间
	 * @param playerId
	 * @param initTime
	 * @return
	 */
	public double armyRroduceTime(long playerId,double initTime){
		return  AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.ARMY_PRODUCE_RATE, initTime);
	}
	
	/**
	 * 侦查行军速度
	 * @param playerId
	 * @return
	 */
	public double scoutMarchSpeed(long playerId){
		return AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.ARMY_SPY_MARCH_SPEED, GlobalPirFactory.getInstance().getDouble(GlobalConstant.SCOUT_SPEED));
	}
	
	/**行军最终时间
	 * @param marchType
	 * @param playerId
	 * @param initTime
	 * @return
	 */
	public int getFinalMarchTime(MarchType marchType,long playerId,int initTime){
		AttributesEnum attributesEnum = AttributeUtil.getMarchTimeAttr(marchType);
		if(attributesEnum == null){
			return initTime;
		}
		int finalTime = (int)AttributeCounter.getPlayerAttribute(playerId, attributesEnum, initTime);
		return finalTime;
	}
	
	/**
	 * 建筑建造时间
	 * @param playerId
	 * @param initTime
	 * @return
	 */
	public int buildingCreateTime(long playerId,int initTime){
		return (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.BUILDING_TIME, initTime);
	}
	
	/**
	 * 收治伤兵数量上限
	 * @param player
	 * @return
	 */
	public int getMaxHurtNum(Player player) {
		BuildingPir buildingPir = BuildingPirFactory.get(BuildFactory.MOD.getTid());
		Map<Integer, Integer> v1Map = buildingPir.getV1();
		List<CountryBuild> countryList = player.roleInfo().getBaseCountry().getCountryBuildByTid(BuildFactory.MOD.getTid());
		int n = 0;
		Iterator<CountryBuild> iterator = countryList.iterator();
		while (iterator.hasNext()) {
			CountryBuild countryBuild = (CountryBuild) iterator.next();
			n += v1Map.get(countryBuild.getLevel());
		}
		n = (int)AttributeCounter.getPlayerAttribute(player.getId(), AttributesEnum.HOSPITAL_MAX_NUM, n);
		return n;
	}
	
	/**
	 * 
	 * 科技研究时间
	 * @param playerId
	 * @param initTime
	 * @return
	 */
	public int scienceTime(long playerId,int initTime){
		return (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.SCIENCE_TIME, initTime);
	}
	
	/**
	 * 装甲产量上限
	 * @param playerId
	 * @param initMaxNum
	 * @return
	 */
	public int armoryCapacity(long playerId,int initMaxNum){
		return (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.ARMORY_CAPACITY, initMaxNum);
	}
	
	/**
	 * 装甲生产消耗
	 * @param playerId
	 * @param initCost
	 * @return
	 */
	public double armyProduceCost(long playerId,double initCost){
		return AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.ARMY_PRODUCE_DECREASE, initCost);
	}
	
	/**
	 * 行军队列
	 * @param playerId
	 * @return
	 */
	public int matchQueue(long playerId){
		return (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.MATCH_QUEUE, GlobalPirFactory.getInstance().initQueueSize[0]);
	}
	
	/**
	 * 勘探开发院采集容量
	 * @return
	 */
	public int capacity(long playerId,int initMaxResource){
		return (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.CAPACITY, initMaxResource);
	}
	
	/**
	 * 集结军队(士兵)上限
	 * @param playerId
	 * @return
	 */
	public int concentrateArmy(long playerId){
		double value = AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.CONCENTRATE_ARMY, 0);
		return Double.valueOf(value).intValue();
	}
	
	/**
	 * 建筑队列数量
	 * @param playerId
	 * @return
	 */
	public int buildingQueueNum(long playerId){
		int[] queueSize = GlobalPirFactory.getInstance().initQueueSize;
		return (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.BUILDING_QUEUE, queueSize[1]);
	}
	
	/**
	 * 科研队列数量
	 * @param playerId
	 * @return
	 */
	public int techQueueNum(long playerId){
		return (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.TECH_QUEUE,GlobalPirFactory.getInstance().initQueueSize[2]);
	}
	
	/**
	 * 防守驻军容量
	 * @param playerId
	 * @param buildSid
	 * @return
	 */
	public int guardMaxNum(long playerId){
		return (int)AttributeCounter.getPlayerAttribute(playerId,AttributesEnum.GUARD_MAX_NUM, 0);
	}
	
	/**
	 * 装甲修理消耗
	 * @param playerId
	 * @param initCost
	 * @return
	 */
	public int hospitalResourceCost(long playerId,int initCost){
		return (int)AttributeCounter.getPlayerAttribute(playerId,AttributesEnum.HOSPITAL_RESOURCE_DECREASE, initCost);
	}
	
	/**
	 * 矿车产量
	 * @param playerId
	 * @param resourceType
	 * @param buildingLevel
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int mineProduct(long playerId,int resourceType,int buildingLevel){
		BuildingPir buildingPir2 = BuildingPirFactory.get(BuildFactory.MINE_CAR.getTid());
		Map<Integer, Map<Integer, Float>> map = (Map<Integer, Map<Integer, Float>>)buildingPir2.getV1();
		Integer addNum = map.get(resourceType).get(buildingLevel).intValue();
		int mineProduct = (int)AttributeCounter.getPlayerAttribute(playerId, AttributesEnum.CAR_GATHER_RATE, addNum);
		return mineProduct;
	}
}
