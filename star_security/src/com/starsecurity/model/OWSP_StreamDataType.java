package com.starsecurity.model;

/***
 * 
 * 流数据类型
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class OWSP_StreamDataType {
	private int OWSP_SDT_VIDEO_ONLY	= 0;
	private int OWSP_SDT_AUDIO_ONLY	= 1;
	private int OWSP_SDT_VIDEO_AUDIO = 2;
	public int getOWSP_SDT_VIDEO_ONLY() {
		return OWSP_SDT_VIDEO_ONLY;
	}
	public void setOWSP_SDT_VIDEO_ONLY(int oWSP_SDT_VIDEO_ONLY) {
		OWSP_SDT_VIDEO_ONLY = oWSP_SDT_VIDEO_ONLY;
	}
	public int getOWSP_SDT_AUDIO_ONLY() {
		return OWSP_SDT_AUDIO_ONLY;
	}
	public void setOWSP_SDT_AUDIO_ONLY(int oWSP_SDT_AUDIO_ONLY) {
		OWSP_SDT_AUDIO_ONLY = oWSP_SDT_AUDIO_ONLY;
	}
	public int getOWSP_SDT_VIDEO_AUDIO() {
		return OWSP_SDT_VIDEO_AUDIO;
	}
	public void setOWSP_SDT_VIDEO_AUDIO(int oWSP_SDT_VIDEO_AUDIO) {
		OWSP_SDT_VIDEO_AUDIO = oWSP_SDT_VIDEO_AUDIO;
	}
}
