package com.starsecurity.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.starsecurity.R;
import com.starsecurity.component.Connection;
import com.starsecurity.component.ConnectionManager;
import com.starsecurity.component.MessageCode;
import com.starsecurity.component.ViewManager;
import com.starsecurity.h264.VideoView;
import com.starsecurity.model.DVRDevice;
import com.starsecurity.model.FavouriteRecord;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_ChannelResponse;
import com.starsecurity.model.TLV_V_DVSInfoRequest;
import com.starsecurity.service.ControlService;
import com.starsecurity.service.ExtendedService;
import com.starsecurity.service.FavouriteControlService;
import com.starsecurity.service.impl.CheckConnectionBroadcast;
import com.starsecurity.service.impl.ControlServiceImpl;
import com.starsecurity.service.impl.ExtendedServiceImpl;
import com.starsecurity.service.impl.FavouriteControlServiceImpl;

/***
 * 
 * @function     功能	  	主界面
 * @author       创建人              肖远东
 * @date        创建日期           2013-03-18
 * @author       修改人           陈明珍/肖远东
 * @date        修改日期           2013-07-30
 * @description 修改记录	 
 *   2013-07-30 使用UI消息接收机制处理非UI线程传递的界面更新消息(Handler) 陈明珍
 *   2013-07-30 网络连线中断后自动切换播放按钮并给出提示 陈明珍       
 *   2013-07-30 加入获取最后一次设置功能  肖远东 
 *   2013-07-27 修正 BACK 键部分情况下出现异常 陈明珍
 *   2013-07-25 修正屏幕分辨率适应问题以及Android 4.2版本API兼容性问题 陈明珍
 *   2013-06-08 加入断线重连功能（3分钟后重连） 陈明珍
 *   2013-05-17 加入控制停止动作controlService.stopAction 陈明珍
 *   2013-05-07 加入方向控制、放大/缩小、焦点放大/缩小、光圈放大/缩小功能  陈明珍
 *   2013-05-03 加入提示信息对字符串资源ID的支持 陈明珍
 *              加入拍照功能
 *   2013-05-03 ViewManager加入上下文初始化         陈明珍
 *   2013-04-26 当前Connection信息获取                  陈明珍
 *             ConnectionManager.getConnection("conn1")
 *   2013-04-15 ViewManager相关信息初始化             陈明珍
 */
public class MainActivity extends Activity {

	public static MainActivity mainActivity;
	
	private PendingIntent pi = null;
	
	/***
	 * 手机存放收藏夹URL
	 */
	private static final String filePath = "/data/data/com.starsecurity/MyFavourites.xml";
	
	/** 底端按钮 */
	private Button playBtn;					// 播放按钮
	private Button captureBtn;				// 截屏按钮
	private Button fullScreenBtn; 			// 全屏按钮
	private Button groupBtn; 				// 通道换组按钮
	private Button ddnsBtn; 				// 云台设置按钮
	private Button settingBtn;				// 设置按钮

	/** 控制按钮 */
	private Button controlUpBtn; 			// 控制向上按钮
	private Button controlDownBtn; 			// 控制向下按钮
	private Button controlLeftBtn;			// 控制向左按钮
	private Button controlRightBtn; 		// 控制向右按钮
	private Button zoomAddBtn;				// 放大按钮
	private Button zoomReduceBtn;			// 缩小按钮
	private Button apertureAddBtn;			// 光圈放大按钮
	private Button apertureReduceBtn; 		// 光圈缩小按钮
	private Button focusAddBtn; 			// 焦点放大按钮
	private Button focusReduceBtn; 			// 焦点缩小按钮
	private TextView msgView;				// 连接状态显示文本
	private Button channelOne; 				// 第一通道
	private Button channelTwo; 				// 第二通道
	private Button channelThree;			// 第三通道
	private Button channelFour;				// 第四通道
	private DVRDevice dvrDevice;			// 存放用户选择好平台后的设备信息
	
	
	/** 存放收藏夹设置信息 */
	private String settingUsername;
	private String settingPassword;
	private String settingServer;
	private String settingPort;
	private String settingChannel;
	
	public Handler mHandler;				// UI消息处理
	
