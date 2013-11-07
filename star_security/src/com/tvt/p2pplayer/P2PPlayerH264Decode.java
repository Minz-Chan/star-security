package com.tvt.p2pplayer;

public class P2PPlayerH264Decode {
	private long H264DecodeHandle = 0L;

	  static
	  {
	    System.loadLibrary("P2PPlayerH264Decode");
	  }

	  private static native byte[] DecodeOneFrame(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);

	  private static native void Destroy(long paramLong);

	  private static native int GetDecodeResult(long paramLong);

	  private static native long Initialize();

	  public void Cleanup()
	  {
	    Destroy(this.H264DecodeHandle);
	  }
	  
	  /**
	   * 解码
	   * @param videoArrayOfByte 视频流字节数组
	   * @param length  视频流字节数组的长度
	   * @param width 宽度 
	   * @param height 高度
	   * @return
	   */
	  public byte[] DecodeOneFrame(byte[] videoArrayOfByte, int length, int width, int height)
	  {
		/*  
	    long l = this.H264DecodeHandle;
	    byte[] arrayOfByte = paramArrayOfByte;
	    int i = length;
	    int j = width;
	    int k = height;*/
	    return DecodeOneFrame(this.H264DecodeHandle, videoArrayOfByte, length, width, height);
	  }

	  /**
	   * 返回已成功解码的数据长度
	   * @return 
	   */
	  public int GetDecodeResult()
	  {
	    return GetDecodeResult(this.H264DecodeHandle);
	  }

	  
	  public int InitDecode()
	  {
		/*
	    long l1 = Initialize();
	    Object localObject2;
	    this.H264DecodeHandle = localObject2;
	    long l2 = this.H264DecodeHandle < 0L;
	    Object localObject1;
	    if (l1 == 0)
	      localObject1 = null;
	    while (true)
	    {
	      return localObject1;
	      int i = 1;
	    }*/

			this.H264DecodeHandle = Initialize();
			
			long i = this.H264DecodeHandle;
			if(i != 0) {
			    return 1;
			} else {
			    return 0;
			}
	  }
}
