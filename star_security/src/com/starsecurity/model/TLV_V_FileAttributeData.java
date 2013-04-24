package com.starsecurity.model;

/***
 * 
 * For vod streaming  only Keyframe 
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
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
