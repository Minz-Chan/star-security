package com.starsecurity.activity;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.Assert;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
//import android.widget.VideoView;


//import io.vov.vitamio.MediaPlayer;
//import io.vov.vitamio.widget.MediaController;
//import io.vov.vitamio.widget.VideoView;

import com.starsecurity.R;
import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_DVSInfoRequest;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_StreamDataFormat;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_V_VideoData;
import com.starsecurity.model.TLV_V_VideoFrameInfo;
import com.starsecurity.model.TLV_Version;
import com.starsecurity.model.convert.ByteArray2Object;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.model.data.H264StreamingReceiver;
import com.starsecurity.service.ControlService;
import com.starsecurity.service.impl.ControlServiceImpl;
import com.starsecurity.test.StarTest;
import com.starsecurity.util.DataProcessUtil;

public class MainActivity extends Activity {

	private Button settingBtn;
	 
	
	//private String path = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
	
	
	private ControlService controlService =  new ControlServiceImpl("conn1");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.activity_main);
		
		/*
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}*?
		
		/*
		LayoutInflater lif = getLayoutInflater();
	    ViewGroup layout = (ViewGroup)lif.inflate(R.layout.activity_main, null);
		
	    mVideoView = new VideoView(this);
	    
	    LayoutParams p = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	    p.addRule(RelativeLayout.ABOVE, R.id.help_msg);
	    p.addRule(RelativeLayout.BELOW, R.id.ip_text);
	    layout.addView(mVideoView, p);
	    
	    mVideoView.setBackgroundColor(Color.BLUE);*/
	    
		
		
		VideoView mVideoView = (VideoView)findViewById(R.id.p2p_view);
		TextView ipView =  (TextView)findViewById(R.id.ip_text);
		TextView msgView = (TextView)findViewById(R.id.help_msg);
		
		mVideoView.init();
		
		ViewManager v = ViewManager.getInstance();
		v.bindVideoView(mVideoView);
		v.bindIpTextView(ipView);
		v.bindHelpMsgView(msgView);
		
		
		settingBtn = (Button) findViewById(R.id.btn_linear_setting);
		
		
		

		//H264StreamingReceiver hStreamRecv = new H264StreamingReceiver();
		//hStreamRecv.setVideoView(mVideoView);
		//new Thread(hStreamRecv).start();
		
		//点击设置按钮时，进行页面跳转，这里采用startActivityForResult，在不释放当前界面的情况下开启新界面
		settingBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingsActivity.class);
				startActivityForResult(intent,0);
			}
		});
		
		
		/*
		PipedOutputStream pipedOutstream  = new PipedOutputStream();
		PipedInputStream pipedInstream = null;
		try {
			pipedInstream = new PipedInputStream(pipedOutstream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PipedOutputStream pipedOutSteam = hStreamRecv.getOutStream();
		PipedInputStream pipedInStream = mVideoView.getInputStream();
	
		try {
			pipedInStream.connect(pipedOutSteam);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		//mVideoView.PlayVideo("/sdcard/test.264");
		

		
	}
	
	//当新开启的设置界面结束跳转回来以后，处理设置界面的参数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode){
		case Activity.RESULT_OK:
			Bundle bundle = data.getExtras();
			String username = bundle.getString("usernameStr");
			String password = bundle.getString("passwordStr");
			String server = bundle.getString("serverStr");
			String port = bundle.getString("portStr");
			
			Connection conn = ConnectionManager.getConnection("conn1");
			conn.setUsername("admin");
			conn.setPassword("123456");
			conn.setSvr_ip("113.250.4.193");
			conn.setPort(Integer.valueOf(8080));
			conn.setChannel_no(0);
			
			
			controlService.playVideo();
			
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
