package com.xgame.logic.server.game.soldier.converter;

import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.country.structs.build.camp.data.SoldierBrief;
import com.xgame.logic.server.game.customweanpon.converter.CustomWeaponConverter;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.playerattribute.bean.AttrbuteProBean;
import com.xgame.logic.server.game.soldier.bean.FullSoldierBean;
import com.xgame.logic.server.game.soldier.bean.SoldierBean;
import com.xgame.logic.server.game.soldier.entity.Soldier;

/**
 * 士兵转换器
 * @author jacky.jiang
 *
 */
public class SoldierConverter {

	
	public static  SoldierBean converterSoldierBean(SoldierBrief soldierBrief) {
		SoldierBean soldierBean = new SoldierBean();
		soldierBean.soldierId = soldierBrief.getSoldierId();
		soldierBean.num = soldierBrief.getNum();
		return soldierBean;
	}
	
	/**
	 * 转换soldier
	 * @param soldier
	 * @return
	 */
	public static FullSoldierBean converterFullSoldierBean(Player player, Soldier soldier, int num) {
		FullSoldierBean soldierBean = new FullSoldierBean();
		soldierBean.soldier = new SoldierBean();
		soldierBean.soldier.soldierId = soldier.getSoldierId();
		soldierBean.soldier.num = num;
		
		DesignMap designMap = player.getCustomWeaponManager().queryDesignMapById(player, soldier.getSoldierId());
		soldierBean.designMap = CustomWeaponConverter.converterDesignMapBean(designMap);
		return soldierBean;
	}
	
	
	public static FullSoldierBean converterNpcSoldierBean(Soldier soldier) {
		FullSoldierBean soldierBean = new FullSoldierBean();
		soldierBean.soldier = new SoldierBean();
		soldierBean.soldier.soldierId = soldier.getSoldierId();
		soldierBean.soldier.num = soldier.getNum();
		soldierBean.designMap = CustomWeaponConverter.converterDesignMapBean(soldier.getDesignMap());
		return soldierBean;
	}
	
	/**
	 * 转换属性
	 * @param soldier
	 * @return
	 */
	public static List<AttrbuteProBean> converterAttribute(Player player, Soldier soldier) {
		List<AttrbuteProBean> attributeList = new ArrayList<>();
		//TODO 
//		for(AttributeEnum attributeEnum : AttributeEnum.values()) {
//			if(attributeEnum.getId() <= SoldierConstant.BATTLE_SOLDIER_ATTR_MAX) {
//				AttrbuteProBean attrbuteProBean = new AttrbuteProBean();
//				attrbuteProBean.attributeId = attributeEnum.getId();
//				attrbuteProBean.value = soldier.getAttribute(player, attributeEnum);
//				attributeList.add(attrbuteProBean);
//			}
//		}
		return attributeList;
	}
	
}
