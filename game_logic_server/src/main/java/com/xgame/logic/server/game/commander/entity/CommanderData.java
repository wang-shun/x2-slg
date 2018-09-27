package com.xgame.logic.server.game.commander.entity;

import io.protostuff.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;

/**
 *指挥官存储数据
 *2016-10-09  11:15:43
 *@author ye.yuan
 *
 */
public class CommanderData implements Serializable, JBaseTransform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	private String name;

	@Tag(2)
	private int level;
	
	
	@Tag(3)
	private long exp;
	
	/**
	 * 统帅点
	 */
	@Tag(4)
	private int cPoint;
	
	/**
	 * 体力
	 */
	@Tag(5)
	private int energy;
	
	/**
	 * 形象id
	 */
	@Tag(6)
	private int style;
	
	/**
	 * 已学天赋点
	 */
	@Tag(7)
	private int talentPoints;
	
	/**
	 * 使用天赋页
	 */
	@Tag(8)
	private int useTalentPage;
	
	/**
	 * 以穿戴装备
	 */
	@Tag(9)
	private Map<Integer, Long> equits = new ConcurrentHashMap<>();
	
	/**
	 * 可以切换的天赋页
	 */
	@Tag(10)
	private Map<Integer,TalentsPage> talents = new ConcurrentHashMap<Integer,TalentsPage>(2);
	
	/**
	 * 上一次体力增加时间
	 */
	@Tag(11)
	private long addEnergyTime;
	
	/**
	 * 指挥官经验增幅时间
	 */
	@Tag(12)
	private long expAddTime;
	
	/**
	 * 指挥官经验增幅比列
	 */
	@Tag(13)
	private double expAddPercent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public int getcPoint() {
		return cPoint;
	}

	public void setcPoint(int cPoint) {
		this.cPoint = cPoint;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public int getTalentPoints() {
		return talentPoints;
	}

	public void setTalentPoints(int talentPoints) {
		this.talentPoints = talentPoints;
	}

	public int getUseTalentPage() {
		return useTalentPage;
	}

	public void setUseTalentPage(int useTalentPage) {
		this.useTalentPage = useTalentPage;
	}

	public Map<Integer, Long> getEquits() {
		return equits;
	}

	public void setEquits(Map<Integer, Long> equits) {
		this.equits = equits;
	}

	public Map<Integer, TalentsPage> getTalents() {
		return talents;
	}

	public void setTalents(Map<Integer, TalentsPage> talents) {
		this.talents = talents;
	}

	public long getAddEnergyTime() {
		return addEnergyTime;
	}

	public void setAddEnergyTime(long addEnergyTime) {
		this.addEnergyTime = addEnergyTime;
	}

	public long getExpAddTime() {
		return expAddTime;
	}

	public void setExpAddTime(long expAddTime) {
		this.expAddTime = expAddTime;
	}

	public double getExpAddPercent() {
		return expAddPercent;
	}

	public void setExpAddPercent(double expAddPercent) {
		this.expAddPercent = expAddPercent;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("name", name);
		jBaseData.put("level", level);
		jBaseData.put("exp", exp);
		jBaseData.put("cPoint", cPoint);
		jBaseData.put("energy", energy);
		jBaseData.put("style", style);
		jBaseData.put("talentPoints", talentPoints);
		jBaseData.put("useTalentPage", useTalentPage);
		List<JBaseData> onEquip = new ArrayList<JBaseData>();
		for(Entry<Integer, Long> entry : equits.entrySet()) {
			JBaseData equipJBaseData = new JBaseData();
			equipJBaseData.put("soltId", entry.getKey());
			equipJBaseData.put("userEquipId", entry.getValue());
			onEquip.add(equipJBaseData);
		}
		jBaseData.put("equits", onEquip);
		
		// 
		List<JBaseData> talents = new ArrayList<JBaseData>();
		for(TalentsPage talentsPage : this.talents.values()) {
			talents.add(talentsPage.toJBaseData());
		}
		jBaseData.put("talents", talents);
		
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.name = jBaseData.getString("name", "");
		this.level = jBaseData.getInt("level", 0);
		this.exp = jBaseData.getLong("exp", 0);
		this.cPoint = jBaseData.getInt("cPoint", 0);
		this.energy = jBaseData.getInt("energy", 0);
		this.style = jBaseData.getInt("style", 0);
		this.talentPoints = jBaseData.getInt("talentPoints", 0);
		this.useTalentPage = jBaseData.getInt("useTalentPage", 0);
		
		List<JBaseData> onEquip = jBaseData.getSeqBaseData("equits");
		for(JBaseData jBaseData2 : onEquip) {
			this.equits.put(jBaseData2.getInt("soltId", 0), jBaseData2.getLong("userEquipId", 0));
		}
		
		List<JBaseData> talents = jBaseData.getSeqBaseData("talents");
		for(JBaseData jBaseData2 : talents) {
			TalentsPage talentsPage = new TalentsPage();
			talentsPage.fromJBaseData(jBaseData2);
			this.talents.put(talentsPage.getId(), talentsPage);
		}
	}
	
}
