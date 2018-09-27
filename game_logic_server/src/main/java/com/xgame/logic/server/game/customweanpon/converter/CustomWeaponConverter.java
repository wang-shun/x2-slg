package com.xgame.logic.server.game.customweanpon.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.xgame.config.peiJian.PeiJianPir;
import com.xgame.config.peiJian.PeiJianPirFactory;
import com.xgame.logic.server.game.country.structs.build.tach.constant.CustomWeapon;
import com.xgame.logic.server.game.customweanpon.bean.DesignMapBean;
import com.xgame.logic.server.game.customweanpon.bean.PartBean;
import com.xgame.logic.server.game.customweanpon.entity.DesignMap;


/**
 * 图纸转换
 * @author jacky.jiang
 *
 */
public class CustomWeaponConverter {
	
	/**
	 * 组装配件列表
	 * @param player
	 * @param partList
	 * @return
	 */
	public static DesignMap assemebleMonster(List<Integer> partList) {
		DesignMap designMap = new DesignMap();
		List<PartBean> customWeaponPJs = new ArrayList<>(partList.size());
		int xdCount = 0;
		int wwqCount = 0;
		int zwqCount = 0;
		int ctwbCount =0;
		int systemIndex = 0;
		for (int i = 0; i < partList.size(); i++) {
			int partId = partList.get(i);
			PeiJianPir peiJianPir = PeiJianPirFactory.get(partId);
			PartBean weaponPJ = new PartBean();
			weaponPJ.partId = partId;
			
			if (peiJianPir.getType5() == CustomWeapon.C_CAO1) {
				xdCount += 1;
				weaponPJ.position = xdCount;
			} else if (peiJianPir.getType5() == CustomWeapon.C_CAO3) {
				zwqCount += 1;
				weaponPJ.position = zwqCount;
			} else if (peiJianPir.getType5() == CustomWeapon.C_CAO4) {
				wwqCount += 1;
				weaponPJ.position = wwqCount;
			} else if (peiJianPir.getType5() == CustomWeapon.C_CAO7) {
				ctwbCount += 1;
				weaponPJ.position = ctwbCount;
			} else if (peiJianPir.getType5() == CustomWeapon.C_CAO0) {
				systemIndex = peiJianPir.getLv();
			} else {
				weaponPJ.position = 1;
			}
			
			customWeaponPJs.add(weaponPJ);
		}
		designMap.setSystemIndex(systemIndex);
		designMap.setPartList(customWeaponPJs);
		return designMap;
	}

	
	public static DesignMapBean converterDesignMapBean(DesignMap designMap) {
		DesignMapBean designMapBean = new DesignMapBean();
		designMapBean.buildIndex = designMap.getBuildIndex();
		designMapBean.id = designMap.getId();
		designMapBean.index = designMap.getIndex();
		designMapBean.name = designMap.getName();
		designMapBean.systemIndex = designMap.getSystemIndex();
		designMapBean.type = designMap.getType();
		designMapBean.version = designMap.getVersion();
		designMapBean.partList = designMap.getPartList();
		designMapBean.unlock = designMap.isUnlock() ? 1 : 0;
		return designMapBean;
	}
	
	
	public static List<DesignMapBean> converterDesignMapBeans(Collection<DesignMap> designMapList) {
		List<DesignMapBean> designMapBeans = new ArrayList<DesignMapBean>();
		for(DesignMap designMap : designMapList) {
			designMapBeans.add(converterDesignMapBean(designMap));
		}
		return designMapBeans;
	}
}
