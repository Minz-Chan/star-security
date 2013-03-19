package com.starsecurity.model;

//u_int8 * data;		//array of data followed.
//size = sizeof(PTZArgData);
//如果是上下左右，牵涉到速度的话，有水平速度arg1，垂直速度arg2；
//如果是预置位的话，操作第几个预制位使用arg1标明
//如果是清除预置位，操作第几个预置位使用arg1标明，如果0xffffffff表示清除全部
//如果是自动巡航，arg1=1表示启动，0表示停止
public class ControlArgData {
	private long arg1;
	private long arg2;
	private long arg3;
	private long arg4;
	public long getArg1() {
		return arg1;
	}
	public void setArg1(long arg1) {
		this.arg1 = arg1;
	}
	public long getArg2() {
		return arg2;
	}
	public void setArg2(long arg2) {
		this.arg2 = arg2;
	}
	public long getArg3() {
		return arg3;
	}
	public void setArg3(long arg3) {
		this.arg3 = arg3;
	}
	public long getArg4() {
		return arg4;
	}
	public void setArg4(long arg4) {
		this.arg4 = arg4;
	}
}
