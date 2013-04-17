package com.starsecurity.util;

import com.starsecurity.h264.H264Decoder;

public class H264DecodeUtil {
	H264Decoder decoder = new H264Decoder();       
	byte [] NalBuf = new byte[65536]; // 64k
	int NalBufUsed = 0;
	boolean bFirst = true;
	boolean bFindPPS = true;
	int mTrans = 0x0F0F0F0F;
	
	public H264DecodeUtil() {
		super();
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
    	
		while (bytesRead-SockBufUsed>0) {
			nalLen = MergeBuffer(NalBuf, NalBufUsed, SockBuf, SockBufUsed, bytesRead-SockBufUsed);
					
			NalBufUsed += nalLen;
			SockBufUsed += nalLen;
			
			/* decode process while NalBuf contains a complete NAL unit */
			while (mTrans == 1) {	
				mTrans = 0xFFFFFFFF;	

				if (bFirst == true) {	// the first start flag, pass
					bFirst = false;
				} else {				// a complete NAL data, include 0x00000001 trail
					if (bFindPPS == true) { // picture parameter set
						if ((NalBuf[4] & 0x1F) == 7) { // if pps
							bFindPPS = false;
						} else {				// if NAL unit sequence is not 'sps, pps, ...', reread from buffer
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
					iTemp = decoder.decode(NalBuf, NalBufUsed - 4, byteBitmap);   
				
		            if(iTemp > 0) {
		            	result = 1;
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
     * Merge SockBuf into NalBuf, return the offset when trail(start code, 0x00000001) is found
     * @param NalBuf buffer array for decoding
     * @param NalBufUsed length of NalBuf data read
     * @param SockBuf buffer array read from file
     * @param SockBufUsed length of SockBuf data read
     * @param SockRemain length of data unread(remain)
     * @return position of trail(0x00000001)
     */
    int MergeBuffer(byte[] NalBuf, int NalBufUsed, byte[] SockBuf, int SockBufUsed, int SockRemain) {
    	int  i = 0;
    	byte Temp;

    	for (i = 0; i < SockRemain; i++) {
    		Temp = SockBuf[i+SockBufUsed];
    		NalBuf[i+NalBufUsed] = Temp;

    		mTrans <<= 8;
    		mTrans  |= Temp;

    		if (mTrans == 1) { // 0x00000001(start code) is found
    			i++;
    			break;
    		}	
    	}

    	return i;
    }
}
