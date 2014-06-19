/*
 * FileName:DataProcessServiceImpl.java
 * 
 * Package:com.starsecurity.service.impl
 * 
 * Date:2013-04-15
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.service.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.starsecurity.R;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.MessageCode;
import com.starsecurity.component.Timecounter;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OWSP_VideoDataFormat;
import com.starsecurity.model.ResponseCode;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_DVSInfoRequest;
import com.starsecurity.model.TLV_V_LoginResponse;
import com.starsecurity.model.TLV_V_StreamDataFormat;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_V_VideoData;
import com.starsecurity.model.TLV_V_VideoFrameInfo;
import com.starsecurity.model.convert.ByteArray2Object;
import com.starsecurity.service.DataProcessService;
import com.starsecurity.util.H264DecodeUtil;

/**
 * @function     功能	  数据处理接口实现类
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-15
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-15
 * @description 修改说明
 *   2013-05-06 加入conn_name; 处理TLV_T_CHANNLE_ANSWER返回值 陈明珍
 *   2013-04-15 首次增加 陈明珍 
 */
public class DataProcessServiceImpl implements DataProcessService {
	private String conn_name;
	private H264DecodeUtil h264 = new H264DecodeUtil();

	private boolean isIFrameFinished = false;
	
	public DataProcessServiceImpl(String conn_name) {
		super();
		this.conn_name = conn_name;
		h264.init(352, 288);
	}



	@Override
	public int process(byte[] data, int length) {
		VideoView v = ViewManager.getInstance().getVideoView();
		int nLeft = length - 4; //未处理的字节数
		int nLen_hdr = OWSP_LEN.TLV_HEADER;
		int flag = 0;
		
		//循环处理所有的TLVl 
		while(nLeft > nLen_hdr)
		{
			//处理TLV头memcpy(&tlv_hdr,buf,nLen_hdr);
			TLV_HEADER tlv_Header = (TLV_HEADER) ByteArray2Object.convert2Object(TLV_HEADER.class, data, flag, OWSP_LEN.TLV_HEADER);
			
			nLeft -= nLen_hdr;
			flag += nLen_hdr;
			
			System.out.println("=================== TLV_HEADER TYPE: " + tlv_Header.getTlv_type() + " ==================");
			System.out.println("=================== TLV_HEADER LENGTH: " + tlv_Header.getTlv_len() + " ==================");
			
			// 处理TLV的V部分
			
			
			if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_FRAME_INFO){
				TLV_V_VideoFrameInfo tlv_V_VideoFrameInfo;
				tlv_V_VideoFrameInfo = (TLV_V_VideoFrameInfo) ByteArray2Object.convert2Object(TLV_V_VideoFrameInfo.class, data, flag, OWSP_LEN.TLV_V_VideoFrameInfo);
				System.out.println(tlv_V_VideoFrameInfo);
				
				//ConnectionManager.getConnection(conn_name).addResultItem(tlv_V_VideoFrameInfo);
				
			} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_PFRAME_DATA ) {
				
				// 若第1帧接到的不是I帧，则后续的P帧不处理
				if (!isIFrameFinished) {
					return 0;
				}
				
				System.out.println("*********************** P Frame process start  *************************");
				byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, data, flag, tlv_Header.getTlv_len());
				
				int result = 0;
				
				try {
					result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
				} catch(Exception e) {
					// 解码过程发生异常
					ViewManager.getInstance().setHelpMsg(R.string.IDS_Unknown);
				}
				
				
				if(result == 1) {
					v.postInvalidate();
					System.out.println("*********************** update video: P Frame  *************************");
					System.out.println("$$$$$Time diff: " + Timecounter.getInstance().getTimeDiff());
				} else if (result == -2) {	// 分辨率切换
					//ConnectionManager.getConnection(conn_name).close();		// 关闭连接
					ConnectionManager.getConnection(conn_name).setConnect_state(0);
					
					Message msg = new Message();
					msg.what = MessageCode.CONNECTION_CLOSED;	// 提示连接断开
					updateUIMessage(msg);
				} else if (result < -66) { // 不支持的profile
					//ConnectionManager.getConnection(conn_name).close();		// 关闭连接
					ConnectionManager.getConnection(conn_name).setConnect_state(0);
					
					Message msg = new Message();
					msg.what = MessageCode.IDS_UNSUPPORTEDPROFILE;
					msg.arg2 = result;

					if (result == -77) {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_MAIN;	// 不支持的类型（Main Profile）
					} else if (result == -88) {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_EXTENDED; // 不支持的类型（Extended Profile）
					} else if (result == -100) {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_HIGH;	// 不支持的类型（High Profile）
					} else {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_UNKNOWN;	// 不支持的类型
					}
					
					updateUIMessage(msg);
				}
				
