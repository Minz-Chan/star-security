package com.starsecurity.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_DVSInfoRequest;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_StreamDataFormat;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_V_VideoData;
import com.starsecurity.model.TLV_V_VideoFrameInfo;
import com.starsecurity.model.TLV_Version;
import com.starsecurity.model.convert.ByteArray2Object;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.util.DataProcessUtil;


public class StarTest extends AndroidTestCase  {
	
	Socket socket = null;
	OutputStream out = null;
    InputStream in = null ;
	
	@Override
	public void setUp() throws Exception {
 		socket = new Socket("113.250.29.219", 8080);  
        System.out.println("Socket=" + socket);  
        
		out = socket.getOutputStream();
		in = socket.getInputStream();
	}

	

	public void testSomething() throws Throwable {  
		
		 /* TLV_V_VersionInfoRequest */
		TLV_V_VersionInfoRequest versionInfoRequest = new TLV_V_VersionInfoRequest();
		versionInfoRequest.setVersionMinor(TLV_Version.VERSION_MINOR);
		versionInfoRequest.setVersionMajor(TLV_Version.VERSION_MAJOR);

		/* TLV_V_PhoneInfoRequest */
		TLV_V_PhoneInfoRequest phoneInfoRequest = new TLV_V_PhoneInfoRequest();
		
		/* 001aa9bba546 */ 
		short[] equipmentIdentity = new short[16];
		equipmentIdentity[0] = '0';
		equipmentIdentity[1] = '0';
		equipmentIdentity[2] = '1';
		equipmentIdentity[3] = 'a';
		equipmentIdentity[4] = 'a';
		equipmentIdentity[5] = '9';
		equipmentIdentity[6] = 'b';
		equipmentIdentity[7] = 'b';
		equipmentIdentity[8] = 'a';
		equipmentIdentity[9] = '5';
		equipmentIdentity[10] = '4';
		equipmentIdentity[11] = '6';
		equipmentIdentity[12] = 0;
		equipmentIdentity[13] = 0;
		equipmentIdentity[14] = 0;
		equipmentIdentity[15] = 0;
		phoneInfoRequest.setEquipmentIdentity(equipmentIdentity);

		short[] equipmentOS = new short[16];
		equipmentOS[0] = 'a';
		equipmentOS[1] = 'n';
		equipmentOS[2] = 'd';
		equipmentOS[3] = 'r';
		equipmentOS[4] = 'o';
		equipmentOS[5] = 'i';
		equipmentOS[6] = 'd';
		equipmentOS[7] = 0;
		equipmentOS[8] = 0;
		equipmentOS[9] = 0;
		equipmentOS[10] = 0;
		equipmentOS[11] = 0;
		equipmentOS[12] = 0;
		equipmentOS[13] = 0;
		equipmentOS[14] = 0;
		equipmentOS[15] = 0;		
		phoneInfoRequest.setEquipmentOS(equipmentOS);
		phoneInfoRequest.setReserve1((short)0);
		phoneInfoRequest.setReserve2((short)0);
		phoneInfoRequest.setReserve3((short)0);
		phoneInfoRequest.setReserve4((short)0);

		
		/* TLV_V_LoginRequest */
		TLV_V_LoginRequest loginRequest = new TLV_V_LoginRequest();
		loginRequest.setUserName("admin".toCharArray());
		loginRequest.setPassword("123456".toCharArray());
		loginRequest.setDeviceId(1);
		loginRequest.setFlag((short)1);
		loginRequest.setChannel((short)1);
		loginRequest.setReserve(0);
		
		/* Header */
		OwspPacketHeader packetHeader = new OwspPacketHeader();
		packetHeader.setPacket_length(112);
		packetHeader.setPacket_seq(1);

		
		byte[] buf = new byte[116];
		int offset = 0;
		
		
		/* Package Header */
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(OwspPacketHeader.class, packetHeader), offset, 
				OWSP_LEN.OwspPacketHeader);
		offset += OWSP_LEN.OwspPacketHeader;

