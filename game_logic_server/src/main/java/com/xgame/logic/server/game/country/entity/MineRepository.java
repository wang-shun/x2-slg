package com.xgame.logic.server.game.country.entity;

import io.protostuff.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;


/**
 * 仓库临时资源
 * @author jacky.jiang
 *
 */
public class MineRepository implements JBaseTransform {
	
	// 石油
	@Tag(1)
	private volatile double oil;
	
	// 稀土
	@Tag(2)
	private volatile double rare;

	// 钻石
	@Tag(3)
	private volatile double money;
	
	// 体力
	@Tag(4)
	private volatile double steel;
	
	@Tag(5)
	private Map<Integer, MineCar> mineCarRepository = new ConcurrentHashMap<Integer, MineCar>();
	
	public void addOil(long oil, long addTime) {
		this.oil += oil;
	}
	
	public void addRare(long rare, long addTime) {
		this.rare += rare;
	}
	
	public void addMoney(long diamond, long addTime) {
		this.money += diamond;
	}
	
	public void addSteel(long steel, long addTime) {
		this.steel += steel;
	}

	public void setSteel(long steel) {
		this.steel = steel;
	}

	public Map<Integer, MineCar> getMineCarRepository() {
		return mineCarRepository;
	}

	public void setMineCarRepository(Map<Integer, MineCar> mineCarRepository) {
		this.mineCarRepository = mineCarRepository;
	}
	
	public double getOil() {
		return oil;
	}

	public void setOil(double oil) {
		this.oil = oil;
	}

	public double getRare() {
		return rare;
	}

	public void setRare(double rare) {
		this.rare = rare;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getSteel() {
		return steel;
	}

	public void setSteel(double steel) {
		this.steel = steel;
	}

	public MineCar getOrCreate(int uid) {
		MineCar mineCar = mineCarRepository.get(uid);
		if(mineCar == null) {
			mineCar =new MineCar();
			mineCar.setUid(uid);
			mineCarRepository.put(uid, mineCar);
		}
		return mineCar;
	}
	
	public MineCar getCar(int uid) {
		MineCar mineCar = mineCarRepository.get(uid);
		return mineCar;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("oil", oil);
		jBaseData.put("rare", rare);
		jBaseData.put("money", money);
		jBaseData.put("steel", steel);

		List<JBaseData> mineCarRepository = new ArrayList<JBaseData>();
		for(MineCar mineCar :this.mineCarRepository.values()) {
			mineCarRepository.add(mineCar.toJBaseData());
		}
		
		jBaseData.put("mineCarRepository", mineCarRepository);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.oil = jBaseData.getDouble("oil", 0);
		this.rare = jBaseData.getDouble("rare", 0);
		this.money = jBaseData.getDouble("money", 0);
		this.steel = jBaseData.getDouble("steel", 0);
		
		List<JBaseData> mineBaseDatas = jBaseData.getSeqBaseData("mineCarRepository");
		for(JBaseData jBaseData2 : mineBaseDatas) {
			MineCar mineCar = new MineCar();
			mineCar.fromJBaseData(jBaseData2);
			this.mineCarRepository.put(mineCar.getUid(), mineCar);
		}
	}
}
