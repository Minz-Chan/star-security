package com.starsecurity.util;

import com.starsecurity.h264.H264Decoder;

public class H264DecodeUtil {
	H264Decoder decoder = new H264Decoder();
    //byte [] mPixel = new byte[320*288*2];
    //ByteBuffer buffer = ByteBuffer.wrap( mPixel );          
	int mTrans = 0x0F0F0F0F;

	byte [] NalBuf = new byte[65536]; // 64k
	int NalBufUsed = 0;
	
	boolean bFirst = true;
	boolean bFindPPS = true;
	
	public H264DecodeUtil() {
		super();
		
		/*
		int i = mPixel.length;
    	
        for(i=0; i<mPixel.length; i++) {
        	mPixel[i]=(byte)0x00;
        }*/
	}
	
	public int init(int width, int height) {
		return decoder.init(width, height);  
	}
	
	public int uninit() {
		return decoder.uninit();
	}

	public int decodePacket(byte[] packet, int len, byte[] byteBitmap)   
    {   
		int result = 0;
    	int iTemp = 0;
    	int nalLen;
    	
    	
    	
    	int bytesRead = len;    	
    	
    	int SockBufUsed = 0;
    	
    	if(bytesRead < 0) {
    		return result;
    	}
        
    	
    	byte [] SockBuf = packet;
    	

            
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
					iTemp = decoder.decode(NalBuf, NalBufUsed-4, byteBitmap);   
				
		            if(iTemp > 0) {
		            	result = 1;
		            	//postInvalidate();  // 使用postInvalidate可以直接在线程中更新界面
		            }
				}

				NalBuf[0]=0;
				NalBuf[1]=0;
				NalBuf[2]=0;
				NalBuf[3]=1;
				
				NalBufUsed=4;
			}		
		} 

        return result;
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
}
