package com.starsecurity.component;

import java.util.Date;

import com.starsecurity.util.ConfigSingleton;

public class Timecounter {
	private static Timecounter m_instance = null;
	
	private long base_time;
	private long pre_time;
	
	synchronized public static Timecounter getInstance() {
		if(m_instance == null) {
			m_instance = new Timecounter();
		}
		
		return m_instance;
	}
	
	
	public void setBaseTime() {
		Date d = new Date();
		base_time = d.getTime();
		pre_time = base_time;
	}
	
	public long getTimeDiff() {
		Date d = new Date();
		long curr = d.getTime();
		long diff = curr - pre_time;
		pre_time = curr;
		return diff;
	}
	
}
