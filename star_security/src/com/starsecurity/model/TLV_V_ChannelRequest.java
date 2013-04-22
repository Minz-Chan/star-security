package com.starsecurity.model;

/* 
ͨ������Э�� 
Streaming server -> Device
*/
public class TLV_V_ChannelRequest {
	private long deviceId;
	private short sourceChannel;	//Դͨ��ID
	private short destChannel;		//�л���Ŀ��ͨ��ID
	private short[] reserve = new short[2];
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public int getSourceChannel() {
		return sourceChannel;
	}
	public short getDestChannel() {
		return destChannel;
	}
	public void setDestChannel(short destChannel) {
		this.destChannel = destChannel;
	}
	public short[] getReserve() {
		return reserve;
	}
	public void setReserve(short[] reserve) {
		this.reserve = reserve;
	}
	public void setSourceChannel(short sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
}
