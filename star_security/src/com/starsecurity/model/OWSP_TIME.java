package com.starsecurity.model;

/* 时间定义 */
public class OWSP_TIME {
	private short m_hour;			//0-24
	private short m_minute;		//0-59
	private short m_second;		//0-59
	private int m_microsecond;	//毫秒	0-1000
	public short getM_hour() {
		return m_hour;
	}
	public void setM_hour(short m_hour) {
		this.m_hour = m_hour;
	}
	public short getM_minute() {
		return m_minute;
	}
	public void setM_minute(short m_minute) {
		this.m_minute = m_minute;
	}
	public short getM_second() {
		return m_second;
	}
	public void setM_second(short m_second) {
		this.m_second = m_second;
	}
	public int getM_microsecond() {
		return m_microsecond;
	}
	public void setM_microsecond(int m_microsecond) {
		this.m_microsecond = m_microsecond;
	}
}
