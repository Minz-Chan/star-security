package com.starsecurity.model;

/* ��Ƶ֡��Ϣ TLV */
public class TLV_V_VideoFrameInfo {
	private short channelId;			//ͨ��ID
	private short reserve;				//����
	private int checksum;				//У���.ĿǰΪ0δ��
	private long frameIndex;			//��Ƶ֡���
	private long time;					//ʱ���
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
//֮������Ƶ����TLV, V��������Ƶ������Raw Data.