package com.starsecurity.model;


/* 扩展视频帧信息 TLV, 当视频数据>64K时使用 */
public class TLV_V_VideoFrameInfoEx {
	private short channelId;			//通道ID
	private short reserve;				//备用
	private int checksum;				//校验和.目前为0未用
	private long frameIndex;			//视频帧序号
	private long time;				    //时间戳.
	private long dataSize;				//视频数据长度
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
	public long getFrameIndex() {
		return frameIndex;
	}
	public void setFrameIndex(long frameIndex) {
		this.frameIndex = frameIndex;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getDataSize() {
		return dataSize;
	}
	public void setDataSize(long dataSize) {
		this.dataSize = dataSize;
	}
}
//之后是若干个视频数据TLV, V部分是视频编码后的Raw Data.