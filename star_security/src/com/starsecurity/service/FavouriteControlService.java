package com.starsecurity.service;

import java.util.List;

import com.starsecurity.model.DVRDevice;
import com.starsecurity.model.FavouriteRecord;

/**
 * @function     功能	  进行收藏夹记录的操作接口
 * @author       创建人                肖远东
 * @date        创建日期           2013-04-26
 * @author       修改人                 肖远东
 * @date        修改日期           2013-04-26
 * @description 修改说明	             首次增加
 */
public interface FavouriteControlService {
	/***
	 * 创建XML文件根节点
	 */
	public boolean createFileAndRoot(String fileName,String rootName);
	
	/***
	 * 根据收藏名获取收藏记录
	 * @param fileName			XML文件名
	 * @param favouriteName		记录名
	 * @return					找到的收藏记录
	 */
	public FavouriteRecord getFavouriteRecordByName(String fileName,String favouriteName);
	
	/***
	 * 查看收藏记录是否存在
	 * @param fileName			XML文件名
	 * @param favouriteName		记录名
	 * @return					是否存在
	 */
	public boolean isExist(String fileName,String favouriteName);
	
	/***
	 * 获取收藏记录名列表
	 * @param fileName		XML文件名
	 * @return 				记录名列表
	 */
	public List<String> getFavouriteList(String fileName);
	
	/***
	 * 修改收藏记录
	 * @param favouriteRecord	新记录
	 * @return					成功与否
	 */
	public boolean coverFavouriteElement(String fileName,FavouriteRecord favouriteRecord);
	
	/***
	 * 删除收藏夹记录
	 * @param fileName			XML文件名
	 * @param favouriteName		记录名
	 * @return					成功与否
	 */
	public boolean removeFavouriteElement(String fileName,String favouriteName);
	
	/***
	 * 手动添加收藏夹操作
	 * @param favouriteRecord	需要添加的记录
	 * @return					成功与否
	 */
	public boolean addFavouriteElement(String fileName,FavouriteRecord favouriteRecord);
	
	/***
	 * 默认添加收藏操作（云台选择某一平台后，自动添加进收藏夹）
	 * @param dvrDevice		需要添加的对象
	 * @param fileName		XML文件名
	 * @return				成功与否
	 */
	public boolean addDefaultFavouriteElement(DVRDevice dvrDevice,String fileName);
	
	/***
	 * 设置最近选取记录
	 * @param recordName	最近选取的记录名
	 * @param fileName		XML文件名
	 * @return
	 */
	public boolean setLastRecord(String fileName,String recordName);
	
	/***
	 * 获取最近选取记录
	 * @param fileName		XML文件名
	 * @return
	 */
	public String getLastRecordName(String fileName);
	
	/***
	 * 设置最近选取通道
	 * @param recordName	最近选取的记录名
	 * @param fileName		XML文件名
	 * @return
	 */
	public boolean setLastChannel(String fileName,String channel);
	
	/***
	 * 获取最近选取通道
	 * @param fileName		XML文件名
	 * @return
	 */
	public String getLastChannel(String fileName);
	
	/***
	 * @param fileName		XML文件名
	 * @param serverIP		云台服务器IP
	 * @return
	 */
	public boolean setServerIP(String fileName,String serverIP);
	
	/***
	 * @param fileName		XML文件名
	 * @return
	 */
	public String getServerIP(String fileName);
	
	/***
	 * @param fileName		XML文件名
	 * @param serverPort	云台服务器端口
	 * @return
	 */
	public boolean setServerPort(String fileName,String serverPort);
	
	/***
	 * @param fileName		XML文件名
	 * @return
	 */
	public String getServerPort(String fileName);
	
	/***
	 * @param fileName		XML文件名
	 * @param userName		云台用户名
	 * @return
	 */
	public boolean setUserName(String fileName,String userName);
	
	/***
	 * @param fileName		XML文件名
	 * @return
	 */
	public String getUserName(String fileName);
	
	/***
	 * @param fileName		XML文件名
	 * @param password		云台用户名密码
	 * @return
	 */
	public boolean setPassword(String fileName,String password);
	
	/***
	 * @param fileName		XML文件名
	 * @return
	 */
	public String getPassword(String fileName);
}
