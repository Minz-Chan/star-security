package com.starsecurity.model;

/***
 * 
 * 云台控制码,取值范围为0~255
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class OWSP_ACTIONCode {
	public final static int OWSP_ACTION_MD_STOP = 0;            // 停止运动
	public final static int OWSP_ACTION_ZOOMReduce=5;
	public final static int OWSP_ACTION_ZOOMADD=6;
	public final static int OWSP_ACTION_FOCUSADD=7;   //焦距
	public final static int OWSP_ACTION_FOCUSReduce=8;
	public final static int OWSP_ACTION_MD_UP=9;                // 向上
	public final static int OWSP_ACTION_MD_DOWN=10;             // 向下
	public final static int OWSP_ACTION_MD_LEFT=11;             // 向左
	public final static int OWSP_ACTION_MD_RIGHT=12;            // 向右
	public final static int OWSP_ACTION_Circle_Add = 13;  		//光圈
	public final static int OWSP_ACTION_Circle_Reduce = 14;     //
	public final static int OWSP_ACTION_AUTO_CRUISE = 15;		//自动巡航
	public final static int OWSP_ACTION_GOTO_PRESET_POSITION = 16; 	//跳转预置位
	public final static int OWSP_ACTION_SET_PRESET_POSITION = 17;	//设置预置位点
	public final static int OWSP_ACTION_CLEAR_PRESET_POSITION = 18; //清除预置位点
	public final static int OWSP_ACTION_ACTION_RESET = 20;
	public final static int OWSP_ACTION_TV_SWITCH = 128;	//切换视频源,消息参数为INT*,1--TV, 2--SV,3--CV1, 4--CV2 
	public final static int OWSP_ACTION_TV_TUNER = 129;		//切换频道, 消息参数为INT*, 为频道号
	public final static int OWSP_ACTION_TV_SET_QUALITY  = 130;		//画质设置, 亮度,色度,饱和度,对比度结构体
}
