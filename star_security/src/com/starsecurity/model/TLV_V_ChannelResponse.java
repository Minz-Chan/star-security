package com.starsecurity.model;

public class TLV_V_ChannelResponse {
	private int result;					//result of request. _RESPONSECODE_SUCC - succeeded, others - failed
	private short currentChannel;	//�����֧�ֵ�ͨ�����򷵻ص�ǰͨ����
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
