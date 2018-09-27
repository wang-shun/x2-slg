package com.xgame.logic.server.game.allianceext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xgame.logic.server.core.net.gate.SessionManager;
import com.xgame.logic.server.core.utils.sequance.IDFactrorySequencer;
import com.xgame.logic.server.core.utils.sequance.IDSequance;
import com.xgame.logic.server.game.alliance.AllianceManager;
import com.xgame.logic.server.game.alliance.PlayerAllianceManager;
import com.xgame.logic.server.game.world.SpriteManager;
import com.xgame.logic.server.game.world.WorldLogicManager;


/**
 * 联盟建筑管理器
 * @author jacky.jiang
 *
 */
@Component
public class AllianceBuildManager{
	
	@Autowired
	private SpriteManager spriteManager;
	@Autowired
	private PlayerAllianceManager playerAllianceManager;
	@Autowired
	private WorldLogicManager worldLogicManager;
	@Autowired
	private AllianceManager allianceManager;
	@Autowired
	private IDFactrorySequencer iDFactrorySequencer;
	@Autowired
	private IDSequance idSequance;
	@Autowired
	private SessionManager sessionManager;
	
//	/**
//	 * 创建联盟建筑
//	 * @param player
//	 * @param buildId
//	 * @param x
//	 * @param y
//	 */
//	public void createAllianceBuild(Player player, int buildId, int x, int y)  {
//		
//		//建筑创建前提条件判断
//		//1.配置检查
//		ArmyBuildingPir armyBuildingPir = ArmyBuildingPirFactory.get(buildId);
//		if(armyBuildingPir == null) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyBuildingPir.class.getSimpleName(),buildId);
//			return;
//		}
//		//2.人员权限
//		Alliance alliance = allianceManager.getRefreshAlliance(player.getAllianceId());
//		if(null == alliance){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE24);
//			return;
//		}
//		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
//		if(!playerAlliance.getAlliancePermission().isCreateAllianceBuild()) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
//			return;
//		}
//		//3.判断是否已有对应联盟建筑
//		List<String> buildingList = alliance.getBuildingList();
//		for(String buildUId : buildingList){
//			AllianceBuild allianceBuild= this.get(buildUId);
//			if(allianceBuild.getBuildTid() == buildId){
//				Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE4);
//				return;
//			}
//		}
//		//4.联盟等级及资金判断
//		if(alliance.getLevel() < armyBuildingPir.getNeedLv()){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE2);
//			return;
//		}
//		List<Integer> list = armyBuildingPir.getLvUpArmyMoney();
//		if(alliance.getCash() < list.get(0)){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE14);
//			return;
//		}
//		//5.坐标有效性判断
//		if(x >= 511 || x<=0 || y >= 511 || y<=0) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE23);
//			return;
//		}
//		SpriteInfo targetSprite = spriteManager.getGrid(x, y);
//		if(!spriteManager.canUseGrid(targetSprite.getIndex())) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE1);
//			return;
//		}
//		AllianceTerritory allianceTerritory = alliance.getAllianceTerritory();
//		if(allianceTerritory == null || allianceTerritory.getTerritory() == null || !allianceTerritory.getTerritory().contains(targetSprite.getIndex())){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE3);
//			return;
//		}
//		//创建联盟建筑数据
//		AllianceBuild allianceBuild = new AllianceBuild();
//		allianceBuild.setAllianceId(alliance.getId());
//		allianceBuild.setBuildTid(armyBuildingPir.getID());
//		long id = iDFactrorySequencer.createEnityID(DBKey.ALLIANCE_BUILD_KEY);
//		allianceBuild.setUid(id);
//		allianceBuild.setLevel(1);
//		allianceBuild.setCreateTime(System.currentTimeMillis());
//		allianceBuild.setId(String.format("%s:%s", alliance.getId(), id));
//		Vector2Bean vector2Bean =new Vector2Bean();
//		vector2Bean.x = x;
//		vector2Bean.y = y;
//		allianceBuild.setPosition(vector2Bean);
//		allianceBuild = InjectorUtil.getInjector().dbCacheService.create(allianceBuild);
//		//加入联盟建筑信息列表
//		alliance.addBuilding(allianceBuild.getId());
//		//联盟资金扣减
//		alliance.setCash(alliance.getCash() - list.get(0));
//		
//		//由于超级矿有持续时间 需要放入队列进行管理
//		if(armyBuildingPir.getBuildingType() == 4){
//			String existTime = armyBuildingPir.getV2();
//			SystemTimerTaskHolder.getTimerTask(SystemTimerCommand.ALLIANCE_BUILD).register((int)(Long.valueOf(existTime)/1000),allianceBuild.getId());
//		}
//		//创建精灵 刷新世界
//		AllianceBuildSprite allianceBuildSprite = new AllianceBuildSprite();
//		allianceBuildSprite.setAllianceId(playerAlliance.getAllianceId());
//		allianceBuildSprite.setBuildUid(id);
//		allianceBuildSprite.setLevel(1);
//		//替换格子上的精灵信息
//		long uid = InjectorUtil.getInjector().idSequance.genId();
//		SpriteInfo newSpriteInfo = SpriteInfo.valueOf(uid, targetSprite.getIndex(), SpriteType.BUILDING, allianceBuildSprite);
//		spriteManager.transferGridToSprite(x, y, newSpriteInfo, true, true);
//		
//		//推送创建消息 通知联盟成员建筑创建信息 返回创建结果
//		ResCreateAllianceBuildMessage resCreateAllianceBuildMessage = new ResCreateAllianceBuildMessage();
//		resCreateAllianceBuildMessage.allianceBuild.add(AllianceBuildBeanConverter.converterAllianceBuildBean(allianceBuild));
//		sessionManager.writePlayers(alliance.getAllianceMember(), resCreateAllianceBuildMessage);
//		//更新世界地图信息
//		worldLogicManager.pushToWorldSprites(newSpriteInfo, Lists.newArrayList(player.getId()));
//		//建筑创建日志记录
//		EventBus.getSelf().fireEvent(new AllianceBuildCreateEventObject(player,allianceBuild.getBuildTid(),allianceBuild.getPosition()));
//	}
//	
//	/**
//	 * 移动联盟建筑
//	 * @param player
//	 * @param buildId
//	 * @param x
//	 * @param y
//	 */
//	public void moveAllianceBuild(Player player, String uid, int x, int y) {
//		//1.人员权限
//		Alliance alliance = allianceManager.getRefreshAlliance(player.getAllianceId());
//		if(null == alliance){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE24);
//			return;
//		}
//		PlayerAlliance playerAlliance = playerAllianceManager.getOrCreate(player.getId());
//		if(!playerAlliance.getAlliancePermission().isCreateAllianceBuild()) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE15);
//			return;
//		}
//		
//		//2.建筑所属判断
//		if(!alliance.getBuildingList().contains(uid)){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE5);
//			return;
//		}
//		
//		AllianceBuild allianceBuild = this.get(uid);
//		if(null == allianceBuild){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE6);
//			return;
//		}
//		//3.配置判断
//		int buildTid = allianceBuild.getBuildTid();
//		ArmyBuildingPir armyBuildingPir = ArmyBuildingPirFactory.get(buildTid);
//		if(armyBuildingPir == null) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE6,ArmyBuildingPir.class.getSimpleName(),buildTid);
//			return;
//		}
//		
//		//4.联盟资金判断
//		if(alliance.getCash() < armyBuildingPir.getNeedArmyMoney2()){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1007_ALLIANCE.CODE14);
//			return;
//		}
//		
//		//5.坐标有效性判断
//		if(x >= 511 || x<=0 || y >= 511 || y<=0) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E001_LOGIN.CODE23);
//			return;
//		}
//		SpriteInfo targetSprite = spriteManager.getGrid(x, y);
//		if(!spriteManager.canUseGrid(targetSprite.getIndex())) {
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE1);
//			return;
//		}
//		AllianceTerritory allianceTerritory = alliance.getAllianceTerritory();
//		if(allianceTerritory == null || allianceTerritory.getTerritory() == null || !allianceTerritory.getTerritory().contains(targetSprite.getIndex())){
//			Language.ERRORCODE.send(player, ErrorCodeEnum.E1100_ALLIANCEEXT.CODE3);
//			return;
//		}
//		
//		SpriteInfo sourceSprite = spriteManager.getGrid(allianceBuild.getPosition().x,allianceBuild.getPosition().y);
//		// 把之前的格子上的东西去掉
//		long spriteUid = idSequance.genId();
//		WorldSprite worldSprite = new WorldSprite();
//		worldSprite.setAllianceId(alliance.getId());
//		SpriteInfo newSprite = SpriteInfo.valueOf(spriteUid, sourceSprite.getIndex(), SpriteType.NONE, worldSprite);
//		spriteManager.transferGridToSprite(sourceSprite.getX(), sourceSprite.getY(), newSprite, true, true);
//		// 在当前格子上放置建筑
//		spriteManager.transferGridToSprite(x, y, sourceSprite, true, true);
//		
//		Vector2Bean vector2Bean =new Vector2Bean();
//		vector2Bean.x = x;
//		vector2Bean.y = y;
//		allianceBuild.setPosition(vector2Bean);
//		InjectorUtil.getInjector().dbCacheService.update(allianceBuild);
//		
//		//推送创建消息 通知联盟成员建筑创建信息 返回创建结果
//		ResMoveAllianceBuildMessage resMoveAllianceBuildMessage = new ResMoveAllianceBuildMessage();
//		resMoveAllianceBuildMessage.allianceBuildBean.add(AllianceBuildBeanConverter.converterAllianceBuildBean(allianceBuild));
//		sessionManager.writePlayers(alliance.getAllianceMember(), resMoveAllianceBuildMessage);
//		//更新世界地图信息
//		worldLogicManager.pushToWorldSprites(newSprite, Lists.newArrayList(player.getId()));
//		worldLogicManager.pushToWorldSprites(sourceSprite, Lists.newArrayList(player.getId()));
//		//建筑移动日志记录
//		EventBus.getSelf().fireEvent(new AllianceBuildMoveEventObject(player,allianceBuild.getBuildTid(),allianceBuild.getPosition()));
//	}
//	
//	/**
//	 * 删除联盟建筑
//	 * @param id
//	 */
//	public void removeAllianceBuild(Player player,String id){
//		AllianceBuild allianceBuild = this.get(id);
//		if(null == allianceBuild){
//			return;
//		}
//		Alliance alliance = allianceManager.getRefreshAlliance(allianceBuild.getAllianceId());
//		if(null == alliance){
//			return;
//		}
//		SpriteInfo sourceSprite = spriteManager.getGrid(allianceBuild.getPosition().x,allianceBuild.getPosition().y);
//		// 把之前的格子上的东西去掉
//		long spriteUid = idSequance.genId();
//		WorldSprite worldSprite = new WorldSprite();
//		worldSprite.setAllianceId(allianceBuild.getAllianceId());
//		SpriteInfo newSprite = SpriteInfo.valueOf(spriteUid, sourceSprite.getIndex(), SpriteType.NONE, worldSprite);
//		spriteManager.transferGridToSprite(sourceSprite.getX(), sourceSprite.getY(), newSprite, true, true);
//		//删除联盟建筑信息
//		alliance.getBuildingList().remove(id);
//		InjectorUtil.getInjector().dbCacheService.update(alliance);
//		//删除建筑
//		InjectorUtil.getInjector().dbCacheService.delete(allianceBuild);
//		//推送建筑删除消息给联盟玩家
//		ResDeleteAllianceBuildMessage resDeleteAllianceBuildMessage = new ResDeleteAllianceBuildMessage();
//		resDeleteAllianceBuildMessage.allianceBuildBean.add(AllianceBuildBeanConverter.converterAllianceBuildBean(allianceBuild));
//		resDeleteAllianceBuildMessage.result = 1;
//		sessionManager.writePlayers(alliance.getAllianceMember(), resDeleteAllianceBuildMessage);
//		//更新世界地图信息
//		worldLogicManager.pushToWorldSprites(newSprite, null);
//		//建筑删除日志记录
//		EventBus.getSelf().fireEvent(new AllianceBuildDeleteEventObject(player,allianceBuild.getBuildTid(),allianceBuild.getPosition()));
//	}
//	
//	/**
//	 * 获取联盟建筑列表
//	 */
//	public void getAllianceBuildList(Player player){
//		
//	}
}
