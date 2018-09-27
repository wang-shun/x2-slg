package com.xgame.logic.server.game.cross.converter;

import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.cross.entity.SimpleRoleInfo;
import com.xgame.logic.server.game.player.entity.RoleInfo;

/**
 * 玩家信息
 * @author jacky.jiang
 *
 */
public class SimplePlayerConverter {
	
	/**
	 * 玩家信息
	 * @param player
	 * @return
	 */
	public static SimpleRoleInfo converterSimpleRoleInfo(RoleInfo roleInfo, Alliance alliance,String allianceTitle,String allianceTitleName,String governmentTitle) {
		SimpleRoleInfo simpleRoleInfo = new SimpleRoleInfo();
		simpleRoleInfo.setImg(roleInfo.getBasics().getHeadImg());
		if(null != alliance){
			simpleRoleInfo.setAllianceId(alliance.getAllianceId());
			simpleRoleInfo.setAllianceName(alliance.getAllianceName());
			simpleRoleInfo.setAllianceAbbr(alliance.getAbbr());
		}else{
			simpleRoleInfo.setAllianceId(0);
			simpleRoleInfo.setAllianceName("");
			simpleRoleInfo.setAllianceAbbr("");
		}
		simpleRoleInfo.setAllianceTitle(allianceTitle);
		simpleRoleInfo.setAllianceTitleName(allianceTitleName);
		simpleRoleInfo.setGovernmentTitle(governmentTitle);
		simpleRoleInfo.setLevel(roleInfo.getCommanderData().getLevel());
		simpleRoleInfo.setMilitaryLevel(0);
		simpleRoleInfo.setName(roleInfo.getBasics().getRoleName());
		simpleRoleInfo.setPlayerId(roleInfo.getBasics().getRoleId());
		simpleRoleInfo.setServerId(roleInfo.getBasics().getServerArea());
		simpleRoleInfo.setUpdateTime(System.currentTimeMillis());
		simpleRoleInfo.setUserName(roleInfo.getBasics().getUserName());
		simpleRoleInfo.setVip(roleInfo.getVipInfo().getVipLevel());
		
		return simpleRoleInfo;
	}
	
	/**
	 * 转换player
	 * @return
	 */
	public static String converterSimplePlayer(SimpleRoleInfo simpleRoleInfo) {
		return JsonUtil.toJSON(simpleRoleInfo);
	}
	
}
