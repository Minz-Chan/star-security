package com.starsecurity.component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.starsecurity.model.OWSP_LEN;
import com.starsecurity.model.OwspPacketHeader;
import com.starsecurity.model.TLV_HEADER;
import com.starsecurity.model.convert.Object2ByteArray;
import com.starsecurity.util.DataProcessUtil;

/**
 * @function     功能	  	数据包封装类
 *     提供add、remove、getPacketByteStream等方法简化复杂组组包过程
 * 组建包时无须加入TLV_HEADER，本类将自动包含。
 * @author       创建人              陈明珍
 * @date        创建日期           2013-05-06
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-06
 * @description 修改说明	        
 *   2013-05-06 首次增加  陈明珍
 */
public class Packet {
	private Queue q = new LinkedList<Object>();
	
	
	public Packet() {}

	/**
	 * 将待发送数据结构对象加入包中
	 * @param obj
	 * @return 1, 成功；0, 失败
	 */
	public int add(Object obj) {
		return (q.offer(obj) ? 1 : 0);
	}
	
	public Object remove(Object obj) {
		return q.remove(obj);
	}
	
	/**
	 * 获取数据包转换后的比特流
	 * @return 数据包对应的比特流
	 */
	public byte[] getPacketByteStream() {
		int data_len = 0;
		Iterator it = q.iterator();
		
		// calculate the length of packet
		while (it.hasNext()) {
			data_len += DataProcessUtil.getHeaderOfData(it.next()).getTlv_len()
					+ OWSP_LEN.TLV_HEADER;
		}
		
		OwspPacketHeader ows_header = new OwspPacketHeader();
		ows_header.setPacket_length(data_len + 4);
		ows_header.setPacket_seq(1);
		
		// add common packet header
		byte[] p_buf = new byte[data_len + OWSP_LEN.OwspPacketHeader];
		byte[] element = Object2ByteArray.convert2ByteArr(ows_header);
		int offset = 0;
		
		DataProcessUtil.fillPacket(p_buf, element, offset, element.length);
		offset += element.length;
		
		element = null;
		
		// put every element into packet buffer to make a complete packet
		it = q.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			TLV_HEADER tlv_header = DataProcessUtil.getHeaderOfData(obj);
			
			element = Object2ByteArray.convert2ByteArr(tlv_header);
			DataProcessUtil.fillPacket(p_buf, element, offset, element.length);
			offset += element.length;
			
			element = Object2ByteArray.convert2ByteArr(obj);
			DataProcessUtil.fillPacket(p_buf, element, offset, element.length);
			offset += element.length;
		}
		
		
		return p_buf;
	}
}
