/*
 * FileName:BByteConvert.java
 * 
 * Package:com.starsecurity
 * 
 * Date:2013-03-19
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.util;

/**
 * @function     ����	  byte����������������ͼ�Ļ�ת
 *     ������Ŀ��Ҫ�� java �� c/c++ ֮����� socket ͨ�ţ� socket ͨ�������ֽ��������ֽڰ�
 * ���еģ� socket���ͷ��뽫����ת��Ϊ�ֽ��������ֽڰ��������շ����ֽ������ֽڰ���ת����
 * ��Ӧ���������͡�������ͷ��ͽ��շ�����ͬ�����ԣ���һ��ֻ�漰���ֽ���ĵ����������� java
 * �� c/c++��ͨ�ţ��������Ҫ����һЩ����Ҫ����Ϊ java ��û�� unsigned ���ͣ����� java��c��
 * ĳЩ���������ϵĳ��Ȳ�һ�¡�������������������������� java �������ͺ������ֽ���(
 * Little-Endian)���ֽڰ� ( �൱�� java �� byte ���� ) ֮��ת��������
 * @author       ������                 �����
 * @date        ��������           2013-03-19
 * @author       �޸���                 �����
 * @date        �޸�����           2013-03-19
 * @description �޸�˵��	             �״�����
 */
public class LByteConvert {
	
	/** 
     * ������תbyte���� 
     *  
     * @param n ��������
     * @return ת�����byte���� 
     */  
    public static byte[] longToBytes(long n) {  
        byte[] b = new byte[8];  
        b[0] = (byte) (n & 0xff);  
        b[1] = (byte) (n >> 8 & 0xff);  
        b[2] = (byte) (n >> 16 & 0xff);  
        b[3] = (byte) (n >> 24 & 0xff);  
        b[4] = (byte) (n >> 32 & 0xff);  
        b[5] = (byte) (n >> 40 & 0xff);  
        b[6] = (byte) (n >> 48 & 0xff);  
        b[7] = (byte) (n >> 56 & 0xff);  
        return b;  
    }  
  
    /** 
     * ������תbyte���� 
     *  
     * @param n ���������� 
     * @param array ת�����byte���� 
     * @param offset �ӵ�offsetλ��ʼת�� 
     */  
    public static void longToBytes(long n, byte[] array, int offset) {  
        array[0 + offset] = (byte) (n & 0xff);  
        array[1 + offset] = (byte) (n >> 8 & 0xff);  
        array[2 + offset] = (byte) (n >> 16 & 0xff);  
        array[3 + offset] = (byte) (n >> 24 & 0xff);  
        array[4 + offset] = (byte) (n >> 32 & 0xff);  
        array[5 + offset] = (byte) (n >> 40 & 0xff);  
        array[6 + offset] = (byte) (n >> 48 & 0xff);  
        array[7 + offset] = (byte) (n >> 56 & 0xff);  
    }  
  
    /** 
     * byte����ת������ 
     *  
     * @param array Ҫת����byte����
     * @return ת����ĳ���������
     */  
    public static long bytesToLong(byte[] array) {  
        return ((((long) array[7] & 0xff) << 56) | (((long) array[6] & 0xff) << 48) | (((long) array[5] & 0xff) << 40)  
                | (((long) array[4] & 0xff) << 32) | (((long) array[3] & 0xff) << 24)  
                | (((long) array[2] & 0xff) << 16) | (((long) array[1] & 0xff) << 8) | (((long) array[0] & 0xff) << 0));  
    }  
  
    /** 
     * byte����ת���������� 
     *  
     * @param array Ҫת����byte���� 
     * @param offset �ӵ�offset��ʼת�� 
     * @return ת����ĳ��������� 
     */  
    public static long bytesToLong(byte[] array, int offset) {  
        return ((((long) array[offset + 7] & 0xff) << 56) | (((long) array[offset + 6] & 0xff) << 48)  
                | (((long) array[offset + 5] & 0xff) << 40) | (((long) array[offset + 4] & 0xff) << 32)  
                | (((long) array[offset + 3] & 0xff) << 24) | (((long) array[offset + 2] & 0xff) << 16)  
                | (((long) array[offset + 1] & 0xff) << 8) | (((long) array[offset + 0] & 0xff) << 0));  
    }  
  
