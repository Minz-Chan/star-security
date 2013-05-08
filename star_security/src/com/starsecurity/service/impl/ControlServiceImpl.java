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


import java.net.Socket;


import com.starsecurity.R;
import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.H264StreamReceiver;
import com.starsecurity.component.Packet;
import com.starsecurity.component.StreamSender;
import com.starsecurity.component.ViewManager;
import com.starsecurity.model.TLV_V_ChannelRequest;
import com.starsecurity.model.TLV_V_ControlRequest;
import com.starsecurity.service.ControlService;



/**
 * @function     功能	  控制操作接口实现类
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-15
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-07
 * @description 修改说明	      
 *   2013-05-07 放大/缩小、焦距放大/缩小、光圈放大/缩小实现 陈明珍
 *   2013-05-06 频道切换实现  陈明珍   
 *   2013-04-15 首次增加 陈明珍
 */
public class ControlServiceImpl implements ControlService {
	private String conn_name = null;

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
		int cmdCode = 0;
		
		if (direction.equals("UP")) {
			cmdCode = 9;	// OWSP_ACTION_MD_UP，向上
		} else if(direction.equals("DOWN")) {
			cmdCode = 10;	// OWSP_ACTION_MD_DOWN，向下
		} else if(direction.equals("LEFT")) {
			cmdCode = 11;	// OWSP_ACTION_MD_LEFT，向左
		} else if(direction.equals("RIGHT")) {
			cmdCode = 12;	// OWSP_ACTION_MD_RIGHT，向右
		}


		return sendControlRequest(cmdCode);
	}

	@Override
	public int zoomInOrOut(boolean flag) {
		int cmdCode = 0;
		
		if (flag) {
			cmdCode = 6;	// OWSP_ACTION_ZOOMADD，放大
		} else {
			cmdCode = 5;	// OWSP_ACTION_ZOOMReduce, 缩小
		}
		
		return sendControlRequest(cmdCode);
	}

	@Override
	public int adjustFocus(boolean flag) {
		int cmdCode = 0;
		
		if (flag) {
			cmdCode = 7;	// OWSP_ACTION_FOCUSADD，焦距放大
		} else {
			cmdCode = 8;	// OWSP_ACTION_FOCUSReduce，焦距缩小
		}
		
		return sendControlRequest(cmdCode);
	}

	@Override
	public int adjustAperture(boolean flag) {
		int cmdCode = 0;
		
		if (flag) {
			cmdCode = 13;	// OWSP_ACTION_Circle_Add， 光圈放大
		} else {
			cmdCode = 14;	// OWSP_ACTION_Circle_Reduce，光圈缩小
		}
		
		return sendControlRequest(cmdCode);
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
			
			ViewManager.getInstance().setHelpMsg(R.string.IDS_ChangeChannel);
			
			result = 1;
		} else {
			
		}

		return result;
	}
	
	
	
	private int sendControlRequest(int cmdCode) {
		int result = 0;
		Connection conn = ConnectionManager.getConnection(conn_name);
		
		if (conn.getConnect_state() == 1) {
			// 发送TLV_V_ControlRequest数据包
			TLV_V_ControlRequest controlRequest = new TLV_V_ControlRequest();
			controlRequest.setDeviceId(1);
			controlRequest.setChannel((short)conn.getChannel_no());
			controlRequest.setCmdCode((short)cmdCode);
			controlRequest.setSize(0);
			
			Packet p = new Packet();
			p.add(controlRequest);
			
			StreamSender sender = new StreamSender(conn_name, p.getPacketByteStream());
			new Thread(sender).start();

			
			result = 1;
		} 

		return result;
	}
}
