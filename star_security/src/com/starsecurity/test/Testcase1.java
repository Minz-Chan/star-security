package com.starsecurity.test;

import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.ByteArrayOutputStream;
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStream;
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.Socket;  

import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_V_LoginRequest;
import com.starsecurity.model.TLV_V_PhoneInfoRequest;
import com.starsecurity.model.TLV_V_VersionInfoRequest;
import com.starsecurity.model.TLV_Version;

public class Testcase1 {
	public static void test() {
		Socket socket = null;  
        BufferedReader br = null;  
        PrintWriter pw = null;  
        try {  
            //客户端socket指定服务器的地址和端口号  
            socket = new Socket("127.0.0.1", 8080);  
            System.out.println("Socket=" + socket);  
            
            byte[] buf = null;
            OutputStream  out = socket.getOutputStream();
            out.write(buf);
       
            
            
            // deleted by minz.chan 2013.03.19 begin
            /*
            //同服务器原理一样  
            br = new BufferedReader(new InputStreamReader(  
                    socket.getInputStream()));  
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
                    socket.getOutputStream())));  
            for (int i = 0; i < 10; i++) {  
                pw.println("howdy " + i);  
                pw.flush();  
                String str = br.readLine();  
                System.out.println(str);  
            }  
            pw.println("END");  
            pw.flush();*/  
            // deleted by minz.chan 2013.03.19 end
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                System.out.println("close......");  
                br.close();  
                pw.close();  
                socket.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
		
	}
	
	public void prepareData() {
		

		/* TLV_V_VersionInfoRequest */
		TLV_V_VersionInfoRequest versionInfoRequest;
		versionInfoRequest.setVersionMinor(TLV_Version.VERSION_MINOR);
		versionInfoRequest.setVersionMajor(TLV_Version.VERSION_MAJOR);

		/* TLV_V_PhoneInfoRequest */
		TLV_V_PhoneInfoRequest phoneInfoRequest;
		
		//  001aa9bba546 
		short[] equipmentIdentity;
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
		//memcpy(phoneInfoRequest.equipmentIdentity, "001aa9bba5460000", 16);
		short[] equipmentOS;
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
		//strcpy((char *)phoneInfoRequest.equipmentOS, "android");
		//phoneInfoRequest.equipmentOS[0] = 0;
		phoneInfoRequest.setReserve1((short)0);
		phoneInfoRequest.setReserve2((short)0);
		phoneInfoRequest.setReserve3((short)0);
		phoneInfoRequest.setReserve4((short)0);

		/* TLV_V_LoginRequest */
		TLV_V_LoginRequest loginRequest;
		//char tmp[32] = {0};
		//memcpy(loginRequest.userName, &tmp, 32);
		//memcpy(loginRequest.password, &tmp, 16);
		//strcpy(loginRequest.userName, "admin");
		//strcpy(loginRequest.password, "123456");
		//memcpy(loginRequest.userName, "admin000000000000000000000000000", 32);
		//memcpy(loginRequest.password, "1234560000000000", 16);
		loginRequest.setUserName("admin".toCharArray());
		loginRequest.setPassword("123456".toCharArray());
		loginRequest.setDeviceId(1);
		loginRequest.setFlag((short)1);
		loginRequest.setChannel((short)1);
		loginRequest.setReserve(0);
		/*
		loginRequest.deviceId = 1;
		loginRequest.flag  = 1;
		loginRequest.channel = 1;
		loginRequest.reserve[0] = 0;
		loginRequest.reserve[1] = 0;*/
		
		/* Header */
		OwspPacketHeader packetHeader;
		/*
		package_len = sizeof(u_int32) + sizeof(TLV_V_VersionInfoRequest) + sizeof(TLV_V_PhoneInfoRequest)
			 + sizeof(TLV_V_LoginRequest) + 3 * sizeof(TLV_HEADER);*/
		//packetHeader.packet_length = ReverseBytes(package_len);
		//packetHeader.packet_length = htonl(package_len);
		//packetHeader.packet_seq = 1;
		packetHeader.setPacket_length(112);
		packetHeader.setPacket_seq(1);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		char *package = (char *)malloc(package_len + 4);
		int nLen = 0;

		/* Package Header */
		memcpy(package + nLen, &packetHeader, sizeof(OwspPacketHeader));
		nLen += sizeof(OwspPacketHeader);

		/* TLV_HEADER + TLV_V_VersionInfoRequest*/
		TLV_HEADER tlv1;
		tlv1.tlv_type = TLV_T_VERSION_INFO_REQUEST;
		tlv1.tlv_len = sizeof(TLV_V_VersionInfoRequest);
		memcpy(package + nLen, &tlv1, sizeof(TLV_HEADER));
		nLen += sizeof(TLV_HEADER);

		memcpy(package + nLen, &versionInfoRequest, sizeof(TLV_V_VersionInfoRequest));
		nLen += sizeof(TLV_V_VersionInfoRequest);

		/* TLV_HEADER + TLV_V_PhoneInfoRequest */
		TLV_HEADER tlv2;
		tlv2.tlv_type = TLV_T_PHONE_INFO_REQUEST;
		tlv2.tlv_len = sizeof(TLV_V_PhoneInfoRequest);
		memcpy(package + nLen, &tlv2, sizeof(TLV_HEADER));
		nLen += sizeof(TLV_HEADER);

		memcpy(package + nLen, &phoneInfoRequest, sizeof(TLV_V_PhoneInfoRequest));
		nLen += sizeof(TLV_V_PhoneInfoRequest);

		/* TLV_HEADER + TLV_V_LoginRequest */
		TLV_HEADER tlv3;
		tlv3.tlv_type = TLV_T_LOGIN_REQUEST;
		tlv3.tlv_len = sizeof(TLV_V_LoginRequest);
		memcpy(package + nLen, &tlv3, sizeof(TLV_HEADER));
		nLen += sizeof(TLV_HEADER);

		memcpy(package + nLen, &loginRequest, sizeof(TLV_V_LoginRequest));
		nLen += sizeof(TLV_V_LoginRequest);
		
		
		
	}
	
}
