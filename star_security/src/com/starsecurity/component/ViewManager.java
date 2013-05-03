/*
 * FileName:ViewManager.java
 * 
 * Package:com.starsecurity.component
 * 
 * Date:2013-04-15
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.component;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.TextView;

import com.starsecurity.h264.VideoView;

/**
 * @function     功能	  界面显示View管理类
 *     该类对软件界面中的相关View控件提供统一管理的接口，使View控件可在子线程中被访问。
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-15
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-03
 * @description 修改说明	          
     2013-05-03 加入提示信息对字符串资源ID的支持
 */
public class ViewManager {
	private static ViewManager m_instance;
	
	private VideoView videoView;				// 视频显示View
	private TextView ipTextView;				// IP信息显示View
	private TextView helpMsgView;				// 提示信息显示View
	
	private static ContextWrapper context;
	
	private ViewManager() {

	}
	
	public static void initContext(Context _context) {
		context = new ContextWrapper(_context); 
	}
	
	public static ViewManager getInstance() {
		if (m_instance == null) {
			m_instance = new ViewManager();
		}
		
		return m_instance;
	}


	public VideoView getVideoView() {
		return videoView;
	}


	public void bindVideoView(VideoView videoView) {
		this.videoView = videoView;
	}



	public void bindIpTextView(TextView ipText) {
		this.ipTextView = ipText;
	}



	public void bindHelpMsgView(TextView helpMsg) {
		this.helpMsgView = helpMsg;
	}
	
	
	/**
	 * 更新IP信息
	 * @param ipText IP信息文本
	 */
	public void setIpText(final String ipText) {
		ipTextView.post(new Runnable() {
			@Override
			public void run() {
				ipTextView.setText(ipText);
			}
		});
	}
	
	/**
	 * 更新提示信息
	 * @param helpMsg 提示信息文本
	 */
	public void setHelpMsg(final String helpMsg) {
		helpMsgView.post(new Runnable() {
			@Override
			public void run() {
				helpMsgView.setText(helpMsg);
			}
		});
	}
	
	/**
	 * 更新提示信息
	 * @param helpMsgId 提示信息字符串资源ID
	 */
	public void setHelpMsg(final int helpMsgId) {
		helpMsgView.post(new Runnable() {
			@Override
			public void run() {
				helpMsgView.setText(context.getString(helpMsgId));
			}
		});
	}
}
