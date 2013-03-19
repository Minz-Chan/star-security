package com.starsecurity.model;

public class TLV_V_StreamIndexData {
	private long timestamp;
	private long pos;		//ÔÚÎÄ¼þ
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getPos() {
		return pos;
	}
	public void setPos(long pos) {
		this.pos = pos;
	}
}
