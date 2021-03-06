/*
 * FileName:ExtendedServiceImpl.java
 * 
 * Package:com.starsecurity.service
 * 
 * Date:2013-04-17
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.starsecurity.R;
import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.service.ExtendedService;

/**
 * @function     功能	  扩展功能接口实现类
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-17
 * @author       修改人              陈明珍
 * @date        修改日期           2013-07-30
 * @description 修改说明	       
 *   2013-07-30 修复SD卡不存在时，依然提示保存成功错误
 *   2013-05-10 全屏及复原功能实现 陈明珍
 */
public class ExtendedServiceImpl implements ExtendedService {
	private String conn_name;
	
	
	
	public ExtendedServiceImpl(String conn_name) {
		super();
		this.conn_name = conn_name;
	}

	@Override
	public File takePicture() {
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        mExternalStorageWriteable = true;
	    } 
	    
	    
	    if (mExternalStorageWriteable) { // 存储空间可写
	    	VideoView v = ViewManager.getInstance().getVideoView();
			Bitmap bmp = null;
			
			v.setDrawingCacheEnabled(true);
			bmp = Bitmap.createBitmap(v.getDrawingCache());
			
			String path = Environment.getExternalStorageDirectory() + File.separator;
			File dir = new File(path + "ImageSave");
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					// 创建目录/SDCard/ImageSave/失败
					ViewManager.getInstance().setHelpMsg(R.string.IDS_CreateFolderFailure);
					return null;
				}
			}

			FileOutputStream out = null;
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String filename = sDateFormat.format(new java.util.Date()) + ".jpg";
			filename = filename.replace(" ", "");
			filename = filename.replace("-", "");
			filename = filename.replace(":", "");
			
			File img = new File(dir.getPath() + File.separator +  filename);
			
			try {
		       out = new FileOutputStream(img);

		       bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
		       out.close();
		       
		       ViewManager.getInstance().setHelpMsg(R.string.IDS_Saveimg);
			} catch (Exception e) {
				e.printStackTrace();
				ViewManager.getInstance().setHelpMsg(R.string.IDS_Unknown);
				return null;
			}
			
			v.setDrawingCacheEnabled(false);
			return img;
	    } else { // 存储空间不可写
	    	ViewManager.getInstance().setHelpMsg(R.string.IDS_Error_Sdcard);
	    	return null;
	    }
	}

	@Override
	public void switchToFullScreen() {
		VideoView v = ViewManager.getInstance().getFullVideoView();
		v.init();
		v.setFullScreenMode(true);
		
		ViewManager.getInstance().changeVideoView(v);

	}

	@Override
	public void restoreFromFullScreen() {
		VideoView v = ViewManager.getInstance().getMainVideoView();
		v.init();
		v.setFullScreenMode(false);
		
		ViewManager.getInstance().changeVideoView(v);
	}

	@Override
	public void getNextChannelList() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveConnectionConfig2File(String recordName, String username,
			String password, String svr_ip, int port, int channel_no) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrentConnection(String username, String password,
			String svr_ip, int port, int channel_no) {
		Connection conn = ConnectionManager.getConnection(conn_name);
		conn.setUsername(username);
		conn.setPassword(password);
		conn.setSvr_ip(svr_ip);
		conn.setPort(port);
		conn.setChannel_no(channel_no);

	}

}
