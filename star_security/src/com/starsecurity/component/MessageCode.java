package com.starsecurity.component;

public class MessageCode {
	public final static int CONNECTION_CLOSED = 1;			// 连接关闭
	public final static int ERR_UNKNOWN = 2;				// 未知错误
	public final static int IDS_TIMEOUT = 3;				// 连接超时
	public final static int IDS_CONSERSUC = 4;				// 连接服务器成功
	public final static int IDS_CONSERFAIL = 5;				// 连接服务器失败
	public final static int IDS_CONNECTSERVER = 6;			// 网络连接中...
	
	
	public final static int _RESPONSECODE_USER_PWD_ERROR = 7;				// 用户或密码错误
	public final static int _RESPONSECODE_PDA_VERSION_ERROR = 8;			// 版本不一致
	public final static int _RESPONSECODE_MAX_USER_ERROR = 9;				// 最大用户数
	public final static int _RESPONSECODE_DEVICE_OFFLINE = 10;				// 设备已离线
 
	
	
	public final static int THREAD_STOP = 12;				// 线程停止 
	public final static int IDS_UNSUPPORTEDPROFILE = 13;	// 不支持的profile	
	
	public final static int SAVE_PICTURE = 14; 				// 拍照
	public final static int UPDATE_VIDEO_SCALE = 15;		// 更新视频区域的Scale
	public final static int IDS_CONNECTMAXCHANNEL = 50;			// 连接成功，可获取最大通道数...
	
}
