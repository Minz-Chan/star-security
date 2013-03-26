/*
 * FileName:DataProcessUtil.java
 * 
 * Package:com.starsecurity
 * 
 * Date:2013-03-19
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.util;

/***
 * 
 * @function     功能	 用于存放项目中使用的一些工具方法
 * @author       创建人                 陈明珍
 * @date        创建日期           2013-03-19
 * @author       修改人                 陈明珍
 * @date        修改日期           2013-03-19
 * @description 修改说明	             首次增加
 */
public class DataProcessUtil {
	public static void fillPacket(byte[] des, byte[] src, int offset, int des_len) {
		System.arraycopy(src, 0, des, offset, des_len);
	}
}
