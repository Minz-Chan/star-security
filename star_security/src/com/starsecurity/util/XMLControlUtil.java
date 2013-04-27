package com.starsecurity.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.starsecurity.model.DVRDevice;
import com.starsecurity.model.FavouriteRecord;

/**
 * @function     功能	  进行MD5加密
 * @author       创建人                肖远东
 * @date        创建日期           2013-04-26
 * @author       修改人                 肖远东
 * @date        修改日期           2013-04-26
 * @description 修改说明	             首次增加
 */
public class XMLControlUtil {
	
	private static final boolean SUCCESS = true;
	private static final boolean FAILED = false;
	
	/***
	 * 为XML文件创建根节点
	 */
	public static boolean createFileAndRoot(String fileName,String rootName){
		Document document = DocumentHelper.createDocument();
		//创建根结点
		document.addElement(rootName);
		try 
		{ 
			/* 将document中的内容写入文件中 */ 
			OutputFormat format = OutputFormat.createPrettyPrint(); 
			format.setEncoding("UTF-8"); 
			XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)),format); 
			writer.write(document); 
			writer.close();
			return SUCCESS;
		}catch(Exception ex) 
		{ 
			ex.printStackTrace();
			return FAILED;
		}
		
	}
	
	/***
	 * 根据收藏名获取收藏记录
	 * @param fileName			XML文件名
	 * @param favouriteName		记录名
	 * @return					找到的收藏记录
	 */
	public static FavouriteRecord getFavouriteRecordByName(String fileName,String favouriteName){
		SAXReader saxReader = new SAXReader();
		FavouriteRecord favouriteRecord = new FavouriteRecord();
		try {
			Document document = saxReader.read(new File(fileName));
			Element rootElement=document.getRootElement();
			List favouriteList = rootElement.selectNodes("//Favourites/Favourite");
			
			for (int index = 0;index<favouriteList.size(); index++) {
				Element favouriteTemp = (Element) favouriteList.get(index);
				if(favouriteTemp.attributeValue("FavouriteName").toString().equals(favouriteName)){
					favouriteRecord.setFavouriteName(favouriteTemp.attributeValue("FavouriteName").toString());		//收藏名
					favouriteRecord.setUserName(favouriteTemp.attributeValue("UserName").toString());		//用户名
					favouriteRecord.setPassword(favouriteTemp.attributeValue("Password").toString());		//密码
					favouriteRecord.setIPAddress(favouriteTemp.attributeValue("IPAddress").toString());		//IP
					favouriteRecord.setPort(favouriteTemp.attributeValue("Port").toString());		//端口
					favouriteRecord.setDefaultChannel(favouriteTemp.attributeValue("DefaultChannel").toString());		//默认通道
					favouriteRecord.setRecordName(favouriteTemp.attributeValue("RecordName").toString());		//记录名
					
					return favouriteRecord;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 	
	}
	
	/***
	 * 查看收藏记录是否存在
	 * @param fileName			XML文件名
	 * @param favouriteName		记录名
	 * @return					是否存在
	 */
	public static boolean isExist(String fileName,String favouriteName){
		SAXReader saxReader = new SAXReader(); 
		try {
			Document document = saxReader.read(new File(fileName));
			Element rootElement=document.getRootElement();
			List favouriteList = rootElement.selectNodes("//Favourites/Favourite");	
			for (int index = 0;index<favouriteList.size(); index++) {
				Element favouriteTemp = (Element) favouriteList.get(index);
				if(favouriteTemp.attributeValue("FavouriteName").toString().equals(favouriteName)){
					return SUCCESS;
				}
			}
			return FAILED;
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		} 	
	}
	
	/***
	 * 获取收藏记录名列表
	 * @param fileName		XML文件名
	 * @return 				记录名列表
	 */
	public static List<String> getFavouriteList(String fileName){
		List<String> favouriteListTemp = new ArrayList();
		SAXReader saxReader = new SAXReader(); 
		try {
			Document document = saxReader.read(new File(fileName));
			Element rootElement=document.getRootElement();
			List favouriteList = rootElement.selectNodes("//Favourites/Favourite");
			
			for (int index = 0;index<favouriteList.size(); index++) {
				Element favouriteTemp = (Element) favouriteList.get(index);
				favouriteListTemp.add(favouriteTemp.attributeValue("FavouriteName").toString());
			}
			return favouriteListTemp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * 修改收藏记录
	 * @param favouriteRecord	新记录
	 * @return					成功与否
	 */
	public static boolean coverFavouriteElement(String fileName,FavouriteRecord favouriteRecord){
		SAXReader saxReader = new SAXReader(); 
		try {
			Document document = saxReader.read(new File(fileName));
			Element rootElement=document.getRootElement();
			List favouriteList = rootElement.selectNodes("//Favourites/Favourite");
			
			for (int index = 0;index<favouriteList.size(); index++) {
				Element favouriteTemp = (Element) favouriteList.get(index);
				if(favouriteTemp.attributeValue("FavouriteName").toString().equals(favouriteRecord.getFavouriteName())){
					favouriteTemp.attribute("FavouriteName").setValue(favouriteRecord.getFavouriteName());		//收藏名
					favouriteTemp.attribute("UserName").setValue(favouriteRecord.getUserName());		//用户名
					favouriteTemp.attribute("Password").setValue(favouriteRecord.getPassword());		//密码
					favouriteTemp.attribute("IPAddress").setValue(favouriteRecord.getIPAddress());		//IP
					favouriteTemp.attribute("Port").setValue(favouriteRecord.getPort());		//端口
					favouriteTemp.attribute("DefaultChannel").setValue(favouriteRecord.getDefaultChannel());		//默认通道
					favouriteTemp.attribute("RecordName").setValue(favouriteRecord.getRecordName());		//记录名
					
					OutputFormat opf=new OutputFormat("",true,"UTF-8");
					opf.setTrimText(true);
					XMLWriter writer=new XMLWriter(new FileOutputStream(fileName),opf);
					writer.write(document);
					writer.close();
					return SUCCESS;
				}
			}
			return FAILED;
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		} 	
	}
	
	/***
	 * 删除收藏夹记录
	 * @param fileName			XML文件名
	 * @param favouriteName		记录名
	 * @return					成功与否
	 */
	public static boolean removeFavouriteElement(String fileName,String favouriteName){
		SAXReader saxReader = new SAXReader(); 
		try {
			Document document = saxReader.read(new File(fileName));
			Element rootElement=document.getRootElement();
			List favouriteList = rootElement.selectNodes("//Favourites/Favourite");
			
			for (int index = 0;index<favouriteList.size(); index++) {
				Element favouriteTemp = (Element) favouriteList.get(index);
				if(favouriteTemp.attributeValue("FavouriteName").toString().equals(favouriteName)){
					favouriteTemp.detach();
					OutputFormat opf=new OutputFormat("",true,"UTF-8");
					opf.setTrimText(true);
					XMLWriter writer=new XMLWriter(new FileOutputStream(fileName),opf);
					writer.write(document);
					writer.close();
					return SUCCESS;
				}
			}
			return FAILED;
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		} 
	}
	
	/***
	 * 手动添加收藏夹操作
	 * @param favouriteRecord	需要添加的记录
	 * @return					成功与否
	 */
	public static boolean addFavouriteElement(String fileName,FavouriteRecord favouriteRecord){
		SAXReader saxReader = new SAXReader(); 
		try {
			Document document = saxReader.read(new File(fileName));
			Element rootElement=document.getRootElement();
			Element favourite = rootElement.addElement("Favourite");
			favourite.addAttribute("FavouriteName", favouriteRecord.getFavouriteName());		//收藏名
			favourite.addAttribute("UserName", favouriteRecord.getUserName());		//用户名
			favourite.addAttribute("Password", favouriteRecord.getPassword());		//密码
			favourite.addAttribute("IPAddress", favouriteRecord.getIPAddress());		//IP
			favourite.addAttribute("Port", favouriteRecord.getPort());		//端口
			favourite.addAttribute("DefaultChannel", favouriteRecord.getDefaultChannel());		//默认通道
			favourite.addAttribute("RecordName", favouriteRecord.getFavouriteName());		//记录名
			
			OutputFormat opf=new OutputFormat("",true,"UTF-8");
			opf.setTrimText(true);
			XMLWriter writer=new XMLWriter(new FileOutputStream(fileName),opf);
			writer.write(document);
			writer.close();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		} 	
	}
	
	/***
	 * 默认添加收藏操作（云台选择某一平台后，自动添加进收藏夹）
	 * @param dvrDevice		需要添加的对象
	 * @param fileName		XML文件名
	 * @return				成功与否
	 */
	public static boolean addDefaultFavouriteElement(DVRDevice dvrDevice,String fileName){
		SAXReader saxReader = new SAXReader(); 
		try {
			Document document = saxReader.read(new File(fileName));
			Element rootElement=document.getRootElement();
			Element favourite = rootElement.addElement("Favourite");
			favourite.addAttribute("FavouriteName", dvrDevice.getDeviceName().toString());		//收藏名
			favourite.addAttribute("UserName", dvrDevice.getLoginUsername().toString());		//用户名
			favourite.addAttribute("Password", dvrDevice.getLoginPassword().toString());		//密码
			favourite.addAttribute("IPAddress", dvrDevice.getLoginIP().toString());		//IP
			favourite.addAttribute("Port", dvrDevice.getMobliePhonePort().toString());		//端口
			favourite.addAttribute("DefaultChannel", dvrDevice.getStarChannel());		//默认通道
			favourite.addAttribute("RecordName", dvrDevice.getDeviceName().toString());		//记录名
			
			OutputFormat opf=new OutputFormat("",true,"UTF-8");
			opf.setTrimText(true);
			XMLWriter writer=new XMLWriter(new FileOutputStream(fileName),opf);
			writer.write(document);
			writer.close();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAILED;
		} 	
	}
}
