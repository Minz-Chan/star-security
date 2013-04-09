package com.starsecurity.h264;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.View;

public class VideoView extends View {
	byte [] mPixel = new byte[320*288*2];
    ByteBuffer buffer;
	Bitmap VideoBit;           

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
        if(VideoBit != null) {
        	VideoBit.copyPixelsFromBuffer(buffer);
            canvas.drawBitmap(VideoBit, 0, 0, null); 
        }
    }
    
    public void init() {
    	
    	buffer = ByteBuffer.wrap( mPixel );
    	VideoBit = Bitmap.createBitmap(320, 288, Config.RGB_565);
    	
    	int i = mPixel.length;
    	
        for(i=0; i<mPixel.length; i++) {
        	mPixel[i]=(byte)0x00;
        }
    }

	public byte[] getmPixel() {
		return mPixel;
	}

    
}