				System.out.println("*********************** P Frame process end  *************************");
				
			} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_IFRAME_DATA ) {
				System.out.println("*********************** I Frame process start  *************************");
				byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, data, flag, tlv_Header.getTlv_len());
				
				h264.setbFirst(true);
				h264.setbFindPPS(true);
				
				int result = 0;
				
				try {
					result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
				} catch(Exception e) {
					// 解码过程发生异常
					ViewManager.getInstance().setHelpMsg(R.string.IDS_Unknown);
				}
				
				isIFrameFinished = true;
				
				if(result == 1) {
					v.postInvalidate();
					System.out.println("*********************** update video: I Frame  *************************");
					System.out.println("$$$$$Time diff: " + Timecounter.getInstance().getTimeDiff());
				} else if (result == -2) {	// 分辨率切换
					//ConnectionManager.getConnection(conn_name).close();		// 关闭连接
					ConnectionManager.getConnection(conn_name).setConnect_state(0);
					
					Message msg = new Message();
					msg.what = MessageCode.CONNECTION_CLOSED;	// 提示连接断开
					updateUIMessage(msg);			
				} else if (result < -66) { // 不支持的profile
					//ConnectionManager.getConnection(conn_name).close();		// 关闭连接
					ConnectionManager.getConnection(conn_name).setConnect_state(0);
					
					Message msg = new Message();
					msg.what = MessageCode.IDS_UNSUPPORTEDPROFILE;
					msg.arg2 = result;

					if (result == -77) {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_MAIN;	// 不支持的类型（Main Profile）
					} else if (result == -88) {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_EXTENDED; // 不支持的类型（Extended Profile）
					} else if (result == -100) {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_HIGH;	// 不支持的类型（High Profile）
					} else {
						msg.arg1 = R.string.IDS_UNSUPPORTEDPROFILE_UNKNOWN;	// 不支持的类型
					}
					
					updateUIMessage(msg);
				}
				
				System.out.println("*********************** I Frame process end  *************************");

			} else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VERSION_INFO_REQUEST){	
				TLV_V_VersionInfoRequest tlv_V_VersionInfoRequest;
				tlv_V_VersionInfoRequest = (TLV_V_VersionInfoRequest) ByteArray2Object.convert2Object(TLV_V_VersionInfoRequest.class, data, flag, OWSP_LEN.TLV_V_VersionInfoRequest);
				System.out.println(tlv_V_VersionInfoRequest);
				//ConnectionManager.getConnection(conn_name).addResultItem(tlv_V_VersionInfoRequest);
			}
			else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_DVS_INFO_REQUEST){	
				TLV_V_DVSInfoRequest tlv_V_DVSInfoRequest;
				tlv_V_DVSInfoRequest = (TLV_V_DVSInfoRequest) ByteArray2Object.convert2Object(TLV_V_DVSInfoRequest.class, data, flag, OWSP_LEN.TLV_V_DVSInfoRequest);
				System.out.println(tlv_V_DVSInfoRequest);
				ConnectionManager.getConnection(conn_name).addResultItem(tlv_V_DVSInfoRequest);
				
				//发送连接成功,可获取最大通道数
				Handler handler = ViewManager.getInstance().getHandler();
				Message msg = new Message();
				msg.what = MessageCode.IDS_CONNECTMAXCHANNEL;
				if (handler != null) {
					handler.sendMessage(msg);
				}
			} else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_CHANNLE_ANSWER){	
				TLV_V_ChannelResponse tlv_V_ChannelResponse;
				tlv_V_ChannelResponse = (TLV_V_ChannelResponse) ByteArray2Object.convert2Object(TLV_V_ChannelResponse.class, data, flag, OWSP_LEN.TLV_V_ChannelResponse);
				
				if (tlv_V_ChannelResponse.getResult() == 1) { // 通道请求成功
					//ConnectionManager.getConnection(conn_name).setChannel_no(tlv_V_ChannelResponse.getCurrentChannel());
				} else { // 通道请求失败
					
					
				}
				
				ConnectionManager.getConnection(conn_name).addResultItem(tlv_V_ChannelResponse);
				
				// 通道切换时，第1帧必须为I帧
				isIFrameFinished = false;
				
				System.out.println(tlv_V_ChannelResponse);
				System.out.println("Result: " + tlv_V_ChannelResponse.getResult()
								 + "curChannel: " + tlv_V_ChannelResponse.getCurrentChannel()
								 + "reserve: " + tlv_V_ChannelResponse.getReserve());
			} else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_STREAM_FORMAT_INFO){
				TLV_V_StreamDataFormat tlv_V_StreamDataFormat;
				tlv_V_StreamDataFormat = (TLV_V_StreamDataFormat) ByteArray2Object.convert2Object(TLV_V_StreamDataFormat.class, data, flag, OWSP_LEN.TLV_V_StreamDataFormat);
				
				System.out.println(tlv_V_StreamDataFormat);
				
				int framerate = tlv_V_StreamDataFormat.getVideoFormat().getFramerate();
				int width = tlv_V_StreamDataFormat.getVideoFormat().getWidth();
				int height = tlv_V_StreamDataFormat.getVideoFormat().getHeight();
				int bitrate = (int)(tlv_V_StreamDataFormat.getVideoFormat().getBitrate() / 1024);
				
				if (tlv_V_StreamDataFormat != null) {
					ViewManager.getInstance().setHelpMsg("FPS:" + framerate + " "
													 + "Width:" + width + " "
													 + "Height:" + height + " "
													 + "bitrate:" + bitrate);
					
					//OWSP_VideoDataFormat videoDataFormat = tlv_V_StreamDataFormat.getVideoFormat();
					
					System.out.println("@@@@@Width: " + width + ", height: " + height);
					
					if (width > 0 && height > 0) {
						h264.init(width, height);	// 初始化视频分辨率
						//ViewManager.getInstance().getMainVideoView().changeScreenRevolution(width, height);
						
						if (width <= height) {	// 走廊模式
							ViewManager.getInstance().setCorridorMode(true);
						} else {
							ViewManager.getInstance().setCorridorMode(false);
						}
						
						VideoView.changeScreenRevolution(width, height);
						
						//ViewManager.getInstance().getMainVideoView().init();
						Message msg =  Message.obtain();
						msg.what = MessageCode.UPDATE_VIDEO_SCALE;
						updateUIMessage(msg);
					}
				}
				
				//ConnectionManager.getConnection(conn_name).addResultItem(tlv_V_StreamDataFormat);
			} else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_LOGIN_ANSWER) {
				TLV_V_LoginResponse tlv_V_LoginResponse;
				tlv_V_LoginResponse = (TLV_V_LoginResponse) ByteArray2Object.convert2Object(TLV_V_LoginResponse.class, data, flag, OWSP_LEN.TLV_V_LoginResponse);
				
				int result = tlv_V_LoginResponse.getResult();
				
				Handler handler = ViewManager.getInstance().getHandler();
				Message msg = new Message();
				
				if (result == ResponseCode._RESPONSECODE_SUCC) { // 登录服务器成功
					ViewManager.getInstance().setHelpMsg(R.string.IDS_LoginSerSuccess);  
				} else {
					ConnectionManager.getConnection(conn_name).setConnect_state(0);	// 关闭连接
					
					if (result == ResponseCode._RESPONSECODE_USER_PWD_ERROR) { // 用户或密码错误
						msg.what = MessageCode._RESPONSECODE_USER_PWD_ERROR;
					} else if (result == ResponseCode._RESPONSECODE_PDA_VERSION_ERROR) { // 版本不一致
						msg.what = MessageCode._RESPONSECODE_PDA_VERSION_ERROR;
					} else if (result == ResponseCode._RESPONSECODE_MAX_USER_ERROR) { // 最大用户数
						msg.what = MessageCode._RESPONSECODE_MAX_USER_ERROR;
					} else if (result == ResponseCode._RESPONSECODE_DEVICE_OFFLINE) { // 设备已离线
						msg.what = MessageCode._RESPONSECODE_DEVICE_OFFLINE;
					} else { // 其他
						// 登录服务器失败，原因即用户或密码错误
						msg.what = MessageCode._RESPONSECODE_USER_PWD_ERROR;

					}  
					
					if (handler != null) {
						handler.sendMessage(msg);
					}
					
				}
				
			}
			
			
			
			nLeft -= tlv_Header.getTlv_len();
			flag += tlv_Header.getTlv_len();
		}
		
		
		
		return 0;
	}
	
	
	/**
	 * 更新UI消息传递
	 * @param msg 消息对象
	 */
	private void updateUIMessage(Message msg) {
		Handler handler = ViewManager.getInstance().getHandler();
		if (handler != null) {
			handler.sendMessage(msg);
		} else {
			ViewManager.getInstance().setHelpMsg(R.string.IDS_Unknown);
		}
	}

}
