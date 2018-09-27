package com.xgame.logic.server.core.utils;

/**
 * z资源
 * @author jacky.jiang
 *
 */
public class DeductResourceContext {
	
	/**
	 *  扣减结果
	 */
	private boolean result;
	
	/**
	 * 扣减石油
	 */
	private int deductOil;
		
	/**
	 * 扣减稀土
	 */
	private int deductRare;
	
	/**
	 * 扣减钢铁
	 */
	private int deductSteel;
	
	/**
	 * 扣减黄静
	 */
	private int deductGold;
	
	/**
	 * 扣减钻石
	 */
	private int deductDiamond;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public int getDeductOil() {
		return deductOil;
	}

	public void setDeductOil(int deductOil) {
		this.deductOil = deductOil;
	}

	public int getDeductRare() {
		return deductRare;
	}

	public void setDeductRare(int deductRare) {
		this.deductRare = deductRare;
	}

	public int getDeductSteel() {
		return deductSteel;
	}

	public void setDeductSteel(int deductSteel) {
		this.deductSteel = deductSteel;
	}

	public int getDeductGold() {
		return deductGold;
	}

	public void setDeductGold(int deductGold) {
		this.deductGold = deductGold;
	}

	public int getDeductDiamond() {
		return deductDiamond;
	}

	public void setDeductDiamond(int deductDiamond) {
		this.deductDiamond = deductDiamond;
	}
	
}
