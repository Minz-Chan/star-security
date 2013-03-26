package com.starsecurity.model;

/* 
通道请求协议 
Streaming server -> Device
*/
public class TLV_V_ChannelRequest {
	private long deviceId;
	private int sourceChannel;	//源通道ID
	private int destChannel;		//切换的目的通道ID
	private int[] reserve = new int[2];
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public int getSourceChannel() {
		return sourceChannel;
	}
	public void setSourceChannel(int sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
	public int getDestChannel() {
		return destChannel;
	}
	public void setDestChannel(int destChannel) {
		this.destChannel = destChannel;
	}
	public int[] getReserve() {
		return reserve;
	}
	public void setReserve(int[] reserve) {
		this.reserve = reserve;
	}		
}
