/*
 * FileName:Connection.java
 * 
 * Package:com.starsecurity.component
 * 
 * Date:2013-04-14
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.starsecurity.R;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_Version;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.util.DataProcessUtil;

/**
 * @function     功能	  	连接信息类
 *     此类提供连接(connect)、关闭连接(close)以及登录验证(loginCheck)等接口
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-14
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-06
 * @description 修改说明	        
 *   2013-05-06 加入连接超时处理
 *   2013-05-05 使用Packet替代原有组包方式
 */
public class Connection {
	private String username;		// dvr端登录用户名
	private String password;		// dvr端登录密码
	private String svr_ip;			// dvr端IP
	private int port;				// dvr端口
	private int channel_no;			// dvr通道号
	
	private int connect_state;		// 当前连接状态：1 连接成功； 0 连接失败
	private Socket sock = null;
	
	/**
	 * 建立连接
	 * @return 返回1，表示成功；返回0，表示失败
	 */
	public int connect() {
		int result = 1;
		
		if (username.equals("") || password.equals("") || svr_ip.equals("")
				|| port <= 0) {
			return 0;
		} 
		
		try {
			//sock = new Socket(svr_ip, port);
			SocketAddress socketAddress = new InetSocketAddress(svr_ip, port); 
			sock = new Socket();
			
			sock.connect(socketAddress, 8000);
			
		} catch (SocketTimeoutException e) {
			// 连接超时
			ViewManager.getInstance().setHelpMsg(R.string.IDS_Time_Out);
			return 0;
		} catch (UnknownHostException e) {
			result = 0;
		} catch (IOException e) {
			result = 0;
		}
		
		return result;
	}
	
	/**
	 * 关闭连接
	 * @return 返回1，表示成功；返回0，表示失败
	 */
	public int close() {
		int result = 1;
		
		if (sock == null) {
			return 0;
		}
		
		try {
			sock.close();
		} catch (IOException e) {
			result = 0;
		}
		
		return result;
	}
	
	/**
	 * 登录请求验证
	 * @return
	 */
	public int loginCheck() {
		int result = 1;
		
		if (sock == null) {
			return 0;
		}

		
		TLV_V_VersionInfoRequest versionInfoRequest = null;
		TLV_V_PhoneInfoRequest phoneInfoRequest = null;
		TLV_V_LoginRequest loginRequest = null;
		
		versionInfoRequest = new TLV_V_VersionInfoRequest();
		versionInfoRequest.setVersionMinor(TLV_Version.VERSION_MINOR);
		versionInfoRequest.setVersionMajor(TLV_Version.VERSION_MAJOR);
		
		phoneInfoRequest = new TLV_V_PhoneInfoRequest();
		
		loginRequest = new TLV_V_LoginRequest();
		loginRequest.setUserName(username.toCharArray());
		loginRequest.setPassword(password.toCharArray());
		loginRequest.setDeviceId(1);
		loginRequest.setFlag((short)1);
		loginRequest.setChannel((short)channel_no);
		loginRequest.setReserve(0);
		
		
		
		Socket socket = sock;
		OutputStream out = null;
		
		try {
			//socket = new Socket(svr_ip, port);
			out = socket.getOutputStream();
			
			Packet p = new Packet();
			p.add(versionInfoRequest);
			p.add(phoneInfoRequest);
			p.add(loginRequest);

			out.write(p.getPacketByteStream());
			
		} catch (UnknownHostException e) {
			// error process
			//e.printStackTrace();
			result = 002;
		} catch (IOException e) {
			// error process
			//e.printStackTrace();
			result = 003;
		}

		
		return result;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSvr_ip() {
		return svr_ip;
	}
	public void setSvr_ip(String svr_ip) {
		this.svr_ip = svr_ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getChannel_no() {
		return channel_no;
	}
	public void setChannel_no(int channel_no) {
		this.channel_no = channel_no;
	}
	public int getConnect_state() {
		return connect_state;
	}
	public void setConnect_state(int connect_state) {
		this.connect_state = connect_state;
	}
	public Socket getSock() {
		return sock;
	}
	public void setSock(Socket sock) {
		this.sock = sock;
	}
	
	
	
}
