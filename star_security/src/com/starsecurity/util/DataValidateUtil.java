package com.starsecurity.util;

/**
 * @function     功能	                数据校验
 * @author       创建人                 陈明珍
 * @date        创建日期           2013-11-20
 * @author       修改人                 肖远东
 * @date        修改日期           2013-11-21
 * @description 修改说明	          增加修改函数
 */
public class DataValidateUtil {

	/**
	 * 验证用户名是否有效（不超过32个字符且不为空）
	 * @param s
	 * @return
	 *    true, 有效
	 *    false, 无效
	 */
	public static boolean isValidUsername(String s) {
		if (s.length() > 32) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 验证密码是否有效（不超过16个字符）
	 * @param s
	 * @return
	 *    true, 有效
	 *    false, 无效
	 */
	public static boolean isValidPassword(String s) {
		if (s.length() > 16) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 验证服务器地址是否有效（不为空）
	 * @param s
	 * @return
	 *    true, 有效
	 *    false, 无效
	 */
	public static boolean isValidServer(String s) {
		if (s.trim().length() == 0) {
			return false;
		}
		
		return true;
	}
	
	/***
	 * 验证域名服务器地址是否有效（不为空）
	 * @param s
	 * @return
	 *    true, 有效
	 *    false, 无效
	 */
	public static boolean isValidDomainServer(String s){
		if (s.trim().length() == 0) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 验证输入是否为有效的端口号
	 * @param s 输入字符串
	 * @return 
	 *   true, 有效
	 *   false, 无效
	 */
	public static boolean isValidPort(String s) {
		int port = 0;
		
		try {
			port = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (port < 100 || port > 65535) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 验证输入是否为有效的域名服务器端口号
	 * @param s 输入字符串
	 * @return 
	 *   true, 有效
	 *   false, 无效
	 */
	public static boolean isValidDomianPort(String s) {
		int port = 0;
		
		try {
			port = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (port < 0 || port > 65535) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 验证输入是否为有效的通道号
	 * @param s 输入字符串
	 * @return 
	 *   true, 有效
	 *   false, 无效
	 */
	public static boolean isValidChannelNo(String s) {
		int channelNo = 0;
		
		try {
			channelNo = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (channelNo <= 0 || channelNo > 32) {
			return false;
		}
		
		return true;
	}
}
