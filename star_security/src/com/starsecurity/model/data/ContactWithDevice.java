package com.starsecurity.model.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.view.Menu;
import android.view.MenuInflater;

import com.starsecurity.R;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelRequest;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_ControlRequest;
import com.starsecurity.model.TLV_V_ControlResponse;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_StreamDataFormat;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_V_VideoFrameInfo;
import com.starsecurity.model.convert.ByteArray2Object;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.util.DataProcessUtil;
/***
 * 
 * @function    功能	                    用于建立、处理手机端与设备间的通信
 * @author      创建人               肖远东
 * @date        创建日期           2013-04-10
 * @author      修改人               肖远东
 * @date        修改日期           2013-04-10
 * @description 修改说明	         首次增加
 *
 */
public class ContactWithDevice {
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;
	//用于记录PacketHeader中sequence的值
	private static int sequence = 1;
	//用于与监控设备通信的socket
	private Socket socket = null;
	//从socket获得输入输出流供通信使用
	private OutputStream socketWriter = null;
	private InputStream socketReader = null;
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public OutputStream getSocketWriter() {
		return socketWriter;
	}
	public void setSocketWriter(OutputStream out) {
		this.socketWriter = out;
	}
	public InputStream getSocketReader() {
		return socketReader;
	}
	public void setSocketReader(InputStream in) {
		this.socketReader = in;
	}
	
