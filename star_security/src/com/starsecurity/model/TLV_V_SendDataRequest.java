package com.starsecurity.model;

/* send data request, streaming server -> remote. 
 * Now this command is ignored, the remote will send data to server actively and immidietely after logining.*/
public class TLV_V_SendDataRequest {
	private long deviceId;			//device id generating by the remote device
	private short videoChannel;	
	private short audioChannel;   
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public short getVideoChannel() {
		return videoChannel;
	}
	public void setVideoChannel(short videoChannel) {
		this.videoChannel = videoChannel;
	}
	public short getAudioChannel() {
		return audioChannel;
	}
	public void setAudioChannel(short audioChannel) {
		this.audioChannel = audioChannel;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	private int reserve;
}
