package com.xgame.logic.server.core.utils.sort;



/**
 * 比较器
 * @author jiangxt
 *
 */
public class CompareBuilder {
	
	private int result;
	private boolean resultOut;
	
	public CompareBuilder() {
		this.result = 0;
		this.resultOut = false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CompareBuilder asc(Comparable left, Comparable right) {
		if(!(this.resultOut)){
			int c = left.compareTo(right);
			if(c != 0) {
				this.resultOut = true;
			}
		}
		return this;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CompareBuilder desc(Comparable left, Comparable right) {
		if(!(this.resultOut)){
			int c = left.compareTo(right);
			if(c != 0) {
				this.resultOut = true;
				this.result = ((c==-1)? 1:-1);
			}
		}
		return this;
	}


	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public boolean isResultOut() {
		return resultOut;
	}

	public void setResultOut(boolean resultOut) {
		this.resultOut = resultOut;
	}
	
	public int compare() {
		return result;
	}
}
