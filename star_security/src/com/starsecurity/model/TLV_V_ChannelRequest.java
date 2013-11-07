package com.starsecurity.model;

/***
 * 
 * 通道请求协议 
 * Streaming server -> Device
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_ChannelRequest {
	private long deviceId;
	private short sourceChannel;	//源通道ID
	private short destChannel;		//切换的目的通道ID
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
