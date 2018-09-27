package com.xgame.logic.server.game.soldier.entity;

import java.io.Serializable;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.country.bean.Vector2Bean;
import com.xgame.logic.server.game.country.structs.build.camp.CampBuildControl;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.AttributeUtil;
import com.xgame.logic.server.game.playerattribute.calculation.AttributeCounter;
import com.xgame.logic.server.game.playerattribute.constant.AttributesEnum;
import com.xgame.logic.server.game.playerattribute.constant.WarAttrConverterTypeEnum;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.constant.SoldierChangeType;
import com.xgame.logic.server.game.soldier.constant.SoldierTypeEnum;
import com.xgame.logic.server.game.war.bean.WarAttr;
import com.xgame.logic.server.game.war.converter.BattleConverter;

/**
 *类似另一个item
 *2016-8-04  19:29:35
 *@author ye.yuan
 *
 */
@Slf4j
public class Soldier implements Serializable, Cloneable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 士兵id
	 */
	private long soldierId;
	
	/**
	 * 正常数据
	 */
	private int num;
	
	/**
	 * 搜索战斗数量
	 */
	private int rtsNum;
	
//	/**
//	 * 家园防守的士兵缩量
//	 */
//	private int defenseNum;
	
	/**
	 * 行军中的士兵的数量
	 */
	private int marchNum;
	
	/**
	 * 第一次生产
	 */
	private boolean fisrtOutput = true;
	
	/**
	 * index信息
	 */
	private int index;
	
	/**
	 * 位置坐标
	 */
	private Vector2Bean vector = new Vector2Bean();
	
	/**
	 * 玩家士兵
	 */
	private long playerId;
	
	/**
	 * 死亡兵的数量
	 */
	private int deadNum;
	
	/**
	 * 受伤的数量
	 */
	private int injureNum;
	
	/**
	 * 防守驻军建筑id
	 */
	private int buildUid;
	
	/**
	 * 战力丢失
	 */
	private int lostFightPower;
	
	/**
	 * 战斗属性
	 */
	private WarAttr warAttr = new WarAttr();
	
	private DesignMap designMap  = new DesignMap();
	
