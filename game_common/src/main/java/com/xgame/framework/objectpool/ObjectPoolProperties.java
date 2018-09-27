package com.xgame.framework.objectpool;

public class ObjectPoolProperties {
	public int maxTotal = 1024; // 最大数
	public int minIdle = 100; // 最小空闲
	public int maxIdle = 200; // 最大空闲
	public int maxWait = 3000; // 最大等待时间
	public boolean blockWhenExhausted = true;// 池对象耗尽之后是否阻塞,maxWait<0时一直等待
	public boolean testOnBorrow = true;// 取对象是验证
	public boolean testOnReturn = true; // 回收验证
	public boolean testOnCreate = true;// 创建时验证
	public boolean testWhileIdle = false;// 空闲验证
	public boolean lifo = false;// 后进先出
}
