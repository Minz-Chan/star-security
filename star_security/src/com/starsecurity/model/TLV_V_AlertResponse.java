package com.starsecurity.model;

/* ����������Ӧ�����������͵��豸 */
public class TLV_V_AlertResponse {
	private int result;    //result of login request. _RESPONSECODE_SUCC - succeeded, others - failed
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
	private int reserve;
}
