package com.starsecurity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.starsecurity.R;
import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.service.ControlService;
import com.starsecurity.service.impl.ControlServiceImpl;

/***
 * 
 * @function     功能	  	主界面
 * @author       创建人              肖远东
 * @date        创建日期           2013-03-18
 * @author       修改人              肖远东
 * @date        修改日期           2013-04-18
 * @description 修改说明	          首次增加
 *
 */
public class MainActivity extends Activity {
	//底端按钮组
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
	
	/***
	 * 存放用户选择好平台后的设备信息
	 */
	private DVRDevice dvrDevice;
	/***
	 * 存放收藏夹设置信息
	 */
	private String settingUsername;
	private String settingPassword;
	private String settingServer;
	private String settingPort;
	private String settingChannel;
	/***
	 * 存放界面显示的通道切换组的页面
	 */
	private static int page = 0;
	/***
	 * 标识是否正在播放
	 */
	private static boolean isPlay = false;
	
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
		
		//通道切换按钮组与界面关联
		channelOne = (Button) findViewById(R.id.btn_number_1);
		channelTwo = (Button) findViewById(R.id.btn_number_2);
		channelThree = (Button) findViewById(R.id.btn_number_3);
		channelFour = (Button) findViewById(R.id.btn_number_4);
		
		//底端按钮组与界面关联
		playBtn = (Button) findViewById(R.id.btn_linear_left);
		captureBtn = (Button) findViewById(R.id.btn_linear_capture);
		fullScreenBtn = (Button) findViewById(R.id.btn_linear_full);
		groupBtn = (Button) findViewById(R.id.btn_linear_group);
		ddnsBtn = (Button) findViewById(R.id.btn_linear_ddns);
		settingBtn = (Button) findViewById(R.id.btn_linear_setting);
		
		//点击播放按钮时，开始播放视频
		playBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(dvrDevice!=null){
					Connection conn = ConnectionManager.getConnection("conn1");
					conn.setUsername(dvrDevice.getLoginUsername());
					if(dvrDevice.getLoginPassword()==null){
						conn.setPassword("");
					}else{
						conn.setPassword(dvrDevice.getLoginPassword());
					}
					conn.setSvr_ip(dvrDevice.getLoginIP());
					conn.setPort(Integer.valueOf(dvrDevice.getMobliePhonePort()));
					conn.setChannel_no(Integer.valueOf(dvrDevice.getStarChannel()));
					controlService.playVideo();
				}else if(settingUsername!=null&&settingServer!=null&&settingPort!=null&&settingChannel!=null){
					Connection conn = ConnectionManager.getConnection("conn1");
					conn.setUsername(settingUsername);
					if(settingPassword==null)
						conn.setPassword("");
					else
						conn.setPassword(settingPassword);
					conn.setSvr_ip(settingServer);
					conn.setPort(Integer.valueOf(settingPort));
					conn.setChannel_no(Integer.parseInt(settingChannel));
					controlService.playVideo();
				}
			}
		});
		
		//点击通道换组按钮时，先验证该平台支持的通道组数，然后根据验证改变通道选择
		groupBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(dvrDevice!=null){
					switch(page){
					case 0:
						if(Integer.parseInt(dvrDevice.getChannelNumber())>4)
							switchChannelBtnImages(1);
						break;
					case 1:
						if(Integer.parseInt(dvrDevice.getChannelNumber())==8)
							switchChannelBtnImages(0);
						if(Integer.parseInt(dvrDevice.getChannelNumber())>8)
							switchChannelBtnImages(2);
						break;
					case 2:
						switchChannelBtnImages(0);
						break;
					}
				}
			}
		});
		
		//点击设置按钮时，进入设置页面，这里采用startActivityForResult，在不释放当前界面的情况下开启新界面
		settingBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingsActivity.class);
				startActivityForResult(intent,0);
			}
		});
		//点击云台设置按钮时，进入云台设置，这里采用startActivityForResult，在不释放当前界面的情况下开启新界面
		ddnsBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DdbssettingActivity.class);
				startActivityForResult(intent,0);
			}
		});
				
	}
	/***
	 * 用于切换界面中的通道图标
	 * @param index 需要转换的页面
	 * @return
	 */
	private int switchChannelBtnImages(int index){
		switch(index){
			case 0:
				channelOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.control_number1));
				channelTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.number_two));
				channelThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.number_three));
				channelFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.number_four));
				page=0;
				break;
			case 1:
				channelOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number5));
				channelTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number6));
				channelThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number7));
				channelFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number8));
				page=1;
				break;
			case 2:
				channelOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number9));
				channelTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number10));
				channelThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number11));
				channelFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_number12));
				page=2;
				break;
		}
		return 1;
	}
	
	/***
	 * 当新开启的设置界面结束跳转回来以后，处理设置界面的参数
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode){
			case Activity.RESULT_OK:
				//将通道选择按钮组还原为初始状态
				switchChannelBtnImages(0);
				if(data!=null){
					Bundle bundle = data.getExtras();
					if(bundle.getString("usernameStr")!=null)
						settingUsername = bundle.getString("usernameStr");
					if(bundle.getString("passwordStr")!=null)
						settingPassword = bundle.getString("passwordStr");
					if(bundle.getString("serverStr")!=null)
						settingServer = bundle.getString("serverStr");
					if(bundle.getString("portStr")!=null)
						settingPort = bundle.getString("portStr");
					if(bundle.getString("channelStr")!=null)
						settingChannel = bundle.getString("channelStr");
				}
				break;
			case Activity.RESULT_FIRST_USER:
				//将通道选择按钮组还原为初始状态
				switchChannelBtnImages(0);
				//用户选择云台设备后，进行播放
				if(data!=null){
					dvrDevice = (DVRDevice) data.getSerializableExtra("DVRDevice");
//					if(dvrDevice!=null){
//						Connection conn = ConnectionManager.getConnection("conn1");
//						conn.setUsername(dvrDevice.getLoginUsername());
//						conn.setPassword(dvrDevice.getLoginPassword());
//						conn.setSvr_ip(dvrDevice.getLoginIP());
//						conn.setPort(Integer.valueOf(dvrDevice.getMobliePhonePort()));
//						conn.setChannel_no(Integer.valueOf(dvrDevice.getStarChannel()));
//						controlService.playVideo();
//						isPlay = true;
//					}
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
     * 若是在当前activity中按了返回键,则退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN)
            {
            	new  AlertDialog.Builder(this).setMessage(getString(R.string.IDS_Out)).
            	setPositiveButton(getString(R.string.IDS_Sure),new DialogInterface.OnClickListener() {
            		@Override
            		public void onClick(DialogInterface dialog, int which) {
            			System.exit(0);
            		}
            	}).setNegativeButton(R.string.IDS_Dispos,null).show(); 
            }
            return super.onKeyDown(keyCode, event);
    }
	
}
