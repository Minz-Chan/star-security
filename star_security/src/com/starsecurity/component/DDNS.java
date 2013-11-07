/*
 * FileName:DDNS.java
 * 
 * Package:com.starsecurity.component
 * 
 * Date:2013-04-18
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.component;


/**
 * @function     功能	     域名服务器信息类
 * @author       创建人                陈明珍
 * @date        创建日期           2013-04-18
 * @author       修改人                陈明珍
 * @date        修改日期           2013-04-18
 * @description 修改说明	            首次增加
 */
public class DDNS {
	//private static DDNS instance;
	
	private String server;					// 域名服务器
	private int port;						// 端口
	private String username;				// 登录用户名
	private String password;				// 登录密码
	private String dvrName;					// 设备名称
	
	
	private DDNS() {
		
	}
	
	/*
	
	public static DDNS getInstance() {
		if (instance == null) {
			instance = new DDNS();
		}
		
		return instance;
	}*/


	public String getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = server;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDvrName() {
		return dvrName;
	}


	public void setDvrName(String dvrName) {
		this.dvrName = dvrName;
	}
	
	
	
	
}
