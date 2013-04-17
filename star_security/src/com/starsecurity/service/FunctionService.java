package com.starsecurity.service;

import android.graphics.Bitmap;

public interface FunctionService {

	int palyVideo();
	int stopVideo();
	Bitmap takePicture();
	void switchToFullscreen();
	void switchToPrimitive();
}
