/*
 * FileName:Object2ByteArray.java
 * 
 * Package:com.starsecurity.model.convert
 * 
 * Date:2013-03-19
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.model.convert;

import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_V_ChannelRequest;
import com.starsecurity.model.TLV_V_ControlRequest;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.util.BByteConvert;
import com.starsecurity.util.LByteConvert;

/**
 * 
 * @function     功能	  将各类转换成byte数组以便网络传输
 *     对项目中用到的数据结构进行字节序列化，使其容易在网络中进行传输
 * @author       创建人                 陈明珍
 * @date        创建日期           2013-03-19
 * @author       修改人                 陈明珍
 * @date        修改日期           2013-05-06
 * @description 修改说明	             
 *   2013-05-06 移除函数convert2ByteArr的Class clazz参数 陈明珍
 */
public class Object2ByteArray {
	public static byte[] convert2ByteArr(Object obj) {
		byte[] buf = null;
		int i = 0;
		
		if( obj instanceof OwspPacketHeader ) {
			buf = new byte[OWSP_LEN.OwspPacketHeader];
			OwspPacketHeader owspPacketHeader = (OwspPacketHeader)obj;
			BByteConvert.uintToBytes(owspPacketHeader.getPacket_length(), buf, 0);
			BByteConvert.uintToBytes(owspPacketHeader.getPacket_seq(), buf, 4);
		} else if ( obj instanceof TLV_HEADER ) {
			buf = new byte[OWSP_LEN.TLV_HEADER]; 
			TLV_HEADER tlv_header = (TLV_HEADER)obj;
			LByteConvert.ushortToBytes(tlv_header.getTlv_type(), buf, 0);
			LByteConvert.ushortToBytes(tlv_header.getTlv_len(), buf, 2);
		} else if ( obj instanceof TLV_V_VersionInfoRequest ) {
			buf = new byte[OWSP_LEN.TLV_V_VersionInfoRequest];
			TLV_V_VersionInfoRequest versionInfoRequest = (TLV_V_VersionInfoRequest)obj;
			LByteConvert.ushortToBytes(versionInfoRequest.getVersionMajor(), buf, 0);
			LByteConvert.ushortToBytes(versionInfoRequest.getVersionMinor(), buf, 2);
		} else if ( obj instanceof TLV_V_PhoneInfoRequest ) {
			buf = new byte[OWSP_LEN.TLV_V_PhoneInfoRequest];
			TLV_V_PhoneInfoRequest phoneInfoRequest = (TLV_V_PhoneInfoRequest)obj;
			
			short[] tmp = phoneInfoRequest.getEquipmentIdentity();
			for( i = 0; i < tmp.length; i++ ) {
				LByteConvert.ubyteToBytes(tmp[i], buf, i);
			}
			tmp = phoneInfoRequest.getEquipmentOS();
			for( i = 0; i < tmp.length; i++ ) {
				LByteConvert.ubyteToBytes(tmp[i], buf, 16 + i);
			}
			
			LByteConvert.ubyteToBytes(phoneInfoRequest.getReserve1(), buf, 32);
			LByteConvert.ubyteToBytes(phoneInfoRequest.getReserve2(), buf, 33);
			LByteConvert.ubyteToBytes(phoneInfoRequest.getReserve3(), buf, 34);
			LByteConvert.ubyteToBytes(phoneInfoRequest.getReserve4(), buf, 35);
		} else if ( obj instanceof TLV_V_LoginRequest ) {
			buf = new byte[OWSP_LEN.TLV_V_LoginRequest];
			TLV_V_LoginRequest loginRequest = (TLV_V_LoginRequest)obj;
			
			char[] tmp = loginRequest.getUserName();
			for( i = 0; i < tmp.length; i++ ) {
				LByteConvert.ubyteToBytes(tmp[i], buf, i);
			}
			tmp = loginRequest.getPassword();
			for( i = 0; i < tmp.length; i++ ) {
				LByteConvert.ubyteToBytes(tmp[i], buf, 32 + i);
			}
			
			LByteConvert.uintToBytes(loginRequest.getDeviceId(), buf, 48);
			LByteConvert.ubyteToBytes(loginRequest.getFlag(), buf, 52);
			LByteConvert.ubyteToBytes(loginRequest.getChannel(), buf, 53);
			LByteConvert.ushortToBytes(loginRequest.getReserve(), buf, 54);
		} else if ( obj instanceof TLV_V_ChannelRequest) {
			buf = new byte[OWSP_LEN.TLV_V_ChannelRequest];
			TLV_V_ChannelRequest channelRequest = (TLV_V_ChannelRequest)obj;
			
			LByteConvert.uintToBytes(channelRequest.getDeviceId(), buf, 0);
			LByteConvert.ubyteToBytes(channelRequest.getSourceChannel(), buf, 4);
			LByteConvert.ubyteToBytes(channelRequest.getDestChannel(), buf, 5);
			LByteConvert.ubyteToBytes(channelRequest.getReserve()[0], buf, 6);
			LByteConvert.ubyteToBytes(channelRequest.getReserve()[1], buf, 7);
			
		} else if ( obj instanceof TLV_V_ControlRequest) {
			buf = new byte[OWSP_LEN.TLV_V_ControlRequest];
			TLV_V_ControlRequest controlRequest = (TLV_V_ControlRequest)obj;
			
			LByteConvert.uintToBytes(controlRequest.getDeviceId(), buf, 0);
			LByteConvert.ubyteToBytes(controlRequest.getChannel(), buf, 4);
			LByteConvert.ubyteToBytes(controlRequest.getCmdCode(), buf, 5);
			LByteConvert.ushortToBytes(controlRequest.getSize(), buf, 6);
		}
		return buf;
	}
}
