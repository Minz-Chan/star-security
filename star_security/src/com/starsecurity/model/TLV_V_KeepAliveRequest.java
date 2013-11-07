package com.starsecurity.model;

/***
 * 
 * 心跳包
 * Device -> Streaming server
 * message: MSG_CMD_DEVICE_KEEP_ALIVE
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
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
