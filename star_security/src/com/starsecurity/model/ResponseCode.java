package com.starsecurity.model;

/***
 * 
 * response result
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class ResponseCode {
	public final static int _RESPONSECODE_SUCC	= 0x01;		//成功
	public final static int _RESPONSECODE_USER_PWD_ERROR = 0x02;		//用户名或密码错
	public final static int _RESPONSECODE_PDA_VERSION_ERROR = 0x04;	//版本不一致
	public final static int _RESPONSECODE_MAX_USER_ERROR = 0x05;	
	public final static int _RESPONSECODE_DEVICE_OFFLINE = 0x06;		//设备已经离线
	public final static int _RESPONSECODE_DEVICE_HAS_EXIST = 0x07;		//设备已经存在
	public final static int _RESPONSECODE_DEVICE_OVERLOAD = 0x08;		//设备性能超载(设备忙)
	public final static int _RESPONSECODE_INVALID_CHANNLE = 0x09;		//设备不支持的通道	
	public final static int _RESPONSECODE_PROTOCOL_ERROR = 0X0A;		//协议解析出错
	public final static int _RESPONSECODE_NOT_START_ENCODE =0X0B;		//未启动编码
	public final static int _RESPONSECODE_TASK_DISPOSE_ERROR = 0X0C;	//任务处理过程出错
}
