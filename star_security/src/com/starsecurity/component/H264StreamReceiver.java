package com.starsecurity.component;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.convert.ByteArray2Object;
import com.starsecurity.service.DataProcessService;
import com.starsecurity.service.impl.DataProcessServiceImpl;


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
		
		Connection conn = ConnectionManager.getConnection(conn_name);
		if(conn.connect() != 1) {
			conn.setConnect_state(0);
			ViewManager.getInstance().setIpText(conn.getSvr_ip());
			
			ViewManager.getInstance().setHelpMsg("无法连接到远程主机...");
			return;
		} else {
			conn.setConnect_state(1);
			ViewManager.getInstance().setIpText(conn.getSvr_ip());
			ViewManager.getInstance().setHelpMsg("连接远程主机成功！");
		}

	    
	    conn.loginCheck();
	    
	    InputStream socketReader = null; 
	    sock = conn.getSock();
	    
 		try {

 			socketReader = sock.getInputStream();
 			
 			if (socketReader == null) {
 				return;
 			}
			
 			int i = 0;
			
			// 获取公共包头头（ packet_length、packet_seq）
			byte[] packetHeaderBuf = new byte[8];
			socketReader.read(packetHeaderBuf);
			OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
			if (!(owspPacketHeader.getPacket_length() >= 4 && owspPacketHeader.getPacket_seq() > 0)) {
				return;
			}
			
			
			// 根据包长度读取包内容
			byte[] tlvContent = new byte[65536];
			socketReader.read(tlvContent, 0, (int) owspPacketHeader.getPacket_length() - 4);
			
			while(!tlvContent.equals("")){
				
				/* 处理接收到的数据 */
				dataProcessService.process(tlvContent, (int)owspPacketHeader.getPacket_length());
				
				/* 检测是否 */
				if (conn.getConnect_state() == 0) {
					break;
				}
				
				
				/* 该部分须检测所收的包的正确性 */
				do {
					// 数据重置
					for (i = 0; i < 8; i++) {
						packetHeaderBuf[i] = 0;
					}
					// 读取包头
					socketReader.read(packetHeaderBuf);
					owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
					
				} while(!(owspPacketHeader.getPacket_length() >= 4 && owspPacketHeader.getPacket_length() < 65536 && owspPacketHeader.getPacket_seq() > 0));
				
			
				
				/* 上一部分须保证接收下来的包的正确性，防止出现lenDataRead < 0的情况 */
				
				socketReader.read(tlvContent, 0, (int) owspPacketHeader.getPacket_length() - 4);
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}  


	}
	
	
	public int process(byte[] data, int length) {
		return 1;
		
	}

}
