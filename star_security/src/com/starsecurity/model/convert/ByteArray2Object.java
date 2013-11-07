/*
 * FileName:ByteArray2Object.java
 * 
 * Package:com.starsecurity
 * 
 * Date:2013-03-19
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.model.convert;

import com.starsecurity.model.OWSP_AudioDataFormat;
import com.starsecurity.model.OWSP_DATE;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OWSP_VideoDataFormat;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_Length;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_DVSInfoRequest;
import com.starsecurity.model.TLV_V_LoginResponse;
import com.starsecurity.model.TLV_V_StreamDataFormat;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_V_VideoData;
import com.starsecurity.model.TLV_V_VideoFrameInfo;
import com.starsecurity.util.BByteConvert;
import com.starsecurity.util.LByteConvert;

/**
 * 
 * @function     功能	  byte数组与各Class之间的互转
 *     项目将收到的Byte数组解析后转换为定义中的各个Class
 * @author       创建人                 肖远东
 * @date        创建日期           2013-03-20
 * @author       修改人                 陈明珍
 * @date        修改日期           2013-06-05
 * @description 修改说明	             
 *   2013-03-22 TLV_V_DVSInfoRequest、TLV_V_ChannelResponse、TLV_V_StreamDataFormat、
 *               TLV_V_VideoFrameInfo四个结构进行解析，并调试通过  陈明珍
 *   2013-06-05 加入TLV_V_LoginResponse处理 陈明珍
 */
