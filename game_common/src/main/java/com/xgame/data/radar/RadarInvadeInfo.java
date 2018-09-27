package com.xgame.data.radar;

import java.io.Serializable;

import io.protostuff.Tag;

import com.xgame.framework.rpc.W2lPlayerInfo;

public class RadarInvadeInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Tag(1)
	public Long uid;
	
	@Tag(2)
	public W2lPlayerInfo w2lPlayerInfo;
	
	@Tag(3)
	public int x;
	
	@Tag(4)
	public int y;
	
}
