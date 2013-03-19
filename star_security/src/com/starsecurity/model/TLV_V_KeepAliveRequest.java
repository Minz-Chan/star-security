package com.starsecurity.model;

/*
ÐÄÌø°ü
Device -> Streaming server
message: MSG_CMD_DEVICE_KEEP_ALIVE
*/
public class TLV_V_KeepAliveRequest {
	private short channelID;	//Channel id
	private short[] reserve = new short[3];
	public short getChannelID() {
		return channelID;
	}
	public void setChannelID(short channelID) {
		this.channelID = channelID;
	}
	public short[] getReserve() {
		return reserve;
	}
	public void setReserve(short[] reserve) {
		this.reserve = reserve;
	}	
}
