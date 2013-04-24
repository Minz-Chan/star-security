package com.starsecurity.model;

/***
 * 
 * specify the format of video, this info issent 
 * to server immidiately after StreamDataFormat
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class OWSP_VideoDataFormat {
	private long codecId;			//FOUR CC code，’H264’
	private long bitrate;			//bps
	private int width;				//image widht
	private int height;				//image height
	private short framerate;			//fps
	private short colorDepth;			//should be 24 bits 
	private int reserve;
	public long getCodecId() {
		return codecId;
	}
	public void setCodecId(long codecId) {
		this.codecId = codecId;
	}
	public long getBitrate() {
		return bitrate;
	}
	public void setBitrate(long bitrate) {
		this.bitrate = bitrate;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public short getFramerate() {
		return framerate;
	}
	public void setFramerate(short framerate) {
		this.framerate = framerate;
	}
	public short getColorDepth() {
		return colorDepth;
	}
	public void setColorDepth(short colorDepth) {
		this.colorDepth = colorDepth;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}		
}
