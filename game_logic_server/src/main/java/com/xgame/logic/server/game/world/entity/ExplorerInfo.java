package com.xgame.logic.server.game.world.entity;



/**
 * 采集信息
 * @author jacky.jiang
 *
 */
public class ExplorerInfo {
	
	/**
	 * 采集速度
	 */
	private double explorerSpeed;
	
	/**
	 * 采集负载
	 */
	private int weight;
	
	/**
	 * 最大采集数量
	 */
	private int maxNum;
	
	/**
	 * 采集时间
	 */
	private int explorerTime;
	
	/**
	 * 上一次采集剩余信息
	 */
	private int plusNum;
	
	public ExplorerInfo() {
		
	}
	public ExplorerInfo(double explorerSpeed, int weight, int maxNum, int explorerTime, int plusNum) {
		this.explorerSpeed = explorerSpeed;
		this.weight = weight;
		this.maxNum = maxNum;
		this.explorerTime = explorerTime;
		this.plusNum = plusNum;
	}
	
	public double getExplorerSpeed() {
		return explorerSpeed;
	}
	
	public void setExplorerSpeed(double explorerSpeed) {
		this.explorerSpeed = explorerSpeed;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getMaxNum() {
		return maxNum;
	}
	
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
	public int getExplorerTime() {
		return explorerTime;
	}
	
	public void setExplorerTime(int explorerTime) {
		this.explorerTime = explorerTime;
	}
	public int getPlusNum() {
		return plusNum;
	}
	public void setPlusNum(int plusNum) {
		this.plusNum = plusNum;
	}
	
}
