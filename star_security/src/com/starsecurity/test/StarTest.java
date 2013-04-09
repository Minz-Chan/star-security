/*
 * FileName:StarTest.java
 * 
 * Package:com.starsecurity
 * 
 * Date:2013-03-22
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import junit.framework.Assert;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.tvt.p2pplayer.BMPImage;
import com.tvt.p2pplayer.P2PPlayerH264Decode;
import com.tvt.p2pplayer.VideoView;


/**
 * @function     功能	  Android Junit测试用类
 * @author       创建人                 陳明珍
 * @date        创建日期           2013-03-22
 * @author       修改人                 陳明珍
 * @date        修改日期           2013-03-22
 * @description 修改说明	             首次增加
 */
public class StarTest extends AndroidTestCase  {
	
	Socket socket = null;
	OutputStream out = null;
    InputStream in = null ; 
	
	@Override
	public void setUp() throws Exception {
		/*
 		socket = new Socket("113.250.29.219", 8080);  
        System.out.println("Socket=" + socket);  
        
		out = socket.getOutputStream();
		in = socket.getInputStream();*/
		int i = 0;  
		i++;
		i++;
	}

	public void testJni() throws Throwable{
		
		int i = 0;
		
		i++;
		i = 1 + i;
		//assertTrue( 1 + 1 == 2);
		
		P2PPlayerH264Decode decoder = new P2PPlayerH264Decode(); 
		

		
		decoder.InitDecode();
		
		
		decoder.Cleanup();
		
		
		File file1 = new File("/sdcard/test.mp4");
		int file_len = (int)file1.length();
		
		int width = 352;
		int height = 288;
		BMPImage.Init(width, height);
		
		byte[] byte1 = new byte[file_len];
		FileInputStream fileInputStream1 = new FileInputStream(file1);
		fileInputStream1.read(byte1, 0, file_len);
		 
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		outstream.write(byte1);
		
		byte[] arrayOfByte19 = outstream.toByteArray();
		byte[] arrayOfByte21 = decoder.DecodeOneFrame(arrayOfByte19, arrayOfByte19.length, width, height);
	      
	      long l2 = System.currentTimeMillis();

	      int result = decoder.GetDecodeResult();
	      if (decoder.GetDecodeResult() == arrayOfByte19.length)
	      {
	        byte[] arrayOfByte22 = new BMPImage(arrayOfByte21).getByte();
	        Bitmap localBitmap1 = BitmapFactory.decodeByteArray(arrayOfByte22, 0, arrayOfByte22.length);
	        
	        //VideoView localVideoView = this.videoView;
	        //localVideoView.setBitmap(localBitmap1);
	      }
	      
	      
	      ByteBuffer pInBuffer = ByteBuffer.allocate(51200);//分配50k的缓存 
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
