package com.starsecurity.model;

//��̨������,ȡֵ��ΧΪ0~255
public class OWSP_ACTIONCode {
	public final static int OWSP_ACTION_MD_STOP = 0;            // ֹͣ�˶�
	public final static int OWSP_ACTION_ZOOMReduce=5;
	public final static int OWSP_ACTION_ZOOMADD=6;
	public final static int OWSP_ACTION_FOCUSADD=7;   //����
	public final static int OWSP_ACTION_FOCUSReduce=8;
	public final static int OWSP_ACTION_MD_UP=9;                    // ����
	public final static int OWSP_ACTION_MD_DOWN=10;             // ����
	public final static int OWSP_ACTION_MD_LEFT=11;             // ����
	public final static int OWSP_ACTION_MD_RIGHT=12;           // ����
	public final static int OWSP_ACTION_Circle_Add = 13;   //��Ȧ
	public final static int OWSP_ACTION_Circle_Reduce = 14;    //
	public final static int OWSP_ACTION_AUTO_CRUISE = 15;//�Զ�Ѳ��
	public final static int OWSP_ACTION_GOTO_PRESET_POSITION = 16; 	//��תԤ��λ
	public final static int OWSP_ACTION_SET_PRESET_POSITION = 17;	//����Ԥ��λ��
	public final static int OWSP_ACTION_CLEAR_PRESET_POSITION = 18; //���Ԥ��λ��
	public final static int OWSP_ACTION_ACTION_RESET = 20;
	public final static int OWSP_ACTION_TV_SWITCH = 128;	//�л���ƵԴ,��Ϣ����ΪINT*,1--TV, 2--SV,3--CV1, 4--CV2 
	public final static int OWSP_ACTION_TV_TUNER = 129;		//�л�Ƶ��, ��Ϣ����ΪINT*, ΪƵ����
	public final static int OWSP_ACTION_TV_SET_QUALITY  = 130;		//��������, ����,ɫ��,���Ͷ�,�ԱȶȽṹ��
}
