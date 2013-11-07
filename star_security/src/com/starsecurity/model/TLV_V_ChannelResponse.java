package com.starsecurity.model;

/***
 * 
 * 通道请求响应
 * Device -> Streaming server
 * message: MSG_CMD_CHANNEL_RESPONSE
 * Streaming server -> Device
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_ChannelResponse {
	private int result;					//result of request. _RESPONSECODE_SUCC - succeeded, others - failed
	private short currentChannel;	//如果不支持的通道，则返回当前通道号
	private short reserve;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public short getCurrentChannel() {
		return currentChannel;
	}
	public void setCurrentChannel(short currentChannel) {
		this.currentChannel = currentChannel;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}	
}
