package com.starsecurity.model;

/* ��չ����Э��, ������̨��TV���� */
public class TLV_V_ControlRequest {
	private long deviceId;			// device id generating by the remote device
	private short channel;			// channel id 
	private short cmdCode;			// ���������֣��μ�_PTZCode
	private int size;				// ���Ʋ������ݳ���,���size==0 ��ʾ�޿��Ʋ���
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public short getChannel() {
		return channel;
	}
	public void setChannel(short channel) {
		this.channel = channel;
	}
	public short getCmdCode() {
		return cmdCode;
	}
	public void setCmdCode(short cmdCode) {
		this.cmdCode = cmdCode;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
