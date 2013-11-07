package com.starsecurity.component;

import java.net.Socket;

import android.os.Handler;
import android.os.Message;



/**
 * @function     功能    心跳包检测服务端存活状态
 * @author       创建人                陈明珍
 * @date        创建日期           2013-08-02
 * @author       修改人                陈明珍
 * @date        修改日期           2013-05-06
 * @description 修改说明	        
 */
public class HeartbeatCheck implements Runnable {
	private Socket sock;
	private Handler handler;
	
	
	
	public HeartbeatCheck(Socket sock, Handler handler) {
		super();
		this.sock = sock;
		this.handler = handler;
	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(2000);		// 每2秒检测一次服务端状态
				
				if (sock != null) {
					sock.sendUrgentData(0xFF);
				}
				
			} catch (Exception e) {
				Message msg = new Message();
				msg.what = MessageCode.THREAD_STOP;
				handler.sendMessage(msg);
			
				e.printStackTrace();
				break;
			}
	
		}
		
	}
}
