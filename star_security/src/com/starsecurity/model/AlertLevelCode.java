package com.starsecurity.model;

//报警级别，主要针对帧预测，开光量探头报警时该值统一为0，
//目前只支持探头报警，当触发报警时，发送报警级别ALC_ALERT，当报警停止时，发送ALC_STOP
public class AlertLevelCode {
	public final static int ALC_ALERT = 0;//报警，警报级别最高，通常用户开关量探头
	public final static int ALC_LEVEL1 = 10;//1级警告，AlertLevelCode的值越大，警告级别越低
	public final static int ALC_STOP = 255;//报警停止，发送停止信息
}
