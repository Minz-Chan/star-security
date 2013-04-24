package com.starsecurity.model;

/***
 * 
 * this format should be sent to the server before any other stream data,
 * Plus if any format of video/audio has changed, it should send this info to server at the first time.
 * followed by VideoDataFormat/AudioDataFormat
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_StreamDataFormat {
	private short videoChannel;					//视频通道号
	private short audioChannel;					//音频通道号
	private short dataType;							//流数据类型, 取值见StreamDataType
	private short reserve;								//保留
	private OWSP_VideoDataFormat videoFormat;	//视频格式
	private OWSP_AudioDataFormat audioFormat;   //音频格式
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
