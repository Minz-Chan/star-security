package com.starsecurity.model;

/* this format should be sent to the server before any other stream data,
Plus if any format of video/audio has changed, it should send this info to server at the first time.
followed by VideoDataFormat/AudioDataFormat*/
public class TLV_V_StreamDataFormat {
	private short videoChannel;					//��Ƶͨ����
	private short audioChannel;					//��Ƶͨ����
	private short dataType;							//����������, ȡֵ��StreamDataType
	private short reserve;								//����
	private OWSP_VideoDataFormat videoFormat;	//��Ƶ��ʽ
	private OWSP_AudioDataFormat audioFormat;  //��Ƶ��ʽ
	public int getVideoChannel() {
		return videoChannel;
	}
	
	public short getAudioChannel() {
		return audioChannel;
	}



	public void setAudioChannel(short audioChannel) {
		this.audioChannel = audioChannel;
	}



	public short getDataType() {
		return dataType;
	}



	public void setDataType(short dataType) {
		this.dataType = dataType;
	}



	public short getReserve() {
		return reserve;
	}



	public void setReserve(short reserve) {
		this.reserve = reserve;
	}



	public void setVideoChannel(short videoChannel) {
		this.videoChannel = videoChannel;
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
