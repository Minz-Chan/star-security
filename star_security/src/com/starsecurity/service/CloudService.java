/*
 * FileName:CloudService.java
 * 
 * Package:com.starsecurity.service
 * 
 * Date:2013-04-18
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.service;

import com.starsecurity.component.DDNS;

/**
 * @function     功能	  	云台控制接口
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-18
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-18
 * @description 修改说明	          首次增加
 */
public interface CloudService {
	/**
	 * 保存DDNS配置到本地文件
	 * @param ddns 域名服务器信息对象
	 */
	public void saveDDNSConfig2File(DDNS ddns);
	
	/**
	 * 请求DVR设备列表
	 * @param ddns 域名服务器信息对象
	 */
	public void requestDevicesInfo(DDNS ddns);
	
	
}