	/***
	 * 
	 * 建立于服务器的通信
	 * @param ip 需要通信的设备IP
	 * @param port 需要通信的设备端口
	 * @param versionInfoRequest
	 * @param phoneInfoRequest
	 * @param loginRequest
	 * @return result即尝试建立通信的结果（成功或失败） 
	 * 
	 */
//	public int establishTheConnection(String ip,int port,TLV_V_VersionInfoRequest versionInfoRequest,TLV_V_PhoneInfoRequest phoneInfoRequest,TLV_V_LoginRequest loginRequest){
//		if(ip==null)
//			return FAILURE;
//		try {
//			socket = new Socket(ip, port);
//			socketWriter = socket.getOutputStream();
//			socketReader = socket.getInputStream();
//		
//			OwspPacketHeader packetHeader = new OwspPacketHeader();
//			packetHeader.setPacket_length(112);
//			packetHeader.setPacket_seq(sequence++);
//			byte[] buf = new byte[116];
//			int offset = 0;
//		
//			
//			/* Package Header */		
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(OwspPacketHeader.class, packetHeader), offset, 
//					OWSP_LEN.OwspPacketHeader);
//			offset += OWSP_LEN.OwspPacketHeader;
//			
//			/* TLV_HEADER + TLV_V_VersionInfoRequest*/
//			TLV_HEADER tlv1 = new TLV_HEADER();
//			tlv1.setTlv_type(TLV_T_Command.TLV_T_VERSION_INFO_REQUEST);
//			tlv1.setTlv_len(OWSP_LEN.TLV_V_VersionInfoRequest);
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv1), offset, OWSP_LEN.TLV_HEADER);
//			offset += OWSP_LEN.TLV_HEADER;
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_VersionInfoRequest.class, versionInfoRequest), 
//					offset, OWSP_LEN.TLV_V_VersionInfoRequest);
//			offset += OWSP_LEN.TLV_V_VersionInfoRequest;
//	
//			/* TLV_HEADER + TLV_V_PhoneInfoRequest */
//			TLV_HEADER tlv2 = new TLV_HEADER();
//			tlv2.setTlv_type(TLV_T_Command.TLV_T_PHONE_INFO_REQUEST); 
//			tlv2.setTlv_len(OWSP_LEN.TLV_V_PhoneInfoRequest);
//	
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv2), offset, OWSP_LEN.TLV_HEADER);
//			offset += OWSP_LEN.TLV_HEADER;
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_PhoneInfoRequest.class, phoneInfoRequest), 
//					offset, OWSP_LEN.TLV_V_PhoneInfoRequest);
//			offset += OWSP_LEN.TLV_V_PhoneInfoRequest;
//	
//			/* TLV_HEADER + TLV_V_LoginRequest */
//			TLV_HEADER tlv3 = new TLV_HEADER();
//			tlv3.setTlv_type(TLV_T_Command.TLV_T_LOGIN_REQUEST); 
//			tlv3.setTlv_len(OWSP_LEN.TLV_V_LoginRequest);
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv3), offset, OWSP_LEN.TLV_HEADER);
//			offset += OWSP_LEN.TLV_HEADER;
//	
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_LoginRequest.class, loginRequest), 
//					offset, OWSP_LEN.TLV_V_LoginRequest);
//			offset += OWSP_LEN.TLV_V_LoginRequest;		
//			socketWriter.write(buf);
//			return SUCCESS;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return FAILURE;
//		}
//	}
//	
//	/***
//	 * 
//	 * 通道切换请求函数
//	 * @param channelRequest 通道切换请求类
//	 * @return
//	 * 
//	 */
//	public int changeChannel(TLV_V_ChannelRequest channelRequest){
//		try {
//			OwspPacketHeader packetHeader = new OwspPacketHeader();
//			int packetlength = OWSP_LEN.OwspPacketHeader+OWSP_LEN.TLV_HEADER+OWSP_LEN.TLV_V_ChannelRequest;
//			packetHeader.setPacket_length(packetlength);
//			packetHeader.setPacket_seq(sequence++);
//			byte[] buf = new byte[packetlength+4];
//			int offset = 0;
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(OwspPacketHeader.class, packetHeader), offset, 
//					OWSP_LEN.OwspPacketHeader);
//			offset += OWSP_LEN.OwspPacketHeader;
//			
//			/* TLV_HEADER + TLV_V_ChannelRequest*/
//			TLV_HEADER tlv1 = new TLV_HEADER();
//			tlv1.setTlv_type(TLV_T_Command.TLV_T_CHANNLE_REQUEST);
//			tlv1.setTlv_len(OWSP_LEN.TLV_V_ChannelRequest);
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv1), offset, OWSP_LEN.TLV_HEADER);
//			offset += OWSP_LEN.TLV_HEADER;
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_ChannelRequest.class, channelRequest), 
//					offset, OWSP_LEN.TLV_V_ChannelRequest);
//			offset += OWSP_LEN.TLV_V_ChannelRequest;
//			socketWriter.write(buf);
//			return SUCCESS;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return FAILURE;
//	}
//
//	public int reciveChannelResponse(){
//		try {
//			int lenDataRead = 0;
//			byte[] packetHeaderBuf = new byte[8];
//			socketReader.read(packetHeaderBuf);
//			OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
//			byte[] tlvContent = new byte[65536];
//			lenDataRead = (int) owspPacketHeader.getPacket_length() - 4;
//			socketReader.read(tlvContent, 0, lenDataRead);
//			
//			while(!tlvContent.equals("")){
//				//得到包长的数据uiLengt
//				int nLeft=(int) owspPacketHeader.getPacket_length()-4; //未处理的字节数
//				int nLen_hdr=OWSP_LEN.TLV_HEADER;
//				int flag = 0;
//				//循环处理所有的TLVl 
//				while(nLeft>nLen_hdr)
//				{
//					//处理TLV头memcpy(&tlv_hdr,buf,nLen_hdr);
//					TLV_HEADER tlv_Header = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, tlvContent, flag, OWSP_LEN.TLV_HEADER);
//					
//					//
//					TLV_V_ChannelResponse tlv_V_ChannelResponse;
//					TLV_V_StreamDataFormat tlv_V_StreamDataFormat;
//					TLV_V_VideoFrameInfo tlv_V_VideoFrameInfo;
//					
//					
//					nLeft-=nLen_hdr;
//					flag+=nLen_hdr;
//					//处理TLV的V部分
//					if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_CHANNLE_ANSWER){
//						tlv_V_ChannelResponse = (TLV_V_ChannelResponse) ByteArray2Object.convert2Object(TLV_V_ChannelResponse.class, tlvContent, flag, OWSP_LEN.TLV_V_ChannelResponse);
//						System.out.println(tlv_V_ChannelResponse);
//					}
//					else if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_STREAM_FORMAT_INFO){
//						tlv_V_StreamDataFormat = (TLV_V_StreamDataFormat) ByteArray2Object.convert2Object(TLV_V_StreamDataFormat.class, tlvContent, flag, OWSP_LEN.TLV_V_StreamDataFormat);
//						System.out.println(tlv_V_StreamDataFormat);
//					}
//					else if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_FRAME_INFO){
//						tlv_V_VideoFrameInfo = (TLV_V_VideoFrameInfo) ByteArray2Object.convert2Object(TLV_V_VideoFrameInfo.class, tlvContent, flag, OWSP_LEN.TLV_V_VideoFrameInfo);
//						System.out.println(tlv_V_VideoFrameInfo);
//					}
//					else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_IFRAME_DATA ) {
//						
//						/*byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len());
//						
//						int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
//						if(result == 1) {
//							v.postInvalidate();
//						}*/
//						
//
//					
//					} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_PFRAME_DATA ) {
//						
//						/*byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len());
//						
//						int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
//						if(result == 1) {
//							v.postInvalidate();
//						}*/
//						
//						
//					}
//					nLeft-=tlv_Header.getTlv_len();
//					flag+=tlv_Header.getTlv_len();
//				}
//				
//				/* 该部分须检测所收的包的正确性 */
//				packetHeaderBuf = null;			
//				packetHeaderBuf = new byte[8];
//				socketReader.read(packetHeaderBuf);
//				owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
//				//tlvContent = new byte[(int) owspPacketHeader.getPacket_length() - 4];
//				
//				/* 上一部分须保证接收下来的包的正确性，防止出现lenDataRead < 0的情况 */
//				lenDataRead = (int) owspPacketHeader.getPacket_length() - 4;
//				socketReader.read(tlvContent, 0, lenDataRead);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return FAILURE;
//	}
//	
//	/***
//	 * 
//	 * 对云台进行控制
//	 * @param controlRequest 云台控制请求，控制动作主要根据cmdCode
//	 * @return
//	 * 
//	 */
//	public int cloudControl(TLV_V_ControlRequest controlRequest){
//		try {
//			OwspPacketHeader packetHeader = new OwspPacketHeader();
//			int packetlength = OWSP_LEN.OwspPacketHeader+OWSP_LEN.TLV_HEADER+OWSP_LEN.TLV_V_ControlRequest;
//			packetHeader.setPacket_length(packetlength);
//			packetHeader.setPacket_seq(sequence++);
//			byte[] buf = new byte[packetlength+4];
//			int offset = 0;
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(OwspPacketHeader.class, packetHeader), offset, 
//					OWSP_LEN.OwspPacketHeader);
//			offset += OWSP_LEN.OwspPacketHeader;
//			
//			/* TLV_HEADER + TLV_V_ControlRequest*/
//			TLV_HEADER tlv1 = new TLV_HEADER();
//			tlv1.setTlv_type(TLV_T_Command.TLV_T_CONTROL_REQUEST);
//			tlv1.setTlv_len(OWSP_LEN.TLV_V_ControlRequest);
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv1), offset, OWSP_LEN.TLV_HEADER);
//			offset += OWSP_LEN.TLV_HEADER;
//			
//			DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_ControlRequest.class, controlRequest), 
//					offset, OWSP_LEN.TLV_V_ControlRequest);
//			offset += OWSP_LEN.TLV_V_ControlRequest;
//			socketWriter.write(buf);
//			return SUCCESS;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return FAILURE;
//	}
//	
//	public int recivecloudControlResponse(){
//		try{
//			int lenDataRead = 0;
//			byte[] packetHeaderBuf = new byte[8];
//			socketReader.read(packetHeaderBuf);
//			OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
//			byte[] tlvContent = new byte[65536];
//			lenDataRead = (int) owspPacketHeader.getPacket_length() - 4;
//			socketReader.read(tlvContent, 0, lenDataRead);
//			
//			while(!tlvContent.equals("")){
//				//得到包长的数据uiLengt
//				int nLeft=(int) owspPacketHeader.getPacket_length()-4; //未处理的字节数
//				int nLen_hdr=OWSP_LEN.TLV_HEADER;
//				int flag = 0;
//				//循环处理所有的TLVl 
//				while(nLeft>nLen_hdr)
//				{
//					//处理TLV头memcpy(&tlv_hdr,buf,nLen_hdr);
//					TLV_HEADER tlv_Header = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, tlvContent, flag, OWSP_LEN.TLV_HEADER);
//					
//					//
//					TLV_V_ControlResponse tlv_V_ControlResponse;
//					
//					
//					nLeft-=nLen_hdr;
//					flag+=nLen_hdr;
//					//处理TLV的V部分
//					if(tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_CONTROL_REQUEST){
//						tlv_V_ControlResponse = (TLV_V_ControlResponse) ByteArray2Object.convert2Object(TLV_V_ControlResponse.class, tlvContent, flag, OWSP_LEN.TLV_V_ControlResponse);
//						System.out.println(tlv_V_ControlResponse);
//					}
//					
//					else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_IFRAME_DATA ) {
//						/*
//						byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len());
//						
//						int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
//						if(result == 1) {
//							v.postInvalidate();
//						}
//						*/
//
//					
//					} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_PFRAME_DATA ) {
//						
//						/*byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, tlvContent, flag, tlv_Header.getTlv_len());
//						
//						int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
//						if(result == 1) {
//							v.postInvalidate();
//						}*/
//						
//						
//					}
//					nLeft-=tlv_Header.getTlv_len();
//					flag+=tlv_Header.getTlv_len();
//				}
//				
//				/* 该部分须检测所收的包的正确性 */
//				packetHeaderBuf = null;			
//				packetHeaderBuf = new byte[8];
//				socketReader.read(packetHeaderBuf);
//				owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
//				//tlvContent = new byte[(int) owspPacketHeader.getPacket_length() - 4];
//				
//				/* 上一部分须保证接收下来的包的正确性，防止出现lenDataRead < 0的情况 */
//				lenDataRead = (int) owspPacketHeader.getPacket_length() - 4;
//				socketReader.read(tlvContent, 0, lenDataRead);
//			}
//		}catch(Exception e){
//			
//		}
//		return FAILURE;
//	}
}