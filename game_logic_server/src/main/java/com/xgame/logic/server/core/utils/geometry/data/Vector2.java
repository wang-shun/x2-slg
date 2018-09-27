/*
	Copyright (c) 2014-2024 569778806@qq.com All Rights Reserved.
*/

package com.xgame.logic.server.core.utils.geometry.data;

import java.io.Serializable;

import com.xgame.logic.server.core.db.redis.JBaseData;
import com.xgame.logic.server.core.db.redis.JBaseTransform;
import com.xgame.logic.server.game.country.bean.Vector2Bean;

import io.protostuff.Tag;




/**
 * 向量点的基本操作对象
 *2016-7-12  20:55:01
 * @version V1.0   
 */
public class Vector2 implements Comparable<Vector2>,Serializable, JBaseTransform{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Tag(1)
	private int x;
	
	@Tag(2)
	private int y;
	
	@Tag(3)
	private long uid;
	
	
	
	public Vector2() {
		super();
	}

	public Vector2Bean toVectorInfo() {
		Vector2Bean vector2Bean = new Vector2Bean();
		vector2Bean.x = this.x;
		vector2Bean.y = this.y;
		return vector2Bean;
	}
	
	public Vector2 zero(){
		return new Vector2(0, 0);
	}
	
	public Vector2(int x, int y, long uid) {
		this.x = x;
		this.y = y;
		this.uid = uid;
	}
	
	public boolean	valueEquals(Vector2 vector3){
		return x==vector3.x&&y==vector3.y;
	}
	
	public Vector2(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public void set(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public void set(Vector2 vector3){
		this.x=vector3.x;
		this.y=vector3.y;
	}
	
	public Vector2 sum(Vector2 vec){
		x+=vec.x;
		y+=vec.y;
		return this;
	}
	
	public Vector2 sub(Vector2 vec){
		x-=vec.x;
		y-=vec.y;
		return this;
	}
	
	public Vector2 mult(double num){
		x*=num;
		y*=num;
		return this;
	}
	
	public Vector2 mult(Vector2 vec){
		x*=vec.x;
		y*=vec.y;
		return this;
	}
	
	public Vector2 div(Vector2 vec){
		x/=vec.x;
		y/=vec.y;
		return this;
	}
	
	public Vector2 div(double num){
		x/=num;
		y/=num;
		return this;
	}
	
	public  Vector2 normalize()
	{
	    return normalize(this);
	}
	
	/**
	 * 向量归一化
	 * @Description:
	 * @param value
	 * @return
	 */
	public  Vector2 normalize(Vector2 value)
	{
	    int num = magnitude(value);
	    if (num > 1E-05f)
	    {
	        return div(num);
	    }
	    return zero();
	}

	public static int magnitude(Vector2 a)
	{
	    return (int) Math.sqrt((a.x * a.x) + (a.y * a.y));
	}
	
	public String toString(){
		return "["+x+","+y+"]";
	}
	
	public Vector2 clone(){
		return new Vector2(x, y);
	}

	public int getX() {
		return x;
	}
	
	public int getAbsX() {
		return Math.abs(x);
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public int getAbsY() {
		return Math.abs(y);
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int compareTo(Vector2 o) {
		return (o.x==x&&o.y==y)?0:1;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Override
	public JBaseData toJBaseData() {
		JBaseData jBaseData = new JBaseData();
		jBaseData.put("x", x);
		jBaseData.put("y", y);
		jBaseData.put("uid", uid);
		return jBaseData;
	}

	@Override
	public void fromJBaseData(JBaseData jBaseData) {
		this.x = jBaseData.getInt("x", 0);
		this.y = jBaseData.getInt("y", 0);
		this.uid = jBaseData.getLong("uid", 0);
	}
	
}
