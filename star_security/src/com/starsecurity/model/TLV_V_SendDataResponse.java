package com.starsecurity.model;

/* send data response, remote -> streaming server */
public class TLV_V_SendDataResponse {
	private int result;			//result of send data request
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
