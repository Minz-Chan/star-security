package com.starsecurity.model;

public class FavouriteRecord {
	private String FavouriteName;		//收藏名
	private String UserName;			//用户名
	private String Password;			//密码
	private String IPAddress;			//IP
	private String Port;				//端口
	private String DefaultChannel;		//默认通道
	public String getFavouriteName() {
		return FavouriteName;
	}
	public void setFavouriteName(String favouriteName) {
		FavouriteName = favouriteName;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getPort() {
		return Port;
	}
	public void setPort(String port) {
		Port = port;
	}
	public String getDefaultChannel() {
		return DefaultChannel;
	}
	public void setDefaultChannel(String defaultChannel) {
		DefaultChannel = defaultChannel;
	}
	public String getRecordName() {
		return RecordName;
	}
	public void setRecordName(String recordName) {
		RecordName = recordName;
	}
	private String RecordName;			//记录名
}
