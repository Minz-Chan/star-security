/*
 * FileName:DataProcessUtil.java
 * 
 * Package:com.starsecurity.util
 * 
 * Date:2013-03-19
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.util;

import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelRequest;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_ControlRequest;
import com.starsecurity.model.TLV_V_DVSInfoRequest;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_StreamDataFormat;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_V_VideoFrameInfo;
import com.starsecurity.model.convert.Object2ByteArray;

/***
 * 
 * @function     功能	用于存放项目中使用的一些工具方法
 * @author       创建人                 陈明珍
 * @date        创建日期           2013-03-19
 * @author       修改人                 陈明珍
 * @date        修改日期           2013-05-06
 * @description 修改说明	             
 *   2013-05-06 增加封包工具方法
 */
public class DataProcessUtil {
	public static void fillPacket(byte[] des, byte[] src, int offset, int src_len) {
		System.arraycopy(src, 0, des, offset, src_len);
	}

	
	public static TLV_HEADER getHeaderOfData(Object obj) {
		TLV_HEADER head = new TLV_HEADER();
		
		if (obj instanceof TLV_V_VersionInfoRequest) {
			head.setTlv_type(TLV_T_Command.TLV_T_VERSION_INFO_REQUEST);
			head.setTlv_len(OWSP_LEN.TLV_V_VersionInfoRequest);
		} else if (obj instanceof TLV_V_PhoneInfoRequest) {
			head.setTlv_type(TLV_T_Command.TLV_T_PHONE_INFO_REQUEST); 
			head.setTlv_len(OWSP_LEN.TLV_V_PhoneInfoRequest);
		} else if (obj instanceof TLV_V_LoginRequest) {
			head.setTlv_type(TLV_T_Command.TLV_T_LOGIN_REQUEST); 
			head.setTlv_len(OWSP_LEN.TLV_V_LoginRequest);
		} else if (obj instanceof TLV_V_ChannelRequest) {
			head.setTlv_type(TLV_T_Command.TLV_T_CHANNLE_REQUEST);
			head.setTlv_len(OWSP_LEN.TLV_V_ChannelRequest);
		} else if (obj instanceof TLV_V_ChannelResponse) {
			head.setTlv_type(TLV_T_Command.TLV_T_CHANNLE_ANSWER);
			head.setTlv_len(OWSP_LEN.TLV_V_ChannelResponse);
		} else if (obj instanceof TLV_V_DVSInfoRequest) {
			head.setTlv_type(TLV_T_Command.TLV_T_DVS_INFO_REQUEST);
			head.setTlv_len(OWSP_LEN.TLV_V_DVSInfoRequest);
		} else if (obj instanceof TLV_V_StreamDataFormat) {
			head.setTlv_type(TLV_T_Command.TLV_T_STREAM_FORMAT_INFO);
			head.setTlv_len(OWSP_LEN.TLV_V_StreamDataFormat);
		} else if (obj instanceof TLV_V_VideoFrameInfo) {
			head.setTlv_type(TLV_T_Command.TLV_T_VIDEO_FRAME_INFO);
			head.setTlv_len(OWSP_LEN.TLV_V_VideoFrameInfo);
		} else if (obj instanceof TLV_V_ControlRequest) {
			head.setTlv_type(TLV_T_Command.TLV_T_CONTROL_REQUEST);
			head.setTlv_len(OWSP_LEN.TLV_V_ControlRequest);
		}
		
		return head;
	}
	
	
}
