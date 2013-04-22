package com.starsecurity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.starsecurity.R;
import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.service.ControlService;
import com.starsecurity.service.impl.ControlServiceImpl;

public class MainActivity extends Activity {
	//最底端按钮组
	/***
	 * 播放按钮
	 */
	private Button playBtn;
	/***
	 * 截屏按钮
	 */
	private Button captureBtn;
	/***
	 * 全屏按钮
	 */
	private Button fullScreenBtn;
	/***
	 * 通道换组按钮
	 */
	private Button groupBtn;
	/***
	 * 云台设置按钮
	 */
	private Button ddnsBtn;
	/***
	 * 设置按钮
	 */
	private Button settingBtn;

	//控制按钮组
	/***
	 * 控制向上按钮
	 */
	private Button controlUpBtn;
	/***
	 * 控制向下按钮
	 */
	private Button controlDownBtn;
	/***
	 * 控制向左按钮
	 */
	private Button controlLeftBtn;
	/***
	 * 控制向右按钮
	 */
	private Button controlRightBtn;
	/***
	 * 放大按钮
	 */
	private Button zoomAddBtn;
	/***
	 * 缩小按钮
	 */
	private Button zoomBtn;
	/***
	 * 光圈放大按钮
	 */
	private Button focusAddBtn;
	/***
	 * 光圈缩小按钮
	 */
	private Button focusBtn;
	/***
	 * 焦点放大按钮
	 */
	private Button jujiaoAddBtn;
	/***
	 * 焦点缩小按钮
	 */
	private Button jujiaoBtn;
	//private String path = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";
	//通道切换按钮
	/***
	 * 第一通道
	 */
	private Button channelOne;
	/***
	 * 第二通道
	 */
	private Button channelTwo;
	/***
	 * 第三通道
	 */
	private Button channelThree;
	/***
	 * 第四通道
	 */
	private Button channelFour;
	
	private String settingUsername;
	private String settingPassword;
	private String settingServer;
	private String settingPort;

	private String ddns_server;
	private String ddns_port;
	private String user_id;
	private String ddns_password;
	private String ddns_devicename;
	
	private ControlService controlService =  new ControlServiceImpl("conn1");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		VideoView mVideoView = (VideoView)findViewById(R.id.p2p_view);
		TextView ipView =  (TextView)findViewById(R.id.ip_text);
		TextView msgView = (TextView)findViewById(R.id.help_msg);
		
		mVideoView.init();
		
		ViewManager v = ViewManager.getInstance();
		v.bindVideoView(mVideoView);
		v.bindIpTextView(ipView);
		v.bindHelpMsgView(msgView);
		
		playBtn = (Button) findViewById(R.id.btn_linear_left);
		captureBtn = (Button) findViewById(R.id.btn_linear_capture);
		fullScreenBtn = (Button) findViewById(R.id.btn_linear_full);
		groupBtn = (Button) findViewById(R.id.btn_linear_group);
		ddnsBtn = (Button) findViewById(R.id.btn_linear_ddns);
		settingBtn = (Button) findViewById(R.id.btn_linear_setting);		
		
		//点击设置按钮时，进行页面跳转，这里采用startActivityForResult，在不释放当前界面的情况下开启新界面
		settingBtn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingsActivity.class);
				startActivityForResult(intent,0);
			}
			
		});
		
		ddnsBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DdbssettingActivity.class);
				startActivityForResult(intent,0);
			}
		});
				
	}
	
	//当新开启的设置界面结束跳转回来以后，处理设置界面的参数
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode){
			case Activity.RESULT_OK:
				if(data!=null){
					Bundle bundle = data.getExtras();
					settingUsername = bundle.getString("usernameStr");
					settingPassword = bundle.getString("passwordStr");
					settingServer = bundle.getString("serverStr");
					settingPort = bundle.getString("portStr");
					
					Connection conn = ConnectionManager.getConnection("conn1");
					conn.setUsername("admin");
					conn.setPassword("123456");
					conn.setSvr_ip("113.250.10.28");
					conn.setPort(Integer.valueOf(8080));
					conn.setChannel_no(0);
					
					controlService.playVideo();
				}
				break;
			case Activity.RESULT_CANCELED:
					if(data!=null){
						Bundle bundle1 = data.getExtras();
						ddns_server = bundle1.getString("ddns_serverStr");
						ddns_port = bundle1.getString("ddns_portStr");
						user_id = bundle1.getString("user_idStr");
						ddns_password = bundle1.getString("ddns_passwordStr");
						ddns_devicename = bundle1.getString("ddns_devicenameStr");
						System.out.println("ddns_server"+ddns_server);
						System.out.println("ddns_port"+ddns_port);
						System.out.println("user_id"+user_id);
						System.out.println("ddns_password"+ddns_password);
						System.out.println("ddns_devicename"+ddns_devicename);
					}
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
