package com.starsecurity.model;

/***
 * 
 * specify the format of audio, this info is sent to server immidiately after StreamDataFormat or VideoDataFormat
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class OWSP_AudioDataFormat {
	private long samplesPerSecond;		//samples per second
	private long bitrate;			//bps
	private int waveFormat;			//wave format, such as WAVE_FORMAT_PCM,WAVE_FORMAT_MPEGLAYER3
	private int channelNumber;		//audio channel number
	private int blockAlign;			//block alignment defined by channelSize * (bitsSample/8)
	private int bitsPerSample;			//bits per sample
	private int frameInterval;		//interval between frames, in milliseconds
	private int reserve;
	public long getSamplesPerSecond() {
		return samplesPerSecond;
	}
	public void setSamplesPerSecond(long samplesPerSecond) {
		this.samplesPerSecond = samplesPerSecond;
	}
	public long getBitrate() {
		return bitrate;
	}
	public void setBitrate(long bitrate) {
		this.bitrate = bitrate;
	}
	public int getWaveFormat() {
		return waveFormat;
	}
	public void setWaveFormat(int waveFormat) {
		this.waveFormat = waveFormat;
	}
	public int getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}
	public int getBlockAlign() {
		return blockAlign;
	}
	public void setBlockAlign(int blockAlign) {
		this.blockAlign = blockAlign;
	}
	public int getBitsPerSample() {
		return bitsPerSample;
	}
	public void setBitsPerSample(int bitsPerSample) {
		this.bitsPerSample = bitsPerSample;
	}
	public int getFrameInterval() {
		return frameInterval;
	}
	public void setFrameInterval(int frameInterval) {
		this.frameInterval = frameInterval;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
}