public class ByteArray2Object {
	/**
	 * 
	 * @param clazz 需要转换成的Class
	 * @param byteArray byte数组
	 * @param start 数组转换起始位置
	 * @param arrayLength 数组转换长度
	 * @return
	 */
	public static Object convert2Object(Class clazz, byte byteArray[],int start,int arrayLength) {
		byte[] tempByteArray = new byte[arrayLength];
		for(int index=0;index<arrayLength;index++){
			tempByteArray[index] = byteArray[start+index];
		}
		if( clazz == OwspPacketHeader.class ) {
			if(tempByteArray.length != OWSP_LEN.OwspPacketHeader)
				return null;
			OwspPacketHeader owspPacketHeader = new OwspPacketHeader();
			owspPacketHeader.setPacket_length(BByteConvert.bytesToUint(tempByteArray,0));
			owspPacketHeader.setPacket_seq(LByteConvert.bytesToUint(tempByteArray,4));
			return owspPacketHeader;
		}else if( clazz == TLV_HEADER.class){
			if(tempByteArray.length != OWSP_LEN.TLV_HEADER)
				return null;
			int i = 0;
			byte[] tlv_typeArray = new byte[4];
			byte[] tlv_lenArray = new byte[4];
			for(i=0;i<2;i++){
				tlv_typeArray[i] = tempByteArray[i];
				tlv_lenArray[i] = tempByteArray[i+2];
			}
			int tlv_typeInt = LByteConvert.bytesToInt(tlv_typeArray);
			int tlv_lenInt = LByteConvert.bytesToInt(tlv_lenArray);
			TLV_HEADER tlv_Header = new TLV_HEADER();
			tlv_Header.setTlv_type(tlv_typeInt);
			tlv_Header.setTlv_len(tlv_lenInt);
			return tlv_Header;
		}else if( clazz == TLV_V_VersionInfoRequest.class ){
			if(tempByteArray.length != OWSP_LEN.TLV_V_VersionInfoRequest)
				return null;
			TLV_V_VersionInfoRequest tlv_V_VersionInfoRequest = new TLV_V_VersionInfoRequest();
			tlv_V_VersionInfoRequest.setVersionMajor(LByteConvert.bytesToUshort(tempByteArray,0));
			tlv_V_VersionInfoRequest.setVersionMinor(LByteConvert.bytesToUshort(tempByteArray,2));
			return tlv_V_VersionInfoRequest;
		}else if( clazz == TLV_V_DVSInfoRequest.class ){
			if ( tempByteArray.length != OWSP_LEN.TLV_V_DVSInfoRequest ) {
				return null;
			}
				
			TLV_V_DVSInfoRequest tlv_V_DVSInfoRequest = new TLV_V_DVSInfoRequest();
			
			int i = 0;
			char[] companyIdentityArray = new char[TLV_Length.STR_LEN_16];			//公司识别码,最多16个字符,后面不足部分为数字0      (为ASCII字符串)
			char[] equipmentIdentityArray = new char[TLV_Length.STR_LEN_16];			//设备识别码,本字段中为DVS的物理地址,即MAC地址,后面不足部分为数字0  (为ASCII字符串)
			char[] equipmentNameArray = new char[TLV_Length.STR_LEN_16];				//设备名称,最多16个字符,后面不足部分为数字0        (为ASCII字符串)
			char[] equipmentVersionArray = new char[TLV_Length.STR_LEN_16];			//设备的软件版本,最多16个字符, 后面不足部分为数字0 (为ASCII字符串)
			OWSP_DATE equipmentDate = new OWSP_DATE();							//设备的出厂日期20090120

			for (i = 0; i < TLV_Length.STR_LEN_16; i++) {
				companyIdentityArray[i] = (char) tempByteArray[i];
				equipmentIdentityArray[i] = (char) tempByteArray[TLV_Length.STR_LEN_16 + i];
				equipmentNameArray[i] = (char) tempByteArray[TLV_Length.STR_LEN_16 * 2 + i];
				equipmentVersionArray[i] = (char) tempByteArray[TLV_Length.STR_LEN_16 * 3 + i];
			}

			
			equipmentDate.setM_year(LByteConvert.bytesToUshort(tempByteArray, TLV_Length.STR_LEN_16 * 4));
			equipmentDate.setM_month((short)LByteConvert.bytesToUbyte(tempByteArray, TLV_Length.STR_LEN_16 * 4 + 2));
			equipmentDate.setM_day((short)LByteConvert.bytesToUbyte(tempByteArray, TLV_Length.STR_LEN_16 * 4 + 3));
	
			tlv_V_DVSInfoRequest.setCompanyIdentity(companyIdentityArray);
			tlv_V_DVSInfoRequest.setEquipmentIdentity(equipmentIdentityArray);
			tlv_V_DVSInfoRequest.setEquipmentName(equipmentNameArray);
			tlv_V_DVSInfoRequest.setEquipmentVersion(equipmentVersionArray);
			tlv_V_DVSInfoRequest.setEquipmentDate(equipmentDate);
			tlv_V_DVSInfoRequest.setChannleNumber((short)LByteConvert.bytesToUbyte(tempByteArray, TLV_Length.STR_LEN_16 * 4 + 4));
			tlv_V_DVSInfoRequest.setReserve1((short)LByteConvert.bytesToUbyte(tempByteArray, TLV_Length.STR_LEN_16 * 4 + 5));
			tlv_V_DVSInfoRequest.setReserve2((short)LByteConvert.bytesToUbyte(tempByteArray, TLV_Length.STR_LEN_16 * 4 + 6));
			tlv_V_DVSInfoRequest.setReserve3((short)LByteConvert.bytesToUbyte(tempByteArray, TLV_Length.STR_LEN_16 * 4 + 7));
			
			return tlv_V_DVSInfoRequest;
		} else if( clazz == TLV_V_ChannelResponse.class ){
			if(tempByteArray.length != OWSP_LEN.TLV_V_ChannelResponse) {
				return null;
				
			}

			TLV_V_ChannelResponse tlv_V_ChannelResponse = new TLV_V_ChannelResponse();
			tlv_V_ChannelResponse.setResult(LByteConvert.bytesToUshort(tempByteArray, 0));
			tlv_V_ChannelResponse.setCurrentChannel((short)LByteConvert.bytesToUbyte(tempByteArray, 2));
			tlv_V_ChannelResponse.setReserve((short)LByteConvert.bytesToUbyte(tempByteArray, 3));
			
			return tlv_V_ChannelResponse;
		} else if( clazz == TLV_V_StreamDataFormat.class ){
			if(tempByteArray.length != OWSP_LEN.TLV_V_StreamDataFormat) {
				return null;
			}
			
			TLV_V_StreamDataFormat tlv_V_StreamDataFormat = new TLV_V_StreamDataFormat();
			
			tlv_V_StreamDataFormat.setVideoChannel((short)LByteConvert.bytesToUbyte(tempByteArray, 0));		// 视频通道号
			tlv_V_StreamDataFormat.setAudioChannel((short)LByteConvert.bytesToUbyte(tempByteArray, 1));		// 音频通道号
			tlv_V_StreamDataFormat.setDataType((short)LByteConvert.bytesToUbyte(tempByteArray, 2));			// 流数据类型, 取值见StreamDataType
			tlv_V_StreamDataFormat.setReserve((short)LByteConvert.bytesToUbyte(tempByteArray, 3));			// 保留
			
			// 视频格式
			OWSP_VideoDataFormat videoFormat = new OWSP_VideoDataFormat();									
			videoFormat.setCodecId(LByteConvert.bytesToUint(tempByteArray, 4));								// FOUR CC code，’H264’
			videoFormat.setBitrate(LByteConvert.bytesToUint(tempByteArray, 4 + 4));							// bps
			videoFormat.setWidth(LByteConvert.bytesToUshort(tempByteArray, 4 + 8));							// image widht
			videoFormat.setHeight(LByteConvert.bytesToUshort(tempByteArray, 4 + 10));						// image height
			videoFormat.setFramerate((short)LByteConvert.bytesToUbyte(tempByteArray, 4 + 12));				// fps
			videoFormat.setColorDepth((short)LByteConvert.bytesToUbyte(tempByteArray, 4 + 13));				// should be 24 bits
			videoFormat.setReserve(LByteConvert.bytesToUshort(tempByteArray, 4 + 14));						// reserve
			
			// 音频格式
			OWSP_AudioDataFormat audioFormat = new OWSP_AudioDataFormat();  								
			audioFormat.setSamplesPerSecond(LByteConvert.bytesToUint(tempByteArray, 20));					// samples per second
			audioFormat.setBitrate(LByteConvert.bytesToUint(tempByteArray, 20 + 4));						// bps
			audioFormat.setWaveFormat(LByteConvert.bytesToUshort(tempByteArray, 20 + 8));					// wave format, such as WAVE_FORMAT_PCM,WAVE_FORMAT_MPEGLAYER3
			audioFormat.setChannelNumber(LByteConvert.bytesToUshort(tempByteArray, 20 + 10));				// audio channel number
			audioFormat.setBlockAlign(LByteConvert.bytesToUshort(tempByteArray, 20 + 12));					// block alignment defined by channelSize * (bitsSample/8)
			audioFormat.setBitsPerSample(LByteConvert.bytesToUshort(tempByteArray, 20 + 14));				// bits per sample
			audioFormat.setFrameInterval(LByteConvert.bytesToUshort(tempByteArray, 20 + 16));				// interval between frames, in milliseconds
			audioFormat.setReserve(LByteConvert.bytesToUshort(tempByteArray, 20 + 18));						// reserve
			
			tlv_V_StreamDataFormat.setVideoFormat(videoFormat);
			tlv_V_StreamDataFormat.setAudioFormat(audioFormat);
			
			return tlv_V_StreamDataFormat;
		} else if( clazz == TLV_V_VideoFrameInfo.class ){
			if(tempByteArray.length != OWSP_LEN.TLV_V_VideoFrameInfo)
				return null;
			TLV_V_VideoFrameInfo tlv_V_VideoFrameInfo = new TLV_V_VideoFrameInfo();
			tlv_V_VideoFrameInfo.setChannelId((short)LByteConvert.bytesToUbyte(tempByteArray, 0));
			tlv_V_VideoFrameInfo.setReserve((short)LByteConvert.bytesToUbyte(tempByteArray, 1));
			tlv_V_VideoFrameInfo.setChecksum(LByteConvert.bytesToUshort(tempByteArray, 2));
			tlv_V_VideoFrameInfo.setFrameIndex(LByteConvert.bytesToUint(tempByteArray, 4));
			tlv_V_VideoFrameInfo.setTime(LByteConvert.bytesToUint(tempByteArray, 8));
			return tlv_V_VideoFrameInfo;
		} else if ( clazz == TLV_V_VideoData.class) {
			
			return tempByteArray;
		} else if ( clazz == TLV_V_LoginResponse.class) {
			if(tempByteArray.length != OWSP_LEN.TLV_V_LoginResponse) {
				return null;
			}
			
			TLV_V_LoginResponse tlv_V_LoginResponse = new TLV_V_LoginResponse();
			tlv_V_LoginResponse.setResult(LByteConvert.bytesToUshort(tempByteArray, 0));
			tlv_V_LoginResponse.setReserve(LByteConvert.bytesToUshort(tempByteArray, 2));
			return tlv_V_LoginResponse;
		}
		
		return null;
	}
}
