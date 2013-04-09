package com.starsecurity.h264;

public class H264Decoder {
	
	static {
        System.loadLibrary("H264Decoder");
    }

	private static native int InitDecoder(int width, int height);
    private static native int UninitDecoder(); 
    private static native int DecoderNal(byte[] in, int insize, byte[] out);
    
    public int init(int width, int height) {
    	return InitDecoder(width, height);
    }
    
    public int uninit() {
    	return UninitDecoder();
    }
    
    public int decode(byte[] in, int insize, byte[] out) {
    	return DecoderNal(in, insize, out);
    }
}
