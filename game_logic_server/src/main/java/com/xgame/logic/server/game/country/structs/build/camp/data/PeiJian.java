package com.xgame.logic.server.game.country.structs.build.camp.data;

import java.io.Serializable;

import com.xgame.logic.server.game.soldier.bean.PeijianBean;

import io.protostuff.Tag;

/**
 *
 *2016-8-16  15:57:18
 *@author ye.yuan
 *
 */
public class PeiJian implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Tag(1)
	private int sid;
	
	@Tag(2)
	private int location;
	
	public PeiJian toPeiJian(){
		PeiJian peiJian = new PeiJian();
		peiJian.sid=sid;
		peiJian.location=location;
		return peiJian;
	}
	
	public PeijianBean toPeijianBean(){
		PeijianBean peiJian = new PeijianBean();
		peiJian.peijianId=sid;
		peiJian.location=location;
		return peiJian;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

}
