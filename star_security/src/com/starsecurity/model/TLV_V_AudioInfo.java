package com.starsecurity.model;

/* 音频信息 TLV */
public class TLV_V_AudioInfo {
	private short channelId;			//channel id
	private short reserve;			//备用
	private int checksum;			//checksum of audio data.
	private long time;					// specify the time when this audio data is created.
	public short getChannelId() {
		return channelId;
	}
	public void setChannelId(short channelId) {
		this.channelId = channelId;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	public int getChecksum() {
		return checksum;
	}
	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}
//之后是音频数据TLV, V部分就是音频编码后的Raw Data.