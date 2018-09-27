package com.xgame.logic.server.game.customweanpon;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.gameconst.DBKey;
import com.xgame.logic.server.core.gamelog.constant.GameLogSource;
import com.xgame.logic.server.core.gamelog.event.EventBus;
import com.xgame.logic.server.core.gamelog.event.EventTypeConst;
import com.xgame.logic.server.core.language.Language;
import com.xgame.logic.server.core.language.view.error.ErrorCodeEnum;
import com.xgame.logic.server.core.language.view.success.SuccessTipEnum;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierData;
import com.xgame.logic.server.game.country.structs.build.mod.ModBuildControl;
import com.xgame.logic.server.game.country.structs.build.tach.constant.CustomWeapon;
import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.customweanpon.entity.PlayerDesignMap;
import com.xgame.logic.server.game.customweanpon.message.ReqCreateDesignMapMessage;
import com.xgame.logic.server.game.customweanpon.message.ResDesignMapUpdateMessage;
import com.xgame.logic.server.game.customweanpon.message.ResPartListMessage;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.entity.eventmodel.AttributeRefreshEventObject;
import com.xgame.logic.server.game.soldier.entity.Soldier;
import com.xgame.logic.server.game.soldier.entity.model.UnlockPeijianEventObject;


/**
 * 自荐武器
 * @author jacky.jiang
 *
 */
@Component
public class CustomWeaponManager {
	
	@Autowired
	private IDFactrorySequencer idFactrorySequencer;
	
	/**
	 * 生产图纸
	 * @param player
	 * @param reqCreateDesignMapMessage
	 */
	public void reqCreateDesignMap(Player player, ReqCreateDesignMapMessage reqCreateDesignMapMessage) {
		
		DesignMapBean designMapBean = reqCreateDesignMapMessage.designMap;

		String tzName = designMapBean.name;
		if (tzName == null || tzName.length() >= 20) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE1.get());
			return;
		}
		
		if(designMapBean.index < 1 ||designMapBean.index > 3) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE13.get());
			return;
		}
		
		if(designMapBean.buildIndex < 1 || designMapBean.buildIndex > 3) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE13.get());
			return;
		}
		
		if(!checkSystemDesignUnlock(player, designMapBean.type, designMapBean.systemIndex)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE13.get());
			return;
		}
		
		PlayerDesignMap playerDesignMap = player.roleInfo().getPlayerDesignMap();
		if(playerDesignMap.checkPositionExist(designMapBean.type, designMapBean.systemIndex, designMapBean.buildIndex, designMapBean.index)) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE13.get());
			return;
		}
		
		int soilderType = designMapBean.type;
		List<PartBean> pJList = designMapBean.partList;
		if(!checkPeijianInfo(player, pJList, soilderType)) {
			return;
		}
		
		DesignMap designMap = createDesignMap(player, tzName, pJList, soilderType, designMapBean.systemIndex, designMapBean.index, designMapBean.buildIndex);
		player.getCustomWeaponManager().sendDesignMap(player, designMap);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E600_WEAPON.CODE1.get());
	}
	
	/**
	 * 校验配件信息
	 * @param player
	 * @param pJList
	 * @param soilderType
	 * @return false 配件信息错误  true 配件信息正确
	 */
	private boolean checkPeijianInfo(Player player, List<PartBean> pJList, int soilderType) {
		int dpNum = 0;// 底盘数量
		int xdNum = 0;// 行动部件数量
		int zwqNum = 0;// 主武器部件数量
		int fwqNum = 0;// 副武器部件数量
		int ptNum = 0;// 炮塔槽位数量
		int ptnbNum = 0;// 炮塔内部部件数量
		int ctwbNum = 0;// 车体外部部件数量
		int ctnbNum = 0;// 车体内部部件数量
		PeiJianPir dpConfig = null;
		PeiJianPir wQJConfig = null;
		Iterator<PartBean> iterator = pJList.iterator();
		while (iterator.hasNext()) {
			PartBean partBean = (PartBean) iterator.next();
			PeiJianPir peijianConf = PeiJianPirFactory.get(partBean.partId);
			if (peijianConf == null) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE17.get());
				return false;
			}

			if (!player.roleInfo().getSoldierData().getUnlockPeijians().containsKey(peijianConf.getId())) {
				Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE17.get());
				return false;
			}
			
			if (peijianConf.getType1() != soilderType) {
				Language.ERRORCODE.send(player,ErrorCodeEnum.E600200_WEAPON.CODE2.get());
				return false;
			}
			
			if (peijianConf.getType5() == CustomWeapon.C_CAO0) {
				dpNum = dpNum + 1;
				dpConfig = peijianConf;// 底盘只能有一个
			} else if (peijianConf.getType5() == CustomWeapon.C_CAO1) {
				xdNum = xdNum + 1;
			} else if (peijianConf.getType5() == CustomWeapon.C_CAO3) {
				zwqNum = zwqNum + 1;
			} else if (peijianConf.getType5() == CustomWeapon.C_CAO4) {
				fwqNum = fwqNum + 1;
			} else if (peijianConf.getType5() == CustomWeapon.C_CAO2) {
				ptNum = ptNum + 1;
				wQJConfig = peijianConf;// 武器架只能有一个
			} else if (peijianConf.getType5() == CustomWeapon.C_CAO6) {
				ptnbNum = ptnbNum + 1;
			} else if (peijianConf.getType5() == CustomWeapon.C_CAO7) {
				ctwbNum = ctwbNum + 1;
			} else if (peijianConf.getType5() == CustomWeapon.C_CAO8) {
				ctnbNum = ctnbNum + 1;
			}
		}
		
		if (dpConfig == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE3.get());
			return false;
		}
		
		if (wQJConfig == null) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE4.get());
			return false;
		}
		
		if (dpNum != 1) {
			Language.ERRORCODE.send(player,ErrorCodeEnum.E600200_WEAPON.CODE5.get());
			return false;
		}
		
		if (xdNum != dpConfig.getCao1()) {
			Language.ERRORCODE.send(player,ErrorCodeEnum.E600200_WEAPON.CODE6.get());
			return false;
		}
		
		if (zwqNum > (Integer)wQJConfig.getCao3()
				|| fwqNum > wQJConfig.getCao4() || ptNum > dpConfig.getCao2()
				|| ptnbNum > wQJConfig.getCao6()
				|| ctwbNum > dpConfig.getCao7() || ctnbNum > dpConfig.getCao8()) {
			Language.ERRORCODE.send(player,ErrorCodeEnum.E600200_WEAPON.CODE7.get());
			return false;
		}
		
		return true;
	}
	
