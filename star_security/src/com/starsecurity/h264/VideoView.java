/*
 * FileName:VideoView.java
 * 
 * Package:com.starsecurity.h264
 * 
 * Date:2013-04-12
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.h264;

import java.nio.ByteBuffer;

import com.starsecurity.component.ViewManager;
import com.starsecurity.util.BitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @function     功能	  	视频图像显示
 *     本类用于对解码后的视频数据进行显示，其中视频帧的宽度(Width)
 * 和高度(Height)可自定义。该类使用前须先调用初始化操作。
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-12
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-10
 * @description 修改说明	  
 *   2013-05-10 加入旋转、拉伸，对全屏模式进行支持 陈明珍
 *   2013-04-16 使图像可适应容器的Width和Height 陈明珍
 */
public class VideoView extends ImageView {
	private static int width = 352;
	private static int height = 288;
	
	private static byte [] mPixel = new byte[width * height * 2];
    private ByteBuffer buffer;
	private Bitmap VideoBit;     
	
	
	private boolean isFullScreenMode = false;

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
        	/* 此处rewind用意
         	 * 4.2中对copyPixelsFromBuffer( )执行的缓冲区进行了调整，每次拷贝结束后，将下次拷贝
        	 * 的起始位置置为前一次拷贝结束时的位置。这样，如果对同一个ByteBuffer执行多次连续拷贝，
        	 * 就要注意每次起始位置。我看了自己的代码，果然这个错误是对一个ByteBuffer进行连续二次
        	 * 拷贝时，第二次的起始位置没有重置为0导致的。
        	 */
        	buffer.rewind();	
        	
        	if ((VideoBit.getWidth() * VideoBit.getHeight() * 2) 
        			!= (buffer.position() + buffer.remaining())) {
        		return;
        	}
        	
        	VideoBit.copyPixelsFromBuffer(buffer);	
        	
        	if (isFullScreenMode) {
        		
        		
        		if (!ViewManager.getInstance().isWideScreen()) {
        			Matrix m = new Matrix();
        			m.setRotate(90);
        			
        			// 旋转
                    Bitmap tmp = Bitmap.createBitmap(VideoBit, 0, 0, VideoBit.getWidth(), VideoBit.getHeight(),
                    		m, true);
            		
                    canvas.drawBitmap(Bitmap.createScaledBitmap(tmp, getWidth(), getHeight(), true)
                    		, 0, 0, null); ; 
        		} else {
        			canvas.drawBitmap(Bitmap.createScaledBitmap(VideoBit, getWidth(), getHeight(), true)
                    		, 0, 0, null);
        		}
                
        		
                
        	} else {
        		canvas.drawBitmap(Bitmap.createScaledBitmap(VideoBit, getWidth(), getHeight(), true)
                		, 0, 0, null); 
        	}

        }
    }
    
    /**
     * VideoView初始化
     */
    public void init() {
    	buffer = ByteBuffer.wrap(mPixel);
    	VideoBit = Bitmap.createBitmap(width, height, Config.RGB_565);
    	
    	
    	/*int i = mPixel.length;
    	
        for (i = 0; i < mPixel.length; i++) {
        	mPixel[i] = (byte)0x00;
        }*/
        
    	if (ViewManager.getInstance().isCorridorMode()) {
    		setScaleType(ImageView.ScaleType.FIT_CENTER);
    	} else {
    		setScaleType(ImageView.ScaleType.FIT_XY);
    	}
        
    }

	public byte[] getmPixel() {
		return mPixel;
	}

	public boolean isFullScreenMode() {
		return isFullScreenMode;
	}

	public void setFullScreenMode(boolean isFullScreenMode) {
		this.isFullScreenMode = isFullScreenMode;
	}

	public static void changeScreenRevolution(int width1, int height1) {
		mPixel = null;

		width = width1;
		height = height1;
		mPixel = new byte[width * height * 2];
		
		/*
		int i = mPixel.length;
		for (i = 0; i < mPixel.length; i++) {
        	mPixel[i] = (byte)0x00;
        }*/
		
	}

	public int getWidth1() {
		return width;
	}

	public int getHeight1() {
		return height;
	}

}
