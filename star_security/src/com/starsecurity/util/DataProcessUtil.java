package com.starsecurity.util;

public class DataProcessUtil {
	public static void fillPacket(byte[] des, byte[] src, int offset, int des_len) {
		System.arraycopy(src, 0, des, offset, des_len);
	}
}
