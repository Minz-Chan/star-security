package com.starsecurity.model;

/***
 * 
 * 扩展控制协议, 包括云台及TV控制
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_ControlRequest {
	private long deviceId;			// device id generating by the remote device
	private short channel;			// channel id 
	private short cmdCode;			// 控制命令字，参见_PTZCode
	private int size;				// 控制参数数据长度,如果size==0 表示无控制参数
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public short getChannel() {
		return channel;
	}
	public void setChannel(short channel) {
		this.channel = channel;
	}
	public short getCmdCode() {
		return cmdCode;
	}
	public void setCmdCode(short cmdCode) {
		this.cmdCode = cmdCode;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
