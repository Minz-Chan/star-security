package com.starsecurity.model;

/***
 * 
 * 扩展报警协议，设备发到服务器
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_AlertRequest {
	private long deviceId;   	// device id generating by the remote device
	private short channelId;   	// channel id 
	private short alertType;   	// 报警种类，参见 _AlertTypeCode
	private short alertLevel;   // 报警级别，参见 _AlertLevelCode
	private short reserve;    	//保留
	private short[] localTime = new short[14];			//报警时本地时间字符串，格式为yyyymmddhhmmss,如"20080919132059"代表2008年9月19日13点20分59秒，时间精度为秒
	private int size;     		// array of data size followed��default =  0
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public short getChannelId() {
		return channelId;
	}
	public void setChannelId(short channelId) {
		this.channelId = channelId;
	}
	public short getAlertType() {
		return alertType;
	}
	public void setAlertType(short alertType) {
		this.alertType = alertType;
	}
	public short getAlertLevel() {
		return alertLevel;
	}
	public void setAlertLevel(short alertLevel) {
		this.alertLevel = alertLevel;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	public short[] getLocalTime() {
		return localTime;
	}
	public void setLocalTime(short[] localTime) {
		this.localTime = localTime;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
