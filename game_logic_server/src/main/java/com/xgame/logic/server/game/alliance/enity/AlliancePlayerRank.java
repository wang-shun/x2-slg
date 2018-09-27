package com.xgame.logic.server.game.alliance.enity;

/**
 * 联盟玩家排行
 * @author jacky.jiang
 *
 */
public class AlliancePlayerRank {
	
	/**
	 * 联盟id
	 */
	private long allianeId;
	
	/**
	 * 联盟捐献
	 */
	private AlliancePlayerDonateRank alliancePlayerDonateRank = new AlliancePlayerDonateRank();
	
	/**
	 * 最大战力排行
	 */
	private AlliancePlayerFightPowerRank alliancePlayerFightPowerRank = new AlliancePlayerFightPowerRank();
	
	/**
	 * 联盟击杀排行榜
	 */
	private AlliancePlayerKilledRank alliancePlayerKilledRank = new AlliancePlayerKilledRank();

	public long getAllianeId() {
		return allianeId;
	}

	public void setAllianeId(long allianeId) {
		this.allianeId = allianeId;
	}

	public AlliancePlayerDonateRank getAlliancePlayerDonateRank() {
		return alliancePlayerDonateRank;
	}

	public void setAlliancePlayerDonateRank(
			AlliancePlayerDonateRank alliancePlayerDonateRank) {
		this.alliancePlayerDonateRank = alliancePlayerDonateRank;
	}

	public AlliancePlayerFightPowerRank getAlliancePlayerFightPowerRank() {
		return alliancePlayerFightPowerRank;
	}

	public void setAlliancePlayerFightPowerRank(AlliancePlayerFightPowerRank alliancePlayerFightPowerRank) {
		this.alliancePlayerFightPowerRank = alliancePlayerFightPowerRank;
	}

	public AlliancePlayerKilledRank getAlliancePlayerKilledRank() {
		return alliancePlayerKilledRank;
	}

	public void setAlliancePlayerKilledRank(AlliancePlayerKilledRank alliancePlayerKilledRank) {
		this.alliancePlayerKilledRank = alliancePlayerKilledRank;
	}
}
