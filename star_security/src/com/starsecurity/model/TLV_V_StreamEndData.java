package com.starsecurity.model;

public class TLV_V_StreamEndData {
	private long timestamp;
	private int result;				//result of request. _RESPONSECODE_SUCC - succeeded, others - failed
	private int reserve;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
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
