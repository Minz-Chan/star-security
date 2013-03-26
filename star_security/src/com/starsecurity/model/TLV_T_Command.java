package com.starsecurity.model;

/* TLV ���������� */
public class TLV_T_Command {
	public final static int TLV_T_VERSION_INFO_ANSWER = 39;
	public final static int TLV_T_VERSION_INFO_REQUEST = 40;
	public final static int TLV_T_LOGIN_REQUEST = 41;
	public final static int TLV_T_LOGIN_ANSWER = 42;
	public final static int TLV_T_TOTAL_CHANNEL = 43;		//NOT USED
	public final static int TLV_T_SENDDATA_REQUEST = 44;		//ͨ������
	public final static int TLV_T_SENDDATA_ANSWER = 45;		//ͨ������Ӧ��
	public final static int TLV_T_TOTAL_CHANEL_ANSWER = 46;	//Not used
	public final static int TLV_T_SUSPENDSENDDATA_REQUEST = 47;		//ֹͣ��������
	public final static int TLV_T_SUSPENDSENDDATA_ANSWER = 48;
	public final static int TLV_T_DEVICE_KEEP_ALIVE	= 49;		//������
	public final static int TLV_T_DEVICE_FORCE_EXIT	= 50;		
	public final static int TLV_T_CONTROL_REQUEST = 51;		//��̨�ȿ�������
	public final static int TLV_T_CONTROL_ANSWER = 52;		//��̨����Ӧ
	public final static int TLV_T_RECORD_REQUEST = 53;		//¼������
	public final static int TLV_T_RECORD_ANSWER	= 54;
	public final static int TLV_T_DEVICE_SETTING_REQUEST = 55;		//�豸������������
	public final static int TLV_T_DEVICE_SETTING_ANSWER	= 56;		//�豸��������Ӧ��
	public final static int TLV_T_KEEP_ALIVE_ANSWER	= 57;		//��������Ӧ
	public final static int TLV_T_DEVICE_RESET = 58;		//֪ͨ�豸����
	public final static int TLV_T_DEVICE_RESET_ANSWER = 59;	//�豸���յ�������������Ӧ��ͨ�����÷���
	public final static int TLV_T_ALERT_REQUEST = 60;   //�����������豸����
	public final static int TLV_T_ALERT_ANSWER = 61;   //���������Ӧ���ɷ�����������ͨ�����Բ��÷���
	public final static int TLV_T_ALERT_SEND_PHOTO = 62;   //�������豸�ɼ���ʱ��ͼƬ�����͵�������
	public final static int TLV_T_ALERT_SEND_PHOTO_ANSWER = 63;   //�豸����MSG_CMD_ALERT_SEND_PHOTO�󣬷������Ļ�Ӧ
	public final static int TLV_T_CHANNLE_REQUEST = 64;   		//�л�����һͨ��
	public final static int TLV_T_CHANNLE_ANSWER = 65;   		//�л���һͨ��Ӧ��
	public final static int TLV_T_SUSPEND_CHANNLE_REQUEST = 66;   		//����ĳһͨ��
	public final static int TLV_T_SUSPEND_CHANNLE_ANSWER = 67;   		//Ӧ��
	public final static int TLV_T_VALIDATE_REQUEST = 68;   		//������֤����
	public final static int TLV_T_VALIDATE_ANSWER = 69;   		//Ӧ��
	public final static int TLV_T_DVS_INFO_REQUEST = 70;			//�豸DVS֪ͨ���ӷ��豸��Ϣ����
	public final static int TLV_T_DVS_INFO_ANSWER = 71;			//
	public final static int TLV_T_PHONE_INFO_REQUEST = 72;			//�ֻ�֪ͨ���ӷ��ֻ���Ϣ����
	public final static int TLV_T_PHONE_INFO_ANSWER	= 73;			//

	//vod & live
	public final static int TLV_T_AUDIO_INFO	= 0x61;   //97		��Ƶ��Ϣ, ��ʾVΪ��Ƶ��Ϣ
	public final static int TLV_T_AUDIO_DATA	= 0x62;   //98		��Ƶ����, ��ʾVΪ��Ƶ����
	public final static int TLV_T_VIDEO_FRAME_INFO = 0x63;   //99    ��Ƶ֡��Ϣ, ��ʾV����������֡��Ϣ
	public final static int TLV_T_VIDEO_IFRAME_DATA = 0x64;   //100   ��Ƶ�ؼ�֡���ݣ���ʾV������Ϊ�ؼ�֡
	public final static int TLV_T_VIDEO_PFRAME_DATA = 0x66;  //102   ��ƵP֡(�ο�֡)����, ��ʾV������Ϊ�ο�֡
	public final static int TLV_T_VIDEO_FRAME_INFO_EX = 0x65;  //101   ��չ��Ƶ֡��Ϣ֧��>=64KB����Ƶ֡
	public final static int TLV_T_STREAM_FORMAT_INFO	= 200;    //			����ʽ��Ϣ ,������Ƶ����,��Ƶ����

	//vod
	public final static int TLV_T_STREAM_FILE_INDEX = 213;
	public final static int TLV_T_STREAM_FILE_ATTRIBUTE = 214;
	public final static int TLV_T_STREAM_FILE_END = 0x0000FFFF;

}