//	/**
//	 * 转换图纸
//	 * @param player
//	 * @param reqCreateDesignMapMessage
//	 */
//	public void changeDesignMap(Player player, long id,  String name, List<PartBean> partBeanList) {
//		DesignMap designMap = player.roleInfo().getPlayerDesignMap().getDesignMap(id);
//		if(designMap != null) {
//			if(!checkPeijianInfo(player, partBeanList, designMap.getType())){
//				return;
//			}
//			
//			DesignMap newDesignMap = createDesignMap(player, designMap.getName(), partBeanList, designMap.getType(), designMap.getSystemIndex(), designMap.getIndex(), designMap.getBuildIndex());
//			newDesignMap.setVersion(designMap.getVersion() + 1);
//			
//			player.roleInfo().getPlayerDesignMap().addDesignMap(newDesignMap);
//			InjectorUtil.getInjector().dbCacheService.update(player);
//		}
//	}
	
	/**
	 * 士兵改装
	 * @param player
	 * @param soldierId
	 * @param name
	 * @param partBeanList
	 */
	public void changeDesignMap(Player player, long soldierId, String name, List<PartBean> partBeanList) {
		
 		DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldierId);
		if(designMap == null || name.length() >= 20) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE1.get());
			return;
		}
		
//		//TODO 校验配件位置等各种合法性
//		int dpNum = 0;//底盘数量
//		int xdNum = 0;//行动部件数量
//		int zwqNum = 0;//主武器部件数量
//		int fwqNum = 0;//副武器部件数量
//		int ptNum = 0;//炮塔槽位数量
//		int ptnbNum = 0;//炮塔内部部件数量
//		int ctwbNum = 0;//车体外部部件数量
//		int ctnbNum = 0;//车体内部部件数量
		int soilderType = designMap.getType();
