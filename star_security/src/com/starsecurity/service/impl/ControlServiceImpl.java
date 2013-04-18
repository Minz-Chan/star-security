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
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.H264StreamReceiver;
import com.starsecurity.service.ControlService;


/**
 * @function     功能	  控制操作接口实现类
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-15
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-15
 * @description 修改说明	          首次增加
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
		
		if (ConnectionManager.getConnection(conn_name).getConnect_state() == 1) {
			// 发送TLV_V_ChannelRequest数据包
		} else {
			
			
		}
		
		/*
		if (ConnectionManager.getConnection(conn_name).close() == 1) {
			ConnectionManager.getConnection(conn_name).setChannel_no(channel_no);
			return playVideo();
		}*/
		return result;
	}
	
	
	
	
}
