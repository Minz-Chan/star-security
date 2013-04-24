package com.starsecurity.model;

/***
 * 
 * For TLV(TLV包头)
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_HEADER {
	private int tlv_type;
	private int tlv_len;
	public int getTlv_type() {
		return tlv_type;
	}
	public void setTlv_type(int tlv_type) {
		this.tlv_type = tlv_type;
	}
	public int getTlv_len() {
		return tlv_len;
	}
	public void setTlv_len(int tlv_len) {
		this.tlv_len = tlv_len;
	}
}
