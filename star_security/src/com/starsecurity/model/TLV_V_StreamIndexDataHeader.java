package com.starsecurity.model;

public class TLV_V_StreamIndexDataHeader {
	private long count;			//
	private long reserve;		//ÔÚÎÄ¼ş
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
