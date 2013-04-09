package com.tvt.p2pplayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BMPImage {
	  private static final int BITMAPFILEHEADER_SIZE = 14;
	  private static final int BITMAPINFOHEADER_SIZE = 40;
	  
	  private static int biHeight;
	  private static int biWidth = 320;
	  static byte[] bmp_header;
	  ByteBuffer bmpBuffer = null;

	  static
	  {
	    biHeight = 240;
	  }

	  public BMPImage(byte[] bBmpData)
	  {
	    int i = biWidth;
	    int j = biHeight;
	    int k = i * j * 3;
	    int l = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE + 12;

	    this.bmpBuffer = ByteBuffer.allocate(k + l);	// allocate byte buffer size
	    this.bmpBuffer.put(bmp_header);					// put bmp header
	    this.bmpBuffer.put(bBmpData);					// put bmp data
	  }

	  /**
	   * 初始化位图文件头相关信息
	   * @param width 图像宽度
	   * @param height 图像高度
	   */
	  public static void Init(int width, int height)
	  {
	    biWidth = width;
	    biHeight = height;
	    int i = width * height * 3;
	    int j = width * height * 3;
	    int file_size = j + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE + 12;
	    int offset = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE + 12;				// data offset
	    ByteArrayOutputStream byteArrOutStream = new ByteArrayOutputStream();
	    DataOutputStream bmpHeaderWriter = new DataOutputStream(byteArrOutStream);
	    j = 2;
	    try
	    {

			bmpHeaderWriter.write(new byte[]{66, 77});		// Identifier, 2 bytes
			bmpHeaderWriter.write(intToDWord(file_size));	// File Size, 1 dword
			bmpHeaderWriter.write(intToWord(0));			// Reserved, 1 dword
			bmpHeaderWriter.write(intToWord(0));
			bmpHeaderWriter.write(intToDWord(offset)); 		// Bitmap Data Offset, 1 dword
			bmpHeaderWriter.write(intToDWord(40));			// Bitmap Header Size, 1 dword
			bmpHeaderWriter.write(intToDWord(width));		// Width, 1 dword
			bmpHeaderWriter.write(intToDWord(height));		// Height, 1 dword
			bmpHeaderWriter.write(intToWord(1)); 			// Planes, 1 word 
			bmpHeaderWriter.write(intToWord(16)); 			// Bits Per Pixel, 1 word
			bmpHeaderWriter.write(intToDWord(3));			// Compression, 1 dword
			bmpHeaderWriter.write(intToDWord(i));			// Bitmap Data Size
			bmpHeaderWriter.write(intToDWord(0));			// HResolution, 1 dword
			bmpHeaderWriter.write(intToDWord(0));			// VResolution, 1 dword
			bmpHeaderWriter.write(intToDWord(0));			// Colors, 1 dword
			bmpHeaderWriter.write(intToDWord(0));			// Important Colors, 1 dword
			bmpHeaderWriter.write(intToDWord(63488));		// Palette, N * 4 bytes
			bmpHeaderWriter.write(intToDWord(2016));
			bmpHeaderWriter.write(intToDWord(31));
	      
			bmpHeaderWriter.close();
			byteArrOutStream.close();
			bmp_header = byteArrOutStream.toByteArray();
			return;
	    }
	    catch (IOException e)
	    {
	    	e.printStackTrace();
	    }
	  }

	  private static byte[] intToDWord(int paramInt)
	  {
	    byte[] arrayOfByte = new byte[4];
	    arrayOfByte[0] = (byte)(paramInt & 0xFF);
	    arrayOfByte[1] = (byte)(paramInt >> 8 & 0xFF);
	    arrayOfByte[2] = (byte)(paramInt >> 16 & 0xFF);
	    arrayOfByte[3] = (byte)(paramInt >> 24 & 0xFF);
	    return arrayOfByte;
	  }

	  private static byte[] intToWord(int paramInt)
	  {
	    byte[] arrayOfByte = new byte[2];
	    arrayOfByte[0] = (byte)(paramInt & 0xFF);
	    arrayOfByte[1] = (byte)(paramInt >> 8 & 0xFF);
	    return arrayOfByte;
	  }

	  public void clear()
	  {
	    this.bmpBuffer.clear();
	  }

	  public byte[] getByte()
	  {
	    return this.bmpBuffer.array();
	  }
}
