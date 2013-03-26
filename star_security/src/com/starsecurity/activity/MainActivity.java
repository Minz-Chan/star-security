package com.starsecurity.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;


//import io.vov.vitamio.MediaPlayer;
//import io.vov.vitamio.widget.MediaController;
//import io.vov.vitamio.widget.VideoView;

import com.starsecurity.R;
import com.starsecurity.test.StarTest;

public class MainActivity extends Activity {

	private Button settingBtn;
	private String username;
	private String password;
	private String server;
	private String port;
	
	private String path = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
	private VideoView mVideoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.activity_main);

		settingBtn = (Button) findViewById(R.id.btn_linear_setting);
		

		
		//������ð�ťʱ������ҳ����ת���������startActivityForResult���ڲ��ͷŵ�ǰ���������¿����½���
		settingBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingsActivity.class);
				startActivityForResult(intent,0);
			}
		});
	}
	
	//���¿��������ý��������ת�����Ժ󣬴������ý���Ĳ���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode){
		case Activity.RESULT_OK:
			Bundle bundle = data.getExtras();
			username = bundle.getString("usernameStr");
			password = bundle.getString("passwordStr");
			server = bundle.getString("serverStr");
			port = bundle.getString("portStr");
			/*
			StarTest test = new StarTest();
			try {
				test.setUp();
				try {
					test.testSomething();
				} catch (Throwable e) {
					e.printStackTrace();
				}
				test.tearDown();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		
			
			try {
				VideoView videoView = (VideoView) findViewById(R.id.p2p_view);
				MediaController mediaController = new MediaController(this); 
				mediaController.setAnchorView(videoView);
				//Uri video = Uri.parse("mp4 video link");
				
				File f = new File("/mnt/sdcard/test.mp4");
				Boolean canRead = f.canRead();
				videoView.setMediaController(mediaController);
				videoView.setVideoPath("/mnt/sdcard/test.mp4");
				//videoView.setVideoURI(video);
				videoView.start();
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
			
			
			/*
			mVideoView = (VideoView) findViewById(R.id.p2p_view);
			mVideoView.setVideoPath(path);
			mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.start();*/
			
			
			
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	} 
}
