package com.starsecurity.model;

/*
ͨ��������Ӧ
Device -> Streaming server
message: MSG_CMD_SUSPEND_RESPONSE
*/
public class TLV_V_ChannelSuspendResponse {
	private int result;				//result of request. _RESPONSECODE_SUCC - succeeded, others - failed
	private int reserve;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}	
}
