package com.starsecurity.model;

/***
 * 
 * login request: remote -> streaming server
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class TLV_V_LoginRequest {
	private char[] userName = new char[TLV_Length.STR_LEN_32];			//用户名, 后面不足部分为数字0      (为ASCII字符串)
	private char[] password = new char[TLV_Length.STR_LEN_16];			//密码, 后面不足部分为数字0        (为ASCII字符串) 
	private long deviceId;					//设备ID. CS模式下由服务器统一分配, P2P模式下为固定值
	private short flag;						//should be set to 1 to be compatible with the previous version.
	public char[] getUserName() {
		return userName;
	}
	public void setUserName(char[] userName) {
		this.userName = userName;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	public long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	public short getFlag() {
		return flag;
	}
	public void setFlag(short flag) {
		this.flag = flag;
	}
	public short getChannel() {
		return channel;
	}
	public void setChannel(short channel) {
		this.channel = channel;
	}
	public int getReserve() {
		return reserve;
	}
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	private short channel;					//channel
	private int reserve;
}
