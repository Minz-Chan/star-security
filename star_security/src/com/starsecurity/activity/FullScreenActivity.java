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
		
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		try {
//			videoView = ViewManager.getInstance().getVideoView().clone();
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		} 
//		
		
		VideoView videoView = (VideoView) findViewById(R.id.sufFull);
		ViewManager.getInstance().setFullVideoView(videoView);
		
		
		extendedService.switchToFullScreen();
		
		/*
		videoView = new VideoView(this);
		videoView.init();
		
		byte[] desPixel = videoView.getmPixel();
		byte[] srcPixel = ViewManager.getInstance().getVideoView().getmPixel();
		System.arraycopy(srcPixel, 0, desPixel, 0, srcPixel.length);
		
		videoView.postInvalidate();
		
		ScrollView sv   = new ScrollView(this);    
		LinearLayout.LayoutParams LP_FF = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);  
		sv.setLayoutParams( LP_FF ); 
		
		LinearLayout layout = new LinearLayout(this);   //线性布局方式     
		layout.setOrientation( LinearLayout.VERTICAL ); //控件对其方式为垂直排列     
		layout.setBackgroundColor( 0xff00ffff );        //设置布局板的一个特殊颜色，这可以检验我们会话时候是否有地方颜色不正确！     
		
		layout.addView(videoView);*/
		
		
		videoView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				onBackPressed();
				return true;
			}
		});
		
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (extendedService != null) {
			extendedService.restoreFromFullScreen();
		}
		
		super.onBackPressed();
		
	}
	
	
	
	
}
