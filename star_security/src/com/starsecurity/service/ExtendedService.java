package com.starsecurity.service;

import android.graphics.Bitmap;

public interface ExtendedService {
	public Bitmap takePicture();
	public void switchToFullScreen();
	public void restoreFromFullScreen();
	public void getNextChannelList();
	public void saveCurrentConfig();
}
