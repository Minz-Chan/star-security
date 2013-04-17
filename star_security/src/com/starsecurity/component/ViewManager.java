package com.starsecurity.component;

import android.widget.TextView;

import com.starsecurity.h264.VideoView;

public class ViewManager {
	private static ViewManager m_instance;
	
	private VideoView videoView;
	private TextView ipTextView;
	private TextView helpMsgView;
	
	private ViewManager() {
		
		
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
	
	
	public void setIpText(final String ipText) {
		ipTextView.post(new Runnable() {
			@Override
			public void run() {
				ipTextView.setText(ipText);
			}
		});
	}
	
	public void setHelpMsg(final String helpMsg) {
		helpMsgView.post(new Runnable() {
			@Override
			public void run() {
				helpMsgView.setText(helpMsg);
			}
		});
	}
}
