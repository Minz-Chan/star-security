package com.starsecurity.model;

/***
 * 
 * 心跳包响应
 * Streaming server -> Device
 * message: MSG_CMD_KEEP_ALIVE_ANSWER
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
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