		/* TLV_HEADER + TLV_V_VersionInfoRequest*/
		TLV_HEADER tlv1 = new TLV_HEADER();
		tlv1.setTlv_type(TLV_T_Command.TLV_T_VERSION_INFO_REQUEST);
		tlv1.setTlv_len(OWSP_LEN.TLV_V_VersionInfoRequest);
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv1), offset, OWSP_LEN.TLV_HEADER);
		offset += OWSP_LEN.TLV_HEADER;
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_VersionInfoRequest.class, versionInfoRequest), 
				offset, OWSP_LEN.TLV_V_VersionInfoRequest);
		offset += OWSP_LEN.TLV_V_VersionInfoRequest;

		/* TLV_HEADER + TLV_V_PhoneInfoRequest */
		TLV_HEADER tlv2 = new TLV_HEADER();
		tlv2.setTlv_type(TLV_T_Command.TLV_T_PHONE_INFO_REQUEST); 
		tlv2.setTlv_len(OWSP_LEN.TLV_V_PhoneInfoRequest);

		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv2), offset, OWSP_LEN.TLV_HEADER);
		offset += OWSP_LEN.TLV_HEADER;
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_PhoneInfoRequest.class, phoneInfoRequest), 
				offset, OWSP_LEN.TLV_V_PhoneInfoRequest);
		offset += OWSP_LEN.TLV_V_PhoneInfoRequest;

		/* TLV_HEADER + TLV_V_LoginRequest */
		TLV_HEADER tlv3 = new TLV_HEADER();
		tlv3.setTlv_type(TLV_T_Command.TLV_T_LOGIN_REQUEST); 
		tlv3.setTlv_len(OWSP_LEN.TLV_V_LoginRequest);
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv3), offset, OWSP_LEN.TLV_HEADER);
		offset += OWSP_LEN.TLV_HEADER;

		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_LoginRequest.class, loginRequest), 
				offset, OWSP_LEN.TLV_V_LoginRequest);
		offset += OWSP_LEN.TLV_V_LoginRequest;
		
		
		out.write(buf);
		System.out.println("Send: " + buf);
		
		
		FileOutputStream fout = new FileOutputStream(new File("/sdcard/test.video"), true);
		
		
		byte[] packetHeaderBuf = new byte[8];
		in.read(packetHeaderBuf);
		OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
		byte[] tlvContent = new byte[(int) owspPacketHeader.getPacket_length() - 4];
		in.read(tlvContent);
		
		while(!tlvContent.equals("")){
			//得到包长的数据uiLengt
			int nLeft=(int) owspPacketHeader.getPacket_length()-4; //未处理的字节数
			int nLen_hdr=OWSP_LEN.TLV_HEADER;
			int flag = 0;
			//循环处理所有的TLVl 
			while(nLeft>nLen_hdr)
			{
				//处理TLV头memcpy(&tlv_hdr,buf,nLen_hdr);
				TLV_HEADER tlv_Header = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, tlvContent, flag, OWSP_LEN.TLV_HEADER);
				
				//
				TLV_V_VersionInfoRequest tlv_V_VersionInfoRequest;
				TLV_V_DVSInfoRequest tlv_V_DVSInfoRequest;
				TLV_V_ChannelResponse tlv_V_ChannelResponse;
				TLV_V_StreamDataFormat tlv_V_StreamDataFormat;
				TLV_V_VideoFrameInfo tlv_V_VideoFrameInfo;
				
				nLeft-=nLen_hdr;
				flag+=nLen_hdr;
				//处理TLV的V部分
				if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VERSION_INFO_REQUEST){
					tlv_V_VersionInfoRequest = (TLV_V_VersionInfoRequest) ByteArray2Object.convert2Object(TLV_V_VersionInfoRequest.class, tlvContent, flag, OWSP_LEN.TLV_V_VersionInfoRequest);
					System.out.println(tlv_V_VersionInfoRequest);
				}
				else if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_DVS_INFO_REQUEST){
					tlv_V_DVSInfoRequest = (TLV_V_DVSInfoRequest) ByteArray2Object.convert2Object(TLV_V_DVSInfoRequest.class, tlvContent, flag, OWSP_LEN.TLV_V_DVSInfoRequest);
					System.out.println(tlv_V_DVSInfoRequest);
				}
				else if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_CHANNLE_ANSWER){
					tlv_V_ChannelResponse = (TLV_V_ChannelResponse) ByteArray2Object.convert2Object(TLV_V_ChannelResponse.class, tlvContent, flag, OWSP_LEN.TLV_V_ChannelResponse);
					System.out.println(tlv_V_ChannelResponse);
				}
				else if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_STREAM_FORMAT_INFO){
					tlv_V_StreamDataFormat = (TLV_V_StreamDataFormat) ByteArray2Object.convert2Object(TLV_V_StreamDataFormat.class, tlvContent, flag, OWSP_LEN.TLV_V_StreamDataFormat);
					System.out.println(tlv_V_StreamDataFormat);
				}
				else if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_FRAME_INFO){
					tlv_V_VideoFrameInfo = (TLV_V_VideoFrameInfo) ByteArray2Object.convert2Object(TLV_V_VideoFrameInfo.class, tlvContent, flag, OWSP_LEN.TLV_V_VideoFrameInfo);
					System.out.println(tlv_V_VideoFrameInfo);
				} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_IFRAME_DATA ) {
					fout.write((byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len()));
				} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_PFRAME_DATA ) {
					fout.write((byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len()));
				}
				nLeft-=tlv_Header.getTlv_len();
				flag+=tlv_Header.getTlv_len();
			}
			
			packetHeaderBuf = new byte[8];
			in.read(packetHeaderBuf);
			owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
			tlvContent = new byte[(int) owspPacketHeader.getPacket_length() - 4];
			in.read(tlvContent);
			
		}
		
		
		fout.close();
		
		
		
		
		
		
		
		
		
		/*
		byte[] readBuf = new byte[2048];
		in.read(readBuf);
		System.out.println("Recv: " + readBuf.toString());  
		
		byte[] readBuf1 = new byte[1024];
		in.read(readBuf1);
		//分析包
		int flag = 0;//记录已分析到数组第几位
		
		//Packet Header, len = 8
		OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, readBuf,flag,OWSP_LEN.OwspPacketHeader);
		flag+=OWSP_LEN.OwspPacketHeader;
		
		//TLV_HEADER +  TLV_V_VersionInfoRequest, len = 4 + 4
		TLV_HEADER tlv_Header1 = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, readBuf, flag, OWSP_LEN.TLV_HEADER);
		flag+=OWSP_LEN.TLV_HEADER;
		TLV_V_VersionInfoRequest tlv_V_VersionInfoRequest = (TLV_V_VersionInfoRequest) ByteArray2Object.convert2Object(TLV_V_VersionInfoRequest.class, readBuf, flag, OWSP_LEN.TLV_V_VersionInfoRequest);
		flag+=OWSP_LEN.TLV_V_VersionInfoRequest;
		
		//TLV_HEADER + TLV_V_DVSInfoRequest, len = 4 + 72
		TLV_HEADER tlv_Header2 = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, readBuf, flag, OWSP_LEN.TLV_HEADER);
		flag+=OWSP_LEN.TLV_HEADER;
		TLV_V_DVSInfoRequest tlv_V_DVSInfoRequest = (TLV_V_DVSInfoRequest) ByteArray2Object.convert2Object(TLV_V_DVSInfoRequest.class, readBuf, flag, OWSP_LEN.TLV_V_DVSInfoRequest);
		flag+=OWSP_LEN.TLV_V_DVSInfoRequest;
		
		//TLV_HEADER + TLV_V_DVSInfoRequest, len = 4 + 4
		TLV_HEADER tlv_Header3 = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, readBuf, flag, OWSP_LEN.TLV_HEADER);
		flag+=OWSP_LEN.TLV_HEADER;
		TLV_V_ChannelResponse tlv_V_ChannelResponse = (TLV_V_ChannelResponse) ByteArray2Object.convert2Object(TLV_V_ChannelResponse.class, readBuf, flag, OWSP_LEN.TLV_V_ChannelResponse);
		flag+=OWSP_LEN.TLV_V_ChannelResponse;
		
		// TLV_HEADER + TLV_V_StreamDataFormat, len = 4 + (4 + 16 + 20) = 44
		TLV_HEADER tlv_Header4 = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, readBuf, flag, OWSP_LEN.TLV_HEADER);
		flag+=OWSP_LEN.TLV_HEADER;
		TLV_V_StreamDataFormat tlv_V_StreamDataFormat = (TLV_V_StreamDataFormat) ByteArray2Object.convert2Object(TLV_V_StreamDataFormat.class, readBuf, flag, OWSP_LEN.TLV_V_StreamDataFormat);
		flag+=OWSP_LEN.TLV_V_StreamDataFormat;
		
		//TLV_HEADER + TLV_V_VideoFrameInfo, len = 4 + 12 
		TLV_HEADER tlv_Header5 = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, readBuf, flag, OWSP_LEN.TLV_HEADER);
		flag+=OWSP_LEN.TLV_HEADER;
		TLV_V_VideoFrameInfo tlv_V_VideoFrameInfo = (TLV_V_VideoFrameInfo) ByteArray2Object.convert2Object(TLV_V_VideoFrameInfo.class, readBuf, flag, OWSP_LEN.TLV_V_VideoFrameInfo);
		*/
		Assert.assertTrue(1 == 1);  
    } 
	
	@Override
	public void tearDown() throws Exception {
		try {
		 	if( socket != null ) {
		 		socket.close();
		 	}
		} catch (IOException e) {
				e.printStackTrace();
		}    
	}

}
