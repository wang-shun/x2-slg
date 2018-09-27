package com.xgame.logic.server.game.alliance;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.config.building.BuildingPir;
import com.xgame.config.building.BuildingPirFactory;
import com.xgame.logic.server.core.db.cache.cache.CacheProxy;
import com.xgame.logic.server.core.utils.CurrencyUtil;
import com.xgame.logic.server.core.utils.InjectorUtil;
import com.xgame.logic.server.game.alliance.constant.AllianceConstant;
import com.xgame.logic.server.game.alliance.converter.AllianceConverter;
import com.xgame.logic.server.game.alliance.enity.Alliance;
import com.xgame.logic.server.game.alliance.enity.AllianceTeam;
import com.xgame.logic.server.game.alliance.enity.AllianceTitle;
import com.xgame.logic.server.game.alliance.enity.PlayerAlliance;
import com.xgame.logic.server.game.alliance.message.ResNotifyPermissionChangeMessage;
import com.xgame.logic.server.game.country.CountryManager;
import com.xgame.logic.server.game.country.structs.build.BuildControl;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.utils.TimeUtils;

/**
 * 玩家联盟管理器
 * @author jacky.jiang
 *
 */
@Component
public class PlayerAllianceManager extends CacheProxy<PlayerAlliance> {
	
	@Autowired
	private CountryManager countryManager;

	@Override
	public Class<?> getProxyClass() {
		return PlayerAlliance.class;
	}
	
	/**
	 * 获取玩家联盟数据
	 * @param playerId
	 * @return
	 */
	public PlayerAlliance getOrCreate(long playerId) {
		
		PlayerAlliance playerAlliance = InjectorUtil.getInjector().dbCacheService.get(PlayerAlliance.class, playerId);
		if (playerAlliance == null) {
			playerAlliance = new PlayerAlliance();
			playerAlliance.setPlayerId(playerId);
			playerAlliance.setFirstRewards(true);
			playerAlliance.setRefreshTime(System.currentTimeMillis());
			if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				playerAlliance.setWeekRefreshTime(TimeUtils.getMondayTimeMillis()-TimeUtils.WEEK_MILLIS);//如果是周日取刷新时间为上周一的时间
			}else{
				playerAlliance.setWeekRefreshTime(TimeUtils.getMondayTimeMillis());//设置周刷新时间为当周星期一0点
			}
			InjectorUtil.getInjector().dbCacheService.create(playerAlliance);
		}
		
		synchronized(playerAlliance) {
			boolean update  = playerAlliance.refresh();
			if(update) {
				//根据玩家军团商店等级增加贡献
				if(playerAlliance.getAllianceId() > 0){
					BuildControl buildControl = countryManager.getBuildControl(AllianceConstant.ALLIANCE_SHOP_BUILD_ID);
					if(buildControl != null && buildControl.getDefianlBuild() != null){
						int level = buildControl.getDefianlBuild().getLevel();
						BuildingPir buildingPir = BuildingPirFactory.get(AllianceConstant.ALLIANCE_SHOP_BUILD_ID);
						List<Integer> list = buildingPir.getV1();
						int donate = list.get(level-1);
						playerAlliance.addDonate(donate);
						Player player = InjectorUtil.getInjector().dbCacheService.get(Player.class, playerAlliance.getPlayerId());
						CurrencyUtil.send(player);
					}
				}
				
				InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			}
		}
		
