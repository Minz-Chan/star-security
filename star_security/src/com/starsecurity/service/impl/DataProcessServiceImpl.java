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

import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_DVSInfoRequest;
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
 *   2013-05-06 加入conn_name; 处理TLV_T_CHANNLE_ANSWER返回值      
 *   2013-04-15 首次增加 陈明珍 
 *   
 */
public class DataProcessServiceImpl implements DataProcessService {
	private String conn_name;
	private VideoView v = ViewManager.getInstance().getVideoView();
	private H264DecodeUtil h264 = new H264DecodeUtil();

	
	
	public DataProcessServiceImpl(String conn_name) {
		super();
		this.conn_name = conn_name;
		h264.init(352, 288);
	}



	@Override
	public int process(byte[] data, int length) {

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
			if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VERSION_INFO_REQUEST){
				TLV_V_VersionInfoRequest tlv_V_VersionInfoRequest;
				tlv_V_VersionInfoRequest = (TLV_V_VersionInfoRequest) ByteArray2Object.convert2Object(TLV_V_VersionInfoRequest.class, data, flag, OWSP_LEN.TLV_V_VersionInfoRequest);
				System.out.println(tlv_V_VersionInfoRequest);
			}
			else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_DVS_INFO_REQUEST){
				TLV_V_DVSInfoRequest tlv_V_DVSInfoRequest;
				tlv_V_DVSInfoRequest = (TLV_V_DVSInfoRequest) ByteArray2Object.convert2Object(TLV_V_DVSInfoRequest.class, data, flag, OWSP_LEN.TLV_V_DVSInfoRequest);
				System.out.println(tlv_V_DVSInfoRequest);
			}
			else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_CHANNLE_ANSWER){
				TLV_V_ChannelResponse tlv_V_ChannelResponse;
				tlv_V_ChannelResponse = (TLV_V_ChannelResponse) ByteArray2Object.convert2Object(TLV_V_ChannelResponse.class, data, flag, OWSP_LEN.TLV_V_ChannelResponse);
				
				if (tlv_V_ChannelResponse.getResult() == 1) { // 通道请求成功
					ConnectionManager.getConnection(conn_name).setChannel_no(tlv_V_ChannelResponse.getCurrentChannel());
				} else { // 通道请求失败
					
					
				}
				
				
				System.out.println(tlv_V_ChannelResponse);
				System.out.println("Result: " + tlv_V_ChannelResponse.getResult()
								 + "curChannel: " + tlv_V_ChannelResponse.getCurrentChannel()
								 + "reserve: " + tlv_V_ChannelResponse.getReserve());
			} else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_STREAM_FORMAT_INFO){
				TLV_V_StreamDataFormat tlv_V_StreamDataFormat;
				tlv_V_StreamDataFormat = (TLV_V_StreamDataFormat) ByteArray2Object.convert2Object(TLV_V_StreamDataFormat.class, data, flag, OWSP_LEN.TLV_V_StreamDataFormat);
				ViewManager.getInstance().setHelpMsg("FPS:" + tlv_V_StreamDataFormat.getVideoFormat().getFramerate() + " "
													 + "Width:" + tlv_V_StreamDataFormat.getVideoFormat().getWidth() + " "
													 + "Height:" + tlv_V_StreamDataFormat.getVideoFormat().getHeight() + " "
													 + "bitrate:" + (int)(tlv_V_StreamDataFormat.getVideoFormat().getBitrate() / 1024));
				System.out.println(tlv_V_StreamDataFormat);
			} else if (tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_FRAME_INFO){
				TLV_V_VideoFrameInfo tlv_V_VideoFrameInfo;
				tlv_V_VideoFrameInfo = (TLV_V_VideoFrameInfo) ByteArray2Object.convert2Object(TLV_V_VideoFrameInfo.class, data, flag, OWSP_LEN.TLV_V_VideoFrameInfo);
				System.out.println(tlv_V_VideoFrameInfo);
			} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_IFRAME_DATA ) {
				System.out.println("*********************** I Frame process start  *************************");
				byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, data, flag, tlv_Header.getTlv_len());
				
				int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
				if(result == 1) {
					v.postInvalidate();
					System.out.println("*********************** update video: I Frame  *************************");
				}
				System.out.println("*********************** I Frame process end  *************************");
			} else if ( tlv_Header.getTlv_type() == TLV_T_Command.TLV_T_VIDEO_PFRAME_DATA ) {
				System.out.println("*********************** P Frame process start  *************************");
				byte[] tmp = (byte[]) ByteArray2Object.convert2Object(TLV_V_VideoData.class, data, flag, tlv_Header.getTlv_len());
				
				int result = h264.decodePacket(tmp, tmp.length, v.getmPixel());
				if(result == 1) {
					v.postInvalidate();
					System.out.println("*********************** update video: P Frame  *************************");
				}
				System.out.println("*********************** P Frame process end  *************************");
				
			}
			nLeft -= tlv_Header.getTlv_len();
			flag += tlv_Header.getTlv_len();
		}
		
		
		
		return 0;
	}

}
