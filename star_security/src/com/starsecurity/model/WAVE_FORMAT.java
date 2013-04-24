package com.starsecurity.model;

/***
 * 
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */	
public class WAVE_FORMAT {
	public final static int WAVE_FORMAT_PCM	= 0x0001;
	public final static int WAVE_FORMAT_MPEGLAYER3 = 0x0055;	// ISO/MPEG Layer 3 ��ʽ���                                                                                
	public final static int WAVE_FORMAT_QUALCOMM_PUREVOICE = 0x0150;                                                                               
	//AMR_NB CBR wave format                                   
	public final static int WAVE_FORMAT_AMR_CBR = 0x7A21;                                                                                
	//AMR VBR Not support yet                                 
	public final static int WAVE_FORMAT_AMR_VBR = 0x7A22;                                                                                
	//AMR_WB Wave format                        
	public final static int WAVE_FORMAT_VOICEAGE_AMR_WB	= 0xA104;                                            
	public final static int CODEC_H264 = 0x34363248;	//H264
}
