/*
 * FileName:ExtendedService.java
 * 
 * Package:com.starsecurity.service
 * 
 * Date:2013-04-17
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.service;

import android.graphics.Bitmap;

/**
 * @function     功能	  	扩展功能接口
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-17
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-17
 * @description 修改说明	          首次增加
 */
public interface ExtendedService {
	
	/**
	 * 拍照功能
	 * @return
	 */
	public Bitmap takePicture();
	
	/**
	 * 进行全屏模式
	 */
	public void switchToFullScreen();
	
	/**
	 * 从全屏回复
	 */
	public void restoreFromFullScreen();
	
	/**
	 * 取得下一页的频道列表
	 */
	public void getNextChannelList();
	
	/**
	 * 保存当前配置到文件中
	 * @param recordName 记录名
	 * @param username 登录用户名
	 * @param password 登录密码
	 * @param svr_ip dvr端IP
	 * @param port dvr端口
	 * @param channel_no 频道号
	 */
	public void saveConnectionConfig2File(String recordName, String username, String password, 
			String svr_ip, int port, int channel_no);
	
	/**
	 * 设置当前连接信息
	 * @param username 登录用户名
	 * @param password 登录密码
	 * @param svr_ip dvr端设备IP
	 * @param port dvr端口
	 * @param channel_no 频道号
	 */
	public void setCurrentConnection(String username, String password, String svr_ip, 
			int port, int channel_no);
}
