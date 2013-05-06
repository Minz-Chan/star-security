package com.starsecurity.model.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.view.View;

import com.starsecurity.h264.VideoView;
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
import com.starsecurity.util.H264DecodeUtil;

public class H264StreamingReceiver implements Runnable {

	VideoView v;
	PipedOutputStream pipedOutStream = new PipedOutputStream();
	
	public PipedOutputStream getOutStream() {
		return pipedOutStream;
	}
	
	
	public void setVideoView(View view) {
		
		this.v = (VideoView) view;
		
	}
	
	
	
	@Override
	public void run() {
		/*
		File f = new File("/sdcard/test.264");
		
		try {
			if(!f.exists()) {
				f.createNewFile();
			} else {
				f.delete();
				f.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		if(v == null) {
			return;
		}
		
		H264DecodeUtil h264 = new H264DecodeUtil();
		
		h264.init(320, 288);
		
		Socket socket = null;
		OutputStream socketWriter = null;
	    InputStream socketReader = null ; 
	    
	    
 		try {
			socket = new Socket("119.86.151.82", 8080);
			System.out.println("Socket=" + socket);  
		        
			socketWriter = socket.getOutputStream();
			socketReader = socket.getInputStream();
		    
			
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
			loginRequest.setChannel((short)0);
			loginRequest.setReserve(0);
			
			/* Header */
			OwspPacketHeader packetHeader = new OwspPacketHeader();
			packetHeader.setPacket_length(112);
			packetHeader.setPacket_seq(1);

			
			byte[] buf = new byte[116];
			int offset = 0;
			
			
			/* Package Header */
			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(packetHeader), offset, 
					OWSP_LEN.OwspPacketHeader);
			offset += OWSP_LEN.OwspPacketHeader;

			/* TLV_HEADER + TLV_V_VersionInfoRequest*/
			TLV_HEADER tlv1 = new TLV_HEADER();
			tlv1.setTlv_type(TLV_T_Command.TLV_T_VERSION_INFO_REQUEST);
			tlv1.setTlv_len(OWSP_LEN.TLV_V_VersionInfoRequest);
			
			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(tlv1), offset, OWSP_LEN.TLV_HEADER);
			offset += OWSP_LEN.TLV_HEADER;
			
			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(versionInfoRequest), 
					offset, OWSP_LEN.TLV_V_VersionInfoRequest);
			offset += OWSP_LEN.TLV_V_VersionInfoRequest;

			/* TLV_HEADER + TLV_V_PhoneInfoRequest */
			TLV_HEADER tlv2 = new TLV_HEADER();
			tlv2.setTlv_type(TLV_T_Command.TLV_T_PHONE_INFO_REQUEST); 
			tlv2.setTlv_len(OWSP_LEN.TLV_V_PhoneInfoRequest);

			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(tlv2), offset, OWSP_LEN.TLV_HEADER);
			offset += OWSP_LEN.TLV_HEADER;
			
			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(phoneInfoRequest), 
					offset, OWSP_LEN.TLV_V_PhoneInfoRequest);
			offset += OWSP_LEN.TLV_V_PhoneInfoRequest;

			/* TLV_HEADER + TLV_V_LoginRequest */
			TLV_HEADER tlv3 = new TLV_HEADER();
			tlv3.setTlv_type(TLV_T_Command.TLV_T_LOGIN_REQUEST); 
			tlv3.setTlv_len(OWSP_LEN.TLV_V_LoginRequest);
			
			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(tlv3), offset, OWSP_LEN.TLV_HEADER);
			offset += OWSP_LEN.TLV_HEADER;

			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(loginRequest), 
					offset, OWSP_LEN.TLV_V_LoginRequest);
			offset += OWSP_LEN.TLV_V_LoginRequest;
			
			
			socketWriter.write(buf);
			System.out.println("Send: " + buf);
			
			
			
			
			int lenDataRead = 0;
			byte[] packetHeaderBuf = new byte[8];
			socketReader.read(packetHeaderBuf);
			OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
			byte[] tlvContent = new byte[65536];
			lenDataRead = (int) owspPacketHeader.getPacket_length() - 4;
			socketReader.read(tlvContent, 0, lenDataRead);
			
			
			
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
						
						byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len());
						
						int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
						if(result == 1) {
							v.postInvalidate();
						}
					
					} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_PFRAME_DATA ) {
						
						byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len());
						
						int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
						if(result == 1) {
							v.postInvalidate();
						}
						
						
					}
					nLeft-=tlv_Header.getTlv_len();
					flag+=tlv_Header.getTlv_len();
				}
				
				/* 该部分须检测所收的包的正确性 */
				packetHeaderBuf = null;			
				packetHeaderBuf = new byte[8];
				socketReader.read(packetHeaderBuf);
				owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
				//tlvContent = new byte[(int) owspPacketHeader.getPacket_length() - 4];
				
				/* 上一部分须保证接收下来的包的正确性，防止出现lenDataRead < 0的情况 */
				lenDataRead = (int) owspPacketHeader.getPacket_length() - 4;
				socketReader.read(tlvContent, 0, lenDataRead);
				
				
			}
			
			//fout.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
 		
 		h264.uninit();

	}

}
