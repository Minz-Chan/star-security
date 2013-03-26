package com.starsecurity.model;

/* login request: remote -> streaming server */
public class TLV_V_LoginRequest {
	private char[] userName = new char[TLV_Length.STR_LEN_32];			//�û���, ���治�㲿��Ϊ����0      (ΪASCII�ַ���)
	private char[] password = new char[TLV_Length.STR_LEN_16];			//����, ���治�㲿��Ϊ����0        (ΪASCII�ַ���) 
	private long deviceId;					//�豸ID. CSģʽ���ɷ�����ͳһ����, P2Pģʽ��Ϊ�̶�ֵ
	private short flag;						//should be set to 1 to be compatible with the previous version.
	public char[] getUserName() {
		return userName;
	}
	public void setUserName(char[] userName) {
		this.userName = userName;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public short getFlag() {
		return flag;
	}
	public void setFlag(short flag) {
		this.flag = flag;
	}
	public short getChannel() {
		return channel;
	}
	public void setChannel(short channel) {
		this.channel = channel;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	private short channel;					//channel
	private int reserve;
}
