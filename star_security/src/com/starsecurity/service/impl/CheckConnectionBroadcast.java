/*
 * FileName:CheckConnectionBroadcast.java
 * 
 * Package:com.starsecurity.service.impl
 * 
 * Date:2013-06-08
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.service.impl;

import java.net.Socket;

import com.starsecurity.R;
import com.starsecurity.activity.MainActivity;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.ViewManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * @function     功能	      断线自动重连
 * @author       创建人              陈明珍
 * @date        创建日期           2013-06-08
 * @author       修改人              陈明珍
 * @date        修改日期           2013-06-08
 * @description 修改说明	          
 *   2013-06-08 断线自动重连 陈明珍 
 */
public class CheckConnectionBroadcast extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		MainActivity main = MainActivity.mainActivity;

	    /* 检查当前连接状态 */
		if (main.getIsPlay()) {
			if (!main.isOpenNetwork()) {
				//main.stopVideo();
		    	ViewManager.getInstance().setHelpMsg(R.string.IDS_InternetOff);
		    	return;
			}
			
			Socket sock = ConnectionManager.getConnection("conn1").getSock();
			boolean isConnected;
				
			if (sock != null) {
				isConnected = sock.isConnected() && !sock.isClosed();
				if (!isConnected) {	// 若连接已断开(网络异常或对方关闭连接)
					main.stopVideo();
					
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					main.playVideo();
				}
			} 
		}
		
	}

}
