package com.starsecurity.component;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
	private static Map<String, Connection> m_instances = new HashMap<String, Connection>();
	
	public static Connection getConnection(String conn_name) {
		if (!m_instances.containsKey(conn_name)) {
			m_instances.put(conn_name, new Connection());
		}
		
		return m_instances.get(conn_name);
	}
	
}
