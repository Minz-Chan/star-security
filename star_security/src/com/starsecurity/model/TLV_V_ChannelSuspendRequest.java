package com.starsecurity.model;

/***
 * 
 * 通道挂起协议 
 * Streaming server -> Device 
 * message: MSG_CMD_CHANNEL_SUSPEND
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
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
