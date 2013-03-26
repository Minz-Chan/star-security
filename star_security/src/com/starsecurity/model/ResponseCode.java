package com.starsecurity.model;

/* response result */
public class ResponseCode {
	final static int _RESPONSECODE_SUCC	= 0x01;		//	成功
	final static int _RESPONSECODE_USER_PWD_ERROR = 0x02;		//  用户名或密码错
	final static int _RESPONSECODE_PDA_VERSION_ERROR = 0x04;		//	版本不一致
	final static int _RESPONSECODE_MAX_USER_ERROR = 0x05;	
	final static int _RESPONSECODE_DEVICE_OFFLINE = 0x06;		//	设备已经离线
	final static int _RESPONSECODE_DEVICE_HAS_EXIST = 0x07;		//  设备已经存在
	final static int _RESPONSECODE_DEVICE_OVERLOAD = 0x08;		//  设备性能超载(设备忙)
	final static int _RESPONSECODE_INVALID_CHANNLE = 0x09;		//  设备不支持的通道
	final static int _RESPONSECODE_PROTOCOL_ERROR = 0X0A;		//协议解析出错
	final static int _RESPONSECODE_NOT_START_ENCODE =0X0B;		//未启动编码
	final static int _RESPONSECODE_TASK_DISPOSE_ERROR = 0X0C;		//任务处理过程出错
}
