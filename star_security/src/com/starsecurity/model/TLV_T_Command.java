package com.starsecurity.model;

/* TLV ���������� */
public class TLV_T_Command {
	final static int TLV_T_VERSION_INFO_ANSWER = 39;
	final static int TLV_T_VERSION_INFO_REQUEST = 40;
	final static int TLV_T_LOGIN_REQUEST = 41;
	final static int TLV_T_LOGIN_ANSWER = 42;
	final static int TLV_T_TOTAL_CHANNEL = 43;		//NOT USED
	final static int TLV_T_SENDDATA_REQUEST = 44;		//ͨ������
	final static int TLV_T_SENDDATA_ANSWER = 45;		//ͨ������Ӧ��
	final static int TLV_T_TOTAL_CHANEL_ANSWER = 46;	//Not used
	final static int TLV_T_SUSPENDSENDDATA_REQUEST = 47;		//ֹͣ��������
	final static int TLV_T_SUSPENDSENDDATA_ANSWER = 48;
	final static int TLV_T_DEVICE_KEEP_ALIVE	= 49;		//������
	final static int TLV_T_DEVICE_FORCE_EXIT	= 50;		
	final static int TLV_T_CONTROL_REQUEST = 51;		//��̨�ȿ�������
	final static int TLV_T_CONTROL_ANSWER = 52;		//��̨����Ӧ
	final static int TLV_T_RECORD_REQUEST = 53;		//¼������
	final static int TLV_T_RECORD_ANSWER	= 54;
	final static int TLV_T_DEVICE_SETTING_REQUEST = 55;		//�豸������������
	final static int TLV_T_DEVICE_SETTING_ANSWER	= 56;		//�豸��������Ӧ��
	final static int TLV_T_KEEP_ALIVE_ANSWER	= 57;		//��������Ӧ
	final static int TLV_T_DEVICE_RESET = 58;		//֪ͨ�豸����
	final static int TLV_T_DEVICE_RESET_ANSWER = 59;	//�豸���յ�������������Ӧ��ͨ�����÷���
	final static int TLV_T_ALERT_REQUEST = 60;   //�����������豸����
	final static int TLV_T_ALERT_ANSWER = 61;   //���������Ӧ���ɷ�����������ͨ�����Բ��÷���
	final static int TLV_T_ALERT_SEND_PHOTO = 62;   //�������豸�ɼ���ʱ��ͼƬ�����͵�������
	final static int TLV_T_ALERT_SEND_PHOTO_ANSWER = 63;   //�豸����MSG_CMD_ALERT_SEND_PHOTO�󣬷������Ļ�Ӧ
	final static int TLV_T_CHANNLE_REQUEST = 64;   		//�л�����һͨ��
	final static int TLV_T_CHANNLE_ANSWER = 65;   		//�л���һͨ��Ӧ��
	final static int TLV_T_SUSPEND_CHANNLE_REQUEST = 66;   		//����ĳһͨ��
	final static int TLV_T_SUSPEND_CHANNLE_ANSWER = 67;   		//Ӧ��
	final static int TLV_T_VALIDATE_REQUEST = 68;   		//������֤����
	final static int TLV_T_VALIDATE_ANSWER = 69;   		//Ӧ��
	final static int TLV_T_DVS_INFO_REQUEST = 70;			//�豸DVS֪ͨ���ӷ��豸��Ϣ����
	final static int TLV_T_DVS_INFO_ANSWER = 71;			//
	final static int TLV_T_PHONE_INFO_REQUEST = 72;			//�ֻ�֪ͨ���ӷ��ֻ���Ϣ����
	final static int TLV_T_PHONE_INFO_ANSWER	= 73;			//

	//vod & live
	final static int TLV_T_AUDIO_INFO	= 0x61;   //97		��Ƶ��Ϣ, ��ʾVΪ��Ƶ��Ϣ
	final static int TLV_T_AUDIO_DATA	= 0x62;   //98		��Ƶ����, ��ʾVΪ��Ƶ����
	final static int TLV_T_VIDEO_FRAME_INFO = 0x63;   //99    ��Ƶ֡��Ϣ, ��ʾV����������֡��Ϣ
	final static int TLV_T_VIDEO_IFRAME_DATA = 0x64;   //100   ��Ƶ�ؼ�֡���ݣ���ʾV������Ϊ�ؼ�֡
	final static int TLV_T_VIDEO_PFRAME_DATA = 0x66;  //102   ��ƵP֡(�ο�֡)����, ��ʾV������Ϊ�ο�֡
	final static int TLV_T_VIDEO_FRAME_INFO_EX = 0x65;  //101   ��չ��Ƶ֡��Ϣ֧��>=64KB����Ƶ֡
	final static int TLV_T_STREAM_FORMAT_INFO	= 200;    //			����ʽ��Ϣ ,������Ƶ����,��Ƶ����

	//vod
	final static int TLV_T_STREAM_FILE_INDEX = 213;
	final static int TLV_T_STREAM_FILE_ATTRIBUTE = 214;
	final static int TLV_T_STREAM_FILE_END = 0x0000FFFF;

}
