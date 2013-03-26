package com.starsecurity.model;

/*
ÐÄÌø°üÏìÓ¦
Streaming server -> Device
message: MSG_CMD_KEEP_ALIVE_ANSWER
*/
public class TLV_V_KeepAliveResponse {
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
