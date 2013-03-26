package com.starsecurity.model;

/* ��չ����Э�飬�豸���������� */
public class TLV_V_AlertRequest {
	private long deviceId;   	// device id generating by the remote device
	private short channelId;   	// channel id 
	private short alertType;   	// �������࣬�μ� _AlertTypeCode
	private short alertLevel;   // �������𣬲μ� _AlertLevelCode
	private short reserve;    	//����
	private short[] localTime = new short[14];			//����ʱ����ʱ���ַ�������ʽΪyyyymmddhhmmss,��"20080919132059"����2008��9��19��13��20��59�룬ʱ�侫��Ϊ��
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
