package com.starsecurity.model;


/* ��չ��Ƶ֡��Ϣ TLV, ����Ƶ����>64Kʱʹ�� */
public class TLV_V_VideoFrameInfoEx {
	private short channelId;			//ͨ��ID
	private short reserve;				//����
	private int checksum;				//У���.ĿǰΪ0δ��
	private long frameIndex;			//��Ƶ֡���
	private long time;				    //ʱ���.
	private long dataSize;				//��Ƶ���ݳ���
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
//֮�������ɸ���Ƶ����TLV, V��������Ƶ������Raw Data.