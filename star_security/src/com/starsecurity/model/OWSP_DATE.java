package com.starsecurity.model;

/* 日期定义 */
public class OWSP_DATE {
	private int m_year;			//年,2009
	private short m_month;		//月,1-12
	private short m_day;			//日,1-31
	public int getM_year() {
		return m_year;
	}
	public void setM_year(int m_year) {
		this.m_year = m_year;
	}
	public short getM_month() {
		return m_month;
	}
	public void setM_month(short m_month) {
		this.m_month = m_month;
	}
	public short getM_day() {
		return m_day;
	}
	public void setM_day(short m_day) {
		this.m_day = m_day;
	}
}
