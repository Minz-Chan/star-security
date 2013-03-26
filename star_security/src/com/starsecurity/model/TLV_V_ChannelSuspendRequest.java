package com.starsecurity.model;

/* 
通道挂起协议 
Streaming server -> Device 
message: MSG_CMD_CHANNEL_SUSPEND
*/
public class TLV_V_ChannelSuspendRequest {
	private short channelId;	//Chanel id
	private short[] reserve = new short[3];
	public short getChannelId() {
		return channelId;
	}
	public void setChannelId(short channelId) {
		this.channelId = channelId;
	}
	public short[] getReserve() {
		return reserve;
	}
	public void setReserve(short[] reserve) {
		this.reserve = reserve;
	}	
}
