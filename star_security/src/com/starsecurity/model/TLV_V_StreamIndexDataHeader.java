package com.starsecurity.model;

/***
 * 
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_StreamIndexDataHeader {
	private long count;			//
	private long reserve;		//在文件
	private long datasize;		//
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getReserve() {
		return reserve;
	}
	public void setReserve(long reserve) {
		this.reserve = reserve;
	}
	public long getDatasize() {
		return datasize;
	}
	public void setDatasize(long datasize) {
		this.datasize = datasize;
	}
}
