/*
 * FileName:ControlServiceImpl.java
 * 
 * Package:com.starsecurity.service.impl
 * 
 * Date:2013-04-15
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.service.impl;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;

import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.H264StreamReceiver;
import com.starsecurity.component.Packet;
import com.starsecurity.component.StreamSender;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelRequest;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.service.ControlService;
import com.starsecurity.util.DataProcessUtil;


/**
 * @function     功能	  控制操作接口实现类
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-15
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-15
 * @description 修改说明	          首次增加
 *   2013-04-05 首次增加 陈明珍
 */
public class ControlServiceImpl implements ControlService {
	private String conn_name = null;
	private Socket client = null;

	public ControlServiceImpl(String conn_name) {
		super();
		this.conn_name= conn_name; 
		//this.client = ConnectionManager.getConnection(this.conn_name).getSock();

	}

	@Override
	public int playVideo() {
		new Thread(new H264StreamReceiver(conn_name)).start();
		return 1;
	}

	@Override
	public int stopVideo() {
		ConnectionManager.getConnection(conn_name).close();
		return 1;
	}

	@Override
	public int move(String direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int zoomInOrOut(boolean flag) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int adjustFocus(boolean flag) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int adjustAperture(boolean flag) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int switchChannel(int channel_no) {
		int result = 0;
		Connection conn = ConnectionManager.getConnection(conn_name);
		
		if (conn.getConnect_state() == 1) {
			// 发送TLV_V_ChannelRequest数据包
			TLV_V_ChannelRequest channelRequest = new TLV_V_ChannelRequest();
			channelRequest.setDeviceId(1);
			channelRequest.setSourceChannel((short)ConnectionManager.getConnection(conn_name).getChannel_no());
			channelRequest.setDestChannel((short)(channel_no - 1));
			channelRequest.setReserve(new short[]{0, 0});
			

			Packet p = new Packet();
			p.add(channelRequest);
			
			StreamSender sender = new StreamSender(conn_name, p.getPacketByteStream());
			new Thread(sender).start();
			
			result = 1;
		} else {
			
		}

		return result;
	}
	
	
	
	
}
