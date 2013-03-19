package com.starsecurity.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.TLV_T_Command;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_Version;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.util.DataProcessUtil;

import junit.framework.Assert;
import android.test.AndroidTestCase;


public class StarTest extends AndroidTestCase  {
	
	Socket socket = null;
	OutputStream out = null;
    InputStream in = null ;
	
	@Override
	protected void setUp() throws Exception {
		socket = new Socket("119.86.147.67", 8080);  
        System.out.println("Socket=" + socket);  
        
		out = socket.getOutputStream();
		in = socket.getInputStream();
	}

	

	public void testSomething() throws Throwable {  
		
		 /* TLV_V_VersionInfoRequest */
		TLV_V_VersionInfoRequest versionInfoRequest = new TLV_V_VersionInfoRequest();
		versionInfoRequest.setVersionMinor(TLV_Version.VERSION_MINOR);
		versionInfoRequest.setVersionMajor(TLV_Version.VERSION_MAJOR);

		/* TLV_V_PhoneInfoRequest */
		TLV_V_PhoneInfoRequest phoneInfoRequest = new TLV_V_PhoneInfoRequest();
		
		/* 001aa9bba546 */ 
		short[] equipmentIdentity = new short[16];
		equipmentIdentity[0] = '0';
		equipmentIdentity[1] = '0';
		equipmentIdentity[2] = '1';
		equipmentIdentity[3] = 'a';
		equipmentIdentity[4] = 'a';
		equipmentIdentity[5] = '9';
		equipmentIdentity[6] = 'b';
		equipmentIdentity[7] = 'b';
		equipmentIdentity[8] = 'a';
		equipmentIdentity[9] = '5';
		equipmentIdentity[10] = '4';
		equipmentIdentity[11] = '6';
		equipmentIdentity[12] = 0;
		equipmentIdentity[13] = 0;
		equipmentIdentity[14] = 0;
		equipmentIdentity[15] = 0;
		phoneInfoRequest.setEquipmentIdentity(equipmentIdentity);

		short[] equipmentOS = new short[16];
		equipmentOS[0] = 'a';
		equipmentOS[1] = 'n';
		equipmentOS[2] = 'd';
		equipmentOS[3] = 'r';
		equipmentOS[4] = 'o';
		equipmentOS[5] = 'i';
		equipmentOS[6] = 'd';
		equipmentOS[7] = 0;
		equipmentOS[8] = 0;
		equipmentOS[9] = 0;
		equipmentOS[10] = 0;
		equipmentOS[11] = 0;
		equipmentOS[12] = 0;
		equipmentOS[13] = 0;
		equipmentOS[14] = 0;
		equipmentOS[15] = 0;		
		phoneInfoRequest.setEquipmentOS(equipmentOS);
		phoneInfoRequest.setReserve1((short)0);
		phoneInfoRequest.setReserve2((short)0);
		phoneInfoRequest.setReserve3((short)0);
		phoneInfoRequest.setReserve4((short)0);

		
		/* TLV_V_LoginRequest */
		TLV_V_LoginRequest loginRequest = new TLV_V_LoginRequest();
		loginRequest.setUserName("admin".toCharArray());
		loginRequest.setPassword("123456".toCharArray());
		loginRequest.setDeviceId(1);
		loginRequest.setFlag((short)1);
		loginRequest.setChannel((short)1);
		loginRequest.setReserve(0);
		
		/* Header */
		OwspPacketHeader packetHeader = new OwspPacketHeader();
		packetHeader.setPacket_length(112);
		packetHeader.setPacket_seq(1);

		
		byte[] buf = new byte[116];
		int offset = 0;
		
		
		/* Package Header */
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(OwspPacketHeader.class, packetHeader), offset, 
				OWSP_LEN.OwspPacketHeader);
		offset += OWSP_LEN.OwspPacketHeader;

		/* TLV_HEADER + TLV_V_VersionInfoRequest*/
		TLV_HEADER tlv1 = new TLV_HEADER();
		tlv1.setTlv_type(TLV_T_Command.TLV_T_VERSION_INFO_REQUEST);
		tlv1.setTlv_len(OWSP_LEN.TLV_V_VersionInfoRequest);
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv1), offset, OWSP_LEN.TLV_HEADER);
		offset += OWSP_LEN.TLV_HEADER;
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_VersionInfoRequest.class, versionInfoRequest), 
				offset, OWSP_LEN.TLV_V_VersionInfoRequest);
		offset += OWSP_LEN.TLV_V_VersionInfoRequest;

		/* TLV_HEADER + TLV_V_PhoneInfoRequest */
		TLV_HEADER tlv2 = new TLV_HEADER();
		tlv2.setTlv_type(TLV_T_Command.TLV_T_PHONE_INFO_REQUEST); 
		tlv2.setTlv_len(OWSP_LEN.TLV_V_PhoneInfoRequest);

		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv2), offset, OWSP_LEN.TLV_HEADER);
		offset += OWSP_LEN.TLV_HEADER;
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_PhoneInfoRequest.class, phoneInfoRequest), 
				offset, OWSP_LEN.TLV_V_PhoneInfoRequest);
		offset += OWSP_LEN.TLV_V_PhoneInfoRequest;

		/* TLV_HEADER + TLV_V_LoginRequest */
		TLV_HEADER tlv3 = new TLV_HEADER();
		tlv3.setTlv_type(TLV_T_Command.TLV_T_LOGIN_REQUEST); 
		tlv3.setTlv_len(OWSP_LEN.TLV_V_LoginRequest);
		
		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_HEADER.class, tlv3), offset, OWSP_LEN.TLV_HEADER);
		offset += OWSP_LEN.TLV_HEADER;

		DataProcessUtil.fillPacket(buf, Object2ByteArray.convert2ByteArr(TLV_V_LoginRequest.class, loginRequest), 
				offset, OWSP_LEN.TLV_V_LoginRequest);
		offset += OWSP_LEN.TLV_V_LoginRequest;
		
		
		out.write(buf);
		System.out.println("Send: " + buf);  
		
		
		byte[] readBuf = new byte[2048];
		in.read(readBuf);
		System.out.println("Recv: " + readBuf);  
		
        Assert.assertTrue(1 == 1);  
    } 
	
	@Override
	protected void tearDown() throws Exception {
		try {
		 	if( socket != null ) {
		 		socket.close();
		 	}
		} catch (IOException e) {
				e.printStackTrace();
		}    
	}

}
