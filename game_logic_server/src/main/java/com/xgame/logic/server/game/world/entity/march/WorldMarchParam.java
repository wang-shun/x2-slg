package com.xgame.logic.server.game.world.entity.march;

import java.util.List;

import com.xgame.logic.server.game.email.bean.EmailSignature;
import com.xgame.logic.server.game.player.entity.Player;
import com.xgame.logic.server.game.war.bean.WarResourceBean;
import com.xgame.logic.server.game.world.bean.WorldSimpleSoldierBean;
import com.xgame.logic.server.game.world.constant.MarchType;
import com.xgame.logic.server.game.world.entity.model.WorldMarchSoldier;

/**
 * 扣减
 * @author jacky.jiang
 *
 */
public class WorldMarchParam {

	/**
	 * 玩家信息
	 */
	private Player player;
	
	/**
	 * 目标点
	 */
	private int targetPoint;
	
	/**
	 * 行军类型
	 */
	private MarchType marchType;
	
	/**
	 * 部队士兵信息
	 */
	private List<WorldSimpleSoldierBean> soldierBriefBeans;
	
	/**
	 * 士兵信息
	 */
	private WorldMarchSoldier worldMarchSoldier;
	
	/**
	 * 队伍信息
	 */
	private String team;
	
	/**
	 * 集结等待时间
	 */
	private int remainTime;
	
	/**
	 * 资源类型
	 */
	private int resourceType;
	
	/**
	 * 防守方签名
	 */
	private EmailSignature defSignature;
	
	/**
	 * 行军前精灵类型
	 */
	private int spriteType;
	
	private WarResourceBean tradeResource;
	
	private boolean systemCreate;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getTargetPoint() {
		return targetPoint;
	}

	public void setTargetPoint(int targetPoint) {
		this.targetPoint = targetPoint;
	}

	public MarchType getMarchType() {
		return marchType;
	}

	public void setMarchType(MarchType marchType) {
		this.marchType = marchType;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public int getResourceType() {
		return resourceType;
	}

	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}

	public List<WorldSimpleSoldierBean> getSoldierBriefBeans() {
		return soldierBriefBeans;
	}

	public void setSoldierBriefBeans(List<WorldSimpleSoldierBean> soldierBriefBeans) {
		this.soldierBriefBeans = soldierBriefBeans;
	}

	public WorldMarchSoldier getWorldMarchSoldier() {
		return worldMarchSoldier;
	}

	public void setWorldMarchSoldier(WorldMarchSoldier worldMarchSoldier) {
		this.worldMarchSoldier = worldMarchSoldier;
	}

	public WarResourceBean getTradeResource() {
		return tradeResource;
	}

	public void setTradeResource(WarResourceBean tradeResource) {
		this.tradeResource = tradeResource;
	}

	public EmailSignature getDefSignature() {
		return defSignature;
	}

	public void setDefSignature(EmailSignature defSignature) {
		this.defSignature = defSignature;
	}

	public int getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(int spriteType) {
		this.spriteType = spriteType;
	}

	public boolean isSystemCreate() {
		return systemCreate;
	}

	public void setSystemCreate(boolean systemCreate) {
		this.systemCreate = systemCreate;
	}
	
}
