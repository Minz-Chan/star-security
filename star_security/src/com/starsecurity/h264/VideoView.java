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

public class VideoView extends View  implements Runnable {
	Bitmap  mBitQQ  = null;   
    Paint   mPaint = null;   
    Bitmap  mSCBitmap = null;   
    byte [] mPixel = new byte[320*288*2];
    ByteBuffer buffer = ByteBuffer.wrap( mPixel );
	Bitmap VideoBit = Bitmap.createBitmap(320, 288, Config.RGB_565);           
	int mTrans = 0x0F0F0F0F;
	String PathFileName; 
	
	PipedInputStream pipedInStream = new PipedInputStream();
	

    public VideoView(Context context) {
        super(context);
        setFocusable(true);
        
       	int i = mPixel.length;
    	
        for(i=0; i<mPixel.length; i++)
        {
        	mPixel[i]=(byte)0x00;
        }
    }
    
    
           
    public VideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}



	public VideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	public PipedInputStream getInputStream() {
		return pipedInStream;
	}

	public void PlayVideo(String file)
    {
    	PathFileName = file;   
    	new Thread(this).start();
    }
    
    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);   

        VideoBit.copyPixelsFromBuffer(buffer);
        canvas.drawBitmap(VideoBit, 0, 0, null); 	
    }
    
    /**
     * 将从文件读出来的缓冲数组(SockBuf)合并进解码缓存数组(NalBuf)，当找到一个开始字时，停止读取返回偏移
     * @param NalBuf 解码缓存数组
     * @param NalBufUsed 解码缓存数组已读取的数据
     * @param SockBuf 读取的文件缓冲数组
     * @param SockBufUsed 文件缓冲数组已读取的数据
     * @param SockRemain 文件缓冲数组还剩余的数据
     * @return 开始字的偏移
     */
    int MergeBuffer(byte[] NalBuf, int NalBufUsed, byte[] SockBuf, int SockBufUsed, int SockRemain)
    {
    	int  i=0;
    	byte Temp;

    	for(i=0; i<SockRemain; i++)
    	{
    		Temp = SockBuf[i+SockBufUsed];
    		NalBuf[i+NalBufUsed]=Temp;

    		mTrans <<= 8;
    		mTrans  |= Temp;

    		if(mTrans == 1) // 找到一个开始字 0x00000001
    		{
    			i++;
    			break;
    		}	
    	}

    	return i;
    }
    
    public void run()   
    {   
    	if(pipedInStream == null) {
    		return;
    	}
    	
    	H264Decoder decoder = new H264Decoder();
    	InputStream is = null;
    	FileInputStream fileIS = null;
    	
    	int iTemp = 0;
    	int nalLen;
    	
    	boolean bFirst=true;
    	boolean bFindPPS=true;
    	
    	int bytesRead=0;    	
    	int NalBufUsed=0;
    	int SockBufUsed=0;
        
    	byte [] NalBuf = new byte[65536]; // 40k
    	byte [] SockBuf = new byte[2048];
    	
    	decoder.init(320, 288);  
   	
    	/*
    	try
    	{    
   			fileIS = new FileInputStream(PathFileName);	
    	}
    	catch(IOException e) {} */
 	
        while (!Thread.currentThread().isInterrupted())   
        {   
        	//while(bytesRead == 0) {
        		 try  
                 {   
               		bytesRead = pipedInStream.read(SockBuf, 0, 2048);
               		//if(bytesRead == 0) {
					//	Thread.currentThread().sleep(1);
               		//}
                 }
                 catch (IOException e) {
                 	e.printStackTrace();
                 } 
        		 //catch (InterruptedException e) {
//						e.printStackTrace();
//				} 
        	//}
           
            
            //if(bytesRead<=0)
            //	break;
            
            SockBufUsed = 0;
            
    		while(bytesRead-SockBufUsed>0)
    		{
    			nalLen = MergeBuffer(NalBuf, NalBufUsed, SockBuf, SockBufUsed, bytesRead-SockBufUsed);
    					
    			NalBufUsed += nalLen;
    			SockBufUsed += nalLen;
    			
    			/* decode process while NalBuf contains a complete NAL unit */
    			while(mTrans == 1)	
    			{
    				mTrans = 0xFFFFFFFF;	

    				if(bFirst == true) // the first start flag, pass
    				{
    					bFirst = false;
    				}
    				else  			// a complete NAL data, include 0x00000001 trail.
    				{
    					if(bFindPPS == true) // picture parameter set
    					{
    						if((NalBuf[4] & 0x1F) == 7) // if pps
    						{
    							bFindPPS = false;
    						}
    						else				// if NAL unit sequence is not 'sps, pps, ...', reread from buffer
    						{
    			   				NalBuf[0]=0;
    		    				NalBuf[1]=0;
    		    				NalBuf[2]=0;
    		    				NalBuf[3]=1;
    		    				
    		    				NalBufUsed=4;
    		    				
    							break;
    						}
    					}
    					// decode nal unit when there exists a complete NAL unit in NalBuf
    					// the second parameter is the length of NAL unit
    					iTemp = decoder.decode(NalBuf, NalBufUsed-4, mPixel);   
					
    		            if(iTemp > 0)
    		            	postInvalidate();  // 使用postInvalidate可以直接在线程中更新界面 
    				}

    				NalBuf[0]=0;
    				NalBuf[1]=0;
    				NalBuf[2]=0;
    				NalBuf[3]=1;
    				
    				NalBufUsed=4;
    			}		
    		} 
        } 
        try{        	
	        if(fileIS!=null)
	        	fileIS.close();		
	        if(is!=null)
	        	is.close();
        }
	    catch (IOException e) {
	    	e.printStackTrace();
        }
        decoder.uninit();
    }  
}