		return playerAlliance;
	}
	
	/**
	 * 切换玩家队伍编号
	 * @param player
	 * @param targetPlayerAlliance
	 * @param teamId
	 */
	public void changePlayerTeamId(Player player, PlayerAlliance targetPlayerAlliance, String teamId) {
		player.async(new Runnable() {
			@Override
			public void run() {
				targetPlayerAlliance.setTeamId(teamId);
				InjectorUtil.getInjector().dbCacheService.update(targetPlayerAlliance);
			}
		});
	}
	
	/**
	 * 卸下军团长
	 * @param player
	 * @param playerAlliance
	 */
	public void changeAllianceLeader(Player player, PlayerAlliance playerAlliance) {
		player.async(new Runnable() {
			@Override
			public void run() {
				playerAlliance.changeLeader();
				InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
			}
		});
	}
	
	/**
	 * 设置军团长权限
	 * @param player
	 * @param playerAlliance
	 */
	public void changeLeaderPermission(Player player, PlayerAlliance playerAlliance) {
		player.async(new Runnable() {
			@Override
			public void run() {
				playerAlliance.setAlliancePermission(playerAlliance.newLeaderPermission());
				InjectorUtil.getInjector().dbCacheService.update(playerAlliance);
				
				// 同通知权限变更
				ResNotifyPermissionChangeMessage resNotifyPermissionChangeMessage = new ResNotifyPermissionChangeMessage();
				resNotifyPermissionChangeMessage.allianceMemberBean = AllianceConverter.converterAllianceMember(player, playerAlliance);
				player.send(resNotifyPermissionChangeMessage);
			}
		});
	}
	
	/**
	 * 获取军团头衔
	 * @param playerId
	 * @return
	 */
	public String[] getAllianceTitle(long playerId) {
		PlayerAlliance playerAlliance = this.getOrCreate(playerId);
		Alliance alliance = InjectorUtil.getInjector().allianceManager.get(playerAlliance.getAllianceId());
		String[] strArr = new String[]{"",""};
		if(alliance != null){
			AllianceTitle allianceTitle = alliance.getAllianceTitle();
			Map<String, AllianceTeam> teamMap = alliance.getTeamMap();
			//allianceTitle ： 1，2，3，4，5，6，7，8，9
			//allianceTitleName：内务大臣，外交大臣，招聘大臣，不败战神，第一战队队长，第二战队队长，第三战队队长，第四战队队长，盟主
			if(alliance.getLeaderId() == playerId){
				if("".equals(strArr[0])){
					strArr[0] = "9";
					strArr[1] = ",";
				}else{
					strArr[0] = strArr[0]+",9";
					strArr[1] = strArr[1]+",";
				}
			} 
			if (alliance.getOffice1Player() == playerId) {
				if("".equals(strArr[0])){
					strArr[0] = "1";
					strArr[1] = allianceTitle.getOffice1Name()==null?",":(allianceTitle.getOffice1Name()+",");
				}else{
					strArr[0] = strArr[0]+",1";
					strArr[1] = strArr[1]+(allianceTitle.getOffice1Name()==null?",":(allianceTitle.getOffice1Name()+","));
				}
			} 
			if (alliance.getOffice2Player() == playerId) {
				if("".equals(strArr[0])){
					strArr[0] = "2";
					strArr[1] = allianceTitle.getOffice2Name()==null?",":(allianceTitle.getOffice2Name()+",");
				}else{
					strArr[0] = strArr[0]+",2";
					strArr[1] = strArr[1]+(allianceTitle.getOffice2Name()==null?",":(allianceTitle.getOffice2Name()+","));
				}
			} 
			if (alliance.getOffice3Player() == playerId) {
				if("".equals(strArr[0])){
					strArr[0] = "3";
					strArr[1] = allianceTitle.getOffice3Name()==null?",":(allianceTitle.getOffice3Name()+",");
				}else{
					strArr[0] = strArr[0]+",3";
					strArr[1] = strArr[1]+(allianceTitle.getOffice3Name()==null?",":(allianceTitle.getOffice3Name()+","));
				}
			} 
			if (alliance.getOffice4Player() == playerId) {
				if("".equals(strArr[0])){
					strArr[0] = "4";
					strArr[1] = allianceTitle.getOffice4Name()==null?",":(allianceTitle.getOffice4Name()+",");
				}else{
					strArr[0] = strArr[0]+",4";
					strArr[1] = strArr[1]+(allianceTitle.getOffice4Name()==null?",":(allianceTitle.getOffice4Name()+","));
				}
			}
			for (AllianceTeam allianceTeam : teamMap.values()) {
				if (allianceTeam.getTeamLeaderId() == playerId) {
					if("".equals(strArr[0])){
						strArr[0] = ""+(Integer.valueOf(allianceTeam.getTeamId().split("#")[1])+4);
						strArr[1] = allianceTeam.getTeamName()==null?",":(allianceTeam.getTeamName()+",");
					}else{
						strArr[0] = strArr[0]+","+(Integer.valueOf(allianceTeam.getTeamId().split("#")[1])+4);
						strArr[1] = strArr[1]+(allianceTeam.getTeamName()==null?",":(allianceTeam.getTeamName()+","));
					}
				}
			}
		}
		if(!"".equals(strArr[1])){
			strArr[1] = strArr[1].substring(0,strArr[1].lastIndexOf(","));
		}
		return strArr;
	}
}
