/*
 * FileName:H264StreamReceiver.java
 * 
 * Package:com.starsecurity.component
 * 
 * Date:2013-04-16
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.component;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.convert.ByteArray2Object;
import com.starsecurity.service.DataProcessService;
import com.starsecurity.service.impl.DataProcessServiceImpl;

/**
 * @function     功能	  h264码流接收线程类
 * @author       创建人                陈明珍
 * @date        创建日期           2013-04-16
 * @author       修改人                陈明珍
 * @date        修改日期           2013-04-16
 * @description 修改说明	            首次增加
 */
public class H264StreamReceiver implements Runnable {
	private String conn_name;
	private Socket sock;
	private DataProcessService dataProcessService = new DataProcessServiceImpl();
	
	

	public H264StreamReceiver(String conn_name) {
		this.conn_name = conn_name;
		sock = ConnectionManager.getConnection(conn_name).getSock();
	}


	@Override
	public void run() {
		int i = 0;
		
		Connection conn = ConnectionManager.getConnection(conn_name);
		if(conn.connect() != 1) {		// 建立连接
			conn.setConnect_state(0);
			ViewManager.getInstance().setIpText(conn.getSvr_ip());
			ViewManager.getInstance().setHelpMsg("无法连接到远程主机...");
			return;
		} else {
			conn.setConnect_state(1);
			ViewManager.getInstance().setIpText(conn.getSvr_ip());
			ViewManager.getInstance().setHelpMsg("连接远程主机成功！");
		}

	    System.out.println("=================== loginCheck begin ==================");
		
	    conn.loginCheck();						// 登录验证
	    
	    System.out.println("=================== loginCheck end ==================");
	    
	    InputStream sockIn = null; 
	    sock = conn.getSock();
	    
	    
 		try {

 			//socketReader = sock.getInputStream();
 			sockIn = new SocketInputStream(sock.getInputStream());

 			System.out.println("=================== get common packet start ==================");
 			
			// 获取公共包头头（ packet_length、packet_seq）
			byte[] packetHeaderBuf = new byte[8];
			sockIn.read(packetHeaderBuf);
			OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
			if (!(owspPacketHeader.getPacket_length() >= 4 && owspPacketHeader.getPacket_seq() > 0)) {
				return;
			}
			
			System.out.println("=================== get common packet end ==================");
			
			
			System.out.println("=================== get packet start ==================");
			// 根据包长度读取包内容
			byte[] tlvContent = new byte[65536];
			sockIn.read(tlvContent, 0, (int) owspPacketHeader.getPacket_length() - 4);
			System.out.println("=================== get packet: " + ((int) owspPacketHeader.getPacket_length() - 4) + "bytes ==================");
			System.out.println("=================== get packet end ==================");
			
			while(!tlvContent.equals("")){
				
				/* 数据处理 */
				dataProcessService.process(tlvContent, (int)owspPacketHeader.getPacket_length());
				System.out.println("=================== finish data process ==================");
				
				
				
				/* 检测连接状态 */
				if (conn.getConnect_state() == 0) {
					break;
				}
				
				System.out.println("=================== get another common packet start ==================");

				/* 数据重置 */
				for (i = 0; i < 8; i++) {
					packetHeaderBuf[i] = 0;
				}
				/* 读取公共包头 */
				sockIn.read(packetHeaderBuf);
				owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
				
				System.out.println("=================== get another common packet end ==================");
				
				System.out.println("=================== get another packet  start ==================");
				
				
				/* 重置数据数组 */
				resetArray(tlvContent);
				
				/*
				 * 受限于网络带宽限制或服务端发送速度限制，此处需等待所需数据被完整接收之后方可往下执行 
				 * read函数在接收满(int) owspPacketHeader.getPacket_length() - 4 长度前将保持阻塞
				 */
				int iRead = sockIn.read(tlvContent, 0, (int) owspPacketHeader.getPacket_length() - 4);
				
				
				System.out.println("=================== length of data received: " + iRead + "   ==================");
				System.out.println("=================== length of packet: " + ((int)owspPacketHeader.getPacket_length() - 4) + "   ==================");
				
				System.out.println("=================== get another packet: " + ((int) owspPacketHeader.getPacket_length() - 4) + "bytes ==================");
				System.out.println("=================== get another packet end ==================");
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}  


	}
	
	public void resetArray(byte[] b) {
		int i = 0;
		for (i = 0; i < b.length; i++) {
			b[i] = 0;
		}
	}
	
	public int process(byte[] data, int length) {
		return 1;
		
	}

}
