/*
 * FileName:SocketInputStream.java
 * 
 * Package:com.starsecurity.component
 * 
 * Date:2013-04-25
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.component;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @function     功能	  Socket网络输入流
 *     此类覆写了过滤流的read(byte[] b, int off, int len)方法，在网络流读取的过程
 * 中将一直等待接收满len个字节。除非流的末尾被侦测到，则返回已读取的字数数。
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-25
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-23
 * @description 修改说明	          
 *   2013-05-53 数据接收部分优化
 */
public class SocketInputStream extends FilterInputStream {

	protected SocketInputStream(InputStream in) {
		super(in);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int iRead = 0;
		int iShouldBeRead = len;
		
		while (iRead < iShouldBeRead) {   
			iRead += super.read(b, iRead, iShouldBeRead - iRead);   
		} 
		
		return iRead;
	}

}
