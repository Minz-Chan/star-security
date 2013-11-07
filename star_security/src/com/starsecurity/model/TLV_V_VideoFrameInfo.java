package com.starsecurity.model;

/***
 * 
 * 视频帧信息 TLV	
 * 之后是视频数据TLV, V部分是视频编码后的Raw Data
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_VideoFrameInfo {
	private short channelId;			//通道ID
	private short reserve;				//备用
	private int checksum;				//校验和.目前为0未用
	private long frameIndex;			//视频帧序号
	private long time;					//时间戳.
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
}
//֮������Ƶ���TLV, V��������Ƶ������Raw Data.