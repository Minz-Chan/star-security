/*
 * FileName:StreamSender.java
 * 
 * Package:com.starsecurity.component
 * 
 * Date:2013-05-06
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.util.DataProcessUtil;

/**
 * @function     功能	  	数据包发送类
 *     提供独立线程进行数据包发送，数据发送完毕线程自动结束。
 * @author       创建人              陈明珍
 * @date        创建日期           2013-05-06
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-06
 * @description 修改说明	        
 *   2013-05-06 首次增加  陈明珍
 */
public class StreamSender implements Runnable {

	private String conn_name;
	private Object data;

	
	public StreamSender(String conn_name, Object data) {
		super();
		this.conn_name = conn_name;
		this.data = data;
	}


	@Override
	public void run() {
		Socket sock = ConnectionManager.getConnection(conn_name).getSock();
		OutputStream out = null;
		try {
			out = sock.getOutputStream();

			out.write((byte[]) data);
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
