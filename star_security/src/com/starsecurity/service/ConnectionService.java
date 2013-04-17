/**
 * 
 */
package com.starsecurity.service;

/**
 * @author Milvxe
 *
 */
public interface ConnectionService {
	
	int connect(String username, String password, String svr_ip, int port, int channel_num);
	
	int close();
}
