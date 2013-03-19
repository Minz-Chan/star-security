package com.starsecurity.model;

/* this format should be sent to the server before any other stream data,
Plus if any format of video/audio has changed, it should send this info to server at the first time.
followed by VideoDataFormat/AudioDataFormat*/
public class TLV_V_StreamDataFormat {
	private int videoChannel;					//��Ƶͨ����
	private int audioChannel;					//��Ƶͨ����
	private int dataType;							//����������, ȡֵ��StreamDataType
	private int reserve;								//����
	private OWSP_VideoDataFormat videoFormat;	//��Ƶ��ʽ
	private OWSP_AudioDataFormat audioFormat;  //��Ƶ��ʽ
	public int getVideoChannel() {
		return videoChannel;
	}
	public void setVideoChannel(int videoChannel) {
		this.videoChannel = videoChannel;
	}
	public int getAudioChannel() {
		return audioChannel;
	}
	public void setAudioChannel(int audioChannel) {
		this.audioChannel = audioChannel;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	public OWSP_VideoDataFormat getVideoFormat() {
		return videoFormat;
	}
	public void setVideoFormat(OWSP_VideoDataFormat videoFormat) {
		this.videoFormat = videoFormat;
	}
	public OWSP_AudioDataFormat getAudioFormat() {
		return audioFormat;
	}
	public void setAudioFormat(OWSP_AudioDataFormat audioFormat) {
		this.audioFormat = audioFormat;
	}
}
