package com.starsecurity.model;

//u_int8 * data;		//array of data followed.
//size = sizeof(PTZArgData);
//������������ң�ǣ�浽�ٶȵĻ�����ˮƽ�ٶ�arg1����ֱ�ٶ�arg2��
//�����Ԥ��λ�Ļ��������ڼ���Ԥ��λʹ��arg1����
//��������Ԥ��λ�������ڼ���Ԥ��λʹ��arg1���������0xffffffff��ʾ���ȫ��
//������Զ�Ѳ����arg1=1��ʾ������0��ʾֹͣ
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
