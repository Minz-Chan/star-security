package com.starsecurity.h264;

import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class VideoView extends ImageView {
	private int width = 352;
	private int height = 288;
	
	private byte [] mPixel = new byte[width * height * 2];
    private ByteBuffer buffer;
	private Bitmap VideoBit;     
	

    public VideoView(Context context) {
        super(context);
        setFocusable(true);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
    
	public VideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
    
    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);   
        if (VideoBit != null) {
        	VideoBit.copyPixelsFromBuffer(buffer);	
            canvas.drawBitmap(Bitmap.createScaledBitmap(VideoBit, getWidth(), getHeight(), true)
            		, 0, 0, null); 
        }
    }
    
    public void init() {
    	
    	buffer = ByteBuffer.wrap(mPixel);
    	VideoBit = Bitmap.createBitmap(width, height, Config.RGB_565);
    	
    	int i = mPixel.length;
    	
        for (i = 0; i < mPixel.length; i++) {
        	mPixel[i] = (byte)0x00;
        }
        
        setScaleType(ImageView.ScaleType.FIT_XY);
    }

	public byte[] getmPixel() {
		return mPixel;
	}

    
}