//	public SoldierAttributeObject getAttributeObject(Player player) {
//		CampBuildControl buildControl = getCampBuildControl(player);
//		if (buildControl != null) {
//			AttributeObject attributeObject = player.roleInfo().getGameAttribute().attributeObjectInfo(buildControl.getAttributeNode(), soldierId);
//			if(attributeObject != null) {
//				return (SoldierAttributeObject)attributeObject;
//			}
//		}
//		return null;
//	}
	
	/**
	 * 获取兵营
	 * @param player
	 * @return
	 */
	private CampBuildControl getCampBuildControl(Player player) {
		DesignMap designMap = player.roleInfo().getPlayerDesignMap().getDesignMap(soldierId);
		if(designMap != null) {
			if(designMap.getType() == SoldierTypeEnum.PLAIN.getCode()) {
				return player.getCountryManager().getPlaneBuildControl();
			} else if(designMap.getType() == SoldierTypeEnum.SUV.getCode()) {
				return player.getCountryManager().getSuvBuildControl();
			} else if(designMap.getType() == SoldierTypeEnum.TANK.getCode()) {
				return player.getCountryManager().getTankBuildControl();
			} else {
				log.error(String.format("------------兵种图纸错误---------%s", designMap.getType()));
			}
		}
		return null;
	}
	
	/**
	 * 士兵属性
	 * @param player
	 * @param attributeEnum
	 * @return
	 */
	public double getAttribute(Player player, AttributesEnum attributeEnum) {
//		SoldierAttributeObject soldierAttributeObject = getAttributeObject(player);
//		if(soldierAttributeObject != null) {
//			return soldierAttributeObject.getAttribute(attributeEnum);
//		}
		Map<AttributesEnum,Double> attrMap = AttributeCounter.getSoldierAttribute(player.getId(), soldierId);
		Double value = attrMap.get(attributeEnum);
		if(value != null){
			return value;
		}
		return 0;
	}
	
	public long getFightPower(Player player,int num) {
		return getSinglePower(player.getId())*num;
	}
	
	/**
	 * 单兵战力
	 * @param playerId
	 * @return
	 */
	public long getSinglePower(long playerId){
		return AttributeUtil.getSingleSoldierFightPower(playerId, soldierId);
	}
	
	
	/**
	 * 浅拷贝 只有基础数据类型
	 * @return
	 */
	public Soldier toSoldier(Player player) {
		Soldier soldier2 = new Soldier();
		soldier2.num=num;
		soldier2.marchNum = this.marchNum;
		soldier2.soldierId = soldierId;
		soldier2.index = index;
		soldier2.warAttr = BattleConverter.converterWarAttr(player.getId(),soldierId,WarAttrConverterTypeEnum.SOLDIER);
		DesignMap designMap = player.roleInfo().getPlayerDesignMap().getDesignMap(soldierId);
		soldier2.designMap = designMap;
		return soldier2;
	}
	
	public SoldierBean toSoldierBean() {
		SoldierBean soldierBean = new SoldierBean();
		soldierBean.num=num;
		soldierBean.soldierId = this.soldierId;
		return soldierBean;
	}
	
	
	/**
	 * 深度拷贝 包括所有基础数据类型和对象
	 * @return
	 */
	public Soldier cloneFullSoldier(Player player) {
		Soldier soldier = toSoldier(player);
		return soldier;
	}
	
	/**
	 * 克隆出固定数量的士兵
	 * @param player
	 * @return
	 */
	public Soldier cloneFullSoldier(Player player, int num) {
		Soldier soldier = cloneFullSoldier(player);
		soldier.setNum(num);
		return soldier;
	}
	
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isFisrtOutput() {
		return fisrtOutput;
	}

	public void setFisrtOutput(boolean fisrtOutput) {
		this.fisrtOutput = fisrtOutput;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Vector2Bean getVector() {
		return vector;
	}

	public void setVector(Vector2Bean vector) {
		this.vector = vector;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public int getDeadNum() {
		return deadNum;
	}

	public void setDeadNum(int deadNum) {
		this.deadNum = deadNum;
	}

	public int getLostFightPower() {
		return lostFightPower;
	}

	public void setLostFightPower(int lostFightPower) {
		this.lostFightPower = lostFightPower;
	}
	
	public int getBuildUid() {
		return buildUid;
	}

	public void setBuildUid(int buildUid) {
		this.buildUid = buildUid;
	}

	public WarAttr getWarAttr() {
		return warAttr;
	}

	public void setWarAttr(WarAttr warAttr) {
		this.warAttr = warAttr;
	}
	
	public long getSoldierId() {
		return soldierId;
	}

	public void setSoldierId(long soldierId) {
		this.soldierId = soldierId;
	}
	
	public int getPveNum() {
		return rtsNum;
	}

	public void setPveNum(int pveNum) {
		this.rtsNum = pveNum;
	}

//	public int getDefenseNum() {
//		return defenseNum;
//	}
//
//	public void setDefenseNum(int defenseNum) {
//		this.defenseNum = defenseNum;
//	}

	public int getMarchNum() {
		return marchNum;
	}

	public void setMarchNum(int marchNum) {
		this.marchNum = marchNum;
	}

	public int getInjureNum() {
		return injureNum;
	}

	public void setInjureNum(int injureNum) {
		this.injureNum = injureNum;
	}
	
	public DesignMap getDesignMap() {
		return designMap;
	}

	public void setDesignMap(DesignMap designMap) {
		this.designMap = designMap;
	}

	/**
	 * 更新士兵根据类型不同
	 * @param num
	 * @param soldierChangeType
	 * @return
	 */
	public int  updateSoldierByType(int num, int soldierChangeType) {
		int returnValue = 0;
		switch (soldierChangeType) {
			case SoldierChangeType.COMMON:
				this.num += num;
				this.num = Math.max(this.num, 0);
				returnValue =this.num;
				break;
				
			case SoldierChangeType.MARCH:
				this.marchNum += num;
				this.marchNum = Math.max(this.marchNum, 0);
				returnValue = this.marchNum;
				break;
				
			case SoldierChangeType.RTS:
				this.rtsNum += num;
				this.rtsNum = Math.max(this.rtsNum, 0);
				returnValue = this.rtsNum;
				break;
				
			case SoldierChangeType.INJURE:
				this.injureNum += num;
				this.injureNum = Math.max(this.injureNum, 0);
				break;
			default:
				break;
		}
		log.info(String.format("log, soldier changeType[%s], current[%s], returnValue[%s]", soldierChangeType, num, returnValue));
		return returnValue;
	}
	
	/**
	 * 判断士兵数量够不够
	 * @param num
	 * @param soldierChangeType
	 * @return true 不足，false 够
	 */
	public boolean checkSoldierNumLimit(int num, int soldierChangeType) {
		switch (soldierChangeType) {
		case SoldierChangeType.COMMON:
			if(this.num <num) {
				return true;
			}
			break;
		case SoldierChangeType.MARCH:
			if(this.marchNum < num) {
				return true;
			}
			break;
			
		case SoldierChangeType.RTS:
			if(this.rtsNum < num) {
				return true;
			}
			break;
		case SoldierChangeType.INJURE:
			if(this.injureNum < num) {
				return true;
			}
			break;
		}
		
		return false;
	}
	
	public static String factoryBattleSoldierId(long soldierId, int index) {
		return String.format("%s:%s",soldierId, index);
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("soldierId", soldierId);
		jBaseData.put("num", num);
		jBaseData.put("rtsNum", rtsNum);
		jBaseData.put("marchNum", marchNum);
		jBaseData.put("fisrtOutput", fisrtOutput);
		jBaseData.put("index", index);
		jBaseData.put("playerId", playerId);
		jBaseData.put("deadNum", deadNum);
		jBaseData.put("injureNum", injureNum);
		jBaseData.put("warAttr", JsonUtil.toJSON(warAttr));
		jBaseData.put("designMap", designMap.toJBaseData());
		jBaseData.put("vector", JsonUtil.toJSON(vector));
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this. soldierId = jBaseData.getLong("soldierId", 0);
		this.num = jBaseData.getInt("num", 0);
		this.rtsNum = jBaseData.getInt("rtsNum", 0);
		this.marchNum = jBaseData.getInt("marchNum", 0);
		this.fisrtOutput = jBaseData.getBoolean("fisrtOutput", false);
		this.index = jBaseData.getInt("index", 0);
		this.playerId = jBaseData.getLong("playerId", 0);
		this.deadNum = jBaseData.getInt("deadNum", 0);
		this.injureNum = jBaseData.getInt("injureNum", 0);
		
		String warAttrStr = jBaseData.getString("warAttr", "");
		if(!StringUtils.isEmpty(warAttrStr)) {
			this.warAttr = JsonUtil.fromJSON(warAttrStr, WarAttr.class);
		}
		
		JBaseData deBaseData = jBaseData.getBaseData("designMap");
		DesignMap designMap = new DesignMap();
		designMap.fromJBaseData(deBaseData);
		this.designMap = designMap;
		
		String vectorStr = jBaseData.getString("vector", "");
		if(!StringUtils.isEmpty(vectorStr)) {
			this.vector = JsonUtil.fromJSON(vectorStr, Vector2Bean.class);
		}
		
	}
}
