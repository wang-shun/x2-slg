package com.xgame.playersearch.util;

public enum CurrencyTypeEnum {
	
	STEEL("钢铁"),OIL("石油"),GOLD("黄金"),RARE("稀土"),DIAMOND("钻石");
	
	private String describe;
	
	private CurrencyTypeEnum(String describe){
		this.describe = describe;
	}
	
	public String getDescribe(){
		return this.describe;
	}
}
