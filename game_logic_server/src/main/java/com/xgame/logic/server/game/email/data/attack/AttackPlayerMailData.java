package com.xgame.logic.server.game.email.data.attack;

import java.util.ArrayList;
import java.util.List;

import com.xgame.logic.server.game.email.data.TalkMailData;

import io.protostuff.Tag;

/**
 *
 * 2017-1-10 10:08:06
 *
 * @author ye.yuan
 *
 */
public class AttackPlayerMailData extends TalkMailData{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4198150663852277669L;

	/**
	 * 主动者损失战力
	 */
	@Tag(50)
	private int senderLost;
	
	/**
	 * 被动者损失战力
	 */
	@Tag(51)
	private int receiverLost;

	/**
	 * 胜利方playerId
	 */
	@Tag(52)
	private long winderId;

	/**
	 * 金钱
	 */
	@Tag(54)
	private long resMoney;

	/**
	 * 石油
	 */
	@Tag(55)
	private long resOil;

	/**
	 * 稀土
	 */
	@Tag(56)
	private long resRare;

	/**
	 * 钢材
	 */
	@Tag(57)
	private long resSteel;

	/**
	 * 主动者剩余士兵
	 */
	@Tag(58)
	private List<MailSoldier> attackSoldierList = new ArrayList<>();
	
	/**
	 * 被动者剩余士兵
	 */
	@Tag(59)
	private List<MailSoldier> defenSoldierList = new ArrayList<>();
	
	/**
	 * 主动id
	 */
	@Tag(60)
	private long activePlayerId;
	
	/**
	 * 被动id
	 */
	@Tag(61)
	private long passivePlayerId;

	public int getSenderLost() {
		return senderLost;
	}

	public void setSenderLost(int senderLost) {
		this.senderLost = senderLost;
	}

	public int getReceiverLost() {
		return receiverLost;
	}

	public void setReceiverLost(int receiverLost) {
		this.receiverLost = receiverLost;
	}

	public long getWinderId() {
		return winderId;
	}

	public void setWinderId(long winderId) {
		this.winderId = winderId;
	}

	public long getResMoney() {
		return resMoney;
	}

	public void setResMoney(long resMoney) {
		this.resMoney = resMoney;
	}

	public long getResOil() {
		return resOil;
	}

	public void setResOil(long resOil) {
		this.resOil = resOil;
	}

	public long getResRare() {
		return resRare;
	}

	public void setResRare(long resRare) {
		this.resRare = resRare;
	}

	public long getResSteel() {
		return resSteel;
	}

	public void setResSteel(long resSteel) {
		this.resSteel = resSteel;
	}

	public List<MailSoldier> getAttackSoldierList() {
		return attackSoldierList;
	}

	public void setAttackSoldierList(List<MailSoldier> attackSoldierList) {
		this.attackSoldierList = attackSoldierList;
	}

	public List<MailSoldier> getDefenSoldierList() {
		return defenSoldierList;
	}

	public void setDefenSoldierList(List<MailSoldier> defenSoldierList) {
		this.defenSoldierList = defenSoldierList;
	}

	public long getActivePlayerId() {
		return activePlayerId;
	}

	public void setActivePlayerId(long activePlayerId) {
		this.activePlayerId = activePlayerId;
	}

	public long getPassivePlayerId() {
		return passivePlayerId;
	}

	public void setPassivePlayerId(long passivePlayerId) {
		this.passivePlayerId = passivePlayerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
