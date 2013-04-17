package com.starsecurity.service;


public interface ControlService {
	
	public int playVideo();
	
	public int stopVideo();
	
	public int move(String direction);
	
	public int zoomInOrOut(boolean flag);
	
	public int adjustFocus(boolean flag);
	
	public int adjustAperture(boolean flag);
	
	public int switchChannel(int channel_no);
	
}
