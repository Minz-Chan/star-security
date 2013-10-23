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



import android.os.Handler;
import android.os.Message;

import com.starsecurity.R;
import com.starsecurity.activity.MainActivity;
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
 * @date        修改日期           2013-05-03
 * @description 修改说明	 
 *   2013-10-07 修改原显示IP文本为显示设备名称（部分情况） 陈明珍
 *   2013-05-07 错误提示完善 陈明珍
 *   2013-05-03 提供信息使用res中string.xml的字符串资源 陈明珍
 *   2013-04-26 改进字节流接收方式，减少丢失数据情况 陈明珍
 */
public class H264StreamReceiver implements Runnable {
	private String conn_name;
	private Socket sock;
	private DataProcessService dataProcessService;
	

	public H264StreamReceiver(String conn_name) {
		this.conn_name = conn_name;
		sock = ConnectionManager.getConnection(conn_name).getSock();
		dataProcessService = new DataProcessServiceImpl(this.conn_name);
	}


	@Override
	public void run() {
		int i = 0;
		Connection conn = ConnectionManager.getConnection(conn_name);
		
		// 连接提示信息
		ViewManager.getInstance().setIpText(conn.getShowName());
		updateUIMessage(MessageCode.IDS_CONNECTSERVER);
		
		// 建立连接
		int result = conn.connect();
		if (result == 1) { // 连接服务器成功
			conn.setConnect_state(1);
			updateUIMessage(MessageCode.IDS_CONSERSUC);
		} else {	// 连接服务器不成功
			conn.setConnect_state(0);
			if (result == -1) { // 连接超时	
				updateUIMessage(MessageCode.IDS_TIMEOUT);
			} else {  // 连接失败
				updateUIMessage(MessageCode.IDS_CONSERFAIL);
				
			}
			
			return;
		}

	    //System.out.println("=================== loginCheck begin ==================");
		
	    conn.loginCheck();						// 登录验证
	    
	    //System.out.println("=================== loginCheck end ==================");
	    
	    InputStream sockIn = null; 
	    sock = conn.getSock();
	    
	    
	    
 		try {
 			
 			
 			// 心跳包线程
 			//HeartbeatCheck hbc = new HeartbeatCheck(sock, handler);
 		    //new Thread(hbc).start();
 		    

 		    sock.setSoTimeout(8000);		// 3秒内没有接收到任何数据时即为超时
 			//socketReader = sock.getInputStream();
 			sockIn = new SocketInputStream(sock.getInputStream());

 			//System.out.println("=================== get common packet start ==================");
 			
			// 获取公共包头头（ packet_length、packet_seq）
			byte[] packetHeaderBuf = new byte[8];
			sockIn.read(packetHeaderBuf);
			OwspPacketHeader owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
			if (!(owspPacketHeader.getPacket_length() >= 4 && owspPacketHeader.getPacket_seq() > 0)) {
				return;
			}
			
			//System.out.println("=================== get common packet end ==================");
			
			Timecounter.getInstance().setBaseTime();
			
			//System.out.println("=================== get packet start ==================");
			// 根据包长度读取包内容
			//int checkCount = 0;
			byte[] tlvContent = new byte[65536];
			sockIn.read(tlvContent, 0, (int) owspPacketHeader.getPacket_length() - 4);
			//System.out.println("=================== get packet: " + ((int) owspPacketHeader.getPacket_length() - 4) + "bytes ==================");
			//System.out.println("=================== get packet end ==================");
			
			while(!tlvContent.equals("")){
				
				/*
				if ((++checkCount) == 50) {	// 当FPS= 6~25时，约每2~8秒确认一次服务器状态
					sock.sendUrgentData(0xFF);				// 心跳包
					checkCount = 0;
				}*/
				
				/* 数据处理 */
				dataProcessService.process(tlvContent, (int)owspPacketHeader.getPacket_length());
				//System.out.println("=================== finish data process ==================");
				
				
				
				/* 检测连接状态 */
				if (conn.getConnect_state() == 0) {
					conn.getSock().close();
					conn.setSock(null);
					
					MainActivity.isPlay = false;
	
					break;
				}
				
				//System.out.println("=================== get another common packet start ==================");
				
				do {
					/* 数据重置 */
					for (i = 0; i < 8; i++) {
						packetHeaderBuf[i] = 0;
					}
					/* 读取公共包头 */
					sockIn.read(packetHeaderBuf, 0, 8);
					owspPacketHeader = (OwspPacketHeader) ByteArray2Object.convert2Object(OwspPacketHeader.class, packetHeaderBuf,0,OWSP_LEN.OwspPacketHeader);
				} while (owspPacketHeader.getPacket_length() <= 0);
				
				//System.out.println("=================== get another common packet end ==================");
				
				//System.out.println("=================== get another packet  start ==================");
				
				
				/* 重置数据数组 */
				//resetArray(tlvContent);
				
				/*
				 * 受限于网络带宽限制或服务端发送速度限制，此处需等待所需数据被完整接收之后方可往下执行 
				 * read函数在接收满(int) owspPacketHeader.getPacket_length() - 4 长度前将保持阻塞
				 */
				int iRead = sockIn.read(tlvContent, 0, (int) owspPacketHeader.getPacket_length() - 4);
				
				
				//System.out.println("=================== length of data received: " + iRead + "   ==================");
				//System.out.println("=================== length of packet: " + ((int)owspPacketHeader.getPacket_length() - 4) + "   ==================");
				
				//System.out.println("=================== get another packet: " + ((int) owspPacketHeader.getPacket_length() - 4) + "bytes ==================");
				//System.out.println("=================== get another packet end ==================");
				
			}
			
			
		} catch (IOException e) {
			if (!conn.getSock().isClosed()) {
				try {
					conn.getSock().close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			updateUIMessage(MessageCode.CONNECTION_CLOSED);
			e.printStackTrace();
		} catch(ArrayIndexOutOfBoundsException e) {
			updateUIMessage(MessageCode.CONNECTION_CLOSED);
			e.printStackTrace();
		} 
 		catch(Exception e) {
 			updateUIMessage(MessageCode.ERR_UNKNOWN);
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
	
	/**
	 * 更新UI消息传递
	 * @param msgCode 消息代码
	 */
	private void updateUIMessage(int msgCode) {
		Message msg = new Message();
		msg.what = msgCode;
		
		Handler handler = ViewManager.getInstance().getHandler();
		if (handler != null) {
			handler.sendMessage(msg);
		} else {
			ViewManager.getInstance().setHelpMsg(R.string.IDS_Unknown);
		}
	}

}
