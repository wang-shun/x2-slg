package com.xgame.logic.server.game.player.entity;

import io.protostuff.Tag;

import java.io.Serializable;

 
public class Produce implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	public ProduceType type; // 建造类型

	@Tag(2)
	public float starttime;// 建造开始时间

	@Tag(3)
	public float endtime;// 建造结束时间

	@Tag(4)
	public int num;// 建造数量

	@Tag(5)
	public int buildingId;// 所属建筑物id

	@Tag(6)
	public int speed;// 建造速度
}
