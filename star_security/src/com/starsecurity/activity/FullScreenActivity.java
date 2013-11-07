/*
 * FileName:FullScreenActivity.java
 * 
 * Package:com.starsecurity.activity
 * 
 * Date:2013-05-09
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.activity;

import com.starsecurity.R;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.service.ExtendedService;
import com.starsecurity.service.impl.ExtendedServiceImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;



/**
 * @function     功能	      全屏视频播放
 * @author       创建人              陈明珍
 * @date        创建日期           2013-05-09
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-10
 * @description 修改说明	          
 *   2013-05-10 实现视频全屏功能 陈明珍
 */
public class FullScreenActivity extends Activity {
	ExtendedService extendedService;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suffull);
		
		
		String conn_name = getIntent().getStringExtra("conn_name");
		extendedService = new ExtendedServiceImpl(conn_name);
		
		VideoView videoView = (VideoView) findViewById(R.id.sufFull);
		ViewManager.getInstance().setFullVideoView(videoView);
		
		extendedService.switchToFullScreen();
		
		
		
		videoView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				onBackPressed();
				return true;
			}
		});
		
		
	}

	@Override
	public void onBackPressed() {
		if (extendedService != null) {
			extendedService.restoreFromFullScreen();
		}
		
		super.onBackPressed();
		
	}
	
	
	
	
}
