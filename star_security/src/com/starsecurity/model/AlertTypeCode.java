package com.starsecurity.model;

//报警种类，目前只支持探头报警，也就是ATC_INFRARED
public class AlertTypeCode {
	public final static int ATC_VIDEO = 0;	//视频帧预测
	public final static int ATC_DEVICE_SERSTART = 1;	/* 设备启动 */	
	public final static int ATC_MOTION = 2;						/* 移动侦测报警 */
	public final static int ATC_VIDEOLOST = 3;				/* 视频丢失报警 */
	public final static int ATC_DISKFULL = 4;					/* 硬盘满报警 */
	public final static int ATC_HIDEALARM=5;					/* 视频遮挡报警 */	
	public final static int ATC_STOP = 6;							/* 服务器停止 */
	public final static int ATC_SDERROR = 7;         	/* SD卡异常*/
	public final static int ATC_INFRARED = 20;					//开关量探头（比如红外探头）
}
