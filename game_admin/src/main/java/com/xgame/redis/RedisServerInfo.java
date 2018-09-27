package com.xgame.redis;

/**
 * 服务器详细信息
 * @author jacky.jiang
 *
 */
public class RedisServerInfo {
	
	/**
	 * ip地址
	 */
	private String ip;
	
	/**
	 * 端口
	 */
	private int port;
	
	/**
	 * 密码
	 */
	private String pass;

	public RedisServerInfo(String ip, int port, String pass) {
		super();
		this.ip = ip;
		this.port = port;
		this.pass = pass;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