	private static int page = 0;			// 存放界面显示的通道切换组的页面
	private static boolean isPlay = false;	// 标识是否正在播放
	private static String functionTempStr;	// 存储临时文本信息
	private static boolean isCloudControl = false;
	
	/** 使用记录单元实例 */
	private FavouriteControlService favouriteControlService = new FavouriteControlServiceImpl("conn1");
	
	/** 控制单元实例 */
	private ControlService controlService =  new ControlServiceImpl("conn1");	
	
	/** 扩展单元实例  */
	private ExtendedService extendedService = new ExtendedServiceImpl("conn1");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainActivity = this;
		
		VideoView mVideoView = (VideoView)findViewById(R.id.p2p_view);
		TextView ipView =  (TextView)findViewById(R.id.ip_text);
		msgView = (TextView)findViewById(R.id.help_msg);
		
		mVideoView.init();
		
		// 初始化上下文
		ViewManager.initContext(this);
		
		ViewManager v = ViewManager.getInstance();
		v.bindVideoView(mVideoView);
		v.bindIpTextView(ipView);
		v.bindHelpMsgView(msgView);
		
		v.setMainVideoView(mVideoView);
		
		/* 获取最后一次设置功能  */
		File myFavouritesFile=new File(filePath);		//存放收藏夹的XMl文件
		//非首次使用，则获取XML存储文件最后一次使用记录
		if(myFavouritesFile.exists()){
			String selectedRecordName= favouriteControlService.getLastRecordName(filePath);
			String lastChannel = favouriteControlService.getLastChannel(filePath);
			if(selectedRecordName!=null&&!selectedRecordName.equals("")){
				FavouriteRecord lastFavouriteRecord = (FavouriteRecord) favouriteControlService.getFavouriteRecordByName(filePath,selectedRecordName);
				isCloudControl = false;
				settingUsername = lastFavouriteRecord.getUserName();
				if(lastFavouriteRecord.getPassword()==null){
					settingPassword = "";
				}else{
					settingPassword = lastFavouriteRecord.getPassword();
				}
				settingServer = lastFavouriteRecord.getIPAddress(); 
				settingPort = lastFavouriteRecord.getPort();
				if(lastChannel==null){
					settingChannel = lastFavouriteRecord.getDefaultChannel();
				}else{
					settingChannel = lastChannel;
				}
			}
		}
		
		// 控制按钮关联
		controlUpBtn = (Button) findViewById(R.id.btn_control_up);
		controlDownBtn = (Button) findViewById(R.id.btn_control_down);
		controlLeftBtn = (Button) findViewById(R.id.btn_control_left);
		controlRightBtn = (Button) findViewById(R.id.btn_control_right);
		zoomAddBtn = (Button) findViewById(R.id.btn_control_zoomadd);
		zoomReduceBtn = (Button) findViewById(R.id.btn_control_zoom);
		apertureAddBtn = (Button) findViewById(R.id.btn_control_jujiaoadd);
		apertureReduceBtn = (Button) findViewById(R.id.btn_control_jujiao);
		focusAddBtn = (Button) findViewById(R.id.btn_control_focusadd);
		focusReduceBtn = (Button) findViewById(R.id.btn_control_focus);
		
		// 通道切换按钮组与界面关联
		channelOne = (Button) findViewById(R.id.btn_number_1);
		channelTwo = (Button) findViewById(R.id.btn_number_2);
		channelThree = (Button) findViewById(R.id.btn_number_3);
		channelFour = (Button) findViewById(R.id.btn_number_4);
		
		channelOne.setOnClickListener(switchChannel);
		channelTwo.setOnClickListener(switchChannel);
		channelThree.setOnClickListener(switchChannel);
		channelFour.setOnClickListener(switchChannel);
		
		
		// 底端按钮组与界面关联
		playBtn = (Button) findViewById(R.id.btn_linear_left);
		captureBtn = (Button) findViewById(R.id.btn_linear_capture);
		fullScreenBtn = (Button) findViewById(R.id.btn_linear_full);
		groupBtn = (Button) findViewById(R.id.btn_linear_group);
		ddnsBtn = (Button) findViewById(R.id.btn_linear_ddns);
		settingBtn = (Button) findViewById(R.id.btn_linear_setting);
		