    /**
     * ����תbyte����
     * 
     * @param n Ҫת����������
     * @return ת�����byte����
     */
    public static byte[] intToBytes(int n) {  
        byte[] b = new byte[4];  
        b[0] = (byte) (n & 0xff);  
        b[1] = (byte) (n >> 8 & 0xff);  
        b[2] = (byte) (n >> 16 & 0xff);  
        b[3] = (byte) (n >> 24 & 0xff);  
        return b;  
    }  
  
    /**
     * ����תbyte����
     * 
     * @param n Ҫת����������
     * @param array ת�����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     */
    public static void intToBytes(int n, byte[] array, int offset) {  
        array[0 + offset] = (byte) (n & 0xff);  
        array[1 + offset] = (byte) (n >> 8 & 0xff);  
        array[2 + offset] = (byte) (n >> 16 & 0xff);  
        array[4 + offset] = (byte) (n >> 24 & 0xff);  
    }  
  
    /** 
     * byte����ת����
     * 
     * @param b Ҫת����byte����
     * @return ת���������
     */  
    public static int bytesToInt(byte b[]) {  
        return b[0] & 0xff | (b[1] & 0xff) << 8 | (b[2] & 0xff) << 16 | (b[3] & 0xff) << 24;  
    }  
  
    /** 
     * byte����תint 
     *  
     * @param b Ҫת����byte���� 
     * @param offset ������ĵڼ�λ��ʼת 
     * @return ת����������� 
     */  
    public static int bytesToInt(byte b[], int offset) {  
        return b[offset + 0] & 0xff | (b[offset + 1] & 0xff) << 8 | (b[offset + 2] & 0xff) << 16  
                | (b[offset + 3] & 0xff) << 24;  
    }  
  
    /** 
     * �޷�������תbyte���� 
     *  
     * @param n Ҫת�������� 
     * @return ת�����byte���� 
     */  
    public static byte[] uintToBytes(long n) {  
        byte[] b = new byte[4];  
        b[0] = (byte) (n & 0xff);  
        b[1] = (byte) (n >> 8 & 0xff);  
        b[2] = (byte) (n >> 16 & 0xff);  
        b[3] = (byte) (n >> 24 & 0xff);  
  
        return b;  
    }  
  

    /**
     * �޷�������תbyte����
     * 
     * @param n Ҫת���ĳ�������
     * @param array ת�����byte����
     * @param offset �ӵ�offsetλ��ʼת�� 
     */
    public static void uintToBytes(long n, byte[] array, int offset) {  
        array[0 + offset] = (byte) (n);  
        array[1 + offset] = (byte) (n >> 8 & 0xff);  
        array[2 + offset] = (byte) (n >> 16 & 0xff);  
        array[3 + offset] = (byte) (n >> 24 & 0xff);  
    }  
  
    /**
     * byte�������޷�������
     * 
     * @param array Ҫת����byte����
     * @return ת����ĳ�������
     */
    public static long bytesToUint(byte[] array) {  
        return ((long) (array[0] & 0xff)) | ((long) (array[1] & 0xff)) << 8 | ((long) (array[2] & 0xff)) << 16  
                | ((long) (array[3] & 0xff)) << 24;  
    }  
  
    /**
     * byte�������޷�������
     * 
     * @param array Ҫת����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     * @return ת����ĳ�������
     */
    public static long bytesToUint(byte[] array, int offset) {  
        return ((long) (array[offset + 0] & 0xff)) | ((long) (array[offset + 1] & 0xff)) << 8  
                | ((long) (array[offset + 2] & 0xff)) << 16 | ((long) (array[offset + 3] & 0xff)) << 24;  
    }  
  
