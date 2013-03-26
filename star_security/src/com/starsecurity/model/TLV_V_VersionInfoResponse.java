package com.starsecurity.model;

//TLV_T: TLV_T_VERSION_INFO_ANSWER
//TLV_L: sizeof(TLV_V_VersionInfoResponse)
public class TLV_V_VersionInfoResponse {
	private int result;				//result of login request. _RESPONSECODE_SUCC - succeeded, others - failed
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
