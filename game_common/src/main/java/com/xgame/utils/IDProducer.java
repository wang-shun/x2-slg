package com.xgame.utils;

/**
 *
 * 2016-8-16 11:36:22
 *
 * @author ye.yuan
 *
 */
public enum IDProducer {

	ID, ;

	private int id = 0;

	private long time = System.currentTimeMillis() / 1000L;
	
	public long mathId(int webId, int serverId) {
		long now = System.currentTimeMillis() / 1000L;
		if (now > time) {
			time = now;
		}
		id += 1;
		if (id > 4095) {
			id &= 4095;
			time += 1L;
		}
		return (webId & 0xFF) << 56 | (serverId & 0xFFF) << 44
				| (time & 0x3FFFFFFF) << 14 | id & 0x3FFF;
	}

	public static long getId(int webId, int serverId) {
		synchronized (ID) {
			return ID.mathId(webId, serverId);
		}
	}
	
	public static void main(String[] args) {
		try {
			for(int i=0;i<1000;i++) {
				System.out.println(getId(1000, 2000));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
