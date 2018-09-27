package com.xgame.msglib.able;


/**
 *
 *2016-8-25  16:45:12
 *@author ye.yuan
 *
 */
public interface Communicationable {
	
	int getId();

	String getQueue();
	
	boolean isSync();
	
	String getServer();
	
    CommandEnum getCommandEnum();
	
	HandlerEnum getHandlerEnum();
	
	public enum CommandEnum{
		SESSION,
		PLAYERMSG,
		;
	}
	
	public enum HandlerEnum{
		SC,
		CS,
		MSG10
		;
	}
}
