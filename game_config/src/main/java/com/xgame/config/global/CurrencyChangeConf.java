package com.xgame.config.global;

import com.singularsys.jep.Jep;

/**
 *
 *2016-8-11  12:14:08
 *@author ye.yuan  expression: (x-minOdds)*0.8+minOdds;
 *
 */
public class CurrencyChangeConf {
	
	private int minRange;
	
	private int maxRange;
	
	private Jep jep = new Jep();
	
	public CurrencyChangeConf(String expression) {
		try {
			jep.parse("(x-y)/z+n");
			String[] split = expression.split(";");
			minRange = Integer.parseInt(split[0]);
			maxRange = Integer.parseInt(split[1]);
			jep.addVariable("y", minRange);
			jep.addVariable("z", Double.parseDouble(split[2]));
			jep.addVariable("n", Double.parseDouble(split[3]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMinRange() {
		return minRange;
	}

	public void setMinRange(int minRange) {
		this.minRange = minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}

	public Jep getJep() {
		return jep;
	}

	public void setJep(Jep jep) {
		this.jep = jep;
	}
}