    /**
     * ������תbyte����
     * 
     * @param n Ҫת���Ķ�������
     * @return ת�����byte����
     */
    public static byte[] shortToBytes(short n) {  
        byte[] b = new byte[2];  
        b[0] = (byte) (n & 0xff);  
        b[1] = (byte) ((n >> 8) & 0xff);  
        return b;  
    }  
  
    /**
     * ������תbyte����
     * 
     * @param n Ҫת���Ķ�������
     * @param array ת�����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     */
    public static void shortToBytes(short n, byte[] array, int offset) {  
        array[offset + 0] = (byte) (n & 0xff);  
        array[offset + 1] = (byte) ((n >> 8) & 0xff);  
    }  
  
    /**
     * byte����ת��������
     * 
     * @param b Ҫת����byte����
     * @return ת����Ķ�������
     */
    public static short bytesToShort(byte[] b) {  
        return (short) (b[0] & 0xff | (b[1] & 0xff) << 8);  
    }  
  
    /**
     * byte����ת��������
     * 
     * @param b Ҫת����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     * @return ת����Ķ�������
     */
    public static short bytesToShort(byte[] b, int offset) {  
        return (short) (b[offset + 0] & 0xff | (b[offset + 1] & 0xff) << 8);  
    }  
  
    /**
     * �޷��Ŷ�����תbyte����
     * 
     * @param n Ҫת����������
     * @return ת�����byte����
     */
    public static byte[] ushortToBytes(int n) {  
        byte[] b = new byte[2];  
        b[0] = (byte) (n & 0xff);  
        b[1] = (byte) ((n >> 8) & 0xff);  
        return b;  
    }  
  
    /**
     * �޷��Ŷ�����תbyte����
     * 
     * @param n Ҫת����������
     * @param array ת�����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     */
    public static void ushortToBytes(int n, byte[] array, int offset) {  
        array[offset + 0] = (byte) (n & 0xff);  
        array[offset + 1] = (byte) ((n >> 8) & 0xff);  
    }  
  
    /**
     * byte����ת�޷��Ŷ�����
     * 
     * @param b Ҫת����byte����
     * @return ������
     */
    public static int bytesToUshort(byte b[]) {  
        return b[0] & 0xff | (b[1] & 0xff) << 8;  
    }  
  
    /**
     * byte�������޷��Ŷ�������
     * 
     * @param b Ҫת����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     * @return ת������޷��Ŷ�������
     */
    public static int bytesToUshort(byte b[], int offset) {  
        return b[offset + 0] & 0xff | (b[offset + 1] & 0xff) << 8;  
    }  
  
    /**
     * �޷����ֽ���תbyte����
     * 
     * @param n Ҫת����������
     * @return ת�����byte����
     */
    public static byte[] ubyteToBytes(int n) {  
        byte[] b = new byte[1];  
        b[0] = (byte) (n & 0xff);  
        return b;  
    }  
  
    /**
     * �޷����ֽ���תbyte����
     * 
     * @param n Ҫת����������
     * @param array ת�����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     */
    public static void ubyteToBytes(int n, byte[] array, int offset) {  
        array[offset] = (byte) (n & 0xff);  
    }  
  
    /**
     * byte����ת�޷����ֽ���
     * 
     * @param array ת��������
     * @return ת������޷����ֽ�������
     */
    public static int bytesToUbyte(byte[] array) {  
        return array[0] & 0xff;  
    }  
  
    /**
     * byte����ת�޷����ֽ���
     * 
     * @param array Ҫת����byte����
     * @param offset �ӵ�offsetλ��ʼת��
     * @return ת������޷����ֽ�������
     */
    public static int bytesToUbyte(byte[] array, int offset) {  
        return array[offset] & 0xff;  
    }  
    // char ���͡� float��double ���ͺ� byte[] ����֮���ת����ϵδʵ�� 
}
