/*
 * FileName:H264Decoder.java
 * 
 * Package:com.starsecurity.h264
 * 
 * Date:2013-04-12
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.h264;

/**
 * @function     功能	  	解码h264码流
 *     本类用于对接收到的h264码流进行解码，解码前须调用init函数对解码器进
 * 行初始化操作；结束解码操作后，须使用uninit函数回收解码器资源。对于本解码
 * 库，其最小解码单元为一个NAL单元。
 * @author       创建人              陈明珍
 * @date        创建日期           2013-04-12
 * @author       修改人              陈明珍
 * @date        修改日期           2013-04-12
 * @description 修改说明	            首次增加
 */
public class H264Decoder {
	
	static {
        System.loadLibrary("H264Decoder");
    }

	private static native int InitDecoder(int width, int height);
    private static native int UninitDecoder(); 
    private static native int DecoderNal(byte[] in, int insize, byte[] out);
    
    /**
     * 解码器初始化
     * @param width	视频宽度
     * @param height 视频高度
     * @return 返回1说明初始化成功，否则表示失败
     */
    public int init(int width, int height) {
    	return InitDecoder(width, height);
    }
    
    /**
     * 解码器资源回收
     * @return 返回1说明资源回收成功，否则表示失败
     */
    public int uninit() {
    	return UninitDecoder();
    }
    
    /**
     * h264解码，以一个NAL单元为单位
     * @param in 包含一个NAL单元的字节数组(以0x00000001开头)
     * @param insize 字节数组长度
     * @param out 接收解码完后数据的缓冲区
     * @return 解码的字节数
     */
    public int decode(byte[] in, int insize, byte[] out) {
    	return DecoderNal(in, insize, out);
    }
}