		// 控制按键单击、松开事件
		controlUpBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_Up));
						controlService.move("UP");
					}
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay){
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		controlDownBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_Down));
						controlService.move("DOWN");
					}
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay){
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		controlLeftBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_Left));
						controlService.move("LEFT");
					}			
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay){
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		controlRightBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_Right));
						controlService.move("RIGHT");
					}		
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay) {
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		
		zoomAddBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_ZoomOut));
						controlService.zoomInOrOut(true);
					}					
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay) {
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		
		zoomReduceBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_ZoomIn));
						controlService.zoomInOrOut(false);
					}
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay) {
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		
		apertureAddBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_OpticalOut));
						controlService.adjustAperture(true);
					}					
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay) {
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		apertureReduceBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_OpticalIn));
						controlService.adjustAperture(false);
					}					
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay) {
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		
		focusAddBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_FocusOut));
						controlService.adjustFocus(true);
					}					
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay) {
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		focusReduceBtn.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//单击事件
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					if(isPlay){
						functionTempStr = msgView.getText().toString();
						msgView.setText(getString(R.string.IDS_FocusIn));
						controlService.adjustFocus(false);
					}
				}
				//按钮松开时，显示播放参数
				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(isPlay) {
						msgView.setText(functionTempStr);
						controlService.stopAction();
					}
				}
				return false;
			}
		});
		
		//点击播放按钮时，开始播放视频
		playBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				if (!isPlay) {
					if (dvrDevice!= null&&isCloudControl) {
						Connection conn = ConnectionManager
								.getConnection("conn1");
						conn.setUsername(dvrDevice.getLoginUsername());
						if (dvrDevice.getLoginPassword() == null) {
							conn.setPassword("");
						} else {
							conn.setPassword(dvrDevice.getLoginPassword());
						}
						conn.setSvr_ip(dvrDevice.getLoginIP());
						conn.setPort(Integer.valueOf(dvrDevice
								.getMobliePhonePort()));
						conn.setChannel_no(Integer.valueOf(dvrDevice
								.getStarChannel()) - 1);
						//根据播放通道显示通道组
						int channelNumber = Integer.parseInt(dvrDevice
								.getStarChannel());
						if(channelNumber>0&&channelNumber<=4)
							switchChannelBtnImages(0);
						else if(channelNumber>4&&channelNumber<=8)
							switchChannelBtnImages(1);
						else if(channelNumber>8&&channelNumber<=12)
							switchChannelBtnImages(2);
						else if(channelNumber>12&&channelNumber<=16)
							switchChannelBtnImages(3);
						
						controlService.playVideo();
						isPlay = true;
						playBtn.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.linear_play));
					} else if (settingUsername != null && settingServer != null
							&& settingPort != null && settingChannel != null&&!isCloudControl) {
						Connection conn = ConnectionManager
								.getConnection("conn1");
						conn.setUsername(settingUsername);
						if (settingPassword == null)
							conn.setPassword("");
						else
							conn.setPassword(settingPassword);
						conn.setSvr_ip(settingServer);
						conn.setPort(Integer.valueOf(settingPort));
						conn.setChannel_no(Integer.parseInt(settingChannel) - 1);
						//根据播放通道显示通道组
						int channelNumber = Integer.parseInt(settingChannel);
						if(channelNumber>0&&channelNumber<=4)
							switchChannelBtnImages(0);
						else if(channelNumber>4&&channelNumber<=8)
							switchChannelBtnImages(1);
						else if(channelNumber>8&&channelNumber<=12)
							switchChannelBtnImages(2);
						else if(channelNumber>12&&channelNumber<=16)
							switchChannelBtnImages(3);
						
						controlService.playVideo();
						isPlay = true;
						playBtn.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.linear_play));
					}
				} else {
					// 停止播放视频
					controlService.stopVideo();
					isPlay = false;
					playBtn.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.linear_left));
					// msgView.setText(getString(R.string.IDS_Connect_dispos));
					ViewManager.getInstance().setHelpMsg(
							R.string.IDS_Connect_dispos);
				}
			}
		});
		
		captureBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				extendedService.takePicture();		// 拍照
			}
			
		});
		
		// 全屏按钮
		fullScreenBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, FullScreenActivity.class);
				intent.putExtra("conn_name", "conn1");
				
				startActivity(intent);
			}
		});
		
		// 单击视频全屏
		mVideoView.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, FullScreenActivity.class);
				intent.putExtra("conn_name", "conn1");
				
				startActivity(intent);
			}
			
		});
		
		
		//点击通道换组按钮时，先验证该平台支持的通道组数，然后根据验证改变通道选择
		groupBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				TLV_V_DVSInfoRequest tlvDVSInfoRequest = (TLV_V_DVSInfoRequest) ConnectionManager.getConnection("conn1").retrieveResultItem(TLV_T_Command.TLV_T_DVS_INFO_REQUEST);
				//TLV_V_ChannelResponse tlvChannelResponse = (TLV_V_ChannelResponse) ConnectionManager.getConnection("conn1").retrieveResultItem(TLV_T_Command.TLV_T_CHANNLE_ANSWER);
				if(tlvDVSInfoRequest!=null&&!isCloudControl){
					int channelNuber = tlvDVSInfoRequest.getChannleNumber();
					switch (page) {
						case 0:
							if (channelNuber > 4)
								switchChannelBtnImages(1);
							break;
						case 1:
							if (channelNuber == 8)
								switchChannelBtnImages(0);
							if (channelNuber > 8)
								switchChannelBtnImages(2);
							break;
						case 2:
							if (channelNuber == 12)
								switchChannelBtnImages(0);
							if (channelNuber > 12)
								switchChannelBtnImages(3);
							break;
						case 3:
							switchChannelBtnImages(0);
							break;
					}
				}
				else if(tlvDVSInfoRequest==null&&!isCloudControl){
					switchChannelBtnImages(1);
				}
				else if (dvrDevice!=null&&isCloudControl) {
					switch (page) {
						case 0:
							if (Integer.parseInt(dvrDevice.getChannelNumber()) > 4)
								switchChannelBtnImages(1);
							break;
						case 1:
							if (Integer.parseInt(dvrDevice.getChannelNumber()) == 8)
								switchChannelBtnImages(0);
							if (Integer.parseInt(dvrDevice.getChannelNumber()) > 8)
								switchChannelBtnImages(2);
							break;
						case 2:
							if (Integer.parseInt(dvrDevice.getChannelNumber()) == 12)
								switchChannelBtnImages(0);
							if (Integer.parseInt(dvrDevice.getChannelNumber()) > 12)
								switchChannelBtnImages(3);
							break;
						case 3:
							switchChannelBtnImages(0);
							break;
					}
				}
			}
		});
		
		// 点击设置按钮时，进入设置页面，这里采用startActivityForResult，在不释放当前界面的情况下开启新界面
		settingBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingsActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		// 点击云台设置按钮时，进入云台设置，这里采用startActivityForResult，在不释放当前界面的情况下开启新界面
		ddnsBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DdbssettingActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		
		
		mHandler = new Handler() { 
			@Override 
			public void handleMessage(Message msg) { 
				switch (msg.what) {
				case MessageCode.CONNECTION_CLOSED: // 设置播放按钮为停止状态
					isPlay = false;
					updatePlayStatus(R.string.IDS_Connect_dispos);
					break;
				case MessageCode.ERR_UNKNOWN:	// 未知错误
					ViewManager.getInstance().setHelpMsg(R.string.IDS_Unknown);
					break;
				case MessageCode.IDS_TIMEOUT:	// 连接超时
					isPlay = false;
					updatePlayStatus(R.string.IDS_Time_Out);
					break;
				case MessageCode.IDS_CONSERSUC: // 连接服务器成功
					ViewManager.getInstance().setHelpMsg(R.string.DS_ConSerSuc);
					break;
				case MessageCode.IDS_CONSERFAIL: // 连接服务器失败
					isPlay = false;
					updatePlayStatus(R.string.IDS_ConSerFail);
					break;
				case MessageCode.IDS_CONNECTSERVER: // 网络连接中...
					ViewManager.getInstance().setHelpMsg(R.string.IDS_ConnectServer);
					break;
				}
			
			} 
		}; 
		
		v.setHandler(mHandler);
		
		
		/* 若网络异常或中断，3分钟后重连 */
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);  
        
        pi = PendingIntent.getBroadcast(this, 0, new Intent(MainActivity.this, CheckConnectionBroadcast.class), Intent.FLAG_ACTIVITY_NEW_TASK);  
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 180 * 1000, pi);   
	}
	
	private OnClickListener switchChannel = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_number_1:
					controlService.switchChannel(page * 4 + 1);
					favouriteControlService.setLastChannel(filePath, String.valueOf(page*4+1));
					break;
				case R.id.btn_number_2:
					controlService.switchChannel(page * 4 + 2);
					favouriteControlService.setLastChannel(filePath, String.valueOf(page*4+2));
					break;
				case R.id.btn_number_3:
					controlService.switchChannel(page * 4 + 3);
					favouriteControlService.setLastChannel(filePath, String.valueOf(page*4+3));
					break;
				case R.id.btn_number_4:
					controlService.switchChannel(page * 4 + 4);
					favouriteControlService.setLastChannel(filePath, String.valueOf(page*4+4));
					break;
			}
		}
	};
	
	/**
	 * 用于切换界面中的通道图标
	 * @param index 需要转换的页面
	 * @return
	 */
	private int switchChannelBtnImages(int index) {
		switch (index) {
			case 0:
				channelOne.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.control_number1));
				channelTwo.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_two));
				channelThree.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_three));
				channelFour.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.number_four));
				page = 0;
				break;
			case 1:
				channelOne.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number5));
				channelTwo.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number6));
				channelThree.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number7));
				channelFour.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number8));
				page = 1;
				break;
			case 2:
				channelOne.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number9));
				channelTwo.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number10));
				channelThree.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number11));
				channelFour.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number12));
				page = 2;
				break;
			case 3:
				channelOne.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number13));
				channelTwo.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number14));
				channelThree.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number15));
				channelFour.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_number16));
				page = 3;
				break;
		}
		return 1;
	}

	/**
	 * 当新开启的设置界面结束跳转回来以后，处理设置界面的参数
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			// 将通道选择按钮组还原为初始状态	
			if (data != null) {
				Bundle bundle = data.getExtras();
				if (bundle.getString("usernameStr") != null)
					settingUsername = bundle.getString("usernameStr");
				if (bundle.getString("passwordStr") != null)
					settingPassword = bundle.getString("passwordStr");
				if (bundle.getString("serverStr") != null)
					settingServer = bundle.getString("serverStr");
				if (bundle.getString("portStr") != null)
					settingPort = bundle.getString("portStr");
				if (bundle.getString("channelStr") != null){
					settingChannel = bundle.getString("channelStr");
				}
				isCloudControl = false;
			}
			break;
		case Activity.RESULT_FIRST_USER:
			// 将通道选择按钮组还原为初始状态
			switchChannelBtnImages(0);
			// 用户选择云台设备后，进行播放
			if (data != null) {
				dvrDevice = (DVRDevice) data.getSerializableExtra("DVRDevice");
				isCloudControl = true;
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
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			new AlertDialog.Builder(this)
					.setMessage(getString(R.string.IDS_Out))
					.setPositiveButton(getString(R.string.IDS_Sure),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (pi != null) {
										pi.cancel();
									}
									//dialog.dismiss();
									//System.exit(0);
									android.os.Process.killProcess(android.os.Process.myPid()); 
								}
							}).setNegativeButton(R.string.IDS_Dispos, null)
					.show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
		
	}

	
	public boolean getIsPlay() {
		return isPlay;
	}
	
	public void playVideo() {
		isPlay = false;
		playBtn.performClick();
	}
	
	public void stopVideo() {
		isPlay = true;
		playBtn.performClick();
	}
	
    /** 
     * 对网络连接状态进行判断 
     * @return  true, 可用； false， 不可用 
     */  
    public boolean isOpenNetwork() {  
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  
        if(connManager.getActiveNetworkInfo() != null) {  
            return connManager.getActiveNetworkInfo().isAvailable();  
        }  
      
        return false;  
    }  
    
    /**
     * 更新播放按钮状态及提示信息
     * @param helpMsgId 提示信息ID
     */
    public void updatePlayStatus(int helpMsgId) {
    	if (isPlay) { // 播放状态
    		playBtn.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.linear_play));
    	} else {	// 停止状态
    		playBtn.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.linear_left));
			ViewManager.getInstance().setHelpMsg(helpMsgId);
    	}
    }

}
