package com.starsecurity.model;

/* DVS�����豸��ϢӦ�� */
public class TLV_V_DVSInfoResponse {
	private int result;    //result of login request. _RESPONSECODE_SUCC - succeeded, others - failed
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
