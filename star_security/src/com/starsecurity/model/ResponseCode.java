package com.starsecurity.model;

/* response result */
public class ResponseCode {
	final static int _RESPONSECODE_SUCC	= 0x01;		//	�ɹ�
	final static int _RESPONSECODE_USER_PWD_ERROR = 0x02;		//  �û����������
	final static int _RESPONSECODE_PDA_VERSION_ERROR = 0x04;		//	�汾��һ��
	final static int _RESPONSECODE_MAX_USER_ERROR = 0x05;	
	final static int _RESPONSECODE_DEVICE_OFFLINE = 0x06;		//	�豸�Ѿ�����
	final static int _RESPONSECODE_DEVICE_HAS_EXIST = 0x07;		//  �豸�Ѿ�����
	final static int _RESPONSECODE_DEVICE_OVERLOAD = 0x08;		//  �豸���ܳ���(�豸æ)
	final static int _RESPONSECODE_INVALID_CHANNLE = 0x09;		//  �豸��֧�ֵ�ͨ��
	final static int _RESPONSECODE_PROTOCOL_ERROR = 0X0A;		//Э���������
	final static int _RESPONSECODE_NOT_START_ENCODE =0X0B;		//δ��������
	final static int _RESPONSECODE_TASK_DISPOSE_ERROR = 0X0C;		//��������̳���
}
