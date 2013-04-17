package com.starsecurity.service.impl;


import java.net.Socket;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.H264StreamReceiver;
import com.starsecurity.service.ControlService;


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
		return 0;
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
}
