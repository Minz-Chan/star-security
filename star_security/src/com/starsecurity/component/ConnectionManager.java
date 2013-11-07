/*
 * FileName:ConnectionManager.java
 * 
 * Package:com.starsecurity.component
 * 
 * Date:2013-04-14
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.component;

import java.util.HashMap;
import java.util.Map;

/**
 * @function     功能	  	连接管理类
 *     此类设计的目的在于使得连接易于管理及维护，可在多种情况下访问已存在
 * 的连接，并进行简单维护。其设计采用类单件模式的思想。
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-14
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-14
 * @description 修改说明	          首次增加
 */
public class ConnectionManager {
	private static Map<String, Connection> m_instances = new HashMap<String, Connection>();
	
	private ConnectionManager() {
		
	}
	
	public static Connection getConnection(String conn_name) {
		if (!m_instances.containsKey(conn_name)) {
			m_instances.put(conn_name, new Connection());
		}
		
		return m_instances.get(conn_name);
	}
	
}
