package com.xgame.logic.server.game.admin.entity;

import java.io.Serializable;


/**
 * 结果集
 * @author jacky.jiang
 *
 */
public class Result<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6773497204320424115L;

	/**
	 * 结果
	 */
	private int result;
	
	/**
	 * 返回内容
	 */
	private T obj;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