//		PeiJianPir dpConfig = null;
//		PeiJianPir wQJConfig = null;
//		Iterator<PartBean> iterator = partBeanList.iterator();
//		while (iterator.hasNext()) {
//			PartBean customWeaponPJ = (PartBean) iterator.next();
//			PeiJianPir peijianConf = PeiJianPirFactory.get(customWeaponPJ.partId);
//			if (peijianConf == null) {
//				Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE13.get());
//				return;
//			}
//			if (player.roleInfo().getSoldierData().getUnlockPeijians().containsKey(peijianConf.getId()) == false) {
//				Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE17.get());
//				return;
//			}
//			if (peijianConf.getType1() != soilderType) {
//				Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE2.get());
//				return;
//			}
//			if (peijianConf.getType5() == CustomWeapon.C_CAO0) {
//				dpNum = dpNum + 1;
//				dpConfig = peijianConf;// 底盘只能有一个
//			} else if (peijianConf.getType5() == CustomWeapon.C_CAO1) {
//				xdNum = xdNum + 1;
//			} else if (peijianConf.getType5() == CustomWeapon.C_CAO3) {
//				zwqNum = zwqNum + 1;
//			} else if (peijianConf.getType5() == CustomWeapon.C_CAO4) {
//				fwqNum = fwqNum + 1;
//			} else if (peijianConf.getType5() == CustomWeapon.C_CAO2) {
//				ptNum = ptNum + 1;
//				wQJConfig = peijianConf;// 武器架只能有一个
//			} else if (peijianConf.getType5() == CustomWeapon.C_CAO6) {
//				ptnbNum = ptnbNum + 1;
//			} else if (peijianConf.getType5() == CustomWeapon.C_CAO7) {
//				ctwbNum = ctwbNum + 1;
//			} else if (peijianConf.getType5() == CustomWeapon.C_CAO8) {
//				ctnbNum = ctnbNum + 1;
//			}
//		}
//		
//		if (dpConfig == null) {
//			Language.ERRORCODE.send(player, 600006);
//			Language.ERRORCODE.send(player,
//					ErrorCodeEnum.E600200_WEAPON.CODE3.get());
//			return;
//		}
//		
//		if (wQJConfig == null) {
//			Language.ERRORCODE.send(player,
//					ErrorCodeEnum.E600200_WEAPON.CODE4.get());
//			return;
//		}
//		
//		if (dpNum != 1) {
//			Language.ERRORCODE.send(player,
//					ErrorCodeEnum.E600200_WEAPON.CODE5.get());
//			return;
//		}
//		
//		if (xdNum != dpConfig.getCao1()) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE6.get());
//			return;
//		}
//		
//		if (zwqNum > (Integer) wQJConfig.getCao3()
//				|| fwqNum > wQJConfig.getCao4() || ptNum > dpConfig.getCao2()
//				|| ptnbNum > wQJConfig.getCao6()
//				|| ctwbNum > dpConfig.getCao7() || ctnbNum > dpConfig.getCao8()) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE7.get());
//			return;
//		}
		
		if(!checkPeijianInfo(player, partBeanList, soilderType)) {
			return;
		}
		
		// 该蓝图生产的装甲正在生产中，无法修改此蓝图。
		if(player.getSoldierManager().checkSoldierOutput(player, soldierId, designMap.getType())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE19.get());
			return;
		}
		
		// TODO 
		
		// 该蓝图生产的装甲正在改造中，无法修改此蓝图。
		if(player.roleInfo().getSoldierData().getRefittingData().getSoldierId() == designMap.getId()) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE20.get());
			return;
		}
		
		// 该蓝图生产的装甲正在修理中，无法修改此蓝图。
		if(player.getSoldierManager().checkSoldierRepair(player, soldierId, designMap.getType())) {
			Language.ERRORCODE.send(player, ErrorCodeEnum.E600200_WEAPON.CODE21.get());
			return;	
		}
		
		// TODO 扣除伤病
		ModBuildControl modBuildControl = player.getCountryManager().getModBuildControl();
		if(modBuildControl == null) {
			Language.ERRORCODE.send(player, 600011);
			return;
		}
		
		Soldier soldier = player.roleInfo().getSoldierData().getSoldiers().get(soldierId);
		if (soldier != null) {
			// 该蓝图生产的装甲正在行军中，无法修改此蓝图。
			if (soldier.getMarchNum() > 0) {
				Language.ERRORCODE.send(player, 600011);
				return;
			}
			
			if (soldier.getNum() > 0) {
				int isdestroy = 0;
				player.getModifyManager().assembly(player, soldier, soldier.getNum(), isdestroy);
			}
			
			if (soldier.getInjureNum() > 0) {
				player.getModifyManager().assembly(player, soldier, soldier.getInjureNum(), 1);
			}
		}
		
		// 改造图纸
		DesignMap designMap2 = this.createDesignMap(player, name, partBeanList, designMap.getType(), designMap.getSystemIndex(), designMap.getIndex(), designMap.getBuildIndex());
		designMap2.setVersion(designMap.getVersion() + 1);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
//		CampBuildControl campBuildControl= null;
//		if(designMap.getType() == SoldierTypeEnum.TANK.getCode()) {
//			campBuildControl = player.getCountryManager().getTankBuildControl();
//		} else if(designMap.getType() == SoldierTypeEnum.SUV.getCode()) {
//			campBuildControl = player.getCountryManager().getSuvBuildControl();
//		} else if(designMap.getType() == SoldierTypeEnum.PLAIN.getCode()) {
//			campBuildControl = player.getCountryManager().getPlaneBuildControl();
//		}
		
