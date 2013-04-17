package com.starsecurity.util;

public class ConfigSingleton {

	private static ConfigSingleton m_instance = null;
	
	private String username;
	private String password;
	private String svr_ip;
	private String port;
	private String channel_num;
	
	
	private ConfigSingleton() {
		
		
	}
	
	synchronized public static ConfigSingleton getInstance() {
		if(m_instance == null) {
			m_instance = new ConfigSingleton();
		}
		
		return m_instance;
	}

	public static ConfigSingleton getM_instance() {
		return m_instance;
	}

	public static void setM_instance(ConfigSingleton m_instance) {
		ConfigSingleton.m_instance = m_instance;
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

	public String getSvr_ip() {
		return svr_ip;
	}

	public void setSvr_ip(String svr_ip) {
		this.svr_ip = svr_ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getChannel_num() {
		return channel_num;
	}

	public void setChannel_num(String channel_num) {
		this.channel_num = channel_num;
	}
	
	

}
