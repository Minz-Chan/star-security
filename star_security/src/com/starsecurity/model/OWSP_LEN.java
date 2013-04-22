package com.starsecurity.model;

/***
 * 
 * 各类型数据长度
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class OWSP_LEN {
	
	public static final int OwspPacketHeader = 8;
	public static final int TLV_HEADER = 4;
	public static final int TLV_V_VersionInfoRequest = 4;
	public static final int TLV_V_PhoneInfoRequest = 36;
	public static final int TLV_V_LoginRequest = 56;
	public static final int TLV_V_DVSInfoRequest = 72;
	public static final int TLV_V_ChannelResponse = 4;
	public static final int TLV_V_StreamDataFormat = 40;
	public static final int TLV_V_VideoFrameInfo = 12;
	public static final int TLV_V_ChannelRequest = 8;
	public static final int TLV_V_ControlRequest = 8;
	public static final int TLV_V_ControlResponse = 4;
}
