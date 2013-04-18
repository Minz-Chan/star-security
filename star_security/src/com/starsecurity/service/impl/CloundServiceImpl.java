/*
 * FileName:CloundServiceImpl.java
 * 
 * Package:com.starsecurity.service.impl
 * 
 * Date:2013-04-18
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.service.impl;

import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.DDNS;
import com.starsecurity.service.CloudService;


/**
 * @function     功能	  云台控制接口实现类
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-18
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-18
 * @description 修改说明	          首次增加
 */
public class CloundServiceImpl implements CloudService {
	private String conn_name;
	
	public CloundServiceImpl(String conn_name) {
		super();
		this.conn_name = conn_name;
	}

	@Override
	public void saveDDNSConfig2File(DDNS ddns) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestDevicesInfo(DDNS ddns) {
		if (ConnectionManager.getConnection(conn_name).getConnect_state() == 1) {
			// 发送TLV_V_ChannelRequest数据包
		}

	}


}
