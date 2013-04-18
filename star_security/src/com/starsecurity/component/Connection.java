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
import java.net.Socket;
import java.net.UnknownHostException;

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
 * @date        修改日期           2013-04-14
 * @description 修改说明	          首次增加
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
			sock = new Socket(svr_ip, port);
			
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
		
		/*
		Connection conn = ConnectionManager.getConnection(this.conn_name);
		
		String username = conn.getUsername();
		String password = conn.getPassword();
		//String svr_ip = conn.getSvr_ip();
		//int port = conn.getPort();
		int channel_no = conn.getChannel_no();*/
		
		
		
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