//		player.getAttributeAppenderManager().rebuildObject(campBuildControl.getAttributeNode(), designMap.getId(), true);
		EventBus.getSelf().fireEvent(new AttributeRefreshEventObject(player));
		// 更新图纸信息
		sendDesignMap(player, designMap2);
		Language.SUCCESSTIP.send(player, SuccessTipEnum.E101_CAMP.CODE3.get());
	}
	
	/**
	 * 获取图纸
	 * @param player
	 * @param soldierId
	 * @return
	 */
	public DesignMap queryDesignMapById(Player player, long soldierId) {
		DesignMap designMap = player.roleInfo().getPlayerDesignMap().getDesignMap(soldierId);
		if(designMap != null) {
			return designMap;
		}
		return null;
	}
	
	/**
	 * 获取最新图纸
	 * @param player
	 * @param type
	 * @param systemIndex
	 * @param buildIndex
	 * @return
	 */
	public DesignMap queryLastestDesignMap(Player player, int type, int systemIndex, int buildIndex) {
		Map<Long, DesignMap> designMapList = player.roleInfo().getPlayerDesignMap().getDesignMap();
		int version = 0;
		DesignMap returnDesignMap = null;
		if(designMapList != null) {
			for(DesignMap designMap : designMapList.values()) {
				if(designMap.getSystemIndex() == systemIndex && designMap.getBuildIndex() == buildIndex && designMap.getType() == type) {
					if(version <= designMap.getVersion()) {
						version =  designMap.getVersion();
						returnDesignMap = designMap;
					}
				}
			}
		}
		return returnDesignMap;
	}
	
	/**
	 * 判断系统兵种图纸是否解锁
	 * @param player
	 * @param type
	 * @param systemIndex
	 * @return
	 */
	public boolean checkSystemDesignUnlock(Player player, int type, int systemIndex) {
		Map<Long, DesignMap> designMapList = player.roleInfo().getPlayerDesignMap().getDesignMap();
		if(designMapList != null) {
			for(DesignMap designMap : designMapList.values()) {
				if(designMap.getSystemIndex() == systemIndex && designMap.getType() == type && designMap.getBuildIndex() == 0) {
					if(designMap.isUnlock()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 组装自建武器
	 * @param player
	 * @param peijians  配件id数组
	 * @param isSystem  是否属于系统的
	 */
	public DesignMap createDesignMap(Player player, String name, List<PartBean> partBeanList, int type, int systemIndex, int index, int buildIndex) {
		DesignMap designMap = new DesignMap();
		long id = idFactrorySequencer.createEnityID(DBKey.DESIGN_MAP_KEY);
		designMap.setBuildIndex(buildIndex);
		designMap.setIndex(index);
		designMap.setName(name);
		designMap.setPartList(partBeanList);
		designMap.setSystemIndex(systemIndex);
		designMap.setType(type);
		designMap.setVersion(1);
		designMap.setId(id);
		
		player.roleInfo().getPlayerDesignMap().addDesignMap(designMap);
		InjectorUtil.getInjector().dbCacheService.update(player);
		
		return designMap;
	}
	
	/**
	 * 发送所有图纸
	 * @param player
	 */
	public void send(Player player) {
		ResDesignMapUpdateMessage resDesignMapUpdateMessage = new ResDesignMapUpdateMessage();
		Collection<DesignMap> collection = player.roleInfo().getPlayerDesignMap().getDesignMap().values();
		List<DesignMapBean> designMapBeans = CustomWeaponConverter.converterDesignMapBeans(collection);
		resDesignMapUpdateMessage.designMapList.addAll(designMapBeans);
		player.send(resDesignMapUpdateMessage);
		
		SoldierData soldierData = player.roleInfo().getSoldierData();
		ResPartListMessage pJMessage = new ResPartListMessage();
		Map<Integer, Integer> map = soldierData.getUnlockPeijians();
		pJMessage.partIdList.addAll(map.keySet());
		player.send(pJMessage);
	}
	
	/**
	 * 发送单个图纸
	 * @param player
	 * @param designMap
	 */
	public void sendDesignMap(Player player, DesignMap designMap) {
		ResDesignMapUpdateMessage resDesignMapUpdateMessage = new ResDesignMapUpdateMessage();
		resDesignMapUpdateMessage.designMapList.add(CustomWeaponConverter.converterDesignMapBean(designMap));
		player.send(resDesignMapUpdateMessage);
	}
	
	/**
	 * 解锁配件
	 * @param id
	 */
	public void unlockPeijian(Player player, int id, GameLogSource gameLogSource) {
		SoldierData soldierData = player.roleInfo().getSoldierData();
		if (soldierData.getUnlockPeijians().containsKey(id) == false) {
			soldierData.getUnlockPeijians().put(id, id);

			EventBus.getSelf().fireEvent(new UnlockPeijianEventObject(player, EventTypeConst.UNLOCK_PEIJIAN, id));

			// 返回配件解锁信息
			ResPartListMessage pJMessage = new ResPartListMessage();
			pJMessage.partIdList.add(id);
			player.send(pJMessage);
		}
	}
	
}
