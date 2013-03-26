package com.starsecurity.model;

//����������
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
