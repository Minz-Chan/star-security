package com.starsecurity.model;

//////////////////////////////////////////////////////////////////////////
//For vod streaming  only Keyframe 
//////////////////////////////////////////////////////////////////////////
public class TLV_V_FileAttributeData {
	private long totalframes;
	private long totaltimes;
	public long getTotalframes() {
		return totalframes;
	}
	public void setTotalframes(long totalframes) {
		this.totalframes = totalframes;
	}
	public long getTotaltimes() {
		return totaltimes;
	}
	public void setTotaltimes(long totaltimes) {
		this.totaltimes = totaltimes;
	}
}
